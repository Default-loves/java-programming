package com.junyi.jdbc;

import java.sql.*;

/**
 * User: JY
 * Date: 2020/7/22 0022
 * Description: 增删改查操作
 */
public class BasisMethod {
    // JDBC连接的URL, 不同数据库有不同的格式:
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        select();
        update();
        insert();
        delete();
    }


    public static void select() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, grade, name, gender FROM students WHERE gender=? AND grade=?")) {
                ps.setObject(1, "M"); // 注意：索引从1开始
                ps.setObject(2, 3);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        long id = rs.getLong("id");
                        long grade = rs.getLong("grade");
                        String name = rs.getString("name");
                        String gender = rs.getString("gender");
                        System.out.printf("id: %d, grade: %d, name: %s, gender: %s\n", id, grade, name, gender);
                    }
                }
            }
        }
    }

    public static void insert() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO students (grade, name, gender) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {     //使其执行后能够返回自增主键的值
                ps.setObject(1, 1); // grade
                ps.setObject(2, "Bob"); // name
                ps.setObject(3, "M"); // gender
                int n = ps.executeUpdate(); // 1
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1); // 注意：索引从1开始
                    }
                }
            }
        }
    }
    public static void update() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE students SET name=? WHERE id=?")) {
                ps.setObject(1, "Bob"); // 注意：索引从1开始
                ps.setObject(2, 999);
                int n = ps.executeUpdate(); // 返回更新的行数
            }
        }
    }
    public static void delete() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id=?")) {
                ps.setObject(1, 999); // 注意：索引从1开始
                int n = ps.executeUpdate(); // 删除的行数
                System.out.printf("Delete: %d row\n", n);
            }
        }
    }

}
