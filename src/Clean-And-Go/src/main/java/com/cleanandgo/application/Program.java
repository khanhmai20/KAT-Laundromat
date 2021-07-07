package com.cleanandgo.application;

import com.cleanandgo.common.Graphics;
import com.cleanandgo.common.InputHandler;
import com.cleanandgo.common.Session;
import com.cleanandgo.data.*;
import com.cleanandgo.models.*;
import com.cleanandgo.services.*;

import java.sql.*;

public class Program
{
	static EmployeeService employeeService = null;
	static AssetService assetService = null;
	static CustomerService customerService = null;
	
	static final int QUIT_SELECTED = 0;
	static final int EQUIPMENT_SUPPLIES_SELECTED = 1;
	static final int CUSTOMER_SERVICE_SELECTED = 2;
	static final int EMPLOYEES_SELECTED = 3;
	
	public static void main(String[] args) throws SQLException 
	{
		launch();
	}
	
	/**
	 * Launches the application with the given connection.
	 */
	public static void launch()
	{
		if (!Session.isLoggedIn())
		{
			System.out.println("-> Employees are required to log in before using the application.\n");
			Session.logIn();
		}
		
		boolean continueApplication = true;
		
		while (continueApplication)
		{
			try 
			{
				System.out.println();
				System.out.println(Graphics.Banner);
				System.out.println(Graphics.MainMenu);
				
				int command = Integer.valueOf(InputHandler.promptUser("\n-> Please select an option: "));
				System.out.println(); // Spacer
				
				switch (command)
				{
					case QUIT_SELECTED:
					{
						close();
						break;
					}
					case EQUIPMENT_SUPPLIES_SELECTED:
					{
						if (assetService == null)
						{
							assetService = new AssetService(Session.connect());
						}
						
						System.out.println();
						assetService.launch();
						System.out.println(); // Spacer
						break;
					}
					case CUSTOMER_SERVICE_SELECTED: 
					{
						if (customerService == null)
						{
							customerService = new CustomerService(Session.connect());
						}
						
						customerService.launch();
						break;
					}
					case EMPLOYEES_SELECTED: 
					{
						if (employeeService == null)
						{
							employeeService = new EmployeeService(Session.connect());
						}
						
						System.out.println();
						employeeService.launch();
						System.out.println(); // Spacer
						break;
					}
					default:
					{
						throw new IllegalArgumentException("Invalid selection.\n");
					}
				}
			} 
			catch (IllegalArgumentException ex) 
			{
				System.out.println(ex.getMessage());
				continue;
			}
			catch (Exception ex)
			{
				System.out.println("Invalid selection.\n");
				continue;
			}
		}
	}
	
	/**
	 * Attempts to close the application.
	 */
	public static void close()
	{
		boolean closeApplication = false;
		
		while (!closeApplication)
		{
			try 
			{
				System.out.println(Graphics.QuitMenu);
				int command = Integer.valueOf(InputHandler.promptUser("\n-> Please select an option: "));
				System.out.println(); // Spacer
				
				switch (command) 
				{
					case 0:
					{
						return;
					}
					case 1:
					{
						System.out.println("-> You have been logged out.");
						System.exit(0);
					}
					default:
					{
						throw new IllegalArgumentException("Invalid selection. \n");
					}
				}
				
			} 
			catch (IllegalArgumentException ex) 
			{
				System.out.println(ex.getMessage());
				continue;
			}
		}
		
	}
}
