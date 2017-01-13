package com.springapp.mvc;

/**
 * Created by bhawna on 12/01/17.
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserQuery {

    private static DatabaseConnection databaseConnection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ///  databaseConnection = new DatabaseConnection("mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/wingify", "admin9Hzihi5", "nMqYGWwuwZcS", 40, 50);
            databaseConnection = new DatabaseConnection(
                    SysProperties.getInstance().getProperty("DB_URL")
                    , SysProperties.getInstance().getProperty("DB_USERNAME")
                    , SysProperties.getInstance().getProperty("DB_PASSWORD"), 40, 50);
            //   databaseConnection = new DatabaseConnection("jdbc:mysql://5877f51a7628e14372000104-codetillyoudie.rhcloud.com:36066/wingify", "adminGlMDj6D", "azSB3DQurQ34", 40, 50);
            // databaseConnectionPool = new DatabaseConnectionPool("jdbc:mysql://127.3.222.2:3306/UserDB", "admin1jlKkX2", "6EXDv53FUuKi", 40, 50);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getDataseBaseConnectionPool() {
        return databaseConnection;
    }
//        try {
//
//        } catch (SQLException e) {
//
//        } finally {
//            try {
//                leaveConnection(connection);
//            } catch (SQLException e) {
//            }
//        }
//        return null;
//    }

    private Connection getConnection() throws SQLException {

        return getDataseBaseConnectionPool().getConnection();
    }

    private void leaveConnection(Connection connection) throws SQLException {

        getDataseBaseConnectionPool().leaveConnection(connection);
    }

    public Boolean newUser(String username, String password) {
        Connection connection = null;
        try {
            connection = getConnection();

            return jdbc.newUser(connection, username, password);

        } catch (SQLException e) {

        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public void setToken(String username, String password, String token) {
        Connection connection = null;
        try {
            connection = getConnection();
            jdbc.setToken(connection, username, token);

        } catch (SQLException e) {

        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }

    }

    public ArrayList<Item> View() throws SQLException {
        ArrayList<Item> a = new ArrayList<Item>();
        a = jdbc.findAllItem(getConnection());
        return a;
    }


    public ArrayList<Item> searchItem(String name) {
        ArrayList<Item> a = null;
        Connection connection = null;
        try {
            connection = getConnection();
            a = jdbc.searchByName(getConnection(), name);
        } catch (SQLException e) {

        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }


        return a;
    }

    public void addItem(String name, String about, Integer price) {
        Connection connection = null;
        try {
            connection = getConnection();

            jdbc.addItem(connection, name, about, price);
            return;
        } catch (SQLException e) {

        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }
        return;
    }


    public boolean checkIfUsernameExist(String token) {
        Connection connection = null;
        try {
            connection = getConnection();
            boolean IfUserExist = jdbc.IfUserExist(connection, token);
            return IfUserExist;
        } catch (SQLException e) {

        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public boolean deleteItem(int id) throws SQLException {
        Connection connection = null;
        connection = getConnection();
        try {
            return jdbc.deleteItem(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public int getId(String cookie) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return jdbc.getId(connection, cookie);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                leaveConnection(connection);
            } catch (SQLException e) {
            }
        }
        return -1;
    }

    public boolean isRegistered(String username, String password) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jdbc.isRegistered(connection, username, password);

    }

    public int getItemId(int user_id, String name) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int id = jdbc.getItemId(connection, user_id, name);
        return id;
    }
}
