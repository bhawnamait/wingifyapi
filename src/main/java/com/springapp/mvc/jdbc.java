package com.springapp.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class jdbc {

    public static final String USER_table = "user";

    private static final String sqlInsertUs = "INSERT INTO " + USER_table + " (username,password) VALUES (?,?)";
    private static final String selectTableSQL = "SELECT username,password from user";
    private static final String SQLS = "SELECT id,name ,about from ITEM";
    private static final String SQLSt = "SELECT name, about, price FROM ITEM WHERE name = ?";
    private static final String SQLStat = "SELECT token from user";
    private static final String SQ = "SELECT * from user WHERE token = ?";
    private static final String s = "SELECT * from ITEM WHERE id_user = ? AND name = ?";
    private static final String sz = "SELECT id from user WHERE username = ? AND password= ?";
    //    private static String TABLE_NAME = "ITEM";
//    public static void editItem(Connection connection,int idx,String name,String about,Integer price) throws SQLException {
//        // String sql = "UPDATE " + USER_EDUCATION_TABLE_NAME + "  SET school_name=?,time_from=?, time_to=?,degree=?,field_of_study=? where global_user_id=? and order_id=?";
//        String sqlele =  "UPDATE " +  TABLE_NAME + "  SET name=?,about=?  where id=? ";
//       // String s =  "SELECT username,password from User by id";
//        boolean success = false;
//        PreparedStatement preparedStatement = null;
//        try {
//
//            preparedStatement = connection.prepareStatement(sqlele);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, about);
//            preparedStatement.setInt(3, idx);
//            preparedStatement.executeUpdate();
//
//
//        }catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } finally {
//            preparedStatement.close();
//        }
//        return ;
//    }
    private static String TABLE_Pro = "ITEM";
    private static final String sqlInsertPro = "INSERT INTO " + TABLE_Pro + " (name,about,price) VALUES (?,?,?)";
    private static final String sqlDeletePro = "DELETE FROM " + TABLE_Pro + " where id=?";

    public static Boolean newUser(Connection connection, String username, String password) throws SQLException {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUs);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
            System.out.print("Databaseconnection");
            return true;
        } catch (SQLException e) {
            throw e;
        }

    }

    public static int IfAuthUser(Connection connection, String username, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
//


            preparedStatement = connection.prepareStatement(selectTableSQL);
            ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
            while (rs.next()) {
                String usern = rs.getString("username");
                String passw = rs.getString("password");
                if ((username.equals(usern)) && (password.equals(passw))) {
                    return 1;
                }
            }
            return 0;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void setToken(Connection connection, String username, String token) throws SQLException {
        String sqlele = "UPDATE " + USER_table + "  SET token=? where username=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlele);

            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            preparedStatement.close();
        }


    }

    public static void addItem(Connection connection, String name, String description, Integer price) throws SQLException {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertPro);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, price.toString());
            preparedStatement.executeUpdate();
            return;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {

            preparedStatement.close();
        }

    }

    public static boolean deleteItem(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlDeletePro);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static ArrayList<Item> findAllItem(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<Item> s = new ArrayList<Item>();
        try {
            preparedStatement = connection.prepareStatement(SQLS);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String RsId = rs.getString("id");
                int id = Integer.parseInt(RsId);
                Item pro = new Item();
                pro.name = rs.getString("name");
                pro.about = rs.getString("about");
                pro.id = id;

                s.add(pro);
            }
            return s;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static ArrayList<Item> searchByName(Connection connection, String name) {
        ArrayList<Item> a = new ArrayList<Item>();
        PreparedStatement preparedStatement = null;
        Item t = new Item();
        try {
            preparedStatement = connection.prepareStatement(SQLSt);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                t.name = rs.getString("name");
                t.about = rs.getString("about");
                t.price = rs.getInt("price");

                a.add(t);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }

    public static boolean IfUserExist(Connection connection, String token) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQLStat);
            ResultSet rs = preparedStatement.executeQuery(SQLStat);
            while (rs.next()) {
                String usern = rs.getString("token");

                if ((token.equals(usern))) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }

    }

    public static Integer getId(Connection connection, String token) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SQ);
            preparedStatement.setString(1, token);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public static int getItemId(Connection connection, int user_id, String name) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setString(2, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return -1;
    }

    public static boolean isRegistered(Connection connection, String username, String password) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sz);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rs.getInt("id");
                return true;
            }

        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
