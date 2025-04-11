package com.example.repeatwithme.servlet;


import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.Booking;
import com.example.repeatwithme.model.MyOwnBooking;
import com.example.repeatwithme.model.User;
import com.google.gson.Gson;

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

@WebServlet(name = "ServletMyOwnBookings" , value = "/servlet-my-own-bookings")
public class ServletMyOwnBookings extends HttpServlet {

    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
    }
    //propria lista prenotazioni
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession();
        System.out.println("MOB sessione -");
        System.out.println("MOB s session -"+s.getId());
        System.out.println("ruoloo : " + s.getAttribute("role"));
        if(s.getAttribute("role") == null){
            s.setAttribute("role", "guest");
        }

        String ruolo = (String) s.getAttribute("role");
        System.out.println("MOB role -"+ ruolo);
        String risp;

        PrintWriter out =  response.getWriter();

        if(!ruolo.equals("guest")){
            if(ruolo.equals("client") || ruolo.equals("admin") ){
                Gson gson = new Gson(); // traduttore da e verso formato JSON
                response.setContentType("application/json");
                ArrayList<MyOwnBooking> list = DAO.printMyOwnBookings(new User(
                        (String) s.getAttribute("idUser"),
                        (String) s.getAttribute("email"),
                        (String) s.getAttribute("username"),
                        (String) s.getAttribute("password"),
                        ruolo
                ));

                risp = gson.toJson(list);
                System.out.println("MOB sessione -"+risp);
                out.print(risp);
            }else{
                response.setContentType("application/text");
                risp="Non hai i permessi per questa operazione";
                out.print(risp);
            }
        }else{
            response.setContentType("application/text");
            risp= "non sei loggato o non sei registrato ";
            out.print(risp);
        }
        out.close();

    }
}
