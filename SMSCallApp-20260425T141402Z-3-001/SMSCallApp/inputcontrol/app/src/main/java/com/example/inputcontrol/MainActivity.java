package com.example.inputcontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    CheckBox checkBox;
    RadioGroup radioGroup;
    ToggleButton toggleButton;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.imageButton);
        checkBox = findViewById(R.id.checkBox);
        radioGroup = findViewById(R.id.radioGroup);
        toggleButton = findViewById(R.id.toggleButton);
        spinner = findViewById(R.id.spinner);

        // Spinner Data
        String[] courses = {"Android", "Java", "Python", "Kotlin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                courses
        );
        spinner.setAdapter(adapter);

        // ImageButton Click
        imageButton.setOnClickListener(view ->
                Toast.makeText(MainActivity.this,
                        "Image Button Clicked",
                        Toast.LENGTH_SHORT).show());

        // CheckBox Click
        checkBox.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                Toast.makeText(MainActivity.this,
                        "CheckBox Selected",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "CheckBox Unselected",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // RadioGroup Selection
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            Toast.makeText(MainActivity.this,
                    "Selected: " + radioButton.getText(),
                    Toast.LENGTH_SHORT).show();
        });

        // ToggleButton Click
        toggleButton.setOnClickListener(view -> {
            if (toggleButton.isChecked()) {
                Toast.makeText(MainActivity.this,
                        "Toggle ON",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Toggle OFF",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Spinner Selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Selected: " + courses[position],
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}