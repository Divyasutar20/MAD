package com.example.tablelayoutdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtSubject, edtMarks;
    Button btnAdd;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        edtName = findViewById(R.id.edtName);
        edtSubject = findViewById(R.id.edtSubject);
        edtMarks = findViewById(R.id.edtMarks);
        btnAdd = findViewById(R.id.btnAdd);
        tableLayout = findViewById(R.id.tableLayout);

        // Button click to add row to TableLayout
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get values from EditTexts
                String name = edtName.getText().toString().trim();
                String subject = edtSubject.getText().toString().trim();
                String marks = edtMarks.getText().toString().trim();

                // Check if fields are not empty
                if (!name.isEmpty() && !subject.isEmpty() && !marks.isEmpty()) {

                    // Create a new TableRow
                    TableRow row = new TableRow(MainActivity.this);

                    // Create TextViews for each column
                    TextView tvName = new TextView(MainActivity.this);
                    tvName.setText(name);
                    tvName.setPadding(8,8,8,8);

                    TextView tvSubject = new TextView(MainActivity.this);
                    tvSubject.setText(subject);
                    tvSubject.setPadding(8,8,8,8);

                    TextView tvMarks = new TextView(MainActivity.this);
                    tvMarks.setText(marks);
                    tvMarks.setPadding(8,8,8,8);

                    // Add TextViews to the row
                    row.addView(tvName);
                    row.addView(tvSubject);
                    row.addView(tvMarks);

                    // Add the row to TableLayout
                    tableLayout.addView(row);

                    // Clear EditTexts for next input
                    edtName.setText("");
                    edtSubject.setText("");
                    edtMarks.setText("");
                }
            }
        });
    }
}
