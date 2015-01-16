package edu.cs.ubbcluj.ro.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.cs.ubbcluj.ro.model.*;
import edu.cs.ubbcluj.ro.repository.service.*;
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
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/FuneralServlet")
public class FuneralServlet extends HttpServlet {

    @EJB
    DeadService deadService;

    @EJB
    FuneralService funeralService;

    @EJB
    GraveyardService graveyardService;

    @EJB
    GraveService graveService;

    @EJB
    OwnerService ownerService;

    @EJB
    TransactionService transactionService;

    @EJB
    UserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String act = request.getParameter("act");
        HttpSession session = request.getSession();
        List<Funeral> funerals = null;

        act = new String(act.getBytes("iso-8859-1"), "UTF-8");
        switch (act) {
            case Constants.DELETE :
                String[] toDelete = request.getParameterValues("selected-con");
                for (String val : toDelete)
                    deleteFuneral(findFuneralById(val));
                session.setAttribute("funerals", funeralService.getAll());
                session.setAttribute("option", Constants.FUNERALS_MANAGEMENT);
                response.sendRedirect(Constants.FUNERAL_PAGE + "?act=" + Constants.FUNERALS_MANAGEMENT.replace(' ', '+'));
                return;
            case Constants.SAVE :
                if (request.getParameter("selected-con") != null) {
                    session.setAttribute("saved", request.getParameter("selected-con"));
                }
                funerals = funeralService.getAll();
                session.setAttribute("saved", Constants.FUNERALS_MANAGEMENT);
                break;
            case Constants.AUTOCOMPLETE:
                response.setContentType("application/json");
                response.getWriter().print( jsonDeads(request.getParameter("filter"), request.getParameter("field")));
                return;
            case Constants.ADD :
                session.setAttribute("graveyards", graveyardService.getAll());
                break;
            case Constants.EDIT :
                session.setAttribute("transactions", getTransactions(findFuneralById(request.getParameter("selected-con"))));
                session.setAttribute("graveyards", graveyardService.getAll());
                session.setAttribute("funeral", findFuneralById(request.getParameter("selected-con")));
                break;
            case Constants.FUNERALS_MANAGEMENT :
                funerals = funeralService.getAll();
                session.setAttribute("option", act);
                break;
            case Constants.SEARCH :
                funerals = filter(request.getParameter("filter-value"));
                session.setAttribute("option", Constants.FUNERALS_MANAGEMENT);
                break;
        }
        if (funerals != null)
            session.setAttribute("funerals", funerals);
        RequestDispatcher rd = request.getRequestDispatcher(Constants.FUNERAL_PAGE);
        if (act.equals(Constants.ADD) || act.equals(Constants.EDIT))
            rd = request.getRequestDispatcher(Constants.FUNERAL_MANAGEMENT_PAGE);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();
        String error = checkParameters(request);

