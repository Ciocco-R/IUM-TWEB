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


@WebServlet(name="ServletInsertBooking",value="/servlet-insert-booking")
public class ServletInsertBooking extends HttpServlet {
    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
    }

    /**
     * inserisce una orenotazione
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        HttpSession s = request.getSession();
        System.out.println("Entro in ServletInsertBooking");

        if(s.getAttribute("role") == null ){
            s.setAttribute("role", "guest");
        }

        String teacherName = request.getParameter("teacherName");
        String teacherSurname = request.getParameter("teacherSurname");
        String courseTitle = request.getParameter("courseTitle");
        String day = request.getParameter("day");
        String hour = request.getParameter("hour");


        String role = (String)s.getAttribute("role");
        String risp;

        if(!role.equals("guest")){
            if(role.equals("client")){
                if(teacherName!=null && teacherSurname!=null && courseTitle!=null && day!=null && hour!=null ){
                    String idThercher = DAO.findTeacherByName(new Teacher(teacherName, teacherSurname)).getIdTeacher();
                    String idCourse = DAO.findCourseByTitle(new Course(courseTitle)).getIdCourse();

                    if (DAO.isUserFree((String) s.getAttribute("idUser"), day, hour)) {

                        if (DAO.existsTeaching(idThercher, idCourse)) {   //controll if  tiching exists

                            if (DAO.isTeacherFree(idThercher, day, hour)) {

                                System.out.println(s.getAttribute("idUser").toString() + " " + idThercher + " "+idCourse+ " " + day + " " + hour);
                                Booking booking = new Booking(
                                        s.getAttribute("idUser").toString(),
                                        idThercher,
                                        idCourse,
                                        day,
                                        hour
                                );

                                DAO.insertBooking(booking);
                                response.setContentType("application/text");
                                PrintWriter out = response.getWriter();
                                risp = "200";
                                out.print(risp);

                            } else {
                                response.setContentType("application/text");
                                PrintWriter out = response.getWriter();
                                risp = "Il docente non è disponibile a quest'ora";
                                out.print(risp);
                            }

                        } else {
                            response.setContentType("application/text");
                            PrintWriter out = response.getWriter();
                            risp = "Linsegnamento selezionato non esiste";
                            out.print(risp);
                        }
                    } else {
                        response.setContentType("application/text");
                        PrintWriter out = response.getWriter();
                        risp = "Per questo giorno a quest'ora sei già prenotato per un altra lezione";
                        out.print(risp);
                    }

                }else{
                    response.setContentType("application/text");
                    PrintWriter out = response.getWriter();
                    risp = "inserisci tutti i parametri della prenotazione";
                    out.print(risp);
                }


            } else {
                response.setContentType("application/text");
                PrintWriter out =  response.getWriter();
                risp="Non hai i permessi per questa operazione";
                out.print(risp);
            }
        }else{
            response.setContentType("application/text");
            PrintWriter out =  response.getWriter();
            risp= "non sei loggato o non sei registrato ";
            out.print(risp);
        }
    }
}
