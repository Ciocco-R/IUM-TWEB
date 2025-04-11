package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;


@WebServlet(name = "ServletLogout" , value = "/servlet-logout")
public class ServletLogout extends HttpServlet {
    private com.example.repeatwithme.dao.DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession();
        System.out.println(s.getId());
        if(s != null ){
            req.getSession().invalidate();
        }
    }
}
