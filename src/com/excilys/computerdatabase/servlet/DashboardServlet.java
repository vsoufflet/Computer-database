package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDAO;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ComputerDAO myComputerDAO = ComputerDAO.getInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		List<Computer> computerList;
		try {
			String search = request.getParameter("search");
			
			if(search!=null){
					computerList = myComputerDAO.filterByName(search);
					request.setAttribute("computerList", computerList);
			}
			else{
				computerList = myComputerDAO.getComputerList();
				request.setAttribute("computerList", computerList);
			}
			if(computerList.size()<=1){
				request.setAttribute("NombreOrdinateurs", computerList.size()+" computer found");
			}
			else{
				request.setAttribute("NombreOrdinateurs", computerList.size()+" computers found");
			}
			
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

