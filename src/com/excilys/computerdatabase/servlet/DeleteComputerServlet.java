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
import com.excilys.computerdatabase.service.ComputerServiceImpl;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerServiceImpl computerService = new ComputerServiceImpl();
	PageWrapper pw = new PageWrapper();
	ComputerMapper cm = new ComputerMapper();
	List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
	List<Computer> computerList = new ArrayList<Computer>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		pw = PageWrapper.builder().search("default").orderBy("default")
				.way("default").build();
		String name = request.getParameter("name");

		ComputerDTO computerDTO = computerService.retrieveByName(name);
		Computer computer = cm.toComputer(computerDTO);
		computerService.delete(computer);

		pw = PageWrapper.builder()
				.computerList(computerService.retrieveList(pw)).build();

		request.setAttribute("PageWrapper", pw);
		request.getRequestDispatcher("DashboardServlet").forward(request,
				response);
	}
}
