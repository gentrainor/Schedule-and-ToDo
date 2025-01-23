package com.example.schedularappv3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;

public class FilterDialogFragment extends DialogFragment {
    private String selectedFilter = "All";

    public interface OnFilterSelectedListener {
        void onFilterSelected(String selectedFilter);
        void onClearFilters();
    }

    private OnFilterSelectedListener listener;

    public FilterDialogFragment(OnFilterSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_dialog_layout, container, false);

        RadioGroup filterOptionsGroup = view.findViewById(R.id.filterOptionsRadioGroup);
        RadioButton showCompletedCheckbox = view.findViewById(R.id.showCompletedRadioButton);

        // Set up a listener for the RadioGroup to handle selections
        filterOptionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.showAssignmentsRadioButton) {
                    selectedFilter = "Assignment";
                } else if (checkedId == R.id.showExamsRadioButton) {
                    selectedFilter = "Exam";
                } else if (checkedId == R.id.showToDosRadioButton) {
                    selectedFilter = "To Do";
                } else if (checkedId == R.id.sortByDateAscRadioButton) {
                    selectedFilter = "Sort by Date ASC";
                } else if (checkedId == R.id.showCompletedRadioButton) { // This is the new filter option
                    selectedFilter = "Completed";
                } else if (checkedId == R.id.clearFiltersRadioButton) {
                    listener.onClearFilters();
                    dismiss();
                    return; // Early return to avoid calling onFilterSelected for clear filters
                }

                listener.onFilterSelected(selectedFilter);
            }
        });

        Button applyFilterButton = view.findViewById(R.id.applyFilterButton);
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFilterSelected(selectedFilter);
                dismiss();
            }
        });

        return view;
    }
}
