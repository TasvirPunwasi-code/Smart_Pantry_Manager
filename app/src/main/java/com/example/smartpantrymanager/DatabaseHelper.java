
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 3;

    // Pantry table
    public static final String TABLE_INGREDIENTS = "ingredients";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry_date";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_NAME = "recipe_name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_INSTRUCTIONS = "instructions";

    // Recipe ingredients table
    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String COL_REQUIREMENT_ID = "requirement_id";
    public static final String COL_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_REQUIRED_QUANTITY = "required_quantity";
    public static final String COL_REQUIRED_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create the existing pantry table.
        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_QUANTITY + " REAL NOT NULL, " +
                        COL_UNIT + " TEXT NOT NULL, " +
                        COL_EXPIRY + " TEXT" +
                        ")";

        db.execSQL(createIngredientsTable);

        // Create the recipes table.
        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RECIPE_NAME + " TEXT NOT NULL, " +
                        COL_DESCRIPTION + " TEXT NOT NULL, " +
                        COL_INSTRUCTIONS + " TEXT NOT NULL" +
                        ")";

        db.execSQL(createRecipesTable);

        // Create the recipe requirements table.
        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        COL_REQUIREMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RECIPE_ID + " INTEGER NOT NULL, " +
                        COL_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        COL_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                        COL_REQUIRED_UNIT + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COL_RECIPE_ID + ") REFERENCES " +
                        TABLE_RECIPES + "(" + COL_RECIPE_ID + ") " +
                        "ON DELETE CASCADE" +
                        ")";

        db.execSQL(createRecipeIngredientsTable);
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        // Upgrade from version 1 to version 2.
        // Preserve the existing pantry table and its data.
        if (oldVersion < 2) {

            String createRecipesTable =
                    "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" +
                            COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COL_RECIPE_NAME + " TEXT NOT NULL, " +
                            COL_DESCRIPTION + " TEXT NOT NULL, " +
                            COL_INSTRUCTIONS + " TEXT NOT NULL" +
                            ")";

            db.execSQL(createRecipesTable);

            String createRecipeIngredientsTable =
                    "CREATE TABLE IF NOT EXISTS " +
                            TABLE_RECIPE_INGREDIENTS + " (" +
                            COL_REQUIREMENT_ID +
                            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COL_RECIPE_ID + " INTEGER NOT NULL, " +
                            COL_INGREDIENT_NAME + " TEXT NOT NULL, " +
                            COL_REQUIRED_QUANTITY + " REAL NOT NULL, " +
                            COL_REQUIRED_UNIT + " TEXT NOT NULL, " +
                            "FOREIGN KEY (" + COL_RECIPE_ID + ") REFERENCES " +
                            TABLE_RECIPES + "(" + COL_RECIPE_ID + ") " +
                            "ON DELETE CASCADE" +
                            ")";

            db.execSQL(createRecipeIngredientsTable);
        }
        if (oldVersion < 3) {
            seedRecipes(db);
        }
    }

    public boolean addIngredient(String name, double quantity,
                                 String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY, expiryDate);

        long result = db.insert(TABLE_INGREDIENTS, null, values);

        return result != -1;
    }

    public boolean updateIngredient(int id, String name, double quantity,
                                    String unit, String expiryDate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_UNIT, unit);
        values.put(COL_EXPIRY, expiryDate);

        int rowsUpdated = db.update(
                TABLE_INGREDIENTS,
                values,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return rowsUpdated > 0;
    }

    public boolean deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(
                TABLE_INGREDIENTS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        return rowsDeleted > 0;
    }

    public Cursor getAllIngredients() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC"
        );
    }
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String recipeQuery = "SELECT * FROM " + TABLE_RECIPES
                + " ORDER BY " + COL_RECIPE_ID + " ASC";

        try (Cursor recipeCursor = db.rawQuery(recipeQuery, null)) {

            while (recipeCursor.moveToNext()) {

                int recipeId = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow(COL_RECIPE_ID));

                String name = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow(COL_RECIPE_NAME));

                String description = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow(COL_DESCRIPTION));

                String instructions = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow(COL_INSTRUCTIONS));

                ArrayList<RecipeRequirement> requirements = new ArrayList<>();

                String requirementQuery =
                        "SELECT * FROM " + TABLE_RECIPE_INGREDIENTS
                                + " WHERE " + COL_RECIPE_ID + " = ?";

                try (Cursor requirementCursor = db.rawQuery(
                        requirementQuery,
                        new String[]{String.valueOf(recipeId)})) {

                    while (requirementCursor.moveToNext()) {

                        String ingredientName = requirementCursor.getString(
                                requirementCursor.getColumnIndexOrThrow(
                                        COL_INGREDIENT_NAME));

                        double quantity = requirementCursor.getDouble(
                                requirementCursor.getColumnIndexOrThrow(
                                        COL_REQUIRED_QUANTITY));

                        String unit = requirementCursor.getString(
                                requirementCursor.getColumnIndexOrThrow(
                                        COL_REQUIRED_UNIT));

                        requirements.add(new RecipeRequirement(
                                ingredientName, quantity, unit));
                    }
                }

                recipes.add(new Recipe(
                        recipeId,
                        name,
                        description,
                        requirements,
                        instructions));
            }
        }

        return recipes;
    }


    private void seedRecipes(SQLiteDatabase db) {

        // Check whether recipes have already been inserted.
        Cursor cursor = db.query(
                TABLE_RECIPES,
                new String[]{COL_RECIPE_ID},
                null,
                null,
                null,
                null,
                null,
                "1"
        );

        boolean recipesExist = cursor.moveToFirst();
        cursor.close();

        // Prevent duplicate recipes.
        if (recipesExist) {
            return;
        }

        // Use the existing recipes as the initial seed data.
        for (Recipe recipe : RecipeRepository.getRecipes()) {

            ContentValues recipeValues = new ContentValues();
            recipeValues.put(COL_RECIPE_NAME, recipe.getName());
            recipeValues.put(COL_DESCRIPTION, recipe.getDescription());
            recipeValues.put(COL_INSTRUCTIONS, recipe.getInstructions());

            long recipeId = db.insert(
                    TABLE_RECIPES,
                    null,
                    recipeValues
            );

            // Skip requirements if the recipe could not be inserted.
            if (recipeId == -1) {
                continue;
            }

            // Store every required ingredient for this recipe.
            for (RecipeRequirement requirement : recipe.getRequirements()) {

                ContentValues requirementValues = new ContentValues();
                requirementValues.put(COL_RECIPE_ID, recipeId);
                requirementValues.put(
                        COL_INGREDIENT_NAME,
                        requirement.getIngredientName()
                );
                requirementValues.put(
                        COL_REQUIRED_QUANTITY,
                        requirement.getQuantity()
                );
                requirementValues.put(
                        COL_REQUIRED_UNIT,
                        requirement.getUnit()
                );

                db.insert(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        requirementValues
                );
            }
        }
    }

}
