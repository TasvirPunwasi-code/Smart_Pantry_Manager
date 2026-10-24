package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        databaseHelper = new DatabaseHelper(this);

        TextView txtTitle = findViewById(R.id.txtRecipeTitle);
        TextView txtDescription = findViewById(R.id.txtRecipeDescription);
        TextView txtIngredients = findViewById(R.id.txtRecipeIngredients);
        TextView txtInstructions = findViewById(R.id.txtRecipeInstructions);
        Button btnBack = findViewById(R.id.btnBackToSuggested);

        btnBack.setOnClickListener(v -> finish());

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        Recipe selectedRecipe = null;

        // Load recipes from SQLite instead of RecipeRepository.
        ArrayList<Recipe> recipes = databaseHelper.getAllRecipes();

        for (Recipe recipe : recipes) {
            if (recipe.getId() == recipeId) {
                selectedRecipe = recipe;
                break;
            }
        }

        if (selectedRecipe != null) {

            txtTitle.setText(selectedRecipe.getName());
            txtDescription.setText(selectedRecipe.getDescription());

            StringBuilder ingredientsBuilder = new StringBuilder();

            ArrayList<RecipeRequirement> requirements =
                    selectedRecipe.getRequirements();

            if (requirements != null) {
                for (RecipeRequirement req : requirements) {
                    ingredientsBuilder.append("• ")
                            .append(req.getQuantity())
                            .append(" ")
                            .append(req.getUnit())
                            .append(" ")
                            .append(req.getIngredientName())
                            .append("\n");
                }
            }

            txtIngredients.setText(ingredientsBuilder.toString().trim());
            txtInstructions.setText(selectedRecipe.getInstructions());

        } else {
            Toast.makeText(
                    this,
                    "Recipe not found.",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        }
    }
}
