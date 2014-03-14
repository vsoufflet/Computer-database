package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDAO;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			Computer computer = myComputerDAO.getComputerByName(request
					.getParameter("name"));

			request.setAttribute("computer", computer);
			request.setAttribute("name", computer.getName());
			request.setAttribute("introduced", computer.getIntroduced());
			request.setAttribute("discontinued", computer.getDiscontinued());
			request.setAttribute("companyId", computer.getCompany().getId());
			request.setAttribute("companyName", computer.getCompany().getName());

			request.getRequestDispatcher("editComputer.jsp").forward(request,
					response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
