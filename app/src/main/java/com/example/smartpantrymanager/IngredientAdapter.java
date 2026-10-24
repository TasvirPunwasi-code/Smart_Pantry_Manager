package com.example.smartpantrymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    public IngredientAdapter(@NonNull Context context, @NonNull List<Ingredient> ingredients) {
        super(context, 0, ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = (convertView != null) ? convertView : LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient, parent, false);

        Ingredient ingredient = getItem(position);

        if (ingredient != null) {
            TextView txtName = itemView.findViewById(R.id.txtIngredientName);
            TextView txtQuantity = itemView.findViewById(R.id.txtIngredientQuantity);

            txtName.setText(ingredient.getName());

            StringBuilder quantityBuilder = new StringBuilder("Quantity: ");
            if (ingredient.getQuantity() == (long) ingredient.getQuantity()) {
                quantityBuilder.append(String.format(Locale.US, "%d %s", (long) ingredient.getQuantity(), ingredient.getUnit()));
            } else {
                quantityBuilder.append(String.format(Locale.US, "%s %s", ingredient.getQuantity(), ingredient.getUnit()));
            }

            if (ingredient.getExpiryDate() != null && !ingredient.getExpiryDate().isEmpty()) {
                quantityBuilder.append(" (Exp: ").append(ingredient.getExpiryDate()).append(")");
            }

            txtQuantity.setText(quantityBuilder.toString());
        }

        return itemView;
    }
}
