package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.AvailableLesson;
import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.lang.System.out;

@WebServlet(name="ServletAvailableRepetition",value="/servlet-available-repetition")
public class ServletAvailableRepetition  extends HttpServlet {
    private  DAO DAO = null;
    //
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("AR");
        HttpSession s = request.getSession() ;

        if(s.getAttribute("role") == null){
            s.setAttribute("role", "guest");
        }

        response.setContentType("application/json");
        PrintWriter out =  response.getWriter();
        Gson gson = new Gson();
        ArrayList<AvailableLesson> list = DAO.availableRepetition();
        String string = gson.toJson(list);
        System.out.println(string);
        out.print(string);
        out.close();

    }

}
