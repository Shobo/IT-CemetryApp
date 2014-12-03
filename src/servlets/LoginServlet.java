package servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Graveyard;
import model.User;
import service.GraveyardService;
import service.UserService;
import utils.PasswordEncryptor;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	@EJB
	UserService userService;

	@EJB
	GraveyardService graveyardServce;
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *      
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		//UserDAO dao = new UserDAO();
		HttpSession session = request.getSession();

		if (act == null) {
		} else if (act.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			boolean loggedIn = userService.login(username,password);
			if (loggedIn) {
				List<User> users = userService.getAll();
				List<Graveyard> graveyards = graveyardServce.getAll();
				session.setAttribute("allUsers", users);
				session.setAttribute("allGraveyards", graveyards);
				RequestDispatcher rd = request
						.getRequestDispatcher("LoginSucces.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request
						.getRequestDispatcher("LoginFailed.jsp");
				rd.forward(request, response);

			}

		}
	}

}
