package com.example.differentlayoutsdemo;

// Required Imports
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare variables for our UI elements
    private TextView myTitle;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connects this Java code to activity_main.xml
        setContentView(R.layout.activity_main);

        // Link the variables to the IDs defined in XML
        myTitle = findViewById(R.id.myTitle);
        myButton = findViewById(R.id.myButton);

        // Add logic to the button
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTitle.setText("Layout Working!");
                Toast.makeText(MainActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}