package edu.cs.ubbcluj.ro.servlets;

import edu.cs.ubbcluj.ro.model.Concession;
import edu.cs.ubbcluj.ro.repository.service.ConcessionService;
import edu.cs.ubbcluj.ro.utils.Constants;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@WebServlet("/ConcessionServlet")
public class ConcessionServlet extends HttpServlet {

    @EJB
    ConcessionService concessionService;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();

        if (act == null) {
            RequestDispatcher rd = request.getRequestDispatcher(Constants.CONCESSIONS_PAGE);
            rd.forward(request, response);
        } else if (act.equals(Constants.VIEW_CONCESSIONS) || act.equals(Constants.CONCESSION_MANAGEMENT)) {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (request.getParameter("year") != null)
                year = Integer.parseInt(request.getParameter("year"));
            List<Concession> concessions = filterByYear(year);
            session.setAttribute("concessions", concessions);
            session.setAttribute("option", act);
            RequestDispatcher rd = request.getRequestDispatcher(Constants.CONCESSIONS_PAGE);
            rd.forward(request, response);
        }
    }

    private List<Concession> filterByYear(int year) {
        List<Concession> concessions = concessionService.getAll();
        List<Concession> result = new ArrayList<>();

        for (Concession c : concessions) {
            Calendar.getInstance().setTime(c.getDate());
            if (Calendar.getInstance().get(Calendar.YEAR) == year)
                result.add(c);
        }
        return result;
    }
}
