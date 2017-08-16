package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Client;
import wa2.entities.Employee;
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
public class AllEmployeesServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Employee> employees = repos.getAllEmployees();
        if (employees!= null) {
            try {
                JSONArray result = new JSONArray();
                for (int i = 0; i < employees.size(); i++) {
                    Employee client = employees.get(i);
                    JSONObject element = new JSONObject();
                    element.put("id", client.getIdperson());
                    element.put("firstname", client.getFirstname());
                    element.put("lastname", client.getLastname());
                    element.put("birth", client.getBirth());
                    element.put("salary", client.getSalary());
                    result.put(element);
                }

                response.setContentType("application/json");
                response.getWriter().println(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}

