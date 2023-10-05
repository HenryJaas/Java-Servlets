package henry.java.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/TargetServlet")
public class TargetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {		
		HttpSession session = req.getSession(false);
		
	    String userName = (String) session.getAttribute("userName");
        String passWord = (String) session.getAttribute("passWord");
        
        try {
			Class.forName("org.mariadb.jdbc.Driver");
        }
        catch(ClassNotFoundException  e)  {
			e.printStackTrace();
		}
		
        try {
            Connection conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2001017_cookieUser", userName, passWord);
            PrintWriter out = res.getWriter();
            out.println("Connection successful!");
            conn.close();
        } catch(SQLException  e)  {
			e.printStackTrace();
			res.sendRedirect("index.html");
		}
		
	}
}


