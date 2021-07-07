package com.cleanandgo.data;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.cleanandgo.common.Session;
import com.cleanandgo.models.*;

/**
 * Contains functionality for employee objects.
 */
public class EmployeeRepository extends Repository<Employee>
{

	/**
	 * Constructs a employee repository given the connection.
	 * @param conn The connection to be used.
	 */
	public EmployeeRepository(Connection conn)
	{
		super(conn);
		seedRepository();
	}
	

	@Override
	void seedRepository() 
	{
		String sql = "SELECT * "
				+    "FROM Employee ";
		
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					int currentEmployeeID = rs.getInt("Employee_ID");

					Name currentEmployeeName = new Name(rs.getString("First_Name"), rs.getString("Last_Name"));
					
					EmployeeProfile currentEmployeeProfile= new EmployeeProfile
					(
							currentEmployeeName, 
							rs.getString("Gender").charAt(0),
							rs.getDate("Employment_Date").toLocalDate(),
							rs.getString("Employee_Position"),
							rs.getBigDecimal("Salary")
					);
					
					EmployeeUser currentEmployeeUser = getEmployeeUser(currentEmployeeID);
					List<Address> currentEmployeeAddresses = getEmployeeAddresses(currentEmployeeID);
					List<Contact> currentEmployeeContacts = getEmployeeContacts(currentEmployeeID);
					List<WorkSchedule> currentEmployeeSchedules = getEmployeeSchedules(currentEmployeeID);
					
					Employee currentEmployee = new Employee
					(
							currentEmployeeID,
							currentEmployeeProfile,
							currentEmployeeUser,
							currentEmployeeAddresses,
							currentEmployeeContacts,
							currentEmployeeSchedules
					);
					
					context.add(currentEmployee);
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}
	
	/**
	 * Retrieves information about the employee's user credentials given the employee's ID.
	 * @param currentEmployeeID The employee's ID.
	 * @return An EmployeeUser object containing information about the employee's user credentials.
	 */
	private EmployeeUser getEmployeeUser(int currentEmployeeID) 
	{
		
		EmployeeUser employeeUser = null;
		
		String sql = "SELECT * "
				   + "FROM Employee_User "
				   + "WHERE Employee_ID = ? ";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, currentEmployeeID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					employeeUser = new EmployeeUser(rs.getString("User_Name"), rs.getString("User_Password"));
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return employeeUser;
	}
	
	/**
	 * Retrieves the employee's addresses using the given employeeID
	 * @param employeeID The employee whose addresses are to be retrieved
	 * @return Returns a list of the employee's addresses.
	 */
	private List<Address> getEmployeeAddresses(int employeeID)
	{
		List<Address> addresses = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Employee_Address "
				   + "JOIN State "
				   + "	USING (State_ID) "
				   + "JOIN Address_Type "
				   + "  USING (Address_Type_ID) "
				   + "WHERE Employee_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					AddressType currentAddressType = new AddressType(rs.getInt("Address_Type_ID"), rs.getString("Type_Name"));
					State currentState = new State(rs.getInt("State_ID"), rs.getString("State_Name"), rs.getString("State_Code"));
					
					Address currentAddress = new Address
					(
							currentAddressType, 
							rs.getString("Address_Line1"),
							rs.getString("Address_Line2"),
							rs.getString("City"),
							currentState,
							rs.getString("Zip_Code")
					);
					
					addresses.add(currentAddress);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return addresses;
	}
	
	/**
	 * Retrieves the employee's contact methods using the given employeeID.
	 * @param employeeID The employee whose contact methods  are to be retrieved.
	 * @return Returns a list of the employee's contact methods.
	 */
	private List<Contact> getEmployeeContacts(int employeeID) 
	{
		List<Contact> contacts = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Employee_Contact "
				   + "JOIN Contact_Type "
				   + "	USING (Contact_Type_ID) "
				   + "WHERE Employee_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					ContactType currentContactType = new ContactType(rs.getInt("Contact_Type_ID"), rs.getString("Type_Name"));
					Contact currentContact = new Contact(currentContactType, rs.getString("Phone_Number"));
					
					contacts.add(currentContact);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return contacts;
	}
	
