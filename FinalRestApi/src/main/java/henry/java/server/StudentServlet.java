package henry.java.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns="/studentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private PreparedStatement ps;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi:3306/e2001017_finalProject", "e2001017", "ThehTX8UrtD");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Code to handle GET requests
        try {
            String studentID = request.getParameter("studentID");
            if (studentID != null) {
                ps = conn.prepareStatement("SELECT * FROM students WHERE studentID = ?");
                ps.setString(1, studentID);
            } else {
                ps = conn.prepareStatement("SELECT * FROM students");
            }
            ResultSet rs = ps.executeQuery();
            
            // Convert the ResultSet to JSON
            JsonArray jsonArray = new JsonArray();
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("studentID", rs.getString("studentID"));
                jsonObject.addProperty("firstname", rs.getString("firstname"));
                jsonObject.addProperty("lastname", rs.getString("lastname"));
                jsonObject.addProperty("email", rs.getString("email"));
                jsonArray.add(jsonObject);
            }
            
            // Write the JSON to the response
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(jsonArray));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Code to handle POST requests
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);

            // Get the student data from the JSON
            String studentID = jsonObject.get("studentID").getAsString();
            String firstname = jsonObject.get("firstname").getAsString();
            String lastname = jsonObject.get("lastname").getAsString();
            String email = jsonObject.get("email").getAsString();

            ps = conn.prepareStatement("INSERT INTO students (studentID, firstname, lastname, email) VALUES (?, ?, ?, ?)");
            ps.setString(1, studentID);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.setString(4, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Code to handle PUT requests
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
            JsonObject jsonObject = new Gson().fromJson(sb.toString(), JsonObject.class);

            // Get the student data from the JSON
            String studentID = jsonObject.get("studentID").getAsString();
            String firstname = jsonObject.get("firstname").getAsString();
            String lastname = jsonObject.get("lastname").getAsString();
            String email = jsonObject.get("email").getAsString();

            ps = conn.prepareStatement("UPDATE students SET firstname = ?, lastname = ?, email = ? WHERE studentID = ?");
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, studentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Code to handle DELETE requests
        try {
            ps = conn.prepareStatement("DELETE FROM students WHERE studentID = ?");
            ps.setString(1, request.getParameter("studentID"));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

