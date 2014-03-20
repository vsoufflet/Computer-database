package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public class ComputerServiceImpl implements ComputerServiceInterface {

	// DAOFactory df = DAOFactory.getInstance();
	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	@Override
	public void create(Computer c) throws SQLException {
		myComputerDAO.create(c);
	}

	@Override
	public Computer retrieveByName(String name) throws SQLException {
		Computer computer = myComputerDAO.retrieveByName(name);
		return computer;
	}

	@Override
	public List<Computer> retrieveList(PageWrapper pw) throws SQLException {
		List<Computer> computerList = myComputerDAO.retrieveAll(pw);
		return computerList;
	}

	@Override
	public List<Computer> retrieveListByCompany(PageWrapper pw)
			throws SQLException {
		List<Computer> computerList = myComputerDAO.retrieveAllByCompany(pw);
		return computerList;
	}

	@Override
	public void delete(Computer c) throws SQLException {
		myComputerDAO.delete(c);

	}
}
