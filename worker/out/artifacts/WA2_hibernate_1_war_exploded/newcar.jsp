<%@ page import="wa2.entities.Car" %>
<%@ page import="wa2.entities.Customer" %>
<%@ page import="wa2.entities.Manufacturer" %>
<%@ page import="wa2.repositories.DBRepository" %>
<%@ page import="java.util.List" %>
<%--
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
      width:1030px;
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

      <h2>Nové auto</h2>

  <%
    DBRepository dbRepository = new DBRepository();

    List<Customer> customers = dbRepository.getAllCustomers();
    List<Manufacturer> manufacturers = dbRepository.getAllManufacturers();

  %>
      <form action="newcar" method="post">
          Název: <input type="text" name="name"><br>
          Výrobce:
          <select name="mans">
              <%
                  for (int i = 0; i < manufacturers.size(); i++) {
                    %>
                      <option value="<%=manufacturers.get(i).getId()%>">
                          <%= manufacturers.get(i).getName() %>
                      </option>
                    <%
                  }
              %>
          </select>
          <br>
          Zákazník:
          <select name="cust">
              <option value="-1" selected> </option>
              <%
                  for (int i = 0; i < customers.size(); i++) {
              %>
              <option value="<%=customers.get(i).getId()%>">
                  <%= customers.get(i).getName() %>
              </option>
              <%
                  }
              %>
          </select>
          <br>
          <input type="submit" value="přidat">
      </form>
  </div>
  </body>
</html>
