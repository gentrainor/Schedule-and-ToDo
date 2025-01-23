package com.example.schedularappv3;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;

import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.app.AlertDialog;


public class CreateAssignmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);

        EditText assignmentNameEditText = findViewById(R.id.assignmentNameEditText);
        EditText dueDateEditText = findViewById(R.id.dueDateEditText);
        EditText courseSectionEditText = findViewById(R.id.courseSectionEditText);
        Button createAssignmentButton = findViewById(R.id.createAssignmentButton);

        boolean isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            // Pre-fill form fields with existing data
            String assignmentName = getIntent().getStringExtra("taskName");
            String dueDate = getIntent().getStringExtra("dueDate");
            String courseSection = getIntent().getStringExtra("taskCourseSection");

            assignmentNameEditText.setText(assignmentName);
            dueDateEditText.setText(dueDate);
            courseSectionEditText.setText(courseSection);

            createAssignmentButton.setText(getString(R.string.save_changes)); // Set to your 'Save' text
        }

        createAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve assignment details from input fields
                String assignmentName = assignmentNameEditText.getText().toString();
                String dueDate = dueDateEditText.getText().toString();
                String courseSection = courseSectionEditText.getText().toString();

                if (assignmentName.isEmpty() || dueDate.isEmpty() || courseSection.isEmpty()) {
                    showAlertDialog();
                } else {
                    // Proceed with creating or saving the assignment
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("taskName", assignmentName);
                    resultIntent.putExtra("dueDate", dueDate);
                    resultIntent.putExtra("taskCourseSection", courseSection);
                    resultIntent.putExtra("taskType", "Assignment");
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
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

