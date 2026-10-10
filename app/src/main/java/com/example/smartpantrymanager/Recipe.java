
package com.example.smartpantrymanager;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private ArrayList<RecipeRequirement> requirements;
    private String instructions;

    public Recipe(int id, String name, String description,
                  ArrayList<RecipeRequirement> requirements,
                  String instructions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<RecipeRequirement> getRequirements() {
        return requirements;
    }

    public String getInstructions() {
        return instructions;
    }
}
