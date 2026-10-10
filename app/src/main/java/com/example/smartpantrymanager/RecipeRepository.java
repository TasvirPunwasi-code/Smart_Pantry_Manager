
package com.example.smartpantrymanager;
import java.util.ArrayList;

public class RecipeRepository {

    private static ArrayList<RecipeRequirement> requirements(
            RecipeRequirement... items) {
        ArrayList<RecipeRequirement> list = new ArrayList<>();

        for (RecipeRequirement item : items) {
            list.add(item);
        }

        return list;
    }

    public static ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(1, "Tomato Salad",
                "A fresh and simple tomato salad.",
                requirements(
                        new RecipeRequirement("tomato", 2, "pieces"),
                        new RecipeRequirement("onion", 1, "pieces")
                ),
                "1. Wash and chop the tomatoes.\n"
                        + "2. Chop the onion.\n"
                        + "3. Mix together and season to taste."));

        recipes.add(new Recipe(2, "Tomato Pasta",
                "Pasta with a simple tomato sauce.",
                requirements(
                        new RecipeRequirement("tomato", 2, "pieces"),
                        new RecipeRequirement("pasta", 200, "g"),
                        new RecipeRequirement("onion", 1, "pieces")
                ),
                "1. Cook the pasta.\n"
                        + "2. Chop and cook the onion and tomatoes.\n"
                        + "3. Mix the sauce with the pasta."));

        recipes.add(new Recipe(3, "Scrambled Eggs",
                "A quick egg breakfast.",
                requirements(
                        new RecipeRequirement("egg", 2, "pieces"),
                        new RecipeRequirement("milk", 50, "ml")
                ),
                "1. Beat the eggs and milk together.\n"
                        + "2. Cook in a lightly oiled pan, stirring gently.\n"
                        + "3. Cook until the eggs are set."));

        recipes.add(new Recipe(4, "Potato Mash",
                "Soft mashed potatoes.",
                requirements(
                        new RecipeRequirement("potato", 3, "pieces"),
                        new RecipeRequirement("milk", 100, "ml"),
                        new RecipeRequirement("butter", 20, "g")
                ),
                "1. Peel and boil the potatoes until soft.\n"
                        + "2. Drain and mash them.\n"
                        + "3. Mix in the milk and butter."));

        recipes.add(new Recipe(5, "Cheese Omelette",
                "An omelette with melted cheese.",
                requirements(
                        new RecipeRequirement("egg", 2, "pieces"),
                        new RecipeRequirement("cheese", 30, "g")
                ),
                "1. Beat the eggs.\n"
                        + "2. Pour them into a lightly oiled pan.\n"
                        + "3. Add cheese and fold when cooked."));

        recipes.add(new Recipe(6, "Banana Smoothie",
                "A creamy banana drink.",
                requirements(
                        new RecipeRequirement("banana", 1, "pieces"),
                        new RecipeRequirement("milk", 250, "ml")
                ),
                "1. Peel the banana.\n"
                        + "2. Blend it with the milk until smooth."));

        recipes.add(new Recipe(7, "Chicken and Rice",
                "A simple chicken and rice meal.",
                requirements(
                        new RecipeRequirement("chicken", 200, "g"),
                        new RecipeRequirement("rice", 150, "g"),
                        new RecipeRequirement("onion", 1, "pieces")
                ),
                "1. Cook the rice according to its instructions.\n"
                        + "2. Cook the chicken thoroughly.\n"
                        + "3. Cook the onion and combine everything."));

        recipes.add(new Recipe(8, "Vegetable Soup",
                "A warm vegetable soup.",
                requirements(
                        new RecipeRequirement("potato", 2, "pieces"),
                        new RecipeRequirement("carrot", 2, "pieces"),
                        new RecipeRequirement("onion", 1, "pieces")
                ),
                "1. Chop the vegetables.\n"
                        + "2. Simmer them in water or stock until tender.\n"
                        + "3. Season to taste."));

        recipes.add(new Recipe(9, "Peanut Butter Toast",
                "Toast with peanut butter.",
                requirements(
                        new RecipeRequirement("bread", 2, "slices"),
                        new RecipeRequirement("peanut butter", 30, "g")
                ),
                "1. Toast the bread.\n"
                        + "2. Spread peanut butter over the toast."));

        recipes.add(new Recipe(10, "Cheese Sandwich",
                "A quick cheese sandwich.",
                requirements(
                        new RecipeRequirement("bread", 2, "slices"),
                        new RecipeRequirement("cheese", 40, "g")
                ),
                "1. Place the cheese between the bread slices.\n"
                        + "2. Serve cold or toast until the cheese melts."));

        recipes.add(new Recipe(11, "Garlic Pasta",
                "Pasta flavoured with garlic and oil.",
                requirements(
                        new RecipeRequirement("pasta", 200, "g"),
                        new RecipeRequirement("garlic", 2, "pieces")
                ),
                "1. Cook the pasta.\n"
                        + "2. Gently cook chopped garlic in oil.\n"
                        + "3. Toss the pasta with the garlic."));

        recipes.add(new Recipe(12, "Apple and Banana Bowl",
                "A simple fresh fruit bowl.",
                requirements(
                        new RecipeRequirement("apple", 1, "pieces"),
                        new RecipeRequirement("banana", 1, "pieces")
                ),
                "1. Wash the apple and peel the banana.\n"
                        + "2. Slice both fruits and serve together."));

        recipes.add(new Recipe(13, "Tuna Sandwich",
                "A quick tuna sandwich.",
                requirements(
                        new RecipeRequirement("bread", 2, "slices"),
                        new RecipeRequirement("tuna", 100, "g")
                ),
                "1. Drain the tuna if necessary.\n"
                        + "2. Place it between two slices of bread."));

        recipes.add(new Recipe(14, "Carrot Salad",
                "A crunchy grated carrot salad.",
                requirements(
                        new RecipeRequirement("carrot", 2, "pieces"),
                        new RecipeRequirement("lemon", 1, "pieces")
                ),
                "1. Wash and grate the carrots.\n"
                        + "2. Squeeze lemon juice over them and mix."));

        recipes.add(new Recipe(15, "Tomato and Cheese Toast",
                "Toasted bread topped with tomato and cheese.",
                requirements(
                        new RecipeRequirement("bread", 2, "slices"),
                        new RecipeRequirement("tomato", 1, "pieces"),
                        new RecipeRequirement("cheese", 30, "g")
                ),
                "1. Slice the tomato and grate the cheese.\n"
                        + "2. Add them to the bread.\n"
                        + "3. Toast until the bread is crisp and the cheese melts."));

        recipes.add(new Recipe(16, "Chicken Sandwich",
                "A chicken-filled sandwich.",
                requirements(
                        new RecipeRequirement("bread", 2, "slices"),
                        new RecipeRequirement("chicken", 100, "g")
                ),
                "1. Cook the chicken thoroughly and slice it.\n"
                        + "2. Place it between two slices of bread."));

        recipes.add(new Recipe(17, "Fried Rice",
                "Rice cooked with egg and vegetables.",
                requirements(
                        new RecipeRequirement("rice", 200, "g"),
                        new RecipeRequirement("egg", 1, "pieces"),
                        new RecipeRequirement("carrot", 1, "pieces")
                ),
                "1. Cook the rice and allow it to cool slightly.\n"
                        + "2. Cook the egg and chopped carrot.\n"
                        + "3. Add the rice and stir-fry until hot."));

        recipes.add(new Recipe(18, "Potato and Cheese Bake",
                "A simple baked potato and cheese dish.",
                requirements(
                        new RecipeRequirement("potato", 3, "pieces"),
                        new RecipeRequirement("cheese", 50, "g"),
                        new RecipeRequirement("milk", 100, "ml")
                ),
                "1. Slice and partially boil the potatoes.\n"
                        + "2. Arrange them in an oven-safe dish.\n"
                        + "3. Add milk and cheese, then bake until tender and golden."));

        recipes.add(new Recipe(19, "Apple Smoothie",
                "A fruit smoothie with milk.",
                requirements(
                        new RecipeRequirement("apple", 1, "pieces"),
                        new RecipeRequirement("milk", 200, "ml")
                ),
                "1. Wash and chop the apple.\n"
                        + "2. Blend with milk until smooth."));

        recipes.add(new Recipe(20, "Tomato and Onion Sauce",
                "A simple sauce for pasta or other meals.",
                requirements(
                        new RecipeRequirement("tomato", 3, "pieces"),
                        new RecipeRequirement("onion", 1, "pieces"),
                        new RecipeRequirement("garlic", 1, "pieces")
                ),
                "1. Chop the tomato, onion and garlic.\n"
                        + "2. Cook the onion and garlic in a little oil.\n"
                        + "3. Add the tomatoes and simmer until softened."));

        recipes.add(new Recipe(21, "Garlic Butter Potatoes",
                "Crispy, flavourful potatoes with garlic and butter.",
                requirements(
                        new RecipeRequirement("potato", 3, "pieces"),
                        new RecipeRequirement("garlic", 2, "pieces"),
                        new RecipeRequirement("butter", 30, "g")
                ),
                "1. Wash and chop the potatoes into small pieces.\n"
                        + "2. Boil the potatoes until tender, then drain.\n"
                        + "3. Melt the butter in a pan and add the chopped garlic.\n"
                        + "4. Add the potatoes and cook until lightly golden.\n"
                        + "5. Serve warm."));

        return recipes;
    }
}
