package main.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import main.model.Course;
import main.model.CourseOffering;

@SuppressWarnings("unused")
public class DBManager {
	private Connection connection;

	public DBManager() {
		String url = System.getenv("ENSF_DB_URL");
		String user = System.getenv("ENSF_DB_USER");
		String password = System.getenv("ENSF_DB_PASSWORD");
	}
}
