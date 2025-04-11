package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.AdminAllBooking;
import com.example.repeatwithme.model.AvailableLesson;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletAllBooking", value = "/servlet-all-booking")
public class ServletAllBooking extends HttpServlet {
    private DAO DAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO= (DAO) ctx.getAttribute("DAO");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role =(String) session.getAttribute("role");
        if(session.getAttribute("role") == null ){
            session.setAttribute("role", "guest");
        }
        //
        String risp;
        PrintWriter out =  response.getWriter();

        if(!role.equals("guest")){
            if(role.equals("admin")){
                ArrayList<AdminAllBooking> list = DAO.printBookings();
                Gson gson = new Gson();
                response.setContentType("application/json");
                String s = gson.toJson(list);
                System.out.println("string"+ s );
                out.print(s);
                out.close();
            }else{
                response.setContentType("application/txt");
                risp="Utente non esistente ti preghimo di ricontrollare le credenziali o eventualmente registrarti !";
                out.print(risp);
            }
        }else{
            response.setContentType("application/txt");
            risp="Non hai inserito le credenziali !";
            out.print(risp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
