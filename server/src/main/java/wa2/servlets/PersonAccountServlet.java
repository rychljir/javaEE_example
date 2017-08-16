package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.PortableInterceptor.ACTIVE;
import wa2.entities.Account;
import wa2.entities.Client;
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
public class PersonAccountServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String personId = request.getParameter("id");

        if (personId != null) {
            List accounts = repos.getAccountsForPerson(personId);
            if (accounts == null) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                try {
                    JSONArray result = new JSONArray();
                    for (int i = 0; i < accounts.size(); i++) {
                        Account account = (Account)(((Object[])accounts.get(i))[0]);
                        JSONObject element = new JSONObject();
                        element.put("id", account.getIdaccount());
                        element.put("number", account.getNumber());
                        element.put("balance", account.getBalance());
                        element.put("currency", account.getCurrency());
                        result.put(element);
                    }
                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

