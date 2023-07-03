package com.example.demodatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etDesc;
    EditText etDate;
    Button btnInsert;
    ToggleButton btnGetTasks;
    TextView tvResults;
    ListView lvtasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etDesc = findViewById(R.id.etDesc);
        etDate = findViewById(R.id.etDate);
        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        lvtasks = findViewById(R.id.lv);



        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = etDesc.getText().toString();
                String date = etDate.getText().toString();

                DBHelper db = new DBHelper(MainActivity.this);
                db.insertTask(desc, date);

                ArrayList<Task> tasks = db.getTasks();
                ArrayAdapter<Task> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tasks);
                lvtasks.setAdapter(adapter);
                adapter.notifyDataSetChanged();

//                StringBuilder sb = new StringBuilder();
//                for (Task task : tasks) {
//                    sb.append(task.getDescription()).append(" - ").append(task.getDate()).append("\n");
//                }
//                tvResults.setText(sb.toString());
               tvResults.setText(lvtasks.toString());
            }
        });
        btnGetTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DBHelper db = new DBHelper(MainActivity.this);
                ArrayList<Task> tasks;
                if (isChecked) {
                    tasks = db.getTasksAscending();
                } else {
                    tasks = db.getTasksDescending();
                }
                db.close();

                ArrayAdapter<Task> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tasks);
                lvtasks.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                StringBuilder sb = new StringBuilder();
                for (Task task : tasks) {
                    sb.append(task.getDescription()).append(" - ").append(task.getDate()).append("\n");
                }
                tvResults.setText(sb.toString());
            }
        });


    }
}
