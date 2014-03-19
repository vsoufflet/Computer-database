package com.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyServiceInterface {

	public void create(Company c) throws SQLException;

	public Company retrieveById(int id) throws SQLException;

	public List<Company> retrieveList() throws SQLException;

}
