package com.example.repeatwithme.model;

public class Teaching {
    private String idTeacher;
    private String idCourse;

    public Teaching(String idTeacher, String idCourse) {
        this.idTeacher = idTeacher;
        this.idCourse = idCourse;
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

    @Override
    public String toString() {
        return "Teaching{" +
                "idTeacher='" + idTeacher + '\'' +
                ", idCourse='" + idCourse + '\'' +
                '}';
    }
}
