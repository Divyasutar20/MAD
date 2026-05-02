package com.example.student;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etEmail;
    RadioGroup radioGroup;
    CheckBox cbSports, cbMusic;
    ToggleButton toggleHostel;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        radioGroup = findViewById(R.id.radioGroup);
        cbSports = findViewById(R.id.cbSports);
        cbMusic = findViewById(R.id.cbMusic);
        toggleHostel = findViewById(R.id.toggleHostel);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedGender = findViewById(selectedId);
                String gender = selectedGender.getText().toString();

                String hobbies = "";
                if (cbSports.isChecked())
                    hobbies += "Sports ";
                if (cbMusic.isChecked())
                    hobbies += "Music ";

                String hostel = toggleHostel.isChecked() ? "Yes" : "No";

                String message = "Name: " + name +
                        "\nEmail: " + email +
                        "\nGender: " + gender +
                        "\nHobbies: " + hobbies +
                        "\nHostel Required: " + hostel;

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}