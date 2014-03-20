package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends javax.servlet.http.HttpServlet
		implements javax.servlet.Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = new ComputerServiceImpl();
	CompanyServiceImpl companyService = new CompanyServiceImpl();;

	public AddComputerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {

			SimpleDateFormat parser = new SimpleDateFormat("YYYY-MM-dd");

			String name = request.getParameter("name");
			Date introduced = parser.parse(request
					.getParameter("introducedDate"));
			Date discontinued = parser.parse(request
					.getParameter("discontinuedDate"));
			Long companyId = Long.parseLong(request.getParameter("company"));

			Computer computer = new Computer();
			computer.setName(name);
			computer.setIntroduced(introduced);
			computer.setDiscontinued(discontinued);
			Company company = companyService.retrieveById(companyId);
			computer.setCompany(company);

			computerService.create(computer);
			request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(
					request, response);

		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la connection: AddComputerServlet");
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("Erreur lors du parse de la date.");
			e.printStackTrace();
			response.sendRedirect("WEB-INF/addComputer.jsp");
		}
	}
}
