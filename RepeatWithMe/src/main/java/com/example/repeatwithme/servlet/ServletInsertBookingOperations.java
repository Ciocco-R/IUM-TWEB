package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.AvailableLesson;
import com.example.repeatwithme.model.Booking;
import com.example.repeatwithme.model.Course;
import com.example.repeatwithme.model.Teacher;
import com.google.gson.Gson;
import com.mysql.cj.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name="ServletInsertBookingOperations",value="/servlet-insert-booking-operations")
public class ServletInsertBookingOperations extends HttpServlet{
    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action= req.getParameter("action");
        switch (action){
            case "getBfromTC":{
                System.out.println("entro GetBfromTC ");
                resp.setContentType("application/json");
                String courseTitle = req.getParameter("courseTitle");
                String nameTeacher = req.getParameter("nameTeacher");
                String surnameTeacher = req.getParameter("surnameTeacher");
                System.out.println("1) "+ courseTitle + " 2) " + nameTeacher + " 3) "+ surnameTeacher);
                PrintWriter out = resp.getWriter();
                ArrayList<AvailableLesson> list=DAO.findAvailableRepetitionsByCourseTeacher(courseTitle, nameTeacher, surnameTeacher);
                Gson gson = new Gson();

                String risp = gson.toJson(list);
                System.out.println(risp);
                out.print(risp);
                out.close();
                break;
            }
            case "getT":{
                System.out.println("entro getT ");
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                ArrayList<Teacher> list=DAO.allTeachers();
                Gson gson = new Gson();

                String risp = gson.toJson(list);
                System.out.println(risp);
                out.print(risp);
                out.close();
                break;
            }
            case "getC":{
                System.out.println("entro getC ");
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                ArrayList<Course> list=DAO.allCourses();
                Gson gson = new Gson();
                String risp = gson.toJson(list);
                System.out.println(risp);
                out.print(risp);
                out.close();
                break;
            }
            case "getTFromC":{
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                System.out.println(req.getParameter("titleC"));
                ArrayList<Teacher> list=DAO.findTeacherByCourse(req.getParameter("titleC"));
                Gson gson = new Gson();
                String risp = gson.toJson(list);
                System.out.println(risp);
                out.print(risp);
                out.close();
                break;
            }
            case "getCFromT":{
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                ArrayList<Course> list=DAO.findCourseByTeacher(req.getParameter("nameT"),req.getParameter("surnameT"));
                Gson gson = new Gson();
                String risp = gson.toJson(list);
                out.print(risp);
                out.close();
                break;
            }
        }


    }
}
