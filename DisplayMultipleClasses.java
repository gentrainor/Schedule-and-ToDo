package com.example.schedularappv3;

public class DisplayMultipleClasses {
    private String className;
    private String daysOfWeek;
    private String startTime;
    private String endTime;
    private String location;
    private String instructor;

    // Constructor
    public DisplayMultipleClasses(String className, String daysOfWeek, String startTime, String endTime, String location, String instructor) {
        this.className = className;
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.instructor = instructor;
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

    public String getInstructor() {
        return instructor;
    }
}

