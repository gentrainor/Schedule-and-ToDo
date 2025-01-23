package com.example.schedularappv3;

public class ClassInfo {
    private String className;
    private String daysOfWeek;
    private String startTime;
    private String endTime;
    private String location;
    private String instructorName;

    // Constructor
    public ClassInfo(String className, String daysOfWeek, String startTime, String endTime, String location, String instructorName) {
        this.className = className;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.instructorName = instructorName;
    }

    // Getters
    public String getClassName() {
        return className;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructorName() {
        return instructorName;
    }

    // Setters
    public void setClassName(String className) {
        this.className = className;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
}
