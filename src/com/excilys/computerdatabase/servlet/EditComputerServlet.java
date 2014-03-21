package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.ComputerValidator;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = new ComputerServiceImpl();
	ComputerMapper cm = new ComputerMapper();
	ComputerValidator cv = new ComputerValidator();

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

		String name = request.getParameter("name");

		ComputerDTO computerDTO = computerService.retrieveByName(name);
		Computer computer = cm.toComputer(computerDTO);
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
			request.setAttribute("companyName", computer.getCompany().getName());
		}
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(
				request, response);
	}
}
