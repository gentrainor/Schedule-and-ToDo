package com.example.schedularappv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


public class ToDoListActivity extends AppCompatActivity implements TaskAdapter.TaskAdapterListener, FilterDialogFragment.OnFilterSelectedListener {
    private TaskAdapter taskAdapter;
    private ArrayList<Task> allTasks;
    private ArrayList<Task> filteredTasks;
    private static final int CREATE_ASSIGNMENT_REQUEST = 1;
    private static final int CREATE_EXAM_REQUEST = 2;
    private static final int CREATE_TASK_REQUEST = 3;
    private static final int EDIT_TASK_REQUEST = 4;

    private String selectedFilter = "All";
    private RecyclerView taskRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistmain);

        taskRecyclerView = findViewById(R.id.taskRecyclerView); // Initialize RecyclerView

        // Initialize the lists
        allTasks = new ArrayList<>();
        filteredTasks = new ArrayList<>();

        // Set up RecyclerView and adapter
        taskAdapter = new TaskAdapter(new ArrayList<>(), this);
        taskRecyclerView.setAdapter(taskAdapter);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up button click listeners
        Button addAssignmentButton = findViewById(R.id.addAssignmentButton);
        Button addExamButton = findViewById(R.id.addExamButton);
        Button addToDoButton = findViewById(R.id.addToDoButton);
        Button filterButton = findViewById(R.id.filterButton);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        addAssignmentButton.setOnClickListener(v -> startActivityForResult(new Intent(ToDoListActivity.this, CreateAssignmentActivity.class), CREATE_ASSIGNMENT_REQUEST));
        addExamButton.setOnClickListener(v -> startActivityForResult(new Intent(ToDoListActivity.this, CreateExamActivity.class), CREATE_EXAM_REQUEST));
        addToDoButton.setOnClickListener(v -> startActivityForResult(new Intent(ToDoListActivity.this, CreateTaskActivity.class), CREATE_TASK_REQUEST));
        filterButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FilterDialogFragment filterDialogFragment = new FilterDialogFragment(ToDoListActivity.this);
            filterDialogFragment.show(fragmentManager, "filter_dialog");
        });

        if (savedInstanceState != null) {
            allTasks = (ArrayList<Task>) savedInstanceState.getSerializable("allTasks");
            selectedFilter = savedInstanceState.getString("selectedFilter");
        } else {
            loadTasksFromSharedPreferences();
        }
    }
    public void onTaskDeleted(Task task) {
        allTasks.remove(task); // Remove the task from the main list
        saveTasksToSharedPreferences(); // Save the updated list to SharedPreferences
        filterTasks(selectedFilter); // Refresh the filtered list and adapter
    }


    @Override
    public void onFilterSelected(String selectedFilter) {
        if ("Sort by Date ASC".equals(selectedFilter) || "Sort by Date DESC".equals(selectedFilter)) {
            applySorting(selectedFilter);
        } else {
            this.selectedFilter = selectedFilter;
            filterTasks(selectedFilter);
        }
    }

    @Override
    public void onClearFilters() {
        clearFilters();
    }

    private void clearFilters() {
        selectedFilter = "All";
        filterTasks(selectedFilter); // Apply the "All" filter
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String taskName = data.getStringExtra("taskName");
            String dueDate = data.getStringExtra("dueDate");
            String taskCourseSection = data.getStringExtra("taskCourseSection");
            String taskType = data.getStringExtra("taskType");

            Task newTask = new Task(taskName, dueDate, taskCourseSection, taskType);
            if (!allTasks.contains(newTask)) {
                allTasks.add(newTask);
                saveTasksToSharedPreferences();
                filterTasks(selectedFilter); // This will update filteredTasks and the adapter
            }

        }
    }
    private void filterTasks(String selectedFilter) {
        filteredTasks.clear();
        for (Task task : allTasks) {
            if ("Completed".equals(selectedFilter) && task.isCompleted()) {
                filteredTasks.add(task);
            } else if ("All".equals(selectedFilter) || task.getType().equals(selectedFilter)) {
                filteredTasks.add(task);
            }
        }
        taskAdapter.setTasks(filteredTasks);
    }


    private void applySorting(String sortOrder) {
        sortTasksByDueDate(sortOrder);
    }

    private void sortTasksByDueDate(String sortOrder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Collections.sort(filteredTasks, (task1, task2) -> {
                    try {
                        Date date1 = dateFormat.parse(task1.getDueDate());
                        Date date2 = dateFormat.parse(task2.getDueDate());
                        return date1.compareTo(date2);
                    } catch (ParseException e) {
                        throw new IllegalArgumentException("Unable to parse the data", e);
                    }
                });

        taskAdapter.setTasks(filteredTasks);
    }
    @Override
    public void onTaskEdit(Task task, int position) {
        Intent intent;
        switch (task.getType()) {
            case "Assignment":
                intent = new Intent(this, CreateAssignmentActivity.class);
                break;
            case "Exam":
                intent = new Intent(this, CreateExamActivity.class);
                break;
            case "To Do":
            default:
                intent = new Intent(this, CreateTaskActivity.class);
                break;
        }

        intent.putExtra("isEditMode", true);
        intent.putExtra("taskName", task.getName());
        intent.putExtra("dueDate", task.getDueDate());
        intent.putExtra("taskCourseSection", task.getCourseSection());
        intent.putExtra("editTaskPosition", position);

        startActivityForResult(intent, EDIT_TASK_REQUEST);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("allTasks", allTasks);
        outState.putString("selectedFilter", selectedFilter);
    }
    public void saveTasksToSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("ToDoPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allTasks); // Convert your tasks list to JSON
        editor.putString("AllTasks", json);
        editor.apply();

        Log.d("EditTask", "Tasks saved to SharedPreferences");
    }

    private void loadTasksFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("ToDoPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("AllTasks", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> loadedTasks = gson.fromJson(json, type); // Load tasks from SharedPreferences

        if (loadedTasks == null) {
            loadedTasks = new ArrayList<>();
        }
        allTasks.clear();
        allTasks.addAll(loadedTasks);

        filterTasks(selectedFilter);
    }

}