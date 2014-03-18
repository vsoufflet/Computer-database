package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionPool {

	private String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";

	private static ConnectionPool mycp = new ConnectionPool();

	private ConnectionPool() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(url);
			config.setUsername("root");
			config.setPassword("root");

			config.setMinConnectionsPerPartition(3);
			config.setMaxConnectionsPerPartition(5);
			config.setPartitionCount(3);

			BoneCP connectionPool = new BoneCP(config);

			Connection connection;
			connection = connectionPool.getConnection();

			// ... do something with the connection here ...

			connection.close();
			connectionPool.shutdown();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		return mycp;
	}

}
