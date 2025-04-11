package com.example.repeatwithme.model;

public class Course {
    private String idCourse;
    private String titolo;

    public Course(String titolo) {
        this.titolo = titolo;
    }

    public Course(String idCourse, String titolo){
        this.idCourse=idCourse;
        this.titolo=titolo;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String toString() {
        return "Course{" +
                "idCourse='" + idCourse + '\'' +
                ", titolo='" + titolo + '\'' +
                '}';
    }
}
