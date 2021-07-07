package com.cleanandgo.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains functionality for all graphics used in the application.
 */
public class Graphics 
{
	public static String Banner;
	public static String MainMenu;
	public static String CustomerServicesMenu;

	public static String AssetMenu;
	public static String AssetExpensesMenu;
	public static String AssetEquipmentMenu;
	public static String AssetSuppliesMenu;
	

	public static String EmployeesMenu;
	public static String QuitMenu;
	
	// Initialize the menus as strings.
	static 
	{
		try
		{
			Banner = fileToString("Banner.txt");
			MainMenu = fileToString("MainMenu.txt");
			CustomerServicesMenu  = fileToString("CustomerServicesMenu.txt");
			CustomerServicesMenu  = fileToString("CustomerServiceMenu.txt");
			
			AssetMenu  = fileToString("AssetMenu.txt");
			AssetExpensesMenu = fileToString("AssetExpensesMenu.txt");
			AssetEquipmentMenu = fileToString("AssetEquipmentMenu.txt");	
			AssetSuppliesMenu = fileToString("AssetSuppliesMenu.txt");
			
			EmployeesMenu = fileToString("EmployeesMenu.txt");
			
			QuitMenu = fileToString("QuitMenu.txt");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Converts a file at the given path to a string
	 * @param filePath The file to be converted
	 * @return A string containing the file contents
	 * @throws IOException File could not be read.
	 */
	private static String fileToString(String filePath) throws IOException
	{
		InputStreamReader is = new InputStreamReader(Graphics.class.getClassLoader().getResourceAsStream(filePath));
		String fileAsString = new BufferedReader(is).lines().collect(Collectors.joining("\n"));
		
		return fileAsString + "\n";
	}
	
	/**
	 * Type writer effect, one character per second is sent to the standard input.
	 * @param text The text to be written
	 */
	public static void type(String text)
	{
		for (int i = 0; i < text.length(); i++)
		{
			System.out.printf("%c", text.charAt(0));
			
			try
		    {
		        Thread.sleep(1000);
		    }
		    catch (InterruptedException ex)
		    {
		        Thread.currentThread().interrupt();
		    }
		}
	}
	
	public static void typeLine(String text)
	{
		type(text);
		System.out.print("\n");
	}
	
	
	/**
	 * Displays the given text as a progress bar
	 * @param text The text to be displayed.
	 */
	public static void showProgressBar(String text)
	{
		System.out.print(text);
		type("...");
	}
}
