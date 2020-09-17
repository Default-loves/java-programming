package com.junyi.simpledatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class SimpleDataSourceApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public static void main(String[] args) {
		SpringApplication.run(SimpleDataSourceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		showConnection();
		showData();
	}
	public void showConnection() throws SQLException {
		System.out.println(dataSource.toString());
		Connection conn = dataSource.getConnection();
		System.out.println(conn.toString());
		conn.close();
	}
	public void showData() {
		jdbcTemplate.queryForList("select * from FOO")
				.forEach(row -> System.out.println(row.toString()));

	}
}
