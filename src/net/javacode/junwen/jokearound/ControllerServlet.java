package net.javacode.junwen.jokearound;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.javacode.junwen.jokearound.Joke;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private JokeDAO jokeDAO;
	 
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
 
        jokeDAO = new JokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
 
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		 
        try {
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/loginuser":
                loginUser(request, response);
                break;
            case "/signup":
            	signupUser(request, response);
            	break;
            case "/loginpage":
            	loginPage(request, response);
            	break;
            case "/logout":
            	logoutUser(request, response);
            	break;
            case "/initdb":
            	InitDb(request, response);
            	break;
            case "/home":
            	Home(request, response);
            	break;
            case "/insert":
                insertJoke(request, response);
                break;
            case "/delete":
                deleteJoke(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateJoke(request, response);
                break;
            case "/jokeError":
            	insertError(request,response);
            	break;
            case "/submitReview":
            	submitReview(request, response);
            	break;
            case "/UserProfile":
            	userProfile(request, response);
            	break;
            case "/listfavuser":
            	listFavuser(request, response);
            	break;
            case "/addfavuser":
            	addFavUser(request, response);
            	break;
            case "/delfavuser":
            	delFavUser(request, response);
            	break;
            case "/delfavjoke":
            	delFavJoke(request, response);
            	break;
            case "/addfavjoke":
            	addFavJoke(request, response);
            	break;
            case "/insertUser":
            	insertUser(request, response);
            	break;
            case "/stats":
            	showStats(request, response);
            	break;
            case "/highlyRevJokes":
            	highlyRevJokes(request, response);
            	break;
            case "/mostActiveUser":
            	mostActiveUser(request, response);
            	break;
            case "/checkCommonFrnd":
            	checkCommonFrnd(request, response);
            	break;
            case "/userNeverPstExcellentJ":
            	userNeverPstExcellentJ(request, response);
            	break;
            case "/userNeverPstPoorR":
            	userNeverPstPoorR(request, response);
            	break;
            case "/userAlwaysPstPoorR":
            	userAlwaysPstPoorR(request, response);
            	break;
            case "/userNeverRecPoorR":
            	userNeverRecPoorR(request, response);
            	break;
            case "/userPairExcellentR":
            	userPairExcellentR(request, response);
            	break;
            default:
                listJoke(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		
		//System.out.println(uname);
		if(uname.equals("root") && pwd.equals("pass1234"))
		{
			 HttpSession session = request.getSession();
	        	session.setAttribute("username", "root");
	        	session.setAttribute("userid", 1);
	        	session.setAttribute("isroot", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("InitDb.jsp");
            dispatcher.forward(request, response);
         
            return;
		}
			
				
        User user = jokeDAO.loginCheck(uname, pwd);
        
        if(user.username != "") {
        	HttpSession session = request.getSession();
        	session.setAttribute("username", user.username);
        	session.setAttribute("userid", user.userid);
        	session.setAttribute("isroot", user.isroot);
        	
        	if (user.isroot) {
        		RequestDispatcher dispatcher = request.getRequestDispatcher("InitDb.jsp");
                dispatcher.forward(request, response);
        	}else {
        		RequestDispatcher dispatcher = request.getRequestDispatcher("home");
                dispatcher.forward(request, response);
        	}
        }else {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
            dispatcher.forward(request, response);
        }
		
        
    }
	
	private void signupUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
		List<User> registerUsers = jokeDAO.listRegisterUsers();
        request.setAttribute("registerUsers", registerUsers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("SignUp.jsp");
        dispatcher.forward(request, response);
    }
	
	private void loginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
        dispatcher.forward(request, response);
    }
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeForm.jsp");
        dispatcher.forward(request, response);
    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("jokeID"));
        Joke existingJoke = jokeDAO.getJoke(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("JokeForm.jsp");
        request.setAttribute("joke", existingJoke);
        dispatcher.forward(request, response);
 
    }
	
	private void Home(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		String uid = session.getAttribute("userid").toString();
		List<Joke> listJoke = jokeDAO.listAllJokes(uid);
        request.setAttribute("listJoke", listJoke);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        dispatcher.forward(request, response);
        	
    }
	
	private void InitDb(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		
		//System.out.println(this.getClass().getClassLoader().getResourceAsStream("files/jokearound.txt").read());
		//ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		//InputStream is = classloader.getResourceAsStream("files/jokearound.txt");
		//BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("files/jokearound_revised.txt");
		InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		for (String line; (line = reader.readLine()) != null;) {
		    jokeDAO.executeSql(line);
		}
		
		response.sendRedirect("Home.jsp");
        	
    }
	
	private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		
        	HttpSession session = request.getSession();
        	session.removeAttribute("username");
        	session.removeAttribute("userid");
        	session.removeAttribute("isroot");
        	session.invalidate();
        	
        	response.sendRedirect("Login.jsp");
        	
    }
	
	private void listJoke(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		HttpSession session = request.getSession();
		String uid = session.getAttribute("userid").toString();
		List<Joke> listJoke = jokeDAO.listAllJokes(uid);
        request.setAttribute("listJoke", listJoke);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        dispatcher.forward(request, response);
    }
	
	 private void insertJoke(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
		 	//check if user already inserted 5 jokes for today
		 	if (!jokeDAO.checkJokeInsert(userID)) {
		        String title = request.getParameter("title");
		        String description = request.getParameter("description");
		        String tags = request.getParameter("tags");
		        String[] tagsplit = tags.split(";");
		 
		        Joke newJoke = new Joke(userID, title, description);
		        int jokeID = jokeDAO.insertJoke(newJoke);
		        for(String tag : tagsplit)
		        {
		        	Tag newTag = new Tag(jokeID, tag);
		        	jokeDAO.insertTag(newTag);
		        }
		        
		        response.sendRedirect("listJoke");
		 	}else {
		 		response.sendRedirect("jokeError");
		 	}
	    }
	 
	 	private void insertError(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        RequestDispatcher dispatcher = request.getRequestDispatcher("jokeInsertError.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	    private void updateJoke(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException {
	    	
	    	int jokeID =  Integer.parseInt(request.getParameter("jokeID"));
	        String title = request.getParameter("title");
	        String description = request.getParameter("description");
	        String tags = request.getParameter("tags");
	 
	        Joke joke = new Joke(jokeID, title, description, tags);
	        jokeDAO.updateJoke(joke);
	        response.sendRedirect("listJoke");
	    }
	 
	    private void deleteJoke(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	 
	        jokeDAO.deleteJoke(id);
	        response.sendRedirect("listJoke");
	 
	    }
	    
	    private void submitReview(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
		 	//check if user already inserted 5 reviews for today
		 	if (!jokeDAO.checkUserReview(userID)) {
		 		
		 		int jokeID = Integer.parseInt(request.getParameter("jokeID"));
		        String score = request.getParameter("score");
		        String remark = request.getParameter("remark");
		 
		        Review newReview = new Review(jokeID, userID, score, remark);
		        //System.out.println(newReview.getRemark());
		        jokeDAO.insertReview(newReview);
		        
		        response.sendRedirect("listJoke");
		 	}else {
		 		response.sendRedirect("jokeError");
		 	}
	    }
	    
	    private void userProfile(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
			String userName = request.getParameter("userName");
			List<Joke> listJoke = jokeDAO.listUserJokes(userName);
	        request.setAttribute("listJoke", listJoke);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserProfile.jsp");
	        dispatcher.forward(request, response);
	        	
	    }
	    
	    private void listFavuser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	    	
	    	HttpSession session = request.getSession();
			int userid = Integer.valueOf(session.getAttribute("userid").toString());
			List<User> listUser = jokeDAO.listFavuser(userid);
	        request.setAttribute("listUser", listUser);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("FavUsers.jsp");
	        dispatcher.forward(request, response);
	        	
	    }
	    
	    private void addFavUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
	        int friendID = Integer.valueOf(request.getParameter("friendID"));
	 
	        jokeDAO.addFavUser(userID, friendID);
	        
	        response.sendRedirect("listJoke");

	    }
	    
	    private void delFavUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
	        int friendID = Integer.valueOf(request.getParameter("friendID"));
	 
	        jokeDAO.delFavUser(userID, friendID);
	        
	        response.sendRedirect("listJoke");

	    }
	    
	    private void addFavJoke(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
	        int jokeID = Integer.valueOf(request.getParameter("jokeID"));
	 
	        jokeDAO.addFavJoke(userID, jokeID);
	        
	        response.sendRedirect("listJoke");

	    }
	    
	    private void delFavJoke(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
		 	HttpSession session = request.getSession();
		 	int userID = Integer.parseInt(session.getAttribute("userid").toString());
		 	
	        int jokeID = Integer.valueOf(request.getParameter("jokeID"));
	 
	        jokeDAO.delFavJoke(userID, jokeID);
	        
	        response.sendRedirect("listJoke");

	    }
	    
	    private void insertUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
		 	
	        String username = request.getParameter("username");
	        String email = request.getParameter("email");
	        String firstname = request.getParameter("firstname");
	        String lastname = request.getParameter("lastname");
	        String password = request.getParameter("password1");
	 
	        User user = new User(username,firstname, lastname, password, email);
	        int userId = jokeDAO.insertUser(user);
	        if(userId > 0)
	        {
	        	//set sessions
	        	HttpSession session = request.getSession();
	        	session.setAttribute("username", username);
	        	session.setAttribute("userid", userId);
	        	session.setAttribute("isroot", false);
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("home");
                dispatcher.forward(request, response);
	        }else {
	        	response.sendRedirect("loginpage");
	        }

	    }
	    
	    private void showStats(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("Statistics.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void highlyRevJokes(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("HighlyRevJokes.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void mostActiveUser(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("MostActiveUser.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void checkCommonFrnd(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("CheckCommonFrnd.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void userNeverPstExcellentJ(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserNeverPstExcellentJ.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void userNeverPstPoorR(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserNeverPstPoorR.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void userAlwaysPstPoorR(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAlwaysPstPoorR.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void userNeverRecPoorR(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserNeverRecPoorR.jsp");
	        dispatcher.forward(request, response);
	    }
	    
	    private void userPairExcellentR(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException, SQLException {
	    	//query data for default link: users posted multi-jokes on same day
			//List<User> registerUsers = jokeDAO.listRegisterUsers();
	        //request.setAttribute("registerUsers", registerUsers);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserPairExcellentR.jsp");
	        dispatcher.forward(request, response);
	    }
	    
}
