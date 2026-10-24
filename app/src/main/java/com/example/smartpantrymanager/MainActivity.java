package com.example.smartpantrymanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listPantry;
    private TextView txtEmptyPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                });

        databaseHelper = new DatabaseHelper(this);

        listPantry = findViewById(R.id.listPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        listPantry.setEmptyView(txtEmptyPantry);

        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        Button btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        Button btnSettings =
                findViewById(R.id.btnSettings);

        btnAddIngredient.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(view -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null && listPantry != null) {
            loadIngredients();
        }
    }

    private void loadIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

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

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                double quantity = cursor.getDouble(quantityIndex);
                String unit = cursor.getString(unitIndex);
                String expiry = cursor.getString(expiryIndex);

                ingredients.add(new Ingredient(
                        id, name, quantity, unit, expiry));


                listPantry.setOnItemLongClickListener((parent, view, position, itemId) -> {
                    Ingredient ingredient = ingredients.get(position);

                    new android.app.AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete Ingredient")
                            .setMessage("Are you sure you want to delete "
                                    + ingredient.getName() + "?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                boolean deleted = databaseHelper.deleteIngredient(
                                        ingredient.getId());

                                if (deleted) {
                                    android.widget.Toast.makeText(
                                            MainActivity.this,
                                            "Ingredient deleted!",
                                            android.widget.Toast.LENGTH_SHORT
                                    ).show();

                                    loadIngredients();
                                } else {
                                    android.widget.Toast.makeText(
                                            MainActivity.this,
                                            "Could not delete ingredient.",
                                            android.widget.Toast.LENGTH_SHORT
                                    ).show();
                                }
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .show();

                    return true;
                });

            }
        }

        IngredientAdapter adapter =
                new IngredientAdapter(this, ingredients);

        listPantry.setAdapter(adapter);

        listPantry.setOnItemClickListener((parent, view, position, id) -> {
            Ingredient ingredient = ingredients.get(position);

            android.content.Intent intent = new android.content.Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class);

            intent.putExtra("ingredient_id", ingredient.getId());
            intent.putExtra("ingredient_name", ingredient.getName());
            intent.putExtra("ingredient_quantity", ingredient.getQuantity());
            intent.putExtra("ingredient_unit", ingredient.getUnit());
            intent.putExtra("ingredient_expiry", ingredient.getExpiryDate());

            startActivity(intent);
        });

    }
}