        switch (act) {
            case Constants.SAVE :
                if (error == null) {
                    Funeral f = findFuneralById(request.getParameter("funeral-id"));
                    Dead d = getDead(request.getParameter("name-dead"), request.getParameter("religion-dead"));
                    Graveyard g = graveyardService.getAll().get(Integer.parseInt(request.getParameter("grave-name")));
                    Parcel p = g.getParcels().get(Integer.parseInt(request.getParameter("grave-parcel")));
                    Grave grave = p.getGraves().get(Integer.parseInt(request.getParameter("grave-number")));


                    if (f == null)
                        createFuneral(d, grave);
                    else {
                        addTransaction(f, d, grave);
                        editFuneral(f, d, grave);
                    }
                }
                break;
        }
        if (error == null) {
            session.setAttribute("funerals", funeralService.getAll());
            session.setAttribute("option", Constants.FUNERALS_MANAGEMENT);
            response.sendRedirect(Constants.FUNERAL_PAGE + "?act=" + Constants.FUNERALS_MANAGEMENT.replace(' ', '+'));
        } else {
            session.setAttribute("error", error);
            response.sendRedirect(Constants.FUNERAL_MANAGEMENT_PAGE + "?act=" + act);
        }
    }

    private String checkParameters(HttpServletRequest request) {
        if (request.getParameter("name-dead").isEmpty())
            return ("Introduceti o valoare pentru numele decedatului");
        if (request.getParameter("religion-dead").isEmpty())
            return ("Introduceti o valoare pentru religia decedatului");
        if (request.getParameter("grave-name") == null)
            return ("Selectati o valoare pentru numele cimitirului");
        if (request.getParameter("grave-parcel") == null)
            return ("Selectati o valoare pentru numarul parcelei");
        if (request.getParameter("grave-number") == null)
            return ("Selectati o valoare pentru numarul mormantului");
        return null;
    }

    public void addTransaction(Funeral f, Dead d, Grave g) {
        String after = "Nume inmormantat: " + d.getLastName() + " " + d.getFirstName() + "\n"
                + "Religia inmormantat: " + d.getReligion() + "\n"
                + "Cimitir " + g.getParcel().getGraveyard().getName() + " " + "Parcela " + g.getParcel().getNumber()
                + "Nr. " + g.getNumber();
        String before = "Nume inmormantat: " + f.getDead().getLastName() + " " + f.getDead().getFirstName() + "\n"
                + "Religia inmormantat: " + f.getDead().getReligion() + "\n"
                + "Cimitir " + f.getDead().getGrave().getParcel().getGraveyard().getName() + " " + "Parcela " + f.getDead().getGrave().getParcel().getNumber()
                + "Nr. " + f.getDead().getGrave().getNumber();

        Transaction t = new Transaction();
        t.setRecordId(f.getId());
        t.setAfterTrans(after);
        t.setBeforeTrans(before);
        t.setTableName("Funerals");
        t.setTransTime(new Date());
        t.setModificationDetails("modified");
        t.setUser(userService.getUser(26));

        
        transactionService.updateTransaction(t);
    }

    private List<Transaction> getTransactions(Funeral f) {
        List<Transaction> all = transactionService.getAll();
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : all)
            if (t.getTableName().equals("Funerals") && t.getRecordId() == f.getId())
                res.add(t);
        return res;
    }


    private Funeral findFuneralById(String id) {
        List<Funeral> funerals = funeralService.getAll();
        for (Funeral f : funerals)
            if (Integer.toString(f.getId()).equals(id))
                return f;
        return null;
    }

    private Dead getDead(String name, String religion) throws UnsupportedEncodingException{
        name = new String(name.getBytes("iso-8859-1"), "UTF-8");
        religion = new String(religion.getBytes("iso-8859-1"), "UTF-8");
        List<Dead> deads = deadService.getAll();
        for (Dead d : deads) {
            String fullname = d.getFirstName() + " " + d.getLastName();
            if (fullname.equals(name) && d.getReligion().equals(religion))
                return d;

        }
        return null;
    }

    private String jsonDeads(String filter, String field) throws UnsupportedEncodingException {
        filter = new String(filter.getBytes("iso-8859-1"), "UTF-8");
        JsonArray result = new JsonArray();
        List<Dead> deads = deadService.getAll();
        for (Dead d : deads) {
            String name = d.getFirstName() + d.getLastName();
            if (field.equals("name") && !name.toLowerCase().contains(filter.toLowerCase()))
                continue;
            if (field.equals("religion") && !d.getReligion().contains(filter))
                continue;

            JsonObject obj = new JsonObject();
            obj.addProperty("name", d.getFirstName() + " " + d.getLastName());
            obj.addProperty("religion", d.getReligion());
            result.add(obj);
        }
        System.out.print(result.size());
        return result.toString();
    }

    private List<Funeral> filter(String value) throws UnsupportedEncodingException {
        List<Funeral> all = funeralService.getAll();
        if (value == null || value.equals(""))
            return all;
        value = new String(value.getBytes("iso-8859-1"), "UTF-8").toLowerCase();
        List<Funeral> result = new ArrayList<>();
        for (Funeral f : all) {
            String name = f.getDead().getFirstName() + " " + f.getDead().getLastName();
            if (name.toLowerCase().contains(value) || f.getDate().toString().contains(value)
                    ||f.getDead().getReligion().toLowerCase().contains(value))
                result.add(f);
        }
        return result;
     }

    private void deleteFuneral(Funeral fn) {
        funeralService.deleteFuneral(fn);
    }

    private void createFuneral(Dead dead, Grave g) {
        Funeral fun = new Funeral();
        fun.setDead(dead);
        fun.getDead().setGrave(g);
        fun.setDate(new Date());
        Time time = new Time(System.currentTimeMillis());
        fun.setTime(time);
        funeralService.insertFuneral(fun);
    }

    private void editFuneral(Funeral f, Dead d, Grave g) {
        if (f.getDead().getId() != d.getId())
            f.setDead(d);
        if (f.getDead().getGrave().getId() != g.getId())
            f.getDead().setGrave(g);
        funeralService.updateFuneral(f);

    }
}