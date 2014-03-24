package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public interface ComputerServiceInterface {

	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	public void create(Computer c);

	public ComputerDTO retrieveByName(String name);

	public ComputerDTO retrieveById(Long id);

	public List<ComputerDTO> retrieveList(PageWrapper pw);

	public List<ComputerDTO> retrieveListByCompany(PageWrapper pw);

	public void delete(Computer c);

}
