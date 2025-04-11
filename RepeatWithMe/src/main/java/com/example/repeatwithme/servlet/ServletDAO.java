package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "ServletDAO", value = "/ServletDAO", loadOnStartup = 1, asyncSupported = true)
public class ServletDAO extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("pwd");
        DAO dao;
        if (ctx.getAttribute("DAO") == null) {

            dao = new DAO(url, user, pwd);
            ctx.setAttribute("DAO", dao);
        } else {

            dao = (DAO) ctx.getAttribute("DAO");
        }
    }
}