	/**
	 * Retrieves the employee's work schedules using the given employeeID.
	 * @param employeeID The employee whose work schedules  are to be retrieved.
	 * @return Returns a list of the employee's work schedules.
	 */
	private List<WorkSchedule> getEmployeeSchedules(int employeeID)
	{
		List<WorkSchedule> employeeSchedules = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Employee_Schedule "
				   + "WHERE Employee_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employeeID);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					
//					WorkSchedule currentSchedule = new WorkSchedule
//					(
//							rs.getDate("Work_Date"),
//							rs.getTime("Start_Time"), 
//							rs.getTime("End_Time"), 
//							rs.getTime("Hours_Worked")
//					);
					
					WorkSchedule currentSchedule = new WorkSchedule
					(
							rs.getDate("Work_Date").toLocalDate(),
							rs.getTime("Start_Time").toLocalTime().plusHours(8),
							rs.getTime("End_Time").toLocalTime().plusHours(8),
							rs.getTime("Hours_Worked").toLocalTime().plusHours(8)
					);
					
					employeeSchedules.add(currentSchedule);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return employeeSchedules;
	}


	

	/**
	 * Adds an employee to the repository.
	 * @param employee The employee to be added.
	 */
	@Override
	public void add(Employee employee) 
	{
		String sql = "INSERT INTO Employee "
				+    "	(First_Name, Last_Name, Gender, Employment_Date, Employee_Position, Salary) "
				+    "VALUES "
				+    "	(?, ?, ?, ?, ?, ?) ";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, employee.getEmployeeProfile().employeeName.firstName);
			ps.setString(2, employee.getEmployeeProfile().employeeName.lastName);
			ps.setString(3, String.valueOf(employee.getEmployeeProfile().gender));
			ps.setDate(4, Date.valueOf(employee.getEmployeeProfile().employmentDate));
			ps.setString(5, employee.getEmployeeProfile().employeePosition);
			ps.setBigDecimal(6, employee.getEmployeeProfile().salary);
			
			ps.executeUpdate();
			employee.setEmployeeID(getNewEmployeeID());
			
