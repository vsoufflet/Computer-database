package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerDTO;
import com.excilys.computerdatabase.domain.PageWrapper;
import com.excilys.computerdatabase.persistence.ComputerDAO;

public class ComputerServiceImpl implements ComputerServiceInterface {

	// DAOFactory df = DAOFactory.getInstance();
	ComputerDAO myComputerDAO = ComputerDAO.getInstance();

	@Override
	public void create(Computer c) {

		try {
			myComputerDAO.create(c);
		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la création. Voir ComputerDAO->create()");
			e.printStackTrace();
		}
	}

	@Override
	public ComputerDTO retrieveByName(String name) {
		ComputerDTO computerDTO = null;
		try {
			computerDTO = myComputerDAO.retrieveByName(name);
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveByName()");
			e.printStackTrace();
		}
		return computerDTO;
	}

	@Override
	public List<ComputerDTO> retrieveList(PageWrapper pw) {

		List<ComputerDTO> computerDTOList = null;
		try {
			computerDTOList = myComputerDAO.retrieveAll(pw);
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveAll()");
			e.printStackTrace();
		}
		return computerDTOList;
	}

	@Override
	public List<ComputerDTO> retrieveListByCompany(PageWrapper pw) {

		List<ComputerDTO> computerDTOList = null;
		try {
			computerDTOList = myComputerDAO.retrieveAllByCompany(pw);
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->retrieveAllByCompany");
			e.printStackTrace();
		}
		return computerDTOList;
	}

	@Override
	public void delete(Computer c) {

		try {
			myComputerDAO.delete(c);
		} catch (SQLException e) {
			System.err
					.println("Erreur de connexion. Voir ComputerDAO->delete()");
			e.printStackTrace();
		}

	}
}
