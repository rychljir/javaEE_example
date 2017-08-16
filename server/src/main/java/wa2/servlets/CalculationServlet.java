package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.communication.Publisher;
import wa2.entities.Calculation;
import wa2.entities.Credit;
import wa2.repositories.DBRepository;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Speedy on 17. 5. 2017.
 */
public class CalculationServlet extends HttpServlet {
    protected Publisher publisher;
    protected DBRepository repos;

    public CalculationServlet() throws JMSException {
        publisher = new Publisher();
        repos = new DBRepository();
    }

    //RETRIEVE
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        Calculation calculation = null;
        if (uuid != null) {
            calculation = repos.getCalculation(uuid);
            if (calculation == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                if (calculation.getResult() != null) {
                    try {
                        JSONObject element = new JSONObject();
                        element.put("calculation", calculation.getResult());
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType("application/json");
                        response.getWriter().println(element);
                        repos.deleteCalculation(calculation.getUuid());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    //CREATE
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String age = request.getParameter("age");
            String years = request.getParameter("years");
            Calculation calculation = new Calculation();
            String uuid = String.valueOf(UUID.randomUUID());
            calculation.setUuid(uuid);
            repos.saveCalculation(calculation);
            publisher.sendCalculation(uuid, age + ";" + years);
            JSONObject element = new JSONObject();
            element.put("uuid", uuid);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().println(element);
        } catch (JMSException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

