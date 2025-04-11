package com.example.repeatwithme.model;

public class Booking {
    private String idBooking;
    private String idUser;
    private String idTeacher;
    private String idCourse;
    private String dayCalender;
    private String hourCalender;
    private String state;

    public Booking(String idUser, String idTeacher, String idCourse, String dayCalender, String hourCalender, String state) {
        this.idUser = idUser;
        this.idTeacher = idTeacher;
        this.idCourse = idCourse;
        this.dayCalender = dayCalender;
        this.hourCalender = hourCalender;
        this.state = state;
    }

    public Booking(String idBooking, String idUser, String idTeacher, String idCourse, String dayCalender, String hourCalender, String state) {
        this.idBooking = idBooking;
        this.idUser = idUser;
        this.idTeacher = idTeacher;
        this.idCourse = idCourse;
        this.dayCalender = dayCalender;
        this.hourCalender = hourCalender;
        this.state = state;
    }

    public Booking(String idUser, String idTeacher, String idCourse, String dayCalender, String hourCalender) {
        this.idUser = idUser;
        this.idTeacher = idTeacher;
        this.idCourse = idCourse;
        this.dayCalender = dayCalender;
        this.hourCalender = hourCalender;
        this.state = "active";
    }




    public String getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(String idBooking) {
        this.idBooking = idBooking;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(String idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getDayCalender() {
        return dayCalender;
    }

    public void setDayCalender(String dayCalender) {
        this.dayCalender = dayCalender;
    }

    public String getHourCalender() {
        return hourCalender;
    }

    public void setHourCalender(String hourCalender) {
        this.hourCalender = hourCalender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Override
    public String toString() {
        return "Booking{" +
                "idBooking='" + idBooking + '\'' +
                ", idUser='" + idUser + '\'' +
                ", idTeacher='" + idTeacher + '\'' +
                ", idCourse='" + idCourse + '\'' +
                ", dayCalender='" + dayCalender + '\'' +
                ", hourCalender='" + hourCalender + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
