package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = new ComputerServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			String name = request.getParameter("name");

			Computer computer = computerService.retrieveByName(name);

			request.setAttribute("computer", computer);
			if (computer.getName() != null) {
				request.setAttribute("name", computer.getName());
			}
			if (computer.getIntroduced() != null) {
				request.setAttribute("introduced", computer.getIntroduced());
			}
			if (computer.getDiscontinued() != null) {
				request.setAttribute("discontinued", computer.getDiscontinued());
			}
			if (computer.getCompany() != null) {
				request.setAttribute("companyId", computer.getCompany().getId());
				request.setAttribute("companyName", computer.getCompany()
						.getName());
			}
			request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(
					request, response);

		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la connection: EditComputerServlet");
			e.printStackTrace();
		}
	}
}