			addEmployeeUser(employee);
			addEmployeeAddresses(employee);
			addEmployeeContacts(employee);
			addEmployeeSchedules(employee);
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
	}

	private int getNewEmployeeID() 
	{
		String sql = "SELECT Employee_ID "
				   + "FROM cleanandgo_development.employee  "
				   + "ORDER BY Employee_ID DESC LIMIT 1 ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					return rs.getInt("Employee_ID");
				}
				
				return -1;
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	        return -1;
	    }
		
	}

	/**
	 * Adds an employee user to the repository.
	 * @param employee The employee whose user credentials are to be added.
	 */
	private void addEmployeeUser(Employee employee) 
	{
		String sql = "INSERT INTO Employee_User "
				   + "VALUES "
				   + "	(?, ?, ?) ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, employee.getEmployeeID());
			ps.setString(2, employee.getEmployeeUser().UserName);
			ps.setString(3, employee.getEmployeeUser().UserPassword);
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}
	
	/**
	 * Adds an employee's addresses to the repository.
	 * @param employee The employee whose addresses are to be added.
	 */
	private void addEmployeeAddresses(Employee employee) 
	{
		String sql = "INSERT INTO Employee_Address "
				   + "VALUES "
				   + "	(?, ?, ?, ?, ?, ?, ?)";
		
		try 
		{
			for(int i = 0; i < employee.getAddresses().size(); i++)
			{
				Address currentAddress = employee.getAddresses().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, employee.getEmployeeID());
				ps.setInt(2, currentAddress.AddressType.AddressTypeID);
				ps.setString(3, currentAddress.Line1);
				ps.setString(4, currentAddress.Line2);
				ps.setString(5, currentAddress.City);
				ps.setInt(6, currentAddress.State.StateID);
				ps.setString(7, currentAddress.ZipCode);
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		
	}
	
	/**
	 * Adds an employee's contact methods to the repository.
	 * @param employee The employee whose contact methods are to be added.
	 */
	private void addEmployeeContacts(Employee employee) 
	{
		String sql = "INSERT INTO Employee_Contact "
				   + "VALUES "
				   + "	(?, ?, ?) ";
		
		try 
		{
			for(int i = 0; i < employee.getContacts().size(); i++)
			{
				Contact currentContact = employee.getContacts().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, employee.getEmployeeID());
				ps.setInt(2, currentContact.ContactType.ContactTypeID);
				ps.setString(3, currentContact.PhoneNumber);
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}
	
	/**
	 * Adds an employee's work schedules to the repository.
	 * @param employee The employee whose work schedules are to be added.
	 */
	private void addEmployeeSchedules(Employee employee)
	{
		String sql = "INSERT INTO Employee_Schedule "
				   + "	(Employee_ID, Work_Date, Start_Time, End_Time) "
				   + "VALUES "
				   + "	(?, ?, ?, ?) ";
		
		try 
		{
			for(int i = 0; i < employee.getSchedules().size(); i++)
			{
				WorkSchedule currentSchedule = employee.getSchedules().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, employee.getEmployeeID());
				ps.setDate(2, Date.valueOf(currentSchedule.WorkDate));
				
				// Plus 8 when we get back from sql, minus 8 when we insert into sql.
				ps.setTime(3, Time.valueOf(currentSchedule.StartTime.minusHours(8)));
				ps.setTime(4, Time.valueOf(currentSchedule.EndTime.minusHours(8)));
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
	}

	@Override
	public void addAll(List<Employee> employees) 
	{
		for (Employee employee : employees)
		{
			add(employee);
		}
		
	}

	/**
	 * Deletes an employee from the repository
	 * 
	 * @param employee The employee to be deleted.
	 */
	@Override
	public void delete(Employee employee)
	{
		String sql = "DELETE FROM Employee "
				   + "WHERE Employee_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, employee.getEmployeeID());
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}

	/**
	 * Deletes all employees using the given list of employees.
	 * 
	 * @param employees The list of employees to be removed.
	 */
	@Override
	public void deleteAll(List<Employee> employees)
	{
		for (Employee employee : employees)
		{
			delete(employee);
		}
	}

	/**
	 * Retrieves an employee using the given ID.
	 * 
	 * @param employeeID The employee's ID.
	 * @return An employee object.
	 */
	@Override
	public Employee getByID(int employeeID)
	{
		return context.stream()
				.filter(e -> e.getEmployeeID() == employeeID)
				.collect(Collectors.toList())
				.get(0);
	}

	/**
	 * Retrieves all employees.
	 * 
	 * @return A list of employees.
	 */
	@Override
	public List<Employee> getAll() 
	{
		return context;
	}
	
	
	/**
	 * Updates an employee using the given employee object.
	 * 
	 * @param employee The employee whose details are to be updated.
	 */
	@Override
	public void update(Employee employee)
	{
		String sql = "UPDATE Employee "
				+    "SET "
				+    "	First_Name = ?, "
				+    "	Last_Name = ?, "
				+    "	Gender = ?, "
				+    "	Employment_Date = ?, "
				+    "	Employee_Position = ?, "
				+    "	Salary = ? "
				+    "WHERE Employee_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, employee.getEmployeeProfile().employeeName.firstName);
			ps.setString(2, employee.getEmployeeProfile().employeeName.lastName);
			ps.setString(3, String.valueOf(employee.getEmployeeProfile().gender));
			ps.setDate(4, Date.valueOf(employee.getEmployeeProfile().employmentDate));
			ps.setString(5, employee.getEmployeeProfile().employeePosition);
			ps.setBigDecimal(6, employee.getEmployeeProfile().salary);
			ps.setInt(7, employee.getEmployeeID());
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
	}


	@Override
	public void save() 
	{
		context.clear();
		seedRepository();
	}

}
