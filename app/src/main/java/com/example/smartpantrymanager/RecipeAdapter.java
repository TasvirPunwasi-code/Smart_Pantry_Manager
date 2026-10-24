package com.example.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_recipe, parent, false);
        }

        Recipe recipe = getItem(position);

        TextView txtName = convertView.findViewById(R.id.txtRecipeName);
        TextView txtDescription =
                convertView.findViewById(R.id.txtRecipeDescription);

        if (recipe != null) {
            txtName.setText(recipe.getName());
            txtDescription.setText(recipe.getDescription());
        }

        return convertView;
    }
}
