package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.jolbox.bonecp.BoneCP;

public class ComputerDAO {
	ConnectionManager cm = ConnectionManager.getInstance();
	DAOFactory df = DAOFactory.getInstance();
	BoneCP connectionPool;
	CompanyServiceImpl companyService = new CompanyServiceImpl();

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

		// conn = cm.getConnection();
		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
		ps = conn.prepareStatement(query);
		ps.setString(1, name);
		rs = ps.executeQuery();

		rs.next();
		computer.setId(rs.getInt(1));
		computer.setName(rs.getString(2));
		computer.setIntroduced(rs.getDate(3));
		computer.setDiscontinued(rs.getDate(4));
		computer.setCompany(companyService.retrieveById(rs.getInt(5)));

		rs.close();
		ps.close();
		conn.close();
		return computer;
	}

	public void deleteComputer(Computer c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE from computer WHERE id=?";

		// conn = cm.getConnection();
		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
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
			if (!orderBy.equalsIgnoreCase("default")) {
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
			// conn = cm.getConnection();
			if (connectionPool == null) {
				connectionPool = df.initialise();
				conn = connectionPool.getConnection();
			} else {
				conn = connectionPool.getConnection();
			}
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + search + "%");
		} else {
			if (!orderBy.equalsIgnoreCase("default")) {
				if (way != null) {
					query = "SELECT * FROM computer ORDER BY " + orderBy + " "
							+ way;
				} else {
					query = "SELECT * FROM computer ORDER BY " + orderBy;
				}
			} else {
				query = "SELECT * FROM computer";
			}
			// conn = cm.getConnection();
			if (connectionPool == null) {
				connectionPool = df.initialise();
				conn = connectionPool.getConnection();
			} else {
				conn = connectionPool.getConnection();
			}
			ps = conn.prepareStatement(query);
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getInt(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));

			Company company = companyService.retrieveById(rs.getInt(5));
			computer.setCompany(company);

			computerList.add(computer);
		}

		rs.close();
		ps.close();
		conn.close();

		return computerList;
	}

	public List<Computer> getListByCompany(String search, String orderBy,
			String way) throws SQLException {

		List<Computer> computerList = new ArrayList<Computer>();
		Connection conn = null;
		PreparedStatement ps = null;
		String query = null;
		ResultSet rs = null;

		if (search != null && search != "") {
			if (!orderBy.equalsIgnoreCase("default")) {
				if (way != null) {
					query = "SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? ORDER BY "
							+ orderBy + " " + way;
				} else {
					query = "SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ? ORDER BY "
							+ orderBy;
				}
			} else {
				query = "SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?";
			}
			// conn = cm.getConnection();
			if (connectionPool == null) {
				connectionPool = df.initialise();
				conn = connectionPool.getConnection();
			} else {
				conn = connectionPool.getConnection();
			}
			ps = conn.prepareStatement(query);
			ps.setString(1, "%" + search + "%");
		} else {
			if (!orderBy.equalsIgnoreCase("default")) {
				if (way != null) {
					query = "SELECT * FROM computer ORDER BY " + orderBy + " "
							+ way;
				} else {
					query = "SELECT * FROM computer ORDER BY " + orderBy;
				}
			} else {
				query = "SELECT * FROM computer";
			}
			// conn = cm.getConnection();
			if (connectionPool == null) {
				connectionPool = df.initialise();
				conn = connectionPool.getConnection();
			} else {
				conn = connectionPool.getConnection();
			}
			ps = conn.prepareStatement(query);
		}

		System.out.println(query);
		rs = ps.executeQuery();

		while (rs.next()) {
			Computer computer = new Computer();
			computer.setId(rs.getInt(1));
			computer.setName(rs.getString(2));
			computer.setIntroduced(rs.getDate(3));
			computer.setDiscontinued(rs.getDate(4));

			Company company = companyService.retrieveById(rs.getInt(5));
			computer.setCompany(company);

			computerList.add(computer);
			System.out.println(computer.toString());
		}

		rs.close();
		ps.close();
		conn.close();

		return computerList;
	}
}
