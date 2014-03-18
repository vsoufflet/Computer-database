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
import com.excilys.computerdatabase.persistence.ComputerDAO;

/**
 * Servlet implementation class OrderByServlet
 */
@WebServlet("/OrderByServlet")
public class OrderByServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderByServlet() {
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
			List<Computer> computerList = new ArrayList<Computer>();
			String orderBy = request.getParameter("orderBy");
			String way = request.getParameter("way");

			computerList = myComputerDAO.getList(orderBy, way);

			request.setAttribute("computerList", computerList);
			request.getRequestDispatcher("dasboard.jsp").forward(request,
					response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
