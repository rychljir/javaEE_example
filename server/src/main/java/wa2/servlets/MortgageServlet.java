package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Account;
import wa2.entities.Credit;
import wa2.entities.Mortgage;
import wa2.entities.Person;
import wa2.repositories.DBRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Speedy on 17. 5. 2017.
 */
public class MortgageServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Mortgage mortgage = null;
        if (id != null) {
            mortgage = repos.getMortgage(id);
            if (mortgage == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();

                    result.put("id", mortgage.getIdmortgage());
                    result.put("value", mortgage.getValue());
                    result.put("years", mortgage.getYears());
                    result.put("acount", mortgage.getAccount().getNumber());

                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Mortgage> mortgageList = repos.getAllMortgages();
            JSONArray result = new JSONArray();
            for (int i = 0; i < mortgageList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", mortgageList.get(i).getIdmortgage());
                    element.put("value", mortgageList.get(i).getValue());
                    element.put("years", mortgageList.get(i).getYears());
                    Account a = mortgageList.get(i).getAccount();
                    if(a!=null){
                        element.put("account", a.getNumber());
                    }else{
                        element.put("account", "unowned");
                    }
                    result.put(element);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().println(result);
        }
    }

    //CREATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer value = null;
        String numberParam;
        if ((numberParam = request.getParameter("value")) != null) {
            value = Integer.parseInt(numberParam);
        } else {
            value = 0;
        }

        Integer years = null;
        if ((numberParam = request.getParameter("years")) != null) {
            years = Integer.parseInt(numberParam);
        } else {
            years = 0;
        }

        String idaccount = request.getParameter("idaccount");

        Mortgage mortgage = new Mortgage();
        mortgage.setValue(value);
        mortgage.setYears(years);
        repos.saveMortgage(mortgage, idaccount);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            repos.deleteMortgage(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //UPDATE
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String idaccount = request.getParameter("idaccount");
        Integer value = null;
        String numberParam;
        if ((numberParam = request.getParameter("value")) != null) {
            value = Integer.parseInt(numberParam);
        }
        Integer years = null;
        if ((numberParam = request.getParameter("years")) != null) {
            years = Integer.parseInt(numberParam);
        }
        if (id != null) {
            repos.updateMortgage(id, value, years, idaccount);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

