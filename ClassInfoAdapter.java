package com.example.schedularappv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {

    private List<ClassInfo> classInfoList;
    private final OnClassClickListener classClickListener;

    public interface OnClassClickListener {
        void onClassClick(int position);
    }

    public ClassInfoAdapter(List<ClassInfo> classInfoList, OnClassClickListener classClickListener) {
        this.classInfoList = classInfoList;
        this.classClickListener = classClickListener;
    }

    @NonNull
    @Override
    public ClassInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_tile, parent, false);
        return new ClassInfoViewHolder(view, classClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInfoViewHolder holder, int position) {
        ClassInfo classInfo = classInfoList.get(position);
        holder.textViewClassName.setText(classInfo.getClassName());
        holder.textViewDaysOfWeek.setText("Days: " + classInfo.getDaysOfWeek());
        holder.textViewStartTime.setText("Start: " + classInfo.getStartTime());
        holder.textViewEndTime.setText("End: " + classInfo.getEndTime());
        holder.textViewLocation.setText("Location: " + classInfo.getLocation());
        holder.textViewInstructor.setText("Instructor: " + classInfo.getInstructorName());

        // Set the edit button click listener
        holder.buttonEdit.setOnClickListener(v -> {
            int clickedPosition = holder.getAdapterPosition();
            if (classClickListener != null && clickedPosition != RecyclerView.NO_POSITION) {
                classClickListener.onClassClick(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classInfoList.size();
    }

    public static class ClassInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewClassName, textViewDaysOfWeek, textViewStartTime, textViewEndTime, textViewLocation, textViewInstructor;
        public Button buttonEdit; // Reference to the Edit button

        public ClassInfoViewHolder(View itemView, OnClassClickListener listener) {
            super(itemView);
            textViewClassName = itemView.findViewById(R.id.textViewCourseName);
            textViewDaysOfWeek = itemView.findViewById(R.id.textViewDaysOfWeek);
            textViewStartTime = itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = itemView.findViewById(R.id.textViewEndTime);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewInstructor = itemView.findViewById(R.id.textViewInstructor);
            buttonEdit = itemView.findViewById(R.id.buttonEdit); // Reference the Edit button

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onClassClick(position);
                }
            });
        }
    }

    public void setClassInfoList(List<ClassInfo> classInfoList) {
        this.classInfoList = classInfoList;
        notifyDataSetChanged();
    }
}
