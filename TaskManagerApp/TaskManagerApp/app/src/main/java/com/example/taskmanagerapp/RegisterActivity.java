package com.example.taskmanagerapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    Button registerBtn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        registerBtn = findViewById(R.id.registerBtn);

        sp = getSharedPreferences("UserData", MODE_PRIVATE);

        registerBtn.setOnClickListener(v -> {
            String e = email.getText().toString();
            String p = password.getText().toString();

            if(!e.isEmpty() && !p.isEmpty()) {
                sp.edit().putString("email", e).putString("password", p).apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}