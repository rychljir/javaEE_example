package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Address;
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
public class AddressServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Address address = null;
        if (id != null) {
            address = repos.getAddress(id);
            if (address == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();
                    result.put("id", address.getIdaddress());
                    result.put("street", address.getStreet());
                    result.put("city", address.getCity());
                    result.put("postal", address.getPostal());
                    response.setContentType("application/json");
                    response.getWriter().println(result);
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Address> addressList = repos.getAllAddress();
            JSONArray result = new JSONArray();
            for (int i = 0; i < addressList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", addressList.get(i).getIdaddress());
                    element.put("street", addressList.get(i).getStreet());
                    element.put("city", addressList.get(i).getCity());
                    element.put("postal", addressList.get(i).getPostal());
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
        String street = request.getParameter("street");
        if (street == null) {
            street = "---";
        }
        String city = request.getParameter("city");
        if (city == null) {
            city = "---";
        }
        Integer postal = null;
        String numberParam;
        if ((numberParam = request.getParameter("postal")) != null) {
            postal = Integer.parseInt(numberParam);
        } else {
            postal = 0;
        }
        Address address = new Address();
        address.setStreet(street);
        address.setPostal(postal);
        address.setCity(city);
        repos.saveAdress(address);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entityId = request.getParameter("id");
        if (entityId != null) {
            repos.deleteAddress(entityId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //UPDATE
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        Integer postal = null;
        String numberParam;
        if ((numberParam = request.getParameter("postal")) != null) {
            postal = Integer.parseInt(numberParam);
        }
        if (id != null) {
            repos.updateAddress(id, street, city, postal);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

