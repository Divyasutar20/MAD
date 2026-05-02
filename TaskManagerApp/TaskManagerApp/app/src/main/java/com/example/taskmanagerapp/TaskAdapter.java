package com.example.taskmanagerapp;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    Context context;
    ArrayList<Task> list;
    DatabaseHelper db;

    public TaskAdapter(Context context, ArrayList<Task> list, DatabaseHelper db) {
        this.context = context;
        this.list = list;
        this.db = db;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int i) {
        return list.get(i);
    }

    public long getItemId(int i) {
        return list.get(i).id;
    }

    public View getView(int i, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        view.setLongClickable(true);

        CheckBox cb = view.findViewById(R.id.checkBox);
        TextView tv = view.findViewById(R.id.taskText);

        Task t = list.get(i);

        tv.setText(t.title);
        cb.setChecked(t.status == 1);

        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            db.updateStatus(t.id, isChecked ? 1 : 0);
        });

        return view;
    }
}