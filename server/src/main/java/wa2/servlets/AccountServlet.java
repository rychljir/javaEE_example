package wa2.servlets;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Account;
import wa2.entities.Client;
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
public class AccountServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Account account = null;
        if (id != null) {
            account = repos.getAccount(id);
            if (account == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();

                    result.put("id", account.getIdaccount());
                    result.put("number", account.getNumber());
                    result.put("balance", account.getBalance());
                    result.put("currency", account.getCurrency());
                    Person p = account.getPerson();
                    if(p!=null){
                        result.put("person", p.getFirstname() + " " + p.getLastname());
                    }else{
                        result.put("person", "unowned");
                    }

                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Account> accountList = repos.getAllAccounts();
            JSONArray result = new JSONArray();
            for (int i = 0; i < accountList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", accountList.get(i).getIdaccount());
                    element.put("number", accountList.get(i).getNumber());
                    element.put("balance", accountList.get(i).getBalance());
                    element.put("currency", accountList.get(i).getCurrency());
                    Person p = accountList.get(i).getPerson();
                    if(p!=null){
                        element.put("person", p.getLastname() + " " + p.getFirstname());
                    }else{
                        element.put("person", "unowned");
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
        String numberParam;
        String idPerson = request.getParameter("idperson");
        Integer number = null;
        if ((numberParam = request.getParameter("number")) != null) {
            number = Integer.parseInt(numberParam);
        } else {
            number = 0;
        }
        Integer balance = null;
        if ((numberParam = request.getParameter("balance")) != null) {
            balance = Integer.parseInt(numberParam);
        } else {
            balance = 0;
        }
        String currency = request.getParameter("currency");
        if (currency == null) {
            currency = "---";
        }
        Account account = new Account();
        account.setNumber(number);
        account.setCurrency(currency);
        account.setBalance(balance);
        repos.saveAccount(account, idPerson);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityId = request.getParameter("id");
        if (entityId != null) {
            repos.deleteAccount(entityId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //UPDATE
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        String numberParam;
        if ((numberParam = request.getParameter("id")) != null) {
            id = Integer.parseInt(numberParam);
        }
        String person = request.getParameter("idperson");

        Integer number = null;
        if ((numberParam = request.getParameter("number")) != null) {
            number = Integer.parseInt(numberParam);
        }
        Integer balance = null;
        if ((numberParam = request.getParameter("balance")) != null) {
            balance = Integer.parseInt(numberParam);
        }
        String currency = request.getParameter("currency");
        if (id != null) {
            repos.updateAccount(id, number, balance, currency, person);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

