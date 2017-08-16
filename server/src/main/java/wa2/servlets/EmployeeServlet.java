package wa2.servlets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import wa2.entities.Address;
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
public class EmployeeServlet extends HttpServlet {
    protected DBRepository repos = new DBRepository();

    //READ
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Employee employee = null;
        if (id != null) {
            employee = repos.getEmployee(id);
            if (employee == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                try {
                    JSONObject result = new JSONObject();

                    result.put("id", employee.getIdperson());
                    result.put("firstname", employee.getFirstname());
                    result.put("lastname", employee.getLastname());
                    result.put("birth", employee.getBirth());
                    result.put("salary", employee.getSalary());

                    response.setContentType("application/json");
                    response.getWriter().println(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<Employee> employeeList = repos.getAllEmployees();
            JSONArray result = new JSONArray();
            for (int i = 0; i < employeeList.size(); i++) {
                JSONObject element = new JSONObject();
                try {
                    element.put("id", employeeList.get(i).getIdperson());
                    element.put("firstname", employeeList.get(i).getFirstname());
                    element.put("lastname", employeeList.get(i).getLastname());
                    element.put("birth", employeeList.get(i).getBirth());
                    element.put("salary", employeeList.get(i).getSalary());
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
        Integer salary = null;
        String numberParam;
        if ((numberParam = request.getParameter("salary")) != null) {
            salary = Integer.parseInt(numberParam);
        } else {
            salary = 0;
        }
        Employee employee = new Employee();
        employee.setFirstname(firstname);
        employee.setLastname(lastname);
        employee.setBirth(birth);
        employee.setSalary(salary);
        repos.saveEmployee(employee);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            repos.deleteEmployee(id);
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
        Integer salary = null;
        String numberParam;
        if ((numberParam = request.getParameter("salary")) != null) {
            salary = Integer.parseInt(numberParam);
        }
        if (id != null) {
            repos.updateEmployee(id, firstname, lastname, birth, salary);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}

