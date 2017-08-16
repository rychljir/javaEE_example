package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Account;
import wa2.entities.Client;
import wa2.entities.Credit;
import wa2.entities.Mortgage;
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
public class CreditServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Credit credit = null;
        if (id != null) {
            credit = repos.getCredit(id);
            if (credit == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();

                    result.put("id", credit.getIdcredit());
                    result.put("value", credit.getValue());
                    result.put("percentage", credit.getPercentage());
                    result.put("acount", credit.getAccount().getNumber());

                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Credit> creditList = repos.getAllCredits();
            JSONArray result = new JSONArray();
            for (int i = 0; i < creditList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", creditList.get(i).getIdcredit());
                    element.put("value", creditList.get(i).getValue());
                    element.put("percentage", creditList.get(i).getPercentage());
                    Account a = creditList.get(i).getAccount();
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

        Integer percentage = null;
        if ((numberParam = request.getParameter("percentage")) != null) {
            percentage = Integer.parseInt(numberParam);
        } else {
            percentage = 0;
        }

        String idaccount = request.getParameter("idaccount");

        Credit credit = new Credit();
        credit.setValue(value);
        credit.setPercentage(percentage);
        repos.saveCredit(credit, idaccount);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityId = request.getParameter("id");
        if (entityId != null) {
            repos.deleteCredit(entityId);
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
        Integer percentage = null;
        if ((numberParam = request.getParameter("percentage")) != null) {
            percentage = Integer.parseInt(numberParam);
        }
        if (id != null) {
            repos.updateCredit(id, value, percentage, idaccount);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

