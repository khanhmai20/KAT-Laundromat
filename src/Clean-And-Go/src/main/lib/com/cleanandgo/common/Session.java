package com.cleanandgo.common;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cleanandgo.models.CustomerTransaction;
import com.cleanandgo.models.Employee;
import com.cleanandgo.models.EmployeeUser;
import com.cleanandgo.models.PaymentType;
import com.cleanandgo.models.Service;
import com.cleanandgo.models.ServiceType;
import com.cleanandgo.models.TransactionDetail;

/**
 * Contains information about the current session.
 */
public class Session 
{
	public static Connection connection;
	public static EmployeeUser user;
	public static int userID;
	
	static
	{
		connection = connect();
		user = null;
		userID = 0;	
	}
	
	/**
	 * Connects to a MySQL connection.
	 * @return The MySQL connection.
	 */
	public static Connection connect()
	{
		try 
		{
			connection = DriverManager.getConnection
			(
					"jdbc:mysql://cleanandgo.mysql.database.azure.com:3306/cleanandgo_development?serverTimezone=UTC", 
					"asathkumara@cleanandgo",
					"G9nY9YjcjlWvGy6qO7jw"
			);
			
			return connection;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Attempts to close the connection.
	 */
	public static void close()
	{
		try 
		{
			connection.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks whether there is a user logged in.
	 * @return True if the user is logged in, false otherwise.
	 */
	public static boolean isLoggedIn()
	{
		return user != null;
	}
	
	/**
	 * Logs the user in.
	 */
	public static void logIn()
	{
		int logInAttempts = 3;
		
		String userName = InputHandler.promptUser(InputHandler.isValidUserName, "\n-> Please enter your username (ex: sfarag@cleango): ");
		String userPassword = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter your password (ex: abc123): ");
		logIn(userName, userPassword);
		
		while (user == null)
		{
			logInAttempts--;
			
			if (logInAttempts <= 0)
			{
				System.out.println("\n-> You entered invalid credentials too many times. Application has been aborted.");
				System.exit(0);
			}
			
			System.out.println("\n-> Account not found. Please contact your administrator if you would like a new password.");
			System.out.printf("%nAttempts Left: %d%n", logInAttempts);
			
			userName = InputHandler.promptUser(InputHandler.isValidUserName, "\n-> Please enter your username (ex: sfarag@cleango): ");
			userPassword = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter your password: ");
			logIn(userName, userPassword);
		}
		
	}
	
	/**
	 * Helper to retrieve user credentials.
	 * @param userName The username that was entered
	 * @param password The plaintext password
	 */
	private static void logIn(String userName, String password)
	{
		String sql = "SELECT *  "
				   + "FROM Employee_User "
				   + "WHERE User_Name = ?";
		try 
		{
			PreparedStatement ps = connection.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, userName);
			
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					String hashedPassword = rs.getString("User_Password");
					
					if (CredentialsHandler.checkHashedPassword(password, hashedPassword))
					{
						user = new EmployeeUser(userName, password);
						userID = rs.getInt("Employee_ID");
					}
				}
			}
			
		}
		catch (SQLException e) 
		{
			user = null;
	    }

	}
	
}
