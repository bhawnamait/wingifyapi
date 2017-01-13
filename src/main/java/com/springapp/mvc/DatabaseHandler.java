package com.springapp.mvc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DatabaseHandler {

    private DatabaseConnection databaseConnectionPool;

    /**
     * @param databaseConnectionPool pool to be used for getting connections to database
     */
    public DatabaseHandler(DatabaseConnection databaseConnectionPool) {
        this.databaseConnectionPool = databaseConnectionPool;
    }

    /**
     * @return connection to connect to Pool
     * @throws java.sql.SQLException
     */
    protected Connection getConnection() throws SQLException {
        return databaseConnectionPool.getConnection();
    }

    /**
     * return connection  to Pool
     */
    protected void leaveConnection(Connection connection) throws SQLException {
        databaseConnectionPool.leaveConnection(connection);
    }

    public String clean(String x) {

        return x.replaceAll("'", "''");
    }
}