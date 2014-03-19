package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public class ComputerServiceImpl implements ComputerServiceInterface {

	// DAOFactory df = DAOFactory.getInstance();
	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	@Override
	public void create(Computer c) throws SQLException {
		myComputerDAO.addComputer(c);
	}

	@Override
	public Computer retrieveByName(String name) throws SQLException {
		Computer computer = myComputerDAO.getComputerByName(name);
		return computer;
	}

	@Override
	public List<Computer> retrieveList(String search, String orderBy, String way)
			throws SQLException {
		List<Computer> computerList = myComputerDAO.getList(search, orderBy,
				way);
		return computerList;
	}

	@Override
	public List<Computer> retrieveListByCompany(String search, String orderBy,
			String way) throws SQLException {
		List<Computer> computerList = myComputerDAO.getListByCompany(search,
				orderBy, way);
		return computerList;
	}

	@Override
	public void delete(Computer c) throws SQLException {
		myComputerDAO.deleteComputer(c);

	}

}
