package com.example.repeatwithme.model;

public class AvailableLesson {
    private String teacherName;
    private String teacherSurname;
    private String courseTitle;
    private String day;
    private String hour;

    public AvailableLesson(String teacherName, String teacherSurname, String courseTitle, String day, String hour) {
        this.teacherName = teacherName;
        this.teacherSurname = teacherSurname;
        this.courseTitle = courseTitle;
        this.day = day;
        this.hour = hour;
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
    @Override
    public String toString() {
        return "AvailableLesson{" +
                "teacherName='" + teacherName + '\'' +
                ", teacherSurname='" + teacherSurname + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                '}';
    }

}
