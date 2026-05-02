package com.example.constraintlayout;

// These "imports" are vital. They tell Java where to find Android's tools.
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 1. Declare variables for your UI components
    private TextView myTitle;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. Link this Java file to your XML file (activity_main.xml)
        setContentView(R.layout.activity_main);

        // 3. Connect variables to the XML elements using their unique IDs
        myTitle = findViewById(R.id.myTitle);
        myButton = findViewById(R.id.myButton);

        // 4. Set up an "Event Listener" to detect when the user taps the button
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This code runs when the button is clicked
                myTitle.setText("Layout Logic Successful!");

                // Pop up a small message at the bottom of the screen
                Toast.makeText(MainActivity.this, "Hello from Java!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}