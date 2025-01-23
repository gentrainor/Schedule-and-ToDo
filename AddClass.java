package com.example.schedularappv3;

import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.AlertDialog;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class AddClass extends AppCompatActivity {

    private EditText editTextClassName, editTextStartTime, editTextEndTime, editTextLocation, editTextInstructor;
    private CheckBox checkboxMonday, checkboxTuesday, checkboxWednesday, checkboxThursday, checkboxFriday, checkboxSaturday, checkboxSunday;
    private Button buttonSaveChanges, buttonDeleteClass;
    private boolean isEditMode = false;
    private int classIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        initializeViews();
        checkEditMode();
        setupTimePickers();
        setupButtons();
    }

    private void initializeViews() {
        editTextClassName = findViewById(R.id.editTextClassName);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndTime = findViewById(R.id.editTextEndTime);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextInstructor = findViewById(R.id.editTextInstructor);

        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        checkboxSaturday = findViewById(R.id.checkboxSaturday);
        checkboxSunday = findViewById(R.id.checkboxSunday);

        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        buttonDeleteClass = findViewById(R.id.buttonDeleteClass);
    }

    private void checkEditMode() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isEditMode = extras.getBoolean("isEditMode", false);
            if (isEditMode) {
                classIndex = extras.getInt("classIndex", -1);
                prefillClassDetails(extras);
                buttonSaveChanges.setText(getString(R.string.save_changes));
                buttonDeleteClass.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupTimePickers() {
        editTextStartTime.setOnClickListener(v -> showTimePickerDialog(editTextStartTime));
        editTextEndTime.setOnClickListener(v -> showTimePickerDialog(editTextEndTime));
    }

    private void setupButtons() {
        buttonSaveChanges.setOnClickListener(v -> saveClass());
        if (isEditMode) {
            buttonDeleteClass.setOnClickListener(v -> deleteClass());
        } else {
            buttonDeleteClass.setVisibility(View.GONE);
        }
    }

    private void showTimePickerDialog(final EditText timeField) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour);
            timeField.setText(selectedTime);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void saveClass() {
        if (editTextClassName.getText().toString().trim().isEmpty() ||
                editTextStartTime.getText().toString().trim().isEmpty() ||
                editTextEndTime.getText().toString().trim().isEmpty() ||
                editTextLocation.getText().toString().trim().isEmpty() ||
                editTextInstructor.getText().toString().trim().isEmpty() ||
                !isAnyDayChecked()) {
            new AlertDialog.Builder(this)
                    .setTitle("Empty Class")
                    .setMessage("Cannot create an empty class")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ClassName", editTextClassName.getText().toString());
            resultIntent.putExtra("ClassDays", getSelectedDays());
            resultIntent.putExtra("StartTime", editTextStartTime.getText().toString());
            resultIntent.putExtra("EndTime", editTextEndTime.getText().toString());
            resultIntent.putExtra("Location", editTextLocation.getText().toString());
            resultIntent.putExtra("InstructorName", editTextInstructor.getText().toString());
            resultIntent.putExtra("isEditMode", isEditMode);
            resultIntent.putExtra("classIndex", classIndex);

            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private boolean isAnyDayChecked() {
        return checkboxMonday.isChecked() || checkboxTuesday.isChecked() ||
                checkboxWednesday.isChecked() || checkboxThursday.isChecked() ||
                checkboxFriday.isChecked() || checkboxSaturday.isChecked() ||
                checkboxSunday.isChecked();
    }


    private void deleteClass() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("deleteClass", true);
        resultIntent.putExtra("classIndex", classIndex);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void prefillClassDetails(Bundle extras) {
        editTextClassName.setText(extras.getString("ClassName", ""));
        editTextStartTime.setText(extras.getString("StartTime", ""));
        editTextEndTime.setText(extras.getString("EndTime", ""));
        editTextLocation.setText(extras.getString("Location", ""));
        editTextInstructor.setText(extras.getString("InstructorName", ""));

        String selectedDays = extras.getString("ClassDays", "");
        if (!selectedDays.isEmpty()) {
            List<String> daysList = Arrays.asList(selectedDays.split(", "));
            checkboxMonday.setChecked(daysList.contains("Mon"));
            checkboxTuesday.setChecked(daysList.contains("Tue"));
            checkboxWednesday.setChecked(daysList.contains("Wed"));
            checkboxThursday.setChecked(daysList.contains("Thu"));
            checkboxFriday.setChecked(daysList.contains("Fri"));
            checkboxSaturday.setChecked(daysList.contains("Sat"));
            checkboxSunday.setChecked(daysList.contains("Sun"));
        }
    }

    private String getSelectedDays() {
        List<String> selectedDays = new ArrayList<>();
        if (checkboxMonday.isChecked()) selectedDays.add("Mon");
        if (checkboxTuesday.isChecked()) selectedDays.add("Tue");
        if (checkboxWednesday.isChecked()) selectedDays.add("Wed");
        if (checkboxThursday.isChecked()) selectedDays.add("Thu");
        if (checkboxFriday.isChecked()) selectedDays.add("Fri");
        if (checkboxSaturday.isChecked()) selectedDays.add("Sat");
        if (checkboxSunday.isChecked()) selectedDays.add("Sun");

        return String.join(", ", selectedDays);
    }
}
