package com.example.repeatwithme.servlet;

import com.example.repeatwithme.dao.DAO;
import com.example.repeatwithme.model.*;
import com.example.repeatwithme.utility.Cripting;
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

@WebServlet(name="servletLogin",value = "/servlet-login")
public class ServletLogin extends HttpServlet {
    private DAO DAO= null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx = config.getServletContext();
        DAO = (DAO)ctx.getAttribute("DAO");
        System.out.println("INIT LOGIN");
    }


    /*
     * Todo
     *       1)input
     *           url:
     *           - possibile null
     *           - possibile User non exist
     *           browser:
     *           - possibile null
     *           - user on esiste
     *         password da criptare se non già fatto
     *
     * */


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting login parameter
        System.out.println("Entro in login");
        HttpSession s = request.getSession(true);       //openSession
        if(s.getAttribute("role") == null){
            s.setAttribute("role", "guest");
        }
        System.out.println(s.getId());
        String email = request.getParameter("email");
        String password = Cripting.encryptMD5(request.getParameter("password"));
        String sessionIdClient = request.getParameter("sessionId");

        String risp;                                // text/json respont to client
        PrintWriter out = response.getWriter();     // Writer

            if (!email.equals("") && !password.equals("")) {    //if login input is not null

                User user= DAO.findUserByEmailPassword(email,password); //serch if the user exist

                if (user != null) { //user exist the return user tate to browser

                    System.out.println("LOGIN - ho trovato luser");
                    response.setContentType("application/json");

                    //inizializzeting state variable
                    s.setAttribute("email",email);
                    s.setAttribute("password",password);
                    s.setAttribute("idUser", user.getIdUser());
                    s.setAttribute("role", user.getRole());

                    System.out.println("LOGIN -"+s.getAttribute("email"));
                    System.out.println("LOGIN -"+s.getAttribute("role"));

                    Gson json= new Gson();
                    risp = json.toJson(user);

                    if(sessionIdClient != null && s.getId().equals(sessionIdClient)){
                        System.out.println(sessionIdClient+"---"+s.getId());
                        String sess= ",'sessionId' : '"+sessionIdClient+"'}" ;
                        sess = sess.replaceAll("'","\"");
                        risp = risp.replace("}","");
                        System.out.println("LOGIN -"+risp + sess);
                        out.print(risp + sess );
                    }else{
                        System.out.println(sessionIdClient+"---"+s.getId());
                        String sess= ",'sessionId' : '"+s.getId()+"'}" ;
                        sess = sess.replaceAll("'","\"");
                        risp = risp.replace("}","");
                        System.out.println("LOGIN -"+risp + sess);
                        out.print(risp + sess );
                    }
                }else{
                    //if user not exist
                    response.setContentType("application/txt");
                    risp="Utente non esistente ti preghiamo di ricontrollare le credenziali o eventualmente registrarti !";
                    out.print(risp);
                }
            }else{
                //if empty form
                response.setContentType("application/txt");
                risp="Non hai inserito le credenziali !";
                out.print(risp);
            }
        out.close();
    }
}
