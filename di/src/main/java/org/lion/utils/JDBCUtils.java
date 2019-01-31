package org.lion.utils;

import java.sql.*;

/**
 * Created by more on 2016-05-27 16:56.
 * This class is utils of jdbc
 */
public class JDBCUtils {
    private JDBCUtils() {
    }

    public static void close(Connection conn, Statement stat, ResultSet rs) throws SQLException {
        try {
            if (rs != null) {
                rs.close();
            }
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
    }
    public static void close(Connection conn, Statement stat) throws SQLException {
       close(conn,stat,null);
    }

    public static Connection getMySqlConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static Connection getMySqlConnection(String dataBase) throws ClassNotFoundException, SQLException {
        return getMySqlConnection("jdbc:mysql:///" + dataBase, "root", "root");
    }
}
