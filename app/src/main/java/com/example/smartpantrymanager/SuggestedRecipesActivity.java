
package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listRecipes;
    private TextView txtRecipeCount;
    private TextView txtNoRecipes;

    private ArrayList<Recipe> availableRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        databaseHelper = new DatabaseHelper(this);

        listRecipes = findViewById(R.id.listRecipes);
        txtRecipeCount = findViewById(R.id.txtRecipeCount);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        Button btnBack = findViewById(R.id.btnBackToPantry);
        btnBack.setOnClickListener(view -> finish());

        listRecipes.setOnItemClickListener(
                (parent, view, position, itemId) -> {
                    Recipe recipe = availableRecipes.get(position);

                    Intent intent = new Intent(
                            SuggestedRecipesActivity.this,
                            RecipeDetailsActivity.class);

                    intent.putExtra("recipe_id", recipe.getId());
                    startActivity(intent);
                });

        loadAvailableRecipes();
    }

    private void loadAvailableRecipes() {
        ArrayList<Ingredient> pantry = new ArrayList<>();

        try (Cursor cursor = databaseHelper.getAllIngredients()) {
            int idIndex = cursor.getColumnIndexOrThrow(
                    DatabaseHelper.COL_ID);
            int nameIndex = cursor.getColumnIndexOrThrow(
                    DatabaseHelper.COL_NAME);
            int quantityIndex = cursor.getColumnIndexOrThrow(
                    DatabaseHelper.COL_QUANTITY);
            int unitIndex = cursor.getColumnIndexOrThrow(
                    DatabaseHelper.COL_UNIT);
            int expiryIndex = cursor.getColumnIndexOrThrow(
                    DatabaseHelper.COL_EXPIRY);

            while (cursor.moveToNext()) {
                pantry.add(new Ingredient(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getDouble(quantityIndex),
                        cursor.getString(unitIndex),
                        cursor.getString(expiryIndex)));
            }
        }

        ArrayList<Recipe> allRecipes = databaseHelper.getAllRecipes();

        availableRecipes = RecipeMatcher.getAvailableRecipes(
                allRecipes, pantry);

        RecipeAdapter adapter = new RecipeAdapter(
                this, availableRecipes);

        listRecipes.setAdapter(adapter);

        int count = availableRecipes.size();

        txtRecipeCount.setText(
                count + (count == 1
                        ? " recipe you can make"
                        : " recipes you can make"));

        txtNoRecipes.setVisibility(
                count == 0 ? android.view.View.VISIBLE
                        : android.view.View.GONE);

        listRecipes.setVisibility(
                count == 0 ? android.view.View.GONE
                        : android.view.View.VISIBLE);
    }
}
