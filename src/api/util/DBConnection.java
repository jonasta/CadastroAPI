package api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
//			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cadastrocliente", "postgres", "root");
			connection = DriverManager.getConnection("jdbc:postgresql://$OPENSHIFT_POSTGRESQL_DB_HOST:$OPENSHIFT_POSTGRESQL_DB_PORT/api", "adminm276w9h", "zFUM84ZuVy35");
			return connection;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}

}