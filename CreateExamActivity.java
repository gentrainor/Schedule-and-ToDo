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


public class CreateExamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        EditText examNameEditText = findViewById(R.id.examNameEditText);
        EditText examDateEditText = findViewById(R.id.examDateEditText);
        EditText examCourseSectionEditText = findViewById(R.id.examCourseSectionEditText);
        Button createExamButton = findViewById(R.id.createExamButton);

        boolean isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            String examName = getIntent().getStringExtra("taskName");
            String examDate = getIntent().getStringExtra("dueDate");
            String courseSection = getIntent().getStringExtra("taskCourseSection");

            examNameEditText.setText(examName);
            examDateEditText.setText(examDate);
            examCourseSectionEditText.setText(courseSection);

            createExamButton.setText(getString(R.string.save_changes)); // Adjust to your 'Save' string
        }

        createExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve exam details from input fields
                String examName = examNameEditText.getText().toString();
                String examDate = examDateEditText.getText().toString();
                String examCourseSection = examCourseSectionEditText.getText().toString();

                if (examName.isEmpty() || examDate.isEmpty() || examCourseSection.isEmpty()) {
                    showAlertDialog();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("taskName", examName);
                    resultIntent.putExtra("dueDate", examDate);
                    resultIntent.putExtra("taskCourseSection", examCourseSection);
                    resultIntent.putExtra("taskType", "Exam");
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }
        });
        examDateEditText.setOnClickListener(new View.OnClickListener() {
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
                        String formattedMonth = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
                        String formattedDay = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String selectedDate = formattedDay + "/" + formattedMonth + "/" + year;
                        EditText dueDateEditText = findViewById(R.id.examDateEditText);
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


