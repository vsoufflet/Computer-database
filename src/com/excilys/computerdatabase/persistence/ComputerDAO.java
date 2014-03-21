package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.service.CompanyServiceImpl;
import com.jolbox.bonecp.BoneCP;

public class ComputerDAO {
	DAOFactory df = DAOFactory.getInstance();
	BoneCP connectionPool;
	CompanyServiceImpl companyService = new CompanyServiceImpl();

	private static ComputerDAO myDAO = new ComputerDAO();;

	private ComputerDAO() {

	}

	public static ComputerDAO getInstance() {
		return myDAO;
	}

	public void create(Computer c) throws SQLException {

		Connection conn = null;
		PreparedStatement ps = null;

		String query = "INSERT into computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?)";

		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
		ps = conn.prepareStatement(query);

		ps.setString(1, c.getName());
		ps.setTimestamp(2, new Timestamp(c.getIntroduced().getTime()));
		ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getTime()));
		ps.setObject(4, c.getCompany().getId());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public ComputerDTO retrieveByName(String name) throws SQLException {

		Connection conn;
		PreparedStatement ps = null;
		String query = "SELECT * FROM computer WHERE name=?";
		ResultSet rs = null;
		ComputerDTO cDTO = new ComputerDTO();

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
		cDTO.setId(rs.getLong(1));
		cDTO.setName(rs.getString(2));
		cDTO.setIntroduced(rs.getString(3));
		cDTO.setDiscontinued(rs.getString(4));
		cDTO.setCompanyId(rs.getLong(5));

		rs.close();
		ps.close();
		conn.close();
		return cDTO;
	}

	public void delete(Computer c) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE from computer WHERE id=?";

		if (connectionPool == null) {
			connectionPool = df.initialise();
			conn = connectionPool.getConnection();
		} else {
			conn = connectionPool.getConnection();
		}
		ps = conn.prepareStatement(query);
		ps.setLong(1, c.getId());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public List<ComputerDTO> retrieveAll(PageWrapper pw) throws SQLException {

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer query = new StringBuffer();
		ResultSet rs = null;

		if (connectionPool == null) {
			connectionPool = df.initialise();

			conn = connectionPool.getConnection();

		} else {
			conn = connectionPool.getConnection();
		}

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT * FROM computer");
		} else {
			query = query.append("SELECT * FROM computer WHERE name LIKE ?");
		}
		if (!pw.getOrderBy().equalsIgnoreCase("default")) {
			query = query.append(" ORDER BY " + pw.getOrderBy());

			if (!pw.getWay().equalsIgnoreCase("default")) {
				query = query.append(" " + pw.getWay());
			}
		}

		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);

		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			ComputerDTO cDTO = new ComputerDTO();
			cDTO.setId(rs.getLong(1));
			cDTO.setName(rs.getString(2));
			cDTO.setIntroduced(rs.getString(3));
			cDTO.setDiscontinued(rs.getString(4));
			cDTO.setCompanyId(rs.getLong(5));

			computerDTOList.add(cDTO);
		}

		rs.close();
		ps.close();
		conn.close();

		return computerDTOList;
	}

	public List<ComputerDTO> retrieveAllByCompany(PageWrapper pw)
			throws SQLException {

		List<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer query = new StringBuffer();
		ResultSet rs = null;

		if (connectionPool == null) {
			connectionPool = df.initialise();

			conn = connectionPool.getConnection();

		} else {
			conn = connectionPool.getConnection();
		}

		if (pw.getSearch().equalsIgnoreCase("default")
				|| pw.getSearch().equalsIgnoreCase("")) {
			query = query.append("SELECT * FROM computer");
			if (!pw.getOrderBy().equalsIgnoreCase("default")) {
				query = query
						.append(" INNER JOIN company ON computer.company_id = company.id ORDER BY "
								+ pw.getOrderBy());
				if (!pw.getWay().equalsIgnoreCase("default")) {
					query = query.append(" " + pw.getWay());
				}
			}
		} else {
			query = query
					.append("SELECT * FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company.name LIKE ?");
			if (!pw.getOrderBy().equalsIgnoreCase("default")) {
				query = query.append(" ORDER BY " + pw.getOrderBy());

				if (!pw.getWay().equalsIgnoreCase("default")) {
					query = query.append(" " + pw.getWay());
				}
			}
		}

		String sqlQuery = query.toString();
		ps = conn.prepareStatement(sqlQuery);
		if (!pw.getSearch().equalsIgnoreCase("default")
				&& !pw.getSearch().equalsIgnoreCase("")) {
			ps.setString(1, "%" + pw.getSearch() + "%");
		}

		rs = ps.executeQuery();

		while (rs.next()) {
			ComputerDTO cDTO = new ComputerDTO();
			cDTO.setId(rs.getLong(1));
			cDTO.setName(rs.getString(2));
			cDTO.setIntroduced(rs.getString(3));
			cDTO.setDiscontinued(rs.getString(4));
			cDTO.setCompanyId(rs.getLong(5));

			computerDTOList.add(cDTO);
		}

		rs.close();
		ps.close();
		conn.close();

		return computerDTOList;
	}
}
