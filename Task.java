package com.example.schedularappv3;
import java.io.Serializable;


public class Task implements Serializable{
    private String name;
    private String dueDate;
    private String courseSection;
    private String type;
    private boolean isCompleted;
    public Task(String name, String dueDate, String courseSection, String type) {
        this.name = name;
        this.dueDate = dueDate;
        this.courseSection = courseSection;
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setCourseSection(String CourseSection) {
        this.courseSection = courseSection;
    }
    public void setType(String type) {
        this.type = type;
    }
}

