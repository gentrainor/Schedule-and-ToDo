package com.example.schedularappv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassInfoAdapter adapter;
    private List<ClassInfo> classInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRecyclerView();
        loadClassInfo();
        setupAddButton();
        setupToDoListButton();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classInfoList = new ArrayList<>();
        adapter = new ClassInfoAdapter(classInfoList, this::editClass);
        recyclerView.setAdapter(adapter);
    }

    private void setupAddButton() {
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> openAddClass(-1));
    }

    private void setupToDoListButton() {
        Button toDoListButton = findViewById(R.id.toDoListPageButton);
        toDoListButton.setOnClickListener(v -> openToDoList()); // Handle click event for ToDo List button
    }

    private void openAddClass(int index) {
        Intent intent = new Intent(MainActivity.this, AddClass.class);
        if (index >= 0) {
            ClassInfo classInfo = classInfoList.get(index);
            intent.putExtra("isEditMode", true);
            intent.putExtra("classIndex", index);
            intent.putExtra("ClassName", classInfo.getClassName());
            intent.putExtra("ClassDays", classInfo.getDaysOfWeek());
            intent.putExtra("StartTime", classInfo.getStartTime());
            intent.putExtra("EndTime", classInfo.getEndTime());
            intent.putExtra("Location", classInfo.getLocation());
            intent.putExtra("InstructorName", classInfo.getInstructorName());
        }
        startActivityForResult(intent, 1);
    }

    private void editClass(int index) {
        openAddClass(index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            handleActivityResult(data);
        }
    }

    private void handleActivityResult(Intent data) {
        boolean isEditMode = data.getBooleanExtra("isEditMode", false);
        int classIndex = data.getIntExtra("classIndex", -1);

        if (data.getBooleanExtra("deleteClass", false) && classIndex != -1) {
            classInfoList.remove(classIndex);
        } else {
            ClassInfo classInfo = new ClassInfo(
                    data.getStringExtra("ClassName"),
                    data.getStringExtra("ClassDays"),
                    data.getStringExtra("StartTime"),
                    data.getStringExtra("EndTime"),
                    data.getStringExtra("Location"),
                    data.getStringExtra("InstructorName")
            );

            if (isEditMode && classIndex != -1) {
                classInfoList.set(classIndex, classInfo);
            } else {
                classInfoList.add(classInfo);
            }
        }

        adapter.notifyDataSetChanged();
        saveClassInfo();
    }

    private void saveClassInfo() {
        SharedPreferences prefs = getSharedPreferences("ClassPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(classInfoList);
        editor.putString("ClassInfoList", json);
        editor.apply();
    }

    private void loadClassInfo() {
        SharedPreferences prefs = getSharedPreferences("ClassPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("ClassInfoList", null);
        Type type = new TypeToken<ArrayList<ClassInfo>>() {}.getType();
        classInfoList = gson.fromJson(json, type);

        if (classInfoList == null) {
            classInfoList = new ArrayList<>();
        }
        adapter.setClassInfoList(classInfoList);
    }

    private void openToDoList() {
        Intent intent = new Intent(MainActivity.this, ToDoListActivity.class);
        startActivity(intent);
    }
}
