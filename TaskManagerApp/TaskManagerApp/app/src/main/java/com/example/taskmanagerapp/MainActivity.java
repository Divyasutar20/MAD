package com.example.taskmanagerapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import android.app.*;
import java.util.Calendar;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    EditText taskInput;
    Button addBtn, logoutBtn;
    ListView listView;

    ArrayList<Task> list;
    TaskAdapter adapter;

    DatabaseHelper db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        taskInput = findViewById(R.id.taskInput);
        addBtn = findViewById(R.id.addBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        listView = findViewById(R.id.listView);

        db = new DatabaseHelper(this);
        list = new ArrayList<>();

        loadTasks();

        adapter = new TaskAdapter(this, list, db);
        listView.setAdapter(adapter);

        listView.setLayoutAnimation(
                android.view.animation.AnimationUtils.loadLayoutAnimation(this, R.anim.fade_in)
        );

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task t = list.get(position);
            db.deleteTaskById(t.id);
            loadTasks();
            adapter.notifyDataSetChanged();
            listView.startLayoutAnimation();
            Toast.makeText(this, "Deleted: " + t.title, Toast.LENGTH_SHORT).show();
            return true;
        });

        addBtn.setOnClickListener(v -> {
            addBtn.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                    .withEndAction(() -> addBtn.animate().scaleX(1f).scaleY(1f));
            String task = taskInput.getText().toString().trim();

            if (!task.isEmpty()) {

                Calendar calendar = Calendar.getInstance();

                TimePickerDialog timePicker = new TimePickerDialog(this,
                        (view, hourOfDay, minute) -> {

                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            calendar.set(Calendar.SECOND, 0);

                            db.addTask(task);
                            taskInput.setText("");
                            loadTasks();
                            adapter.notifyDataSetChanged();
                            listView.startLayoutAnimation();

                            setReminder(task, calendar.getTimeInMillis());

                            Toast.makeText(this, "Reminder Set", Toast.LENGTH_SHORT).show();

                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false);

                timePicker.show();

            } else {
                Toast.makeText(this, "Enter Task", Toast.LENGTH_SHORT).show();
            }
        });

        sp = getSharedPreferences("UserData", MODE_PRIVATE);

        logoutBtn.setOnClickListener(v -> {
            logoutBtn.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                    .withEndAction(() -> logoutBtn.animate().scaleX(1f).scaleY(1f));
            sp.edit().putBoolean("isLoggedIn", false).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    void loadTasks() {
        list.clear();
        Cursor c = db.getTasks();

        if (c.moveToFirst()) {
            do {
                list.add(new Task(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2)
                ));
            } while (c.moveToNext());
        }
        c.close();
    }

    void showNotification(String task) {
        String channelId = "task_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Task Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Task Added")
                .setContentText(task)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        NotificationManagerCompat.from(this)
                .notify((int) System.currentTimeMillis(), builder.build());
    }
    void setReminder(String task, long time) {

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("task", task);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                } else {
                    // fallback if permission not granted
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

                    Toast.makeText(this,
                            "Exact alarm not allowed. Using normal alarm.",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Permission required for alarm", Toast.LENGTH_LONG).show();
        }
    }
}