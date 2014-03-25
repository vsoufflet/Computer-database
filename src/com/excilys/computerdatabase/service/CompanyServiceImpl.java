package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.excilys.computerdatabase.persistence.ConnectionJDBC;
import com.excilys.computerdatabase.persistence.LogDAO;

public class CompanyServiceImpl implements CompanyServiceInterface {

	private static CompanyServiceImpl myCompanyService = new CompanyServiceImpl();

	CompanyDAO myCompanyDAO = CompanyDAO.getInstance();
	LogDAO myLogDAO = LogDAO.getInstance();
	ConnectionJDBC connectionJDBC = ConnectionJDBC.getInstance();

	private CompanyServiceImpl() {

	}

	public static CompanyServiceImpl getInstance() {
		return myCompanyService;
	}

	@Override
	public void create(Company c) {
		Connection conn = connectionJDBC.getConnection();
		try {
			myCompanyDAO.create(c, conn);
			Log log = Log.builder().type("Info")
					.description("Creating company. Name = " + c.getName())
					.build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur lors de la création. Voir CompanDAO->create()");
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
	public Company retrieveById(Long id) {
		Company company = null;
		Connection conn = connectionJDBC.getConnection();
		try {
			company = myCompanyDAO.retrieveById(id, conn);
			Log log = Log.builder().type("Info")
					.description("Looking for company n° " + id).build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
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
		return company;
	}

	@Override
	public List<Company> retrieveList() {
		List<Company> companyList = null;
		Connection conn = connectionJDBC.getConnection();
		try {
			companyList = myCompanyDAO.retrieveList(conn);
			Log log = Log.builder().type("Info")
					.description("Looking for the whole company list").build();
			myLogDAO.create(log, conn);
			conn.commit();
		} catch (SQLException e) {
			System.err
					.println("Erreur de chargement depuis la base. Voir CompanyDAO->retrieveList()");
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
		return companyList;
	}
}
