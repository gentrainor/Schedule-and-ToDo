package com.example.schedularappv3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.widget.Button;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private ArrayList<Task> taskList;

    private TaskAdapterListener listener;
    public TaskAdapter(ArrayList<Task> taskList, TaskAdapterListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }
    public interface TaskAdapterListener {
        void onTaskDeleted(Task task);
        void onTaskEdit(Task task, int position);
    }


    public void setTasks(ArrayList<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        // Bind task data to views in the task item layout
        Task task = taskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        // Return the number of tasks in the data structure
        return taskList.size();
    }

    // Method to add a new task to the data structure
    public void addTask(Task task) {
        taskList.add(task);
        notifyItemInserted(taskList.size() - 1);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        // Define views in the task item layout
        private final TextView taskNameTextView;
        private final TextView dueDateTextView;
        private final TextView courseSectionTextView;
        private final CheckBox completionCheckBox;
        private final Button deleteTaskButton;
        private final Button editTaskButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteTaskButton = itemView.findViewById(R.id.deleteTaskButton);
            taskNameTextView = itemView.findViewById(R.id.taskNameTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            courseSectionTextView = itemView.findViewById(R.id.courseSectionTextView);
            completionCheckBox = itemView.findViewById(R.id.completionCheckBox);
            editTaskButton = itemView.findViewById(R.id.editTaskButton);
            completionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = taskList.get(position);
                    task.setCompleted(isChecked);
                }
            });
            deleteTaskButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = taskList.get(position);
                    listener.onTaskDeleted(task); // Notify the activity to handle the deletion
                }
            });
            editTaskButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = taskList.get(position);
                    listener.onTaskEdit(task, position);
                    listener.onTaskDeleted(task);
                }
            });
        }

        // Method to bind task data to views
        public void bind(Task task) {
            taskNameTextView.setText(task.getName());
            dueDateTextView.setText(task.getDueDate());
            courseSectionTextView.setText(task.getCourseSection());
            completionCheckBox.setChecked(task.isCompleted());
            //deleteCheckBox.setChecked(false);
        }
    }

}