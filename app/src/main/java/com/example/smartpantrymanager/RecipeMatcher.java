package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeMatcher {

    public static ArrayList<Recipe> getAvailableRecipes(
            ArrayList<Recipe> recipes,
            ArrayList<Ingredient> pantry) {

        ArrayList<Recipe> availableRecipes = new ArrayList<>();

        // Check each recipe in the database.
        for (Recipe recipe : recipes) {

            // A recipe with no requirements should not be suggested.
            if (recipe.getRequirements() == null
                    || recipe.getRequirements().isEmpty()) {
                continue;
            }

            boolean canMakeRecipe = true;

            // Check every ingredient required by this recipe.
            for (RecipeRequirement requirement : recipe.getRequirements()) {

                String requiredName =
                        normalizeName(requirement.getIngredientName());

                // Ignore requirements with missing ingredient names.
                if (requiredName.isEmpty()) {
                    canMakeRecipe = false;
                    break;
                }

                double availableQuantity = 0;

                String requiredUnit =
                        normalizeUnit(requirement.getUnit());

                // Search the pantry for the required ingredient.
                for (Ingredient ingredient : pantry) {

                    if (ingredient.getName() == null
                            || ingredient.getName().trim().isEmpty()) {
                        continue;
                    }

                    String pantryName =
                            normalizeName(ingredient.getName());

                    String pantryUnit =
                            normalizeUnit(ingredient.getUnit());

                    // Ingredient names and units must match.
                    if (requiredName.equals(pantryName)
                            && requiredUnit.equals(pantryUnit)) {

                        // Convert the quantity to a common unit before
                        // adding it to the available pantry quantity.
                        availableQuantity += convertToBaseQuantity(
                                ingredient.getQuantity(),
                                ingredient.getUnit());
                    }
                }

                // Convert the recipe requirement to the same base unit.
                double requiredQuantity = convertToBaseQuantity(
                        requirement.getQuantity(),
                        requirement.getUnit());

                // If there is not enough, the entire recipe is unavailable.
                if (availableQuantity < requiredQuantity) {
                    canMakeRecipe = false;
                    break;
                }
            }

            // Only suggest recipes for which every requirement is met.
            if (canMakeRecipe) {
                availableRecipes.add(recipe);
            }
        }

        return availableRecipes;
    }

    // Standardise ingredient names so simple singular/plural differences
    // do not prevent a match.
    private static String normalizeName(String name) {

        if (name == null) {
            return "";
        }

        String normalized = name.trim().toLowerCase(Locale.ROOT);

        if (normalized.isEmpty()) {
            return "";
        }

        // Example: berries becomes berry.
        if (normalized.endsWith("ies") && normalized.length() > 3) {
            return normalized.substring(0, normalized.length() - 3) + "y";
        }

        // Example: tomatoes becomes tomato.
        if (normalized.endsWith("oes") && normalized.length() > 3) {
            return normalized.substring(0, normalized.length() - 2);
        }

        // Remove a simple plural ending, but preserve words ending in "ss".
        if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 1) {

            return normalized.substring(0, normalized.length() - 1);
        }

        return normalized;
    }

    // Standardise unit names so equivalent units can be compared.
    private static String normalizeUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            // Pieces
            case "piece":
            case "pc":
            case "pcs":
                return "pieces";

            // Mass: kilograms are converted to grams.
            case "kg":
            case "kilogram":
            case "kilograms":
            case "gram":
            case "grams":
                return "g";

            // Volume: litres are converted to millilitres.
            case "l":
            case "liter":
            case "liters":
            case "litre":
            case "litres":
            case "milliliter":
            case "milliliters":
            case "millilitre":
            case "millilitres":
                return "ml";

            // Keep other units separate.
            default:
                return normalized;
        }
    }

    // Convert supported measurements to a common base unit.
    // Mass uses grams; volume uses millilitres.
    private static double convertToBaseQuantity(
            double quantity, String unit) {

        if (unit == null) {
            return quantity;
        }

        String normalized = unit.trim().toLowerCase(Locale.ROOT);

        switch (normalized) {

            case "kg":
            case "kilogram":
            case "kilograms":
            case "l":
            case "liter":
            case "liters":
            case "litre":
            case "litres":
                return quantity * 1000;

            default:
                return quantity;
        }
    }
}
