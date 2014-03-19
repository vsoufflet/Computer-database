package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.jolbox.bonecp.BoneCP;

public class CompanyDAO {

	ConnectionManager cm = ConnectionManager.getInstance();
	DAOFactory df = DAOFactory.getInstance();
	BoneCP connectionPool;

	private static CompanyDAO myDAO = new CompanyDAO();

	private CompanyDAO() {

	}

	public static CompanyDAO getInstance() {
		return myDAO;
	}

	public void addCompany(Company c) throws SQLException {

		Connection conn = null;

		PreparedStatement ps = null;
		String query = "INSERT into computer (id,name,introduced,discontinued,company_id) VALUES(?,?,?,?,?)";

		// conn = cm.getConnection();
		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
		ps = conn.prepareStatement(query);

		ps.setInt(1, c.getId());
		ps.setString(2, c.getName());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public Company getCompanyById(int id) throws SQLException {
		List<Company> companyList = getCompanyList();

		for (Company c : companyList) {
			if (id == c.getId()) {
				Company company = c;
				return company;
			}
		}
		return null;
	}

	public List<Company> getCompanyList() throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		String query = "SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companyList = new ArrayList<Company>();

		// conn = cm.getConnection();
		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
		stmt = (Statement) conn.createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {

			Company c = new Company();
			c.setId(rs.getInt(1));
			c.setName(rs.getString(2));

			companyList.add(c);
		}
		rs.close();
		stmt.close();
		conn.close();

		return companyList;
	}

	/*
	 * public List<Computer> filterByName(String name) throws SQLException {
	 * 
	 * List<Computer> computerList = new ArrayList<Computer>(); Connection conn
	 * = null; PreparedStatement ps = null; ResultSet rs = null; String query =
	 * "SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?"
	 * ;
	 * 
	 * conn = cm.getConnection(); ps = conn.prepareStatement(query);
	 * ps.setString(1, "%" + name + "%"); rs = ps.executeQuery();
	 * 
	 * while (rs.next()) { Computer computer = new Computer();
	 * computer.setId(rs.getInt(1)); computer.setName(rs.getString(2));
	 * computer.setIntroduced(rs.getDate(3));
	 * computer.setDiscontinued(rs.getDate(4));
	 * 
	 * Company company = myDAO.getCompanyById(rs.getInt(5));
	 * computer.setCompany(company);
	 * 
	 * computerList.add(computer); }
	 * 
	 * rs.close(); ps.close(); conn.close();
	 * 
	 * return computerList; }
	 */
}
