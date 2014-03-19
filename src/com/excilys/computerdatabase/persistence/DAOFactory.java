package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DAOFactory {

	private String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private String driver = "com.mysql.jdbc.Driver";
	private String userName = "root";
	private String passWord = "root";
	private BoneCP connectionPool;
	// private CompanyDAO myCompanyDAO;
	// private ComputerDAO myComputerDAO;

	private static DAOFactory df = new DAOFactory();

	public static CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}

	public static ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	private DAOFactory() {
		/*
		 * try { Class.forName(driver); } catch (ClassNotFoundException e) {
		 * System.err.println("Le driver est introuvable.");
		 * e.printStackTrace(); }
		 */
	}

	public static DAOFactory getInstance() {
		return df;
	}

	public BoneCP initialise() {

		BoneCP connectionPool = null;
		try {
			Class.forName(driver);
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername(userName);
			config.setPassword(passWord);

			config.setMinConnectionsPerPartition(2);
			config.setMaxConnectionsPerPartition(8);
			config.setPartitionCount(2);

			connectionPool = new BoneCP(config);

		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Erreur d'accès à la base de données.");
			e.printStackTrace();
		}
		return connectionPool;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e) {
			System.err.println("La connection n'a pas pu être établie.");
			e.printStackTrace();
		}
		return connection;
	}
}
