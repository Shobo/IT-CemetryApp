package edu.cs.ubbcluj.ro.servlets;

import edu.cs.ubbcluj.ro.model.Owner;
import edu.cs.ubbcluj.ro.repository.service.DeadService;
import edu.cs.ubbcluj.ro.repository.service.OwnerService;
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
import java.util.List;

@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {

    @EJB
    OwnerService ownerService;

    @EJB
    DeadService deadService;


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();
        List<Owner> owners = null;

        act = new String(act.getBytes("iso-8859-1"), "UTF-8");

        switch (act) {
            case Constants.OWNER_MANAGEMENT:
                owners = ownerService.getAll();
                session.setAttribute("option", act);
                break;

        }

        if (owners != null){
            session.setAttribute("owners", owners);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constants.PERSONS_PAGE);
        if (act.equals(Constants.ADD) || act.equals(Constants.EDIT)){
            //TODO PERSONS_MANAGEMENT_PAGE
            // requestDispatcher = request.getRequestDispatcher(Constants.PERSONS_MANAGEMENT_PAGE);
        }
        requestDispatcher.forward(request, response);

    }
}