package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = ComputerServiceImpl.getInstance();
	CompanyServiceImpl companyService = CompanyServiceImpl.getInstance();
	ComputerMapper cm = new ComputerMapper();

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

		PageWrapper pw = new PageWrapper();
		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		List<Computer> computerList = new ArrayList<Computer>();

		String searchBy = request.getParameter("searchBy") == null ? "default"
				: request.getParameter("searchBy");
		String search = request.getParameter("search") == null ? "default"
				: request.getParameter("search");
		String orderBy = request.getParameter("orderBy") == null ? "default"
				: request.getParameter("orderBy");
		String way = request.getParameter("way") == null ? "default" : request
				.getParameter("way");

		pw = PageWrapper.builder().searchBy(searchBy).search(search)
				.orderBy(orderBy).way(way).build();

		if (searchBy.equalsIgnoreCase("computer")) {

			if (!orderBy.equalsIgnoreCase("company.id")
					&& !orderBy.equalsIgnoreCase("company.name")) {
				computerDTOList = computerService.retrieveList(pw);
			} else {
				computerDTOList = computerService.retrieveListByCompany(pw);
			}

		} else if (searchBy.equalsIgnoreCase("company")) {
			computerDTOList = computerService.retrieveListByCompany(pw);

		} else {

			if (!orderBy.equalsIgnoreCase("company.id")
					&& !orderBy.equalsIgnoreCase("company.name")) {
				computerDTOList = computerService.retrieveList(pw);
			} else {
				computerDTOList = computerService.retrieveListByCompany(pw);
			}
		}

		for (ComputerDTO cDTO : computerDTOList) {
			Computer computer = cm.toComputer(cDTO);
			computerList.add(computer);
		}

		pw.setComputerList(computerList);
		request.setAttribute("PageWrapper", pw);

		if (computerDTOList.size() <= 1) {
			request.setAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computer found");
		} else {
			request.setAttribute("NombreOrdinateurs", computerDTOList.size()
					+ " computerService found");
		}

		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request,
				response);
	}
}
