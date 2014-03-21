package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDAO;

public class CompanyServiceImpl implements CompanyServiceInterface {

	// DAOFactory df = DAOFactory.getInstance();
	CompanyDAO myCompanyDAO = CompanyDAO.getInstance();

	@Override
	public void create(Company c) {

		try {
			myCompanyDAO.create(c);
		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la création. Voir CompanDAO->create()");
			e.printStackTrace();
		}

	}

	@Override
	public Company retrieveById(Long id) {

		Company company = null;
		try {
			company = myCompanyDAO.retrieveById(id);
		} catch (SQLException e) {
			System.err
					.println("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public List<Company> retrieveList() {

		List<Company> companyList = null;
		try {
			companyList = myCompanyDAO.retrieveList();
		} catch (SQLException e) {
			System.err
					.println("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
			e.printStackTrace();
		}
		return companyList;
	}
}
