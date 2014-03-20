package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public interface ComputerServiceInterface {

	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	public void create(Computer c) throws SQLException;

	public Computer retrieveByName(String name) throws SQLException;

	public List<Computer> retrieveList(PageWrapper pw) throws SQLException;

	public List<Computer> retrieveListByCompany(PageWrapper pw)
			throws SQLException;

	public void delete(Computer c) throws SQLException;

}
