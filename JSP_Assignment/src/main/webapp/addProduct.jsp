<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
<%
    String name = request.getParameter("name");
    int id = Integer.parseInt(request.getParameter("id"));
    float price = Float.parseFloat(request.getParameter("price"));

    Class.forName("org.mariadb.jdbc.Driver");
    Connection con = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi:3306/e2001017_JSP", "e2001017", "ThehTX8UrtD");
    PreparedStatement ps = con.prepareStatement("INSERT INTO Product VALUES (?, ?, ?)");
    
    ps.setString(1, name);
    ps.setInt(2, id);
    ps.setFloat(3, price);
    
    int i = ps.executeUpdate();
    
    if(i > 0) {
        out.print("Product added successfully");
    }
%>