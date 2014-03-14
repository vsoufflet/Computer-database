package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CompanyDAO {

ConnectionManager cm = new ConnectionManager();
	
	private static CompanyDAO myDAO = new CompanyDAO();;
	
	private CompanyDAO(){
		
	}
	
	public static CompanyDAO getInstance(){
		return myDAO;
	}
	
	public void addCompany(Company c) throws SQLException{
		
		Connection conn = null;
		PreparedStatement ps =null;
		String query="INSERT into computer (id,name,introduced,discontinued,company_id) VALUES(?,?,?,?,?)";
			
		conn = cm.getConnection();
		ps = conn.prepareStatement(query);
			
		ps.setInt(1, c.getId());
		ps.setString(2, c.getName());
		
		ps.executeUpdate();
			
		ps.close();
		conn.close();
	}
	
	public List<Company> getCompanyList() throws SQLException{
		
		Connection conn = null;
		Statement stmt = null;
		String query="SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companyList = new ArrayList<Company>();
		
		conn = cm.getConnection();
		stmt = (Statement) conn.createStatement();
		rs = stmt.executeQuery(query);
		
		while(rs.next()){
			
			Company c = new Company();
			c.setId(rs.getInt(1));
			c.setName(rs.getString(2));
			
			companyList.add(c);
		}
		rs.close();
		stmt.close();
		conn.close();
		
		return companyList;
	}
	
	public Company getCompanyById(int id) throws SQLException{
		List<Company> companyList = getCompanyList();
		
		for(Company c: companyList){
			if(id==c.getId()){
				Company company = c;
				return company;
			}
		}
		return null;
	}
}
