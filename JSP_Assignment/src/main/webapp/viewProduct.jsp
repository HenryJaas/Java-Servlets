<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
<%
    Class.forName("org.mariadb.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi:3306/e2001017_JSP", "e2001017", "ThehTX8UrtD");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
%>

<!DOCTYPE html>
<html>
<head>
    <title>View Products</title>
</head>
<body>
    <h1>Products</h1>

    <% while(rs.next()) { %>

        <h2><%= rs.getString(1) %></h2> <!-- Name -->
        <p>ID: <%= rs.getInt(2) %></p> <!-- ID -->
        <p>Price: <%= rs.getFloat(3) %></p> <!-- Price -->

    <% } %>

</body>
</html>

<%
    rs.close();
    stmt.close();
    con.close();
%>