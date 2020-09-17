package com.junyi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description:
 */
public class JDBCDemo {
    public static void main(String[] args) throws SQLException {
        // JDBC连接的URL, 不同数据库有不同的格式:
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "123456";
        // 获取连接:
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

        // 关闭连接:
        conn.close();
    }
}
