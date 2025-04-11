package com.example.repeatwithme.model;

public class MyOwnBooking {
    private String teacherName;
    private String teacherSurname;
    private String courseTitle;
    private String day;
    private String hour;
    private String state;

    public MyOwnBooking(String teacherName, String teacherSurname, String courseTitle, String day, String hour, String state) {
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.courseTitle = courseTitle;
        this.day = day;
        this.hour = hour;
        this.state = state;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherSurname() {
        return teacherSurname;
    }

    public void setTeacherSurname(String teacherSurname) {
        this.teacherSurname = teacherSurname;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "MyOwnBooking{" +
                "teacherName='" + teacherName + '\'' +
                ", teacherSurname='" + teacherSurname + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
