<%@ page import="wa2.repositories.DBRepository" %>
<%@ page import="wa2.entities.Customer" %>
<%@ page import="java.util.List" %><%--
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

    tr:nth-child(even) {background-color: #f2f2f2}

    table {
        border-collapse: collapse;
    }

    body div{
        padding-left: 20px;
    }

    table, th, td {
        border: 1px solid black;
        padding: 3px;
        margin-bottom: 15px;
        margin-top: 15px;
    }

      thead{
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

      <h2>Seznam všech klientů</h2>

      <%
          int pageNumber = request.getParameter("pageNumber") == null ? 0 : Integer.parseInt(request.getParameter("pageNumber"));
          if(pageNumber<0) pageNumber = 0;
      %>

  <%
    DBRepository dbRepository = new DBRepository();

    List<Customer> customerList = dbRepository.getAllCustomers(pageNumber*10, 10);

  %>
      <table>
          <thead>
          <tr>
              <th>Id</th>
              <th>Jméno</th>
          </tr>
          </thead>
          <tbody>
          <%
          for (Customer c : customerList) {
      %>
      <tr>
          <td><%= c.getId()%></td><td><%= c.getName() %></td>
      </tr>
      <%
          }
      %>
          </tbody>
      </table>

      <%
          if(pageNumber>0){
      %>
      <a href="customers.jsp?pageNumber=<%= pageNumber - 1 %>">Previous</a>
      <%
          }
      %>

      <%
          if(pageNumber+1<(dbRepository.getNumberOfCustomers() / 10)){
      %>
      <a href="customers.jsp?pageNumber=<%= pageNumber + 1 %>">Next</a>
      <%
          }
      %>
      <br>
      <b>Stránka: <%= pageNumber+1 %></b>
  </div>

  </body>
</html>
