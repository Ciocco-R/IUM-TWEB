package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.User;
import com.example.repeatwithme.utility.Cripting;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name="servletSignin",value = "/servlet-signin")
public class ServletSignin extends HttpServlet {

    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
        System.out.println("INIT SIGNIN");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession();
        if(s.getAttribute("role") == null){
            s.setAttribute("role", "guest");
        }
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String risp;
        if(email!=null && username!=null && password!=null){

            User tmpUser = DAO.findUserByEmail(email);
            if(tmpUser == null) {
                User u = new User(email, username, Cripting.encryptMD5(password));
                DAO.insertUser(u);
            }else{
                response.setContentType("application/txt");
                PrintWriter out = response.getWriter();
                risp = "Esiste già un account con la mail inserita";
                out.print(risp);
            }
        }else{
            response.setContentType("application/txt");
            PrintWriter out = response.getWriter();
            risp = "La preghiamo di compilare tutti i campi disponibili";
            out.print(risp);
        }

    }
}
