package com.cleanandgo.common;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import com.cleanandgo.data.RepositoryFactory;
import com.cleanandgo.models.AssetType;
import com.cleanandgo.models.Employee;
import com.cleanandgo.models.ServiceType;
import com.mysql.cj.util.StringUtils;

/**
 * Contains functionality to handler input using predicates.
 */
public class InputHandler 
{
	public static Predicate<String> isNotBlank;
	public static Predicate<String> isValidUserName;
	public static Predicate<String> isValidZipcode;
	public static Predicate<String> isValidPhoneNumber;
	public static Predicate<String> isValidLocalTime;
	public static Predicate<String> isValidLocalDate;
	public static Predicate<String> isValidAssetType;
	public static Predicate<String> isNumber;
	public static Predicate<String> isValidServiceType;
	public static Predicate<String> isValidEmployeeID;
	
	// Initialize all our predicates
	static 
	{
		isNotBlank = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				return value.length() > 0;
			}
			
		};
		
		isValidUserName = new Predicate<String>()
		{
			@Override
			public boolean test(String value) 
			{
				try 
				{
					return value.substring(value.indexOf("@") + 1, value.length()).contentEquals("cleango");
				} 
				catch (Exception e)
				{
					return false;
				}
			}
		};
	
		isValidPhoneNumber = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					String digits = value.substring(0, 3)
							+ value.substring(4, 7)
							+ value.substring(8, value.length());
					
					boolean isDigits = StringUtils.isStrictlyNumeric(digits);
					
					return value.length() == 12
						&& isDigits
						&& value.charAt(3) == '-'
						&& value.charAt(7) == '-';
				} 
				catch (NumberFormatException ex) 
				{
					return false;
				}
				catch (IndexOutOfBoundsException ex) 
				{
					return false;
				}
				catch (Exception ex) 
				{
					return false;
				}
				
			}
			
		};
		
		isValidZipcode = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					boolean isValidZip = StringUtils.isStrictlyNumeric(value.substring(0, 5));
					
					if (value.length() > 5)
					{
						return value.charAt(5) == '-' 
							&&	StringUtils.isStrictlyNumeric(value.substring(value.indexOf('-') + 1, value.length()))
							&&	isValidZip;
					}
					
					return isValidZip;
				} 
				catch (NumberFormatException ex)
				{
					return false;
				}
				catch (Exception e) 
				{
					return false;
				}
			}
			
		};
		
		isValidLocalTime = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					boolean isValidShortFormat = value.substring(0, 2).length() == 2;
					
					if (value.length() > 2)
					{
						boolean isValidLongFormat = isValidShortFormat 
								&&	value.charAt(3) == ':'
								&&  value.substring(value.indexOf(':') + 1, value.length()).length() == 2;
						
						LocalTime tryLocalTime = LocalTime.parse(value);
						return true;
					}
					
					LocalTime tryLocalTime = LocalTime.parse(value);
					return true;
				} 
				catch (DateTimeParseException ex) 
				{
					return false;
				}
				catch (IndexOutOfBoundsException ex) 
				{
					return false;
				}
				catch (Exception ex) 
				{
					return false;
				}
				
			}
			
		};
		
		isValidLocalDate = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					boolean isValidFormat = value.length() == 10
							&& value.charAt(4) == '-'
							&& value.charAt(7) == '-';
							
					LocalDate tryLocalDate = LocalDate.parse(value);
					return true;
				} 
				catch (DateTimeParseException ex) 
				{
					return false;
				}
				catch (IndexOutOfBoundsException ex) 
				{
					return false;
				}
				catch (Exception ex) 
				{
					return false;
				}
				
			}
			
		};
		
		isValidAssetType = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					return AssetType.assetTypes.containsKey(Integer.valueOf(value));
				} 
				catch (Exception ex) 
				{
					return false;
				}
				
			}
			
		};
		
		isNumber = new Predicate<String>() {

			@Override
			public boolean test(String value) {
				try {
					return StringUtils.isStrictlyNumeric(value); 
				}
				catch (NumberFormatException ex) 
				{
					return false;
				}
			}
		};
		
		isValidServiceType = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					return ServiceType.serviceTypes.containsKey(Integer.valueOf(value));
				} 
				catch (Exception ex) 
				{
					return false;
				}
			} 
				
			
		};
		
		isValidEmployeeID = new Predicate<String>() 
		{

			@Override
			public boolean test(String value) 
			{
				try 
				{
					Employee employee = RepositoryFactory.getEmployeeRepository().getByID(Integer.valueOf(value));
					return employee != null;
				} 
				catch (Exception ex) 
				{
					return false;
				}
			} 
				
			
		};
	}
	
	private static String promptUser(String prompt) 
    {
        try 
        {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            
            while(c != '\n' && c != -1) 
            {
                buffer.append((char)c);
                c = System.in.read();
            }
            
            return buffer.toString().trim();
        } 
        catch (IOException e) 
        {
            return "";
        }
    }
	
	/**
	 * Prompts the user using the given prompt and checks it against the given predicate
	 * @param predicate The predicate to be tested
	 * @param prompt The prompt for the user
	 * @return A valid string that tested positive for the predicate.
	 */
	private static String promptUser(Predicate<String> predicate, String prompt)
	{
		boolean isValidInput = false;
		String userInput = "";
		
		
		while (!isValidInput)
		{
			userInput = promptUser(prompt);
			
			if (predicate.test(userInput))
			{
				isValidInput = true;
			}
			else
			{
				System.out.println("Invalid input.\n");
			}
		}
		
		return userInput;
		
	}
	
	public static String promptUser(String format, Object...args)
	{
		return promptUser(String.format(format, args));
	}
	
	public static String promptUser(Predicate<String> predicate, String format, Object...args)
	{
		return promptUser(predicate, String.format(format, args));
	}
}
