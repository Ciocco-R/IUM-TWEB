package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.*;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletAdminManagement", value = "/servlet-admin-management")
public class ServletAdminManagement extends HttpServlet{
    private DAO DAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO= (DAO) ctx.getAttribute("DAO");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/text");
        HttpSession session = request.getSession();

        String role = (String) session.getAttribute("role");

        System.out.println("In ServletAdminManagement");

        if(session.getAttribute("role") == null ){
            session.setAttribute("role", "guest");
        }

        String risp;
        PrintWriter out = response.getWriter();

        if(role.equals("admin")){
            String action = request.getParameter("action");
            switch(action){
                case "insertTeacher":{
                    String teacherName = request.getParameter("teacherName");
                    String teacherSurname = request.getParameter("teacherSurname");
                    Teacher teacher = new Teacher(teacherName, teacherSurname);
                    if(!DAO.existsTeacher(teacher)){
                        DAO.insertTeacher(teacher);
                        risp= "Docente inserito";
                        out.print(risp);
                    }else{
                        if(DAO.isTeacherVisible(teacher)){
                            risp= "Docente già esistente";
                            out.print(risp);
                        }else{
                            DAO.ChangeTeacherVisible(teacher,1);
                            risp ="Docente reso visibile";
                            out.print(risp);
                        }
                    }
                    break;
                }
                case "removeTeacher":{
                    String teacherName = request.getParameter("teacherName");
                    String teacherSurname = request.getParameter("teacherSurname");
                    Teacher teacher = new Teacher(teacherName, teacherSurname);
                    if(DAO.existsTeacher(teacher)){
                        DAO.removeTheacher(teacher);
                        risp= "Docente rimosso";
                        out.print(risp);
                    }else{
                        risp= "Docente non esistente";
                        out.print(risp);
                    }
                    break;
                }
                case "insertCourse":{
                    String courseTitle = request.getParameter("courseTitle");
                    Course course = new Course(courseTitle);
                    if(!DAO.existsCourse(course)){
                        DAO.insertCourse(course);
                        risp= "Corso inserito";
                        out.print(risp);
                    }else{
                        if(DAO.isCourseVisible(course)){
                            risp= "Corso già esistente";
                            out.print(risp);
                        }else{
                            DAO.ChangeCourseVisible(course,1);
                            risp ="Corso cambiato in visibile";
                            out.print(risp);
                        }


                    }
                    break;
                }
                case "removeCourse":{
                    String courseTitle = request.getParameter("courseTitle");
                    Course course = new Course(courseTitle);
                    if(DAO.existsCourse(course)){
                        DAO.removeCourse(course);
                        risp= "Corso rimosso";
                        out.print(risp);
                    }else{
                        risp= "Corso non esistente";
                        out.print(risp);
                    }
                    break;
                }
                case "insertTeaching":{
                    String teacherName = request.getParameter("teacherName");
                    String teacherSurname = request.getParameter("teacherSurname");
                    String courseTitle = request.getParameter("courseTitle");
                    Course course = new Course(courseTitle);
                    Teacher teacher = new Teacher(teacherName, teacherSurname);
                    if(DAO.existsCourse(course) && DAO.existsTeacher(teacher)){

                        teacher = DAO.findTeacherByName(teacher);
                        course = DAO.findCourseByTitle(course);
                        if(!(DAO.existsTeaching(teacher.getIdTeacher(), course.getIdCourse()))) {
                            DAO.insertTeaching(new Teaching(teacher.getIdTeacher(), course.getIdCourse()));
                            risp= "Insegnamento inserito";
                            out.print(risp);
                        }else{
                            if(DAO.isTeachingVisible(teacher.getIdTeacher(), course.getIdCourse())) {
                                risp = "Insegnamento già esistente";
                                out.print(risp);
                            }else{
                                DAO.ChangeTeachingVisible(teacher.getIdTeacher(), course.getIdCourse(),1);
                                risp ="Insegnamento reso visibile";
                                out.print(risp);
                            }
                        }
                    }else {
                        risp= "L'insegnante o il corso non esistono";
                        out.print(risp);
                    }
                    break;
                }
                case "removeTeaching":{
                    String teacherName = request.getParameter("teacherName");
                    String teacherSurname = request.getParameter("teacherSurname");
                    String courseTitle = request.getParameter("courseTitle");
                    Course course = new Course(courseTitle);
                    Teacher teacher = new Teacher(teacherName, teacherSurname);
                    if(DAO.existsCourse(course) && DAO.existsTeacher(teacher)){

                        teacher = DAO.findTeacherByName(teacher);
                        course = DAO.findCourseByTitle(course);

                        if((DAO.existsTeaching(teacher.getIdTeacher(), course.getIdCourse()))) {

                            DAO.removeTheaching(new Teaching(teacher.getIdTeacher(), course.getIdCourse()));
                            risp= "Insegnamento rimosso";
                            out.print(risp);
                        }else{

                            risp= "Insegnamento non esistente";
                            out.print(risp);

                        }
                    }
                    else {
                        risp= "L'insegnante o il corso non esistono";
                        out.print(risp);
                    }
                    break;
                }
            }
        }else{
            risp= "Non hai l'autorizzazione adatta";
            out.print(risp);
        }

    }
}
