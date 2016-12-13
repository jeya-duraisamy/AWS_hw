package com.rj.hw.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rj.hw.model.Users;


@RestController
@RequestMapping("/service")
public class UsersController {
	
	protected static final String DB_URL = "jdbc:oracle:thin:tl5/tl5@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=stgrac-scan)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=tl5prd_rac.prod.elemica.com)))";
	

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public List<Users> getUsers(){
		Connection dbConn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			dbConn = DriverManager.getConnection(DB_URL);
			
			Statement st = null;
			
			st = dbConn.createStatement();
			// Getting the integration_stage_content
			ResultSet rs = st
					.executeQuery("SELECT person_id, FIRST_NAME, LAST_NAME, USER_ID FROM USERS WHERE ROWNUM <= 20 ");
			
			List<Users> users = new ArrayList<>();
			
			if (rs.next()) {
				Users user = new Users();
				user.setFirstName( rs.getString("FIRST_NAME"));
				user.setLastName(rs.getString("LAST_NAME"));
				user.setUserId(rs.getString("USER_ID"));
				user.setPersonId(rs.getLong("PERSON_ID"));
				
				users.add(user);
			}
			
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dbConn != null) {
				try {
					dbConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" Connection closed");
			}
		}
		
		return null;
	}
	
}
