package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ConnectionJDBC;
import com.excilys.computerdatabase.persistence.LogDAO;

public class ComputerServiceImpl implements ComputerServiceInterface {

	private static ComputerServiceImpl myComputerService = new ComputerServiceImpl();

	ComputerDAO myComputerDAO = ComputerDAO.getInstance();
	LogDAO myLogDAO = LogDAO.getInstance();

	ConnectionJDBC connectionJDBC = ConnectionJDBC.getInstance();
	Connection conn = connectionJDBC.getConnection();

	private ComputerServiceImpl() {

	}

	public static ComputerServiceImpl getInstance() {
		return myComputerService;
	}

	@Override
	public void create(Computer c) {
		Connection conn = connectionJDBC.getConnection();
		try {
			Log log = Log.builder().type("Info")
					.description("Creating computer. Name = " + c.getName())
					.build();
			myLogDAO.create(log, conn);
			myComputerDAO.create(c, conn);

			conn.commit();

		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la création. Voir ComputerDAO->create()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public ComputerDTO retrieveByName(String name) {
		ComputerDTO computerDTO = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			computerDTO = myComputerDAO.retrieveByName(name, conn);
			Log log = Log
					.builder()
					.type("Info")
					.description(
							"Looking for computer which name is "
									+ computerDTO.getName()).build();
			myLogDAO.create(log, conn);

			conn.commit();

		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveByName()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return computerDTO;
	}

	@Override
	public ComputerDTO retrieveById(Long id) {
		ComputerDTO computerDTO = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			computerDTO = myComputerDAO.retrieveById(id, conn);
			Log log = Log
					.builder()
					.type("Info")
					.description(
							"Looking for computer n° " + computerDTO.getId())
					.build();
			myLogDAO.create(log, conn);

			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveById()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return computerDTO;
	}

	@Override
	public List<ComputerDTO> retrieveList(PageWrapper pw) {
		List<ComputerDTO> computerDTOList = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			computerDTOList = myComputerDAO.retrieveAll(pw, conn);
			Log log = Log.builder().type("Info")
					.description("Looking for the whole computer list").build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveAll()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return computerDTOList;
	}

	@Override
	public List<ComputerDTO> retrieveListByCompany(PageWrapper pw) {
		List<ComputerDTO> computerDTOList = null;
		Connection conn = connectionJDBC.getConnection();

		try {
			computerDTOList = myComputerDAO.retrieveAllByCompany(pw, conn);
			Log log = Log
					.builder()
					.type("Info")
					.description(
							"Looking for the whole computer list via their company")
					.build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveAllByCompany");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return computerDTOList;
	}

	@Override
	public void delete(Computer c) {
		Connection conn = connectionJDBC.getConnection();
		try {
			myComputerDAO.delete(c, conn);
			Log log = Log.builder().type("Info")
					.description("Deleting computer n° ").build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->delete()");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
