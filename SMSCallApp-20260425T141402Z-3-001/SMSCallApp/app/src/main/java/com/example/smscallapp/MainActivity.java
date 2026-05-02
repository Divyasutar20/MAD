package com.example.smscallapp;   // MUST match your manifest

import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    EditText etPhone, etMessage;
    Button btnSMS, btnCall, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = findViewById(R.id.etPhone);
        etMessage = findViewById(R.id.etMessage);
        btnSMS = findViewById(R.id.btnSMS);
        btnCall = findViewById(R.id.btnCall);
        btnSettings = findViewById(R.id.btnSettings);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS}, 1);

        btnSMS.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            String msg = etMessage.getText().toString();
            SmsManager.getDefault().sendTextMessage(phone, null, msg, null, null);
        });

        btnCall.setOnClickListener(v -> {
            String phone = etPhone.getText().toString();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
        });
    }
}
