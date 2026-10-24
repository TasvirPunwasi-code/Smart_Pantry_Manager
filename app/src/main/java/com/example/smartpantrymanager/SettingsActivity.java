package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText edtProfileName;
    private Spinner spinnerUnit;
    private SharedPreferences preferences;

    private final String[] units = {
            "Metric (g, kg, ml, L)",
            "Imperial (oz, lb, fl oz)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        edtProfileName = findViewById(R.id.edtProfileName);
        spinnerUnit = findViewById(R.id.spinnerUnit);

        Button btnSave = findViewById(R.id.btnSaveSettings);
        Button btnBack = findViewById(R.id.btnBackSettings);

        preferences = getSharedPreferences(
                "SmartPantrySettings", MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units);

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinnerUnit.setAdapter(adapter);

        loadSettings();

        btnSave.setOnClickListener(buttonView -> saveSettings());

        btnBack.setOnClickListener(buttonView -> finish());
    }

    private void loadSettings() {
        String savedName = preferences.getString("profile_name", "");
        String savedUnit = preferences.getString(
                "measurement_unit", units[0]);

        edtProfileName.setText(savedName);

        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(savedUnit)) {
                spinnerUnit.setSelection(i);
                break;
            }
        }
    }

    private void saveSettings() {
        String name = edtProfileName.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem().toString();

        if (name.isEmpty()) {
            edtProfileName.setError("Please enter your name");
            edtProfileName.requestFocus();
            return;
        }

        boolean saved = preferences.edit()
                .putString("profile_name", name)
                .putString("measurement_unit", unit)
                .commit();

        if (saved) {
            Toast.makeText(this,
                    "Settings saved!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Could not save settings.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
