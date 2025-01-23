package com.example.schedularappv3;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;

import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        EditText taskNameEditText = findViewById(R.id.taskNameEditText);
        EditText dueDateEditText = findViewById(R.id.dueDateEditText);
        EditText taskCourseSectionEditText = findViewById(R.id.taskCourseSectionEditText);
        Button createTaskButton = findViewById(R.id.createTaskButton);

        boolean isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            String taskName = getIntent().getStringExtra("taskName");
            String dueDate = getIntent().getStringExtra("dueDate");
            String courseSection = getIntent().getStringExtra("taskCourseSection");

            taskNameEditText.setText(taskName);
            dueDateEditText.setText(dueDate);
            taskCourseSectionEditText.setText(courseSection);

            createTaskButton.setText(getString(R.string.save_changes)); // Adjust to your 'Save' string
        }


        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve task details from input fields
                String taskName = taskNameEditText.getText().toString();
                String dueDate = dueDateEditText.getText().toString();
                String taskCourseSection = taskCourseSectionEditText.getText().toString();

                if (taskName.isEmpty() || dueDate.isEmpty() || taskCourseSection.isEmpty()) {
                    showAlertDialog();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("taskName", taskName);
                    resultIntent.putExtra("dueDate", dueDate);
                    resultIntent.putExtra("taskCourseSection", taskCourseSection);
                    resultIntent.putExtra("taskType", "To Do");

                    setResult(RESULT_OK, resultIntent);

                    finish();
                }
            }
        });
        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();  // Show the DatePickerDialog when dueDateEditText is clicked
            }
        });
    }
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Ensure the month and day have leading zeros if necessary
                        String formattedMonth = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                        String formattedDay = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String selectedDate = formattedDay + "/" + formattedMonth + "/" + year;
                        EditText dueDateEditText = findViewById(R.id.dueDateEditText);
                        dueDateEditText.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Incomplete Information")
                .setMessage("Please fill in all required fields to create the assignment.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}



