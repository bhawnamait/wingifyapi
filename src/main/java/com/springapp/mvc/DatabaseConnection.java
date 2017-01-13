package com.springapp.mvc;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class DatabaseConnection {
    private static Logger LOG = Logger.getLogger(DatabaseConnection.class.getName());
    private static long DB_EVICTION_TIMEOUT_MILLISEC = 100000;
    private DataSource ds = null;
    private GenericObjectPool connectionPool;

    public DatabaseConnection(String dbUrl, String userName, String password, int minIdle, int maxActive) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //also you need the MySQL driver

        } catch (Exception e) {
            LOG.error("Error when attempting to obtain DB Driver: " + new Date().toString(), e);
        }

        System.out.println("Trying to connect to database...");
        Connection connection = null;
        try {
            this.ds = setupDataSource(dbUrl, userName, password, minIdle, maxActive);

            connection = getConnection();

            System.out.println("Connection attempt to database succeeded.");
            LOG.debug("Connection attempt to database succeeded.");

        } catch (Exception e) {
            System.out.println("Error when attempting to connect to DB using ('" + dbUrl + "','" + userName + "')" + e);
            LOG.error("Error when attempting to connect to DB using ('" + dbUrl + "','" + userName + "')", e);
        } finally {
            if (connection != null && !connection.isClosed())
                connection.close();
        }

    }

    protected void finalize() {
        LOG.debug("Finalizing ConnectionManager");
        try {
            super.finalize();
        } catch (Throwable ex) {
            LOG.error("ConnectionManager finalize failed to disconnect from mysql: ", ex);
        }
    }

    private DataSource setupDataSource(String connectURI,
                                       String username,
                                       String password,
                                       int minIdle, int maxActive
    ) throws Exception {

        connectionPool = new GenericObjectPool(null);

        connectionPool.setMinIdle(minIdle);
        connectionPool.setMaxActive(maxActive);
        connectionPool.setTestOnBorrow(true);
        connectionPool.setMinEvictableIdleTimeMillis(DB_EVICTION_TIMEOUT_MILLISEC);
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(connectURI, username, password);
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);


        return dataSource;

    }

    public Connection getConnection() throws SQLException {
        Connection connection = ds.getConnection();

        if (connection == null)
            connection = ds.getConnection();

        return connection;
    }

    public void leaveConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed())
            connection.close();
    }
}