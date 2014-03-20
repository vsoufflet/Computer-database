package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDAO;

public class CompanyServiceImpl implements CompanyServiceInterface {

	// DAOFactory df = DAOFactory.getInstance();
	CompanyDAO myCompanyDAO = CompanyDAO.getInstance();

	@Override
	public void create(Company c) throws SQLException {
		myCompanyDAO.create(c);
	}

	@Override
	public Company retrieveById(Long id) throws SQLException {
		Company company = myCompanyDAO.retrieveById(id);
		return company;
	}

	@Override
	public List<Company> retrieveList() throws SQLException {
		List<Company> companyList = myCompanyDAO.retrieveList();
		return companyList;
	}
}
