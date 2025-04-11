package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.Booking;
import com.example.repeatwithme.model.Course;
import com.example.repeatwithme.model.Teacher;

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

@WebServlet(name="servletBookingState" , value="/servlet-booking-state")
public class ServletBookingState extends HttpServlet {

    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
        System.out.println("IN SERVLET BOOKING STATE");
    }

    /*
    commentino
    come gestiamo le prenotazioni fatte
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ENTRO NELLA SERVLET BOOKING STATE");
        HttpSession s = request.getSession();
        if(s.getAttribute("role") == null){
            s.setAttribute("role", "guest");
        }
        String email = request.getParameter("email");
        String teacherName = request.getParameter("teacherName");
        String teacherSurname = request.getParameter("teacherSurname");
        String courseTitle = request.getParameter("courseTitle");
        String day = request.getParameter("day");
        String hour = request.getParameter("hour");
        String state = request.getParameter("state");
        String role = (String) s.getAttribute("role");
        String risp;
        PrintWriter out = response.getWriter();


        if(!role.equals("guest")){
            if(role.equals("client") || role.equals("admin")){

                Booking b=null;
                if(email == null){
                    b = new Booking(s.getAttribute("idUser").toString(),
                            DAO.findTeacherByName(new Teacher(teacherName, teacherSurname)).getIdTeacher(),
                            DAO.findCourseByTitle(new Course(courseTitle)).getIdCourse(),
                            day,hour);
                }else{
                     b= new Booking(DAO.findUserByEmail(email).getIdUser(),
                            DAO.findTeacherByName(new Teacher(teacherName, teacherSurname)).getIdTeacher(),
                            DAO.findCourseByTitle(new Course(courseTitle)).getIdCourse(),
                            day,hour);
                }


                DAO.changeStateBooking(DAO.findBookingByUserTeacherLessonStateDayHour(b),state);

            }else{
                //if user not exist
                response.setContentType("application/txt");
                risp="Utente non esistente ti preghimo di ricontrollare le credenziali o eventualmente registrarti !";
                out.print(risp);
            }
        }else{
            response.setContentType("application/txt");
            risp="Non hai inserito le credenziali !";
            out.print(risp);
        }
        out.close();
    }
}
