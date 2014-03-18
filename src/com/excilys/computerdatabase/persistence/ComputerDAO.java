package com.excilys.computerdatabase.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.mysql.jdbc.Connection;

public class ComputerDAO {
	ConnectionManager cm = new ConnectionManager();
	CompanyDAO cDAO = CompanyDAO.getInstance();

	private static ComputerDAO myDAO = new ComputerDAO();;

	private ComputerDAO() {

	}

	public static ComputerDAO getInstance() {
		return myDAO;
	}

	public void addComputer(Computer c) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;

		String query = "INSERT into computer (id,name,introduced,discontinued,company_id) VALUES(?,?,?,?,?)";

		conn = cm.getConnection();
		ps = conn.prepareStatement(query);

		ps.setInt(1, c.getId());
		ps.setString(2, c.getName());
		ps.setTimestamp(3, new Timestamp(c.getIntroduced().getTime()));
		ps.setTimestamp(4, new Timestamp(c.getDiscontinued().getTime()));
		ps.setObject(5, c.getCompany().getId());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public Computer getComputerByName(String name) throws SQLException {

		Connection conn;
		PreparedStatement ps = null;
		String query = "SELECT * FROM computer WHERE name=?";
		ResultSet rs = null;
		Computer computer = new Computer();
		conn = cm.getConnection();
		ps = conn.prepareStatement(query);
		ps.setString(1, name);
		rs = ps.executeQuery();

		rs.next();
		computer.setId(rs.getInt(1));
		computer.setName(rs.getString(2));
		computer.setIntroduced(rs.getDate(3));
		computer.setDiscontinued(rs.getDate(4));
		computer.setCompany(cDAO.getCompanyById(rs.getInt(5)));

		rs.close();
		ps.close();
		conn.close();
		return computer;
	}

	/*
	 * public List<Computer> getComputerList() throws SQLException {
	 * 
	 * Connection conn = null; Statement stmt = null; String query =
	 * "SELECT * FROM computer"; ResultSet rs = null; List<Computer>
	 * computerList = new ArrayList<Computer>();
	 * 
	 * conn = cm.getConnection(); stmt = (Statement) conn.createStatement(); rs
	 * = stmt.executeQuery(query);
	 * 
	 * while (rs.next()) {
	 * 
	 * Computer computer = new Computer(); computer.setId(rs.getInt(1));
	 * computer.setName(rs.getString(2)); computer.setIntroduced(rs.getDate(3));
	 * computer.setDiscontinued(rs.getDate(4));
	 * 
	 * Company company = cDAO.getCompanyById(rs.getInt(5));
	 * computer.setCompany(company);
	 * 
	 * computerList.add(computer); } rs.close(); stmt.close(); conn.close();
	 * 
	 * return computerList; }
	 * 
	 * public List<Computer> getSortedList(String orderBy, String way) throws
	 * SQLException {
	 * 
	 * List<Computer> computerList = new ArrayList<Computer>(); Connection conn
	 * = null; PreparedStatement ps = null; String query = null;
	 * 
	 * if (way != null) { query = "SELECT * FROM computer ORDER BY " + orderBy +
	 * " " + way;
	 * 
	 * } else { query = "SELECT * FROM computer ORDER BY " + orderBy; } conn =
	 * cm.getConnection(); ps = conn.prepareStatement(query);
	 * 
	 * ResultSet rs = ps.executeQuery(); while (rs.next()) {
	 * 
	 * Computer computer = new Computer(); computer.setId(rs.getInt(1));
	 * computer.setName(rs.getString(2)); computer.setIntroduced(rs.getDate(3));
	 * computer.setDiscontinued(rs.getDate(4));
	 * 
	 * Company company = cDAO.getCompanyById(rs.getInt(5));
	 * computer.setCompany(company);
	 * 
	 * computerList.add(computer); } rs.close(); ps.close(); conn.close();
	 * 
	 * return computerList; }
	 * 
	 * public List<Computer> filterByName(String name) throws SQLException {
	 * 
	 * List<Computer> computerList = new ArrayList<Computer>(); Connection conn
	 * = null; PreparedStatement ps = null; ResultSet rs = null; String query =
	 * "SELECT * FROM computer WHERE name LIKE ?";
	 * 
	 * conn = cm.getConnection(); ps = conn.prepareStatement(query);
	 * ps.setString(1, "%" + name + "%"); rs = ps.executeQuery();
	 * 
	 * while (rs.next()) { Computer computer = new Computer();
	 * computer.setId(rs.getInt(1)); computer.setName(rs.getString(2));
	 * computer.setIntroduced(rs.getDate(3));
	 * computer.setDiscontinued(rs.getDate(4));
	 * 
	 * Company company = cDAO.getCompanyById(rs.getInt(5));
	 * computer.setCompany(company);
	 * 
	 * computerList.add(computer); }
	 * 
	 * rs.close(); ps.close(); conn.close();
	 * 
	 * return computerList; }
	 */

	public void deleteComputer(Computer c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE from computer WHERE id=?";
		conn = cm.getConnection();
		ps = conn.prepareStatement(query);
		ps.setInt(1, c.getId());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public List<Computer> getList(String search, String orderBy, String way)
			throws SQLException {

		List<Computer> computerList = new ArrayList<Computer>();
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;

		if (search != null && search != "") {
			if (!"default".equalsIgnoreCase(orderBy)) {
				if (way != null) {
					query = "SELECT * FROM computer WHERE name LIKE ? ORDER BY "
							+ orderBy + " " + way;
				} else {
					query = "SELECT * FROM computer WHERE name LIKE ? ORDER BY "
							+ orderBy;
				}
			} else {
				query = "SELECT * FROM computer WHERE name LIKE ?";
			}
			conn = cm.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + search + "%");
		} else {
			if (!"default".equalsIgnoreCase(orderBy)) {
				if (way != null) {
					query = "SELECT * FROM computer ORDER BY " + orderBy + " "
							+ way;
				} else {
					query = "SELECT * FROM computer ORDER BY " + orderBy;
				}
			} else {
				query = "SELECT * FROM computer";
			}
			conn = cm.getConnection();
			ps = conn.prepareStatement(query);
		}
		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getInt(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));

			Company company = cDAO.getCompanyById(rs.getInt(5));
			computer.setCompany(company);

			computerList.add(computer);
		}

		rs.close();
		ps.close();
		conn.close();

		return computerList;
	}
}
