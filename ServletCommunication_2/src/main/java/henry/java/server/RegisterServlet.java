package henry.java.server;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns="/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection conn;
    private PreparedStatement ps;
	
	@Override
	public void init(ServletConfig config) {
		ServletContext context = config.getServletContext();
		String driverClass = context.getInitParameter("driverClass");
		String db = "jdbc:mariadb://mariadb.vamk.fi:3306/e2001017_communication";
		
		String dbUser = context.getInitParameter("db_user");
		String dbPassword = context.getInitParameter("db_password");
		
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(db, dbUser, dbPassword);
			ps = conn.prepareStatement("INSERT INTO random (email, password) VALUES (?, ?)");
			System.out.println(conn);
		} catch(SQLException | ClassNotFoundException  e)  {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			ps.setString(1, email);
			ps.setString(2, password);
			ps.executeUpdate();
			
			RequestDispatcher reqDis = request.getRequestDispatcher("login.html");
			reqDis.forward(request, response);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    RequestDispatcher reqDis = request.getRequestDispatcher("register.html");
	    reqDis.forward(request, response);
	}
	
	@Override
	public void destroy() {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

