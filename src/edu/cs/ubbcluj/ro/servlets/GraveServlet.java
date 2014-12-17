package edu.cs.ubbcluj.ro.servlets;


import edu.cs.ubbcluj.ro.model.Grave;
import edu.cs.ubbcluj.ro.repository.service.GraveService;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/GraveServlet")
public class GraveServlet extends HttpServlet {

    @EJB
    GraveService graveService;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();

        if (act == null) {
            RequestDispatcher rd = request.getRequestDispatcher("Graves.jsp");
            rd.forward(request, response);
        } else if (act.equals("Gestiune Morminte")) {
            List<Grave> graves = graveService.getAll();
            session.setAttribute("graves", graves);
            RequestDispatcher rd = request
                    .getRequestDispatcher("Graves.jsp");
            rd.forward(request, response);
        }
    }
}
