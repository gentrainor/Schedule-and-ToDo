package com.example.schedularappv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<DisplayMultipleClasses> classList;

    public ClassAdapter(List<DisplayMultipleClasses> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_tile, parent, false); // Assuming you want to use class_tile.xml for layout
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        DisplayMultipleClasses currentItem = classList.get(position);
        holder.textViewClassName.setText(currentItem.getClassName());
        // Assuming DisplayMultipleClasses has the methods getDaysOfWeek(), getStartTime(), getEndTime(), getLocation()
        holder.textViewDaysOfWeek.setText("Days: " + currentItem.getDaysOfWeek());
        holder.textViewStartTime.setText("Start: " + currentItem.getStartTime());
        holder.textViewEndTime.setText("End: " + currentItem.getEndTime());
        holder.textViewLocation.setText("Location: " + currentItem.getLocation());
        holder.textViewInstructor.setText("Instructor: " + currentItem.getInstructor());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewClassName, textViewDaysOfWeek, textViewStartTime, textViewEndTime, textViewLocation, textViewInstructor;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClassName = itemView.findViewById(R.id.textViewCourseName);
            textViewDaysOfWeek = itemView.findViewById(R.id.textViewDaysOfWeek);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewInstructor = itemView.findViewById(R.id.textViewInstructor);
        }
    }
}
