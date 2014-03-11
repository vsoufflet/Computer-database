package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.db.DAO;
import com.excilys.computerdatabase.dbcomponents.Computer;

public class AddComputerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	public AddComputerServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Computer computer = new Computer();
		
		computer.setId(Integer.parseInt(request.getParameter("id")));
		computer.setName(request.getParameter("name"));
		computer.setIntroduced(new Date(Date.parse(request.getParameter("introducedDate"))));
		computer.setDiscontinued(new Date(Date.parse(request.getParameter("discontinuedDate"))));
		
		switch (request.getParameter("company")){
		case "Apple":
			computer.setCompany_id(1);
			break;
		case "Dell":
			computer.setCompany_id(2);
			break;
		case "Lenovo":
			computer.setCompany_id(3);
			break;
		default:
			computer.setCompany_id(0);	
		}
		System.out.println(computer);
		
		try {
			
			DAO dao = new DAO();
			
			dao.addComputer(computer);
			List<Computer> computerList = dao.getComputerList();
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			
			pw.print("<html><head><title>Liste des ordinateurs</title></head><body>");
			pw.println("<table>");
			
			Iterator<Computer> it = computerList.iterator();
			while(it.hasNext()){
				
				Computer c = (Computer) it.next();
				pw.println("<tr>");
				pw.println("<td>" + c.getId() + "</td>");
				pw.println("<td>" + c.getName() + "</td>");
				pw.println("<td>" + c.getIntroduced() + "</td>");
				pw.println("<td>" + c.getDiscontinued() + "</td>");
				pw.println("<td>" + c.getCompany_id() + "</td>");
				pw.println("</tr>");
			}
			
			pw.println("</table>");
			pw.println("</body></html>");
			pw.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
