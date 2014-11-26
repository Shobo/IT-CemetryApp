package controller;

import model.Autentificare;
import model.user;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andreea on 25/11/14.
 */


public class loginController extends HttpServlet {

    public loginController() {
        super();
    }

    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String job = request.getParameter("job");
        RequestDispatcher rd = null;

        Autentificare auth = new Autentificare();
        String result = auth.authentificate(username, password, job);
        if (result.equals("Success")) {
            rd = request.getRequestDispatcher("/success.jsp");
            user user = new user(username, password,job);
            request.setAttribute("user", user);
        } else {
            rd = request.getRequestDispatcher("/log.html");
        }
        rd.forward(request, response);
    }




}
