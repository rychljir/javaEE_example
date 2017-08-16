package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class PersonServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Person> personList = repos.getAllPersons();
        JSONArray result = new JSONArray();
        for (int i = 0; i < personList.size(); i++) {
            JSONObject element = new JSONObject();
            try {
                element.put("id", personList.get(i).getIdperson());
                element.put("firstname", personList.get(i).getFirstname());
                element.put("lastname", personList.get(i).getLastname());
                element.put("birth", personList.get(i).getBirth());
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

