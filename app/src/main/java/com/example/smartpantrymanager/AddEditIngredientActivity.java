package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText edtName, edtQuantity, edtUnit, edtExpiry;
    private DatabaseHelper databaseHelper;

    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        databaseHelper = new DatabaseHelper(this);

        edtName = findViewById(R.id.edtIngredientName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtExpiry = findViewById(R.id.edtExpiryDate);

        Button btnSave = findViewById(R.id.btnSaveIngredient);

        // Check whether an existing ingredient was sent to this screen.
        if (getIntent().hasExtra("ingredient_id")) {
            ingredientId = getIntent().getIntExtra("ingredient_id", -1);

            edtName.setText(getIntent().getStringExtra("ingredient_name"));

            double quantity = getIntent().getDoubleExtra(
                    "ingredient_quantity", 0);

            edtQuantity.setText(String.valueOf(quantity));

            edtUnit.setText(getIntent().getStringExtra("ingredient_unit"));

            String expiry = getIntent().getStringExtra("ingredient_expiry");
            edtExpiry.setText(expiry == null ? "" : expiry);

            btnSave.setText("Update Ingredient");
        }

        btnSave.setOnClickListener(view -> saveIngredient());
    }

    private void saveIngredient() {
        String name = edtName.getText().toString().trim();
        String quantityText = edtQuantity.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String expiry = edtExpiry.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this,
                    "Please enter a name, quantity and unit.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Please enter a valid quantity.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Double.isFinite(quantity) || quantity <= 0) {
            Toast.makeText(this,
                    "Quantity must be greater than zero.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        boolean saved;

        if (ingredientId == -1) {
            // Add a new ingredient.
            saved = databaseHelper.addIngredient(
                    name, quantity, unit,
                    expiry.isEmpty() ? null : expiry);
        } else {
            // Update the existing ingredient.
            saved = databaseHelper.updateIngredient(
                    ingredientId, name, quantity, unit,
                    expiry.isEmpty() ? null : expiry);
        }

        if (saved) {
            Toast.makeText(this,
                    ingredientId == -1
                            ? "Ingredient saved!"
                            : "Ingredient updated!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,
                    "Could not save the ingredient.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
