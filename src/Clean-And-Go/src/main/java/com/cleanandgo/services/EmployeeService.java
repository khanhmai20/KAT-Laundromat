package com.cleanandgo.services;

import com.cleanandgo.common.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.*;
import java.time.format.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.cleanandgo.data.EmployeeRepository;
import com.cleanandgo.data.RepositoryFactory;
import com.cleanandgo.models.*;

/**
 * Contains functionality for managing employees.
 */
public class EmployeeService extends Service<Employee>
{
	final int ADD_EMPLOYEE_SELECTED = 1;
	final int SEARCH_EMPLOYEE_SELECTED = 2;
	final int VIEW_SCHEDULES_SELECTED = 3;
	
	final int RETURN_SELECTED = 0;
	
	public EmployeeService(Connection conn) 
	{
		super(conn);
		repository = RepositoryFactory.getEmployeeRepository();
	}
	
	
	public void launch()
	{
		boolean continueEmployeeService = true;
		
		while (continueEmployeeService)
		{
			System.out.println("\n" + Graphics.EmployeesMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case RETURN_SELECTED: // Go back to main menu.
					{
						return;
					}
					case ADD_EMPLOYEE_SELECTED: 
					{
						createAll();
						break;
					}
					case SEARCH_EMPLOYEE_SELECTED: 
					{
						getAll();
						break;
					}
					case VIEW_SCHEDULES_SELECTED: 
					{
  					    viewWeeklySchedules();
						break;
					}

					default:
					{
						throw new IllegalArgumentException("Invalid selection.");
					}
				}
			} 
			catch (IllegalArgumentException ex) 
			{
				System.out.println(ex.getMessage());
			}
		}
		
	}


	private void viewWeeklySchedules() 
	{
		System.out.println("\nWeekly Schedules");
		System.out.println("--------------------------------------------------------");
		
		List<Employee> employees = this.repository.getAll();
		
		DayOfWeek firstDayOfWeek = WeekFields.of(Locale.US).getFirstDayOfWeek();
		DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
		
		LocalDate first = LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
		LocalDate last = LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
		
		for (int i = 0; i < employees.size(); i++) 
		{
			Employee currentEmployee = employees.get(i);
			
			System.out.println(currentEmployee.getEmployeeProfile().employeeName);
			
			List<WorkSchedule> schedules = currentEmployee.getSchedules().stream()
											.filter(schedule -> 
												schedule.WorkDate.isBefore(last) 
												& schedule.WorkDate.isAfter(first))
											.collect(Collectors.toList());
			
			if (schedules.isEmpty())
			{
				System.out.println("\tNo Schedules");
			}
			else
			{
				schedules.forEach(schedule -> 
					System.out.printf(
							"\tDate: %s, Start Time: %s, Hours Worked: %s",
							schedule.WorkDate.toString(),
							schedule.StartTime.toString(),
							schedule.EndTime.toString()
					)
					
				);
			}
			
			System.out.println();
		}
	}


	@Override
	void create()
	{
    	EmployeeProfile employeeProfile = getEmployeeProfile();
		EmployeeUser employeeUser = getEmployeeUser(employeeProfile.employeeName);
		List<Address> addresses = getEmployeeAddresses();
		List<Contact> contacts = getEmployeeContacts();
		List<WorkSchedule> schedules = getEmployeeSchedules();
		
		Employee newEmployee = new Employee
		(
				employeeProfile,
				employeeUser,
				addresses,
				contacts,
				schedules
		);
				
		this.repository.add(newEmployee);
		this.repository.save();
	}

	EmployeeProfile getEmployeeProfile()
	{
		System.out.println("\nProfile");
		System.out.println("--------------------------------------------------------");
		
		String firstName = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's first name: ");
		String lastName = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's last name: ");
		char gender = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's gender: (M/F) ").charAt(0);
    	LocalDate employmentDate = LocalDate.parse(LocalDate.now().toString());
    	String employeePosition = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's position: ");
    	BigDecimal salary = new BigDecimal(InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's salary: "));
    	
    	Name employeeName = new Name(firstName, lastName);
    	
    	return new EmployeeProfile(employeeName, gender, employmentDate, employeePosition, salary);
    	
	}
	
	EmployeeUser getEmployeeUser(Name employeeName)
	{
		System.out.println("\nCredentials");
		System.out.println("--------------------------------------------------------");
		String userName = CredentialsHandler.getUserName(employeeName);
		System.out.printf("The employee's user name was generated as: %s%n", userName);
		
		String password = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's password: ");
		password = CredentialsHandler.getHashedPassword(password);
		
		return new EmployeeUser(userName, password);
	}
	
	List<Address> getEmployeeAddresses()
	{
		List<Address> addresses = new ArrayList<>();
		
		System.out.println("\nAddresses");
		System.out.println("--------------------------------------------------------");
		
		for (int i = 1; i <= AddressType.addressTypes.size(); i++)
		{
			AddressType currentAddressType = new AddressType(i);
			
			String line1 = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's %s address: ", currentAddressType.TypeName);
			String line2 = InputHandler.promptUser("-> Please enter any additional info about the employee's %s address: ", currentAddressType.TypeName);
			String city =  InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's city: ");
			String state = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the employee's state: ");
			String zipcode = InputHandler.promptUser(InputHandler.isValidZipcode, "-> Please enter the employee's zipcode: ");
			
			Address currentAddress = new Address(currentAddressType, line1, line2, city, new State(49, state, "WA"), zipcode);
			
			addresses.add(currentAddress);
			
			if (i == 1)
			{
				boolean shippingSameAsBilling = InputHandler.promptUser("\n-> Is your shipping address the same as your billing address? (Y/N) ")
						.toUpperCase()
						.contentEquals("Y");
				
				if (shippingSameAsBilling)
				{
					Address shippingAddress = new Address(currentAddress);
					shippingAddress.AddressType.AddressTypeID = 2;
					addresses.add(shippingAddress);
					
					break;
				}
			}
			
		}
		
		return addresses;
	}
	
	List<Contact> getEmployeeContacts()
	{
		List<Contact> contacts = new ArrayList<>();
		
		boolean addMoreContacts = InputHandler.promptUser("\n-> Do you want to add contact methods for this employee? (Y/N) ")
				.toUpperCase()
				.contentEquals("Y");
		
		if (!addMoreContacts)
		{
			return contacts;
		}
		
		System.out.println("\nContacts");
		System.out.println("--------------------------------------------------------");
		
		for (int i = 1; i <= ContactType.contactTypes.size(); i++)
		{
			ContactType currentContactType = new ContactType(i);
			
			String phoneNumber = InputHandler.promptUser(InputHandler.isValidPhoneNumber, "-> Please enter the employee's %s number (with dashes): ", currentContactType.TypeName);
			
			Contact currentContact = new Contact(currentContactType, phoneNumber);
			
			contacts.add(currentContact);
			
			if (i != ContactType.contactTypes.size())
			{
				addMoreContacts = InputHandler.promptUser("\n-> Do you want to add more contact methods? (Y/N) ")
						.toUpperCase()
						.contentEquals("Y");
				
				if (!addMoreContacts)
				{
					break;
				}
			}
		}
		
		return contacts;
	}
	
	List<WorkSchedule> getEmployeeSchedules()
	{
		List<WorkSchedule> schedules = new ArrayList<>();
		
		boolean addMoreSchedules = InputHandler.promptUser("\n-> Do you want to add schedules for this employee? (Y/N) ")
				.toUpperCase()
				.contentEquals("Y");
		
		if (!addMoreSchedules)
		{
			return schedules;
		}
		
		System.out.println("\nWork Schedules");
		System.out.println("--------------------------------------------------------");
		
		while (true)
		{
			LocalDate workDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the work date (YYYY-MM-DD): "));
			LocalTime startTime = LocalTime.parse(InputHandler.promptUser(InputHandler.isValidLocalTime, "-> Please enter the start time in 24 hour format (HH:MM): "));
			LocalTime endTime = LocalTime.parse(InputHandler.promptUser(InputHandler.isValidLocalTime, "-> Please enter the end time in 24 hour format (HH:MM): "));
			
			if (endTime.compareTo(startTime) < 0)
			{
				System.out.println("Error: End time cannot be greater than start time. ");
				continue;
			}
			
			LocalTime hoursWorked = LocalTime.parse
			(
					String.valueOf(Duration.between(startTime, endTime).toHours()),
					DateTimeFormatter.ofPattern("H")
			);
			
			WorkSchedule currentSchedule = new WorkSchedule(workDate, startTime, endTime, hoursWorked);
			schedules.add(currentSchedule);
			
			addMoreSchedules = InputHandler.promptUser("\n-> Do you want to add more schedules? (Y/N) ")
					.toUpperCase()
					.contentEquals("Y");
			
			if (!addMoreSchedules)
			{
				break;
			}
		}
		
		return schedules;
	}

	@Override
	void createAll() 
	{
		boolean continueAdding = true;
		
		while(continueAdding)
		{
			try 
			{
				create();
			} 
			catch (Exception ex) 
			{
				System.out.println("Failed to create employee. ");
//				continue;
			}
			
			continueAdding = InputHandler.promptUser("\n-> Do you want to add another employee? (Y/N) ")
								.toUpperCase()
								.contentEquals("Y");

		}
	}


	@Override
	void get(int employeeID) 
	{
		Employee employee = this.repository.getByID(employeeID);
		
		if (employee != null)
		{
			EmployeeProfile profile = employee.getEmployeeProfile();
			System.out.println("\nProfile");
			System.out.println("----------------------------------------------");
			System.out.printf("Name: %s (%c)%n", profile.employeeName, profile.gender);
			
			
			List<Address> addresses = employee.getAddresses();
			System.out.println("\nAddresses");
			System.out.println("----------------------------------------------");
			
			for (int i = 0; i < addresses.size(); i++)
			{
				Address currentAddress = addresses.get(i);
				System.out.println(currentAddress);
			}
			
			List<Contact> contacts = employee.getContacts();
			System.out.println("\nContacts");
			System.out.println("----------------------------------------------");
			
			for (int i = 0; i < contacts.size(); i++)
			{
				Contact currentContact = contacts.get(i);
				System.out.println(currentContact);
			}
			
			List<WorkSchedule> schedules = employee.getSchedules();
			System.out.println("\nSchedules");
			System.out.println("----------------------------------------------");
			
			for (int i = 0; i < schedules.size(); i++)
			{
				WorkSchedule currentSchedule = schedules.get(i);
				System.out.println(currentSchedule);
			}
		}
		else
		{
			System.out.println("Not found.");
		}

	}


	@Override
	void getAll() 
	{
		boolean continueSearch = true;
		
		while(continueSearch)
		{
			try 
			{
				int employeeID = Integer.valueOf(InputHandler.promptUser("\n-> Please enter an employee ID: "));
				get(employeeID);
				
			} 
			catch (IllegalArgumentException ex) 
			{
				System.out.println("Not found.");
//				continue;
			}
			catch (IndexOutOfBoundsException ex)
			{
				System.out.println("Not found.");
//				continue;
			}
			catch (Exception ex)
			{
				System.out.println("Not found.");
			}
			
			continueSearch = InputHandler.promptUser("\n-> Do you want to keep searching? (Y/N) ")
								.toUpperCase()
								.contentEquals("Y");

		}
	}


	@Override
	void update() 
	{
		System.out.println();
//		this.repository.update(employee);
//		this.repository.save();
	}


	@Override
	void remove() 
	{
//		this.repository.remove(employee);
//		this.repository.save();
		
	}


	@Override
	void removeAll() 
	{
		
	}


	@Override
	<U> List<Employee> filter(Predicate<U> predicate) 
	{
		return null;
	}
}
