package com.example.framelayoutexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Local variables instead of class fields
        TextView overlayText = findViewById(R.id.overlayText);
        Button buttonStart = findViewById(R.id.buttonStart);

        // Lambda for click listener
        buttonStart.setOnClickListener(v -> {
            overlayText.setText(getString(R.string.button_clicked_text));
            Toast.makeText(MainActivity.this, getString(R.string.button_pressed_toast), Toast.LENGTH_SHORT).show();
        });
    }
}
