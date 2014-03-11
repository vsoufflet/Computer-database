package com.excilys.computerdatabase.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dbcomponents.Computer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DAO {

	private Connection getConnection() throws SQLException{
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/DB1";
			connection = (Connection) DriverManager.getConnection(url, "root", "root");
		
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Mysql introuvable");
			e.printStackTrace();
		}
		return connection;
	}
	
	public void addComputer(Computer c) throws SQLException{
		
		Connection conn = null;
		PreparedStatement ps =null;
		String query="INSERT into computer (id,name,introduced,discontinued,company_id) VALUES(?,?,?,?,?)";
		
			conn = getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, c.getId());
			ps.setString(2, c.getName());
			ps.setDate(3, c.getIntroduced());
			ps.setDate(4, c.getDiscontinued());
			ps.setInt(5, c.getCompany_id());
		
			ps.executeUpdate();
			
			ps.close();
			conn.close();
	}
	
	public List<Computer> getComputerList() throws SQLException{
		
		Connection conn = null;
		Statement stmt = null;
		String query="SELECT id,name,introduced,discontinued,company_id FROM computer";
		ResultSet rs = null;
		List<Computer> computerList = new ArrayList<Computer>();
		
		conn = getConnection();
		stmt = (Statement) conn.createStatement();
		rs = stmt.executeQuery(query);
		
		while(rs.next()){
			
			Computer c = new Computer();
			c.setId(rs.getInt(1));
			c.setName(rs.getString(2));
			c.setIntroduced(rs.getDate(3));
			c.setDiscontinued(rs.getDate(4));
			c.setCompany_id(rs.getInt(5));
			
			computerList.add(c);
		}
		rs.close();
		stmt.close();
		conn.close();
		
		return computerList;
	}
}
