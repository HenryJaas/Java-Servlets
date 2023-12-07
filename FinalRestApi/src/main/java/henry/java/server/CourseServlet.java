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

@WebServlet(urlPatterns="/courseServlet")
public class CourseServlet extends HttpServlet {
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
            String courseID = request.getParameter("courseID");
            if (courseID != null) {
                ps = conn.prepareStatement("SELECT * FROM courses WHERE courseID = ?");
                ps.setString(1, courseID);
            } else {
                ps = conn.prepareStatement("SELECT * FROM courses");
            }
            ResultSet rs = ps.executeQuery();
            
            // Convert the ResultSet to JSON
            JsonArray jsonArray = new JsonArray();
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("courseID", rs.getString("courseID"));
                jsonObject.addProperty("name", rs.getString("name"));
                jsonObject.addProperty("teacherName", rs.getString("teacherName"));
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

            // Get the course data from the JSON
            String courseID = jsonObject.get("courseID").getAsString();
            String name = jsonObject.get("name").getAsString();
            String teacherName = jsonObject.get("teacherName").getAsString();

            ps = conn.prepareStatement("INSERT INTO courses (courseID, name, teacherName) VALUES (?, ?, ?)");
            ps.setString(1, courseID);
            ps.setString(2, name);
            ps.setString(3, teacherName);
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

            // Get the course data from the JSON
            String courseID = jsonObject.get("courseID").getAsString();
            String name = jsonObject.get("name").getAsString();
            String teacherName = jsonObject.get("teacherName").getAsString();

            ps = conn.prepareStatement("UPDATE courses SET name = ?, teacherName = ? WHERE courseID = ?");
            ps.setString(1, name);
            ps.setString(2, teacherName);
            ps.setString(3, courseID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Code to handle DELETE requests
        try {
            ps = conn.prepareStatement("DELETE FROM courses WHERE courseID = ?");
            ps.setString(1, request.getParameter("courseID"));
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


