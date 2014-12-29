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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@WebServlet("/ConcessionServlet")
public class ConcessionServlet extends HttpServlet {

    @EJB
    ConcessionService concessionService;

    @EJB
    ReceiptService receiptService;

    @EJB
    OwnerService ownerService;

    @EJB
    GraveyardService graveyardService;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        HttpSession session = request.getSession();
        List<Concession> concessions = null;

        switch (act) {
            case Constants.DELETE :
                int nr = Integer.parseInt(request.getParameter("selected-con"));
                deleteConcession(concessionService.getAll().get(nr));
                concessions = concessionService.getAll();
                session.setAttribute("option", Constants.CONCESSION_MANAGEMENT);
                break;
            case Constants.AUTOCOMPLETE :
                response.setContentType("application/json");
                response.getWriter().print(jsonOwners(request.getParameter("filter"), request.getParameter("field")));
                return;
            case Constants.ADD :
                session.setAttribute("graveyards", graveyardService.getAll());
                RequestDispatcher rd = request.getRequestDispatcher(Constants.CONCESSION_MANAGEMENT_PAGE);
                rd.forward(request, response);
                break;
            case Constants.VIEW_CONCESSIONS :
                int year = Calendar.getInstance().get(Calendar.YEAR);
                if (request.getParameter("year") != null)
                    year = Integer.parseInt(request.getParameter("year"));
                concessions = filterByYear(year);
                session.setAttribute("option", act);
                break;
            case "loadGraveDetails" :
                String toLoad = request.getParameter("field");
                String res = "";
                int graveyard = Integer.parseInt(request.getParameter("graveyard"));
                if (toLoad.equals("parcel"))
                    res = jsonParcels(graveyardService.getAll().get(graveyard));
                if (toLoad.equals("grave")) {
                    int parcel = Integer.parseInt(request.getParameter("parcel"));
                    res = jsonGraves(graveyardService.getAll().get(graveyard).getParcels().get(parcel));
                }
                response.setContentType("application/json");
                response.getWriter().print(res);
                return;
            case Constants.CONCESSION_MANAGEMENT :
                concessions = concessionService.getAll();
                session.setAttribute("option", act);
        }
        if (concessions != null)
            session.setAttribute("concessions", concessions);
        RequestDispatcher rd = request.getRequestDispatcher(Constants.CONCESSIONS_PAGE);
        rd.forward(request, response);
    }

    private void deleteConcession(Concession con) {
        for (Receipt r : con.getReceipts())
            receiptService.deleteReceipt(r);
        concessionService.deleteConcession(con);
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

    private String jsonParcels(Graveyard g) {
        List<Parcel> parcels = g.getParcels();
        JsonArray res = new JsonArray();
        for (Parcel p : parcels) {
            JsonObject obj = new JsonObject();
            obj.addProperty("number", p.getNumber());
            res.add(obj);
        }
        return res.toString();
    }

    private String jsonGraves(Parcel p) {
        List<Grave> graves = p.getGraves();
        JsonArray res = new JsonArray();
        for (Grave g : graves) {
            if (g.getConcessions().size() == 0) {
                JsonObject obj = new JsonObject();
                obj.addProperty("number", g.getNumber());
                obj.addProperty("surface", g.getSurface());
                res.add(obj);
            }
        }
        return res.toString();
    }

    private String jsonOwners(String filter, String field) throws UnsupportedEncodingException {
        filter = new String(filter.getBytes("iso-8859-1"), "UTF-8");
        JsonArray res = new JsonArray();
        List<Owner> owners = ownerService.getAll();
        for (Owner o : owners) {
            String name = o.getLastName() + " " + o.getFirstName();
            if (field.equals("name") && !name.toLowerCase().contains(filter.toLowerCase()))
                    continue;
            if (field.equals("cnp") && !((Integer)o.getId()).toString().contains(filter))
                continue;
            if (field.equals("address") && !o.getAddress().contains(filter))
                continue;
            JsonObject obj = new JsonObject();
            obj.addProperty("name", o.getLastName() + " " + o.getFirstName());
            obj.addProperty("cnp", o.getId());
            obj.addProperty("address", o.getAddress());
            res.add(obj);
        }
        System.out.print(res.size());
        return res.toString();
    }
}
