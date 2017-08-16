<%@ page import="wa2.repositories.DBRepository" %>
<%@ page import="wa2.entities.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="wa2.entities.Car" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="wa2.entities.Address" %><%--
  Created by IntelliJ IDEA.
  User: mudrama1
  Date: 15.03.2017
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JPA - rychljir</title>
</head>

<style>
    #menu {
        list-style-type: none;
        width: 1030px;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333333;
    }

    #menu li {
        float: left;
    }

    #menu li a {
        display: block;
        color: white;
        text-align: center;
        padding: 16px;
        text-decoration: none;
    }

    #menu li a:hover {
        background-color: #111111;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2
    }

    table {
        border-collapse: collapse;
    }

    body div {
        padding-left: 20px;
    }

    table, th, td {
        border: 1px solid black;
        padding: 3px;
        margin-bottom: 15px;
        margin-top: 15px;
    }

    thead {
        background-color: #4CAF50;
    }


</style>
<body>

<ul id="menu">
    <li><a href="/customers">Seznam klientů</a></li>
    <li><a href="/cars">Seznam aut</a></li>
    <li><a href="/manufacturers">Seznam výrobců</a></li>
    <li><a href="/detail">Detail zákazníka</a></li>
    <li><a href="/newcar">Přidat auto</a></li>
    <li><a href="/newadress">Přidat adresu</a></li>
    <li><a href="/deletemanufacturer">Smazat výrobce</a></li>
    <li><a href="/deletecustomer">Smazat zákazníka</a></li>
</ul>

<div>
    <h2>Detail zákazníka</h2>

    <%
        int selectedCust = request.getParameter("cust") == null ? -1 : Integer.parseInt(request.getParameter("cust"));
    %>
    <%
        DBRepository dbRepository = new DBRepository();
        List addressList = new ArrayList();
        List carList = new ArrayList();
        List<Customer> customers = dbRepository.getAllCustomers();
        if (selectedCust != -1) {
            addressList = dbRepository.getAddressForCustomer(selectedCust);
            carList = dbRepository.getCarsForCustomer(selectedCust);
        }

    %>
    <form action="detail.jsp">
        <select name="cust">
            <%
                for (int i = 0; i < customers.size(); i++) {
            %>
            <option value="<%=customers.get(i).getId()%>"
                    <%
                        if (customers.get(i).getId() == selectedCust) {
                    %>
                    selected
                    <%
                        }
                    %>
            ><%= customers.get(i).getName() %>
            </option>
            <%
                }
            %>
        </select>
        <input type="submit" value="zvolit">
    </form>
    <% if (carList != null && carList.size() > 0) {
    %>
    <h2>Auta</h2>


    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Název</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < carList.size(); i++) {
        %>
        <tr>
            <td><%= ((Car) ((Object[]) carList.get(i))[1]).getId()%>
            </td>
            <td><%= ((Car) ((Object[]) carList.get(i))[1]).getCarName()%>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%
        }

        if (addressList != null && addressList.size() > 0) {
    %>
    <h2>Adresy</h2>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Lokace</th>
            <th>Pokoj</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < addressList.size(); i++) {
        %>
        <tr>
            <td><%= ((Address) ((Object[]) addressList.get(i))[1]).getId()%>
            </td>
            <td><%= ((Address) ((Object[]) addressList.get(i))[1]).getLocation()%>
            </td>
            <td><%= ((Address) ((Object[]) addressList.get(i))[1]).getRoom()%>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>

</div>
</body>
</html>
