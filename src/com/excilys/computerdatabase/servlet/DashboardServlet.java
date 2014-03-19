package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = new ComputerServiceImpl();
	CompanyServiceImpl companyService = new CompanyServiceImpl();

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<Computer> computerList = new ArrayList<Computer>();
		try {
			String searchBy = request.getParameter("searchBy") == null ? "default"
					: request.getParameter("searchBy");
			String search = request.getParameter("search");
			String orderBy = request.getParameter("orderBy") == null ? "default"
					: request.getParameter("orderBy");
			String way = request.getParameter("way");

			System.out.println(search + " " + searchBy + " " + orderBy);

			if (searchBy.equalsIgnoreCase("computer")) {
				if (!orderBy.equalsIgnoreCase("company.id")
						&& !orderBy.equalsIgnoreCase("company.name")) {
					computerList = computerService.retrieveList(search,
							orderBy, way);
				} else {
					computerList = computerService.retrieveListByCompany(
							search, orderBy, way);
				}
			} else if (searchBy.equalsIgnoreCase("company")) {
				computerList = computerService.retrieveListByCompany(search,
						orderBy, way);
			} else {
				if (!orderBy.equalsIgnoreCase("company.id")
						&& !orderBy.equalsIgnoreCase("company.name")) {
					computerList = computerService.retrieveList(search,
							orderBy, way);
				} else {
					computerList = computerService.retrieveListByCompany(
							search, orderBy, way);
				}
			}
			request.setAttribute("computerList", computerList);

			if (computerList.size() <= 1) {
				request.setAttribute("NombreOrdinateurs", computerList.size()
						+ " computer found");
			} else {
				request.setAttribute("NombreOrdinateurs", computerList.size()
						+ " computerService found");
			}

			request.getRequestDispatcher("dashboard.jsp").forward(request,
					response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
