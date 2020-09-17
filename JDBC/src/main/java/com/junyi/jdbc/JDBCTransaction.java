package com.junyi.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.junyi.jdbc.BasisMethod.delete;
import static com.junyi.jdbc.BasisMethod.update;
import static com.junyi.jdbc.BasisMethod.insert;
/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: JDBC 事务示例
 */
public class JDBCTransaction {
    public static void main(String[] args) throws SQLException {
        String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "123456";
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        try {
            // 关闭自动提交:
            conn.setAutoCommit(false);
            // 执行多条SQL语句:
            insert();
            update();
            delete();
            // 提交事务:
            conn.commit();
        } catch (SQLException e) {
            // 回滚事务:
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}
