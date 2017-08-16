package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Account;
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
public class AccountMortgageServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("id");

        if (accountId != null) {
            List mortgages = repos.getMortgageForAccount(accountId);
            if (mortgages == null) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                try {
                    JSONArray result = new JSONArray();
                    for (int i = 0; i < mortgages.size(); i++) {
                        Mortgage mortgage = (Mortgage)(((Object[])mortgages.get(i))[0]);
                        JSONObject element = new JSONObject();
                        element.put("id", mortgage.getIdmortgage());
                        element.put("value", mortgage.getValue());
                        element.put("years", mortgage.getYears());
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

