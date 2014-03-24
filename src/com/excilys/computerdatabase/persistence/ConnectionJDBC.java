package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionJDBC {

	private static String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "root";
	private static String passWord = "root";
	private static BoneCP connectionPool;
	// private ThreadLocal<Connection> threadLocal;

	private static ConnectionJDBC conn = new ConnectionJDBC();

	public static CompanyDAO getCompanyDAO() {
		return CompanyDAO.getInstance();
	}

	public static ComputerDAO getComputerDAO() {
		return ComputerDAO.getInstance();
	}

	private ConnectionJDBC() {

	}

	public static ConnectionJDBC getInstance() {
		return conn;
	}

	public static void initialise() {

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
			// threadLocal = new ThreadLocal<Connection>();

		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Erreur d'accès à la base de données.");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			if (connectionPool == null) {
				initialise();
			}
			connection = connectionPool.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.err.println("La connection n'a pas pu être établie.");
			e.printStackTrace();
		}
		return connection;
	}
}
