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
public class ClientServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Client client = null;
        if (id != null) {
            client = repos.getClient(id);
            if (client == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();

                    result.put("id", client.getIdperson());
                    result.put("firstname", client.getFirstname());
                    result.put("lastname", client.getLastname());
                    result.put("birth", client.getBirth());
                    result.put("number", client.getNumber());

                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Client> clientList = repos.getAllClients();
            JSONArray result = new JSONArray();
            for (int i = 0; i < clientList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", clientList.get(i).getIdperson());
                    element.put("firstname", clientList.get(i).getFirstname());
                    element.put("lastname", clientList.get(i).getLastname());
                    element.put("birth", clientList.get(i).getBirth());
                    element.put("number", clientList.get(i).getNumber());
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
        String firstname = request.getParameter("firstname");
        if (firstname == null) {
            firstname = "---";
        }
        String lastname = request.getParameter("lastname");
        if (lastname == null) {
            lastname = "---";
        }
        String birth = request.getParameter("birth");
        if (birth == null) {
            birth = "---";
        }
        Integer number = null;
        String numberParam;
        if ((numberParam = request.getParameter("number")) != null) {
            number = Integer.parseInt(numberParam);
        } else {
            number = 0;
        }
        Client client = new Client();
        client.setFirstname(firstname);
        client.setLastname(lastname);
        client.setBirth(birth);
        client.setNumber(number);
        repos.saveClient(client);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityId = request.getParameter("id");
        if (entityId != null) {
            repos.deleteClient(entityId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //UPDATE
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String birth = request.getParameter("birth");
        Integer number = null;
        String numberParam;
        if ((numberParam = request.getParameter("number")) != null) {
            number = Integer.parseInt(numberParam);
        }
        if (id != null) {
            repos.updateClient(id, firstname, lastname, birth, number);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

