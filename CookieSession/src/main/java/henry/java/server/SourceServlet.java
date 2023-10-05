package henry.java.server;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/SourceServlet")
public class SourceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String passWord = req.getParameter("passWord");
		
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(60);
		
		
		session.setAttribute("userName", userName);
		session.setAttribute("passWord", passWord);
		
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		out.println("<a href='TargetServlet'> Click here to get the username</a>");
	}
}