package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class AccountCreditServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountId = request.getParameter("id");

        if (accountId != null) {
            List credits = repos.getCreditForAccount(accountId);
            if (credits == null) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                try {
                    JSONArray result = new JSONArray();
                    for (int i = 0; i < credits.size(); i++) {
                        Credit credit = (Credit)(((Object[])credits.get(i))[0]);
                        JSONObject element = new JSONObject();
                        element.put("id", credit.getIdcredit());
                        element.put("value", credit.getValue());
                        element.put("percentage", credit.getPercentage());
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

