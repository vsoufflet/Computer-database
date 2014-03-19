package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

			Computer computer = new Computer();
			computer.setName(request.getParameter("name"));
			computer.setIntroduced(parser.parse(request
					.getParameter("introducedDate")));
			computer.setDiscontinued(parser.parse(request
					.getParameter("discontinuedDate")));

			Company company = companyService.retrieveById(Integer
					.parseInt(request.getParameter("company")));
			computer.setCompany(company);

			computerService.create(computer);
			request.getRequestDispatcher("addComputer.jsp").forward(request,
					response);

		} catch (SQLException | ParseException e) {
			e.printStackTrace();
			request.getRequestDispatcher("addComputer.jsp").forward(request,
					response);
		}
	}
}
