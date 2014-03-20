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
import com.excilys.computerdatabase.domain.PageWrapper;
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
	PageWrapper pw = new PageWrapper();
	List<Computer> computerList = pw.getComputerList();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
	}

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		try {
			String searchBy = request.getParameter("searchBy") == null ? "default"
					: request.getParameter("searchBy");
			String search = request.getParameter("search") == null ? "default"
					: request.getParameter("search");
			String orderBy = request.getParameter("orderBy") == null ? "default"
					: request.getParameter("orderBy");
			String way = request.getParameter("way") == null ? "default"
					: request.getParameter("way");

			pw = PageWrapper.builder().searchBy(searchBy).search(search)
					.orderBy(orderBy).way(way).build();

			if (searchBy.equalsIgnoreCase("computer")) {

				if (!orderBy.equalsIgnoreCase("company.id")
						&& !orderBy.equalsIgnoreCase("company.name")) {
					computerList = computerService.retrieveList(pw);
				} else {
					computerList = computerService.retrieveListByCompany(pw);
				}

			} else if (searchBy.equalsIgnoreCase("company")) {
				computerList = computerService.retrieveListByCompany(pw);

			} else {

				if (!orderBy.equalsIgnoreCase("company.id")
						&& !orderBy.equalsIgnoreCase("company.name")) {
					computerList = computerService.retrieveList(pw);
				} else {
					computerList = computerService.retrieveListByCompany(pw);
				}
			}

			pw = PageWrapper.builder().computerList(computerList).build();
			request.setAttribute("PageWrapper", pw);

			if (computerList.size() <= 1) {
				request.setAttribute("NombreOrdinateurs", computerList.size()
						+ " computer found");
			} else {
				request.setAttribute("NombreOrdinateurs", computerList.size()
						+ " computerService found");
			}

			request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(
					request, response);

		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la connection: DashboardServlet");
			e.printStackTrace();
		}
	}
}
