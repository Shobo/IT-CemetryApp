package edu.cs.ubbcluj.ro.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();

        if (act == null) {
            RequestDispatcher rd = request.getRequestDispatcher("Graves.jsp");
            rd.forward(request, response);
        } else if (act.equals("Gestiune Morminte")) {
            RequestDispatcher rd = request
                    .getRequestDispatcher("Graves.jsp");
            rd.forward(request, response);
        }
    }
}