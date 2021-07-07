package com.cleanandgo.data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cleanandgo.common.Session;
import com.cleanandgo.models.Address;
import com.cleanandgo.models.AddressType;
import com.cleanandgo.models.Contact;
import com.cleanandgo.models.ContactType;
import com.cleanandgo.models.Customer;
import com.cleanandgo.models.CustomerProfile;
import com.cleanandgo.models.CustomerTransaction;
import com.cleanandgo.models.Employee;
import com.cleanandgo.models.Name;
import com.cleanandgo.models.PaymentType;
import com.cleanandgo.models.Service;
import com.cleanandgo.models.ServiceType;
import com.cleanandgo.models.State;
import com.cleanandgo.models.TransactionDetail;

import java.sql.*;

/**
 * Contains functionality for customer objects.
 */
public class CustomerRepository extends Repository<Customer>
{
	public CustomerRepository(Connection conn)
	{
		super(conn);
		seedRepository();
	}

	@Override
	void seedRepository() 
	{
		String sql = "SELECT * "
				+    "FROM Customer ";
		
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					int currentCustomerID = rs.getInt("Customer_ID");

					Name currentCustomerName = new Name(rs.getString("First_Name"), rs.getString("Last_Name"));
					
					CustomerProfile currentCustomerProfile = new CustomerProfile
					(
							currentCustomerName, 
							rs.getString("Customer_Email")
					);
					
					List<CustomerTransaction> currentCustomerTransactions = getCustomerTransactions(currentCustomerID);
					
					List<Contact> currentCustomerContacts = getCustomerContacts(currentCustomerID);
				
					List<Address> currentCustomerAddress = getCustomerAddress(currentCustomerID);
					
					Customer currentCustomer = new Customer
					(
							currentCustomerID,
							currentCustomerProfile,
							currentCustomerTransactions,
							currentCustomerContacts,
							currentCustomerAddress
					);
					
					context.add(currentCustomer);
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	private List<Contact> getCustomerContacts(int customerID)
	{
		List<Contact> CustomerContact = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Customer_Contact "
				   + "JOIN Contact_Type "
				   + "	USING (Contact_Type_ID) "
				   + "WHERE Customer_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				
				while (rs.next())
				{
					ContactType currentContactType = new ContactType(rs.getInt("Contact_Type_ID"), rs.getString("Type_Name"));
				
					Contact currentContact = new Contact
					(
							currentContactType,
							rs.getString("Phone_Number")
					);
					
					CustomerContact.add(currentContact);
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
		return CustomerContact;
	}

	private List<Address> getCustomerAddress(int customerID)
	{
		List<Address> CustomerAddress = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Customer_Address "
				   + "JOIN State "
				   + "	USING (State_ID) "
				   + "JOIN Address_Type "
				   + "  USING (Address_Type_ID) "
				   + "WHERE Customer_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			
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
					
					CustomerAddress.add(currentAddress);
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
		return CustomerAddress;
	}
	
	private List<CustomerTransaction> getCustomerTransactions(int customerID) 
	{		
		List<CustomerTransaction> customerTransactions = new ArrayList<>();
		
		String sql = "SELECT *  "
				   + "FROM Customer_Transaction  "
				   + "JOIN Service  "
				   + "	USING (Service_ID) "
				   + "JOIN Service_Type "
				   + "	USING (Service_Type_ID) "
				   + "WHERE Customer_ID = ? ";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					PaymentType currentPaymentType = new PaymentType(rs.getInt("Payment_Type_ID"), rs.getString("Type_Name"));
					
					TransactionDetail currentCustomerTransactionDetail = new TransactionDetail
					(
							rs.getDate("Billing_Date").toLocalDate(), 
							rs.getDate("Shipping_Date").toLocalDate(),
							rs.getDate("Due_Date") == null ? null : rs.getDate("Due_Date").toLocalDate(),
							rs.getBigDecimal("Amount_Charged"), 
							rs.getBigDecimal("Current_Balance"), 
							currentPaymentType
					);
					
					ServiceType currentCustomerServiceType = new ServiceType
					(
							rs.getInt("Service_Type_ID"),
							rs.getString("Service_Type_ID"),
							rs.getString("Service_Description")
					);
					
					Service currentCustomerService = new Service
					(
							rs.getInt("Service_Type_ID"),
							currentCustomerServiceType,
							rs.getString("Type_Name"),
							rs.getString("Service_Description"),
							rs.getDouble("Rate"),
							rs.getTime("Duration") == null ? null : rs.getTime("Duration").toLocalTime().plusHours(8)
							
					);
					
					Employee currentEmployee = new Employee
					(
							rs.getInt("Employee_ID"),
							null,
							null,
							null,
							null,
							null
					);
					
					CustomerTransaction currentCustomerTransaction = new CustomerTransaction
					(
							rs.getInt("Customer_Transaction_ID"),
							rs.getInt("Customer_ID"),
							currentCustomerTransactionDetail,
							rs.getString("Review"),
							currentEmployee,
							currentCustomerService
					);
					
					customerTransactions.add(currentCustomerTransaction);
				}
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
		return customerTransactions;
	}

	@Override
	public void add(Customer customer)
	{
		String sql = "INSERT INTO Customer "
				+    "	(First_Name, Last_Name, Customer_Email) "
				+    "VALUES "
				+    "	(?, ?, ?) ";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, customer.getCustomerProfile().customerName.firstName);
			ps.setString(2, customer.getCustomerProfile().customerName.lastName);
			ps.setString(3, customer.getCustomerProfile().customerEmail);
		
			ps.executeUpdate();
			
			customer.setCustomerID(getNewCustomerID());
			addCustomerAddresses(customer);
			addCustomerTransactions(customer);	
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}
	
	private int getNewCustomerID() 
	{
		String sql = "SELECT Customer_ID "
				   + "FROM Customer "
				   + "ORDER BY Customer_ID DESC LIMIT 1 ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					return rs.getInt("Customer_ID");
				}
				
				return -1;
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	        return -1;
	    }
	}
	
	private void addCustomerAddresses(Customer customer) 
	{
		String sql = "INSERT INTO Customer_Address "
				   + "VALUES "
				   + "	(?, ?, ?, ?, ?, ?, ?)";
		
		try 
		{
			for(int i = 0; i < customer.getCustomerAddresses().size(); i++)
			{
				Address currentAddress = customer.getCustomerAddresses().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, customer.getCustomerID());
				ps.setString(2, currentAddress.Line1);
				ps.setString(3, currentAddress.Line2);
				ps.setString(4, currentAddress.City);
				ps.setInt(5, currentAddress.State.StateID);
				ps.setString(6, currentAddress.ZipCode);
				ps.setInt(7, currentAddress.AddressType.AddressTypeID);
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }	
	}
	
	private void addCustomerTransactions(Customer customer)
	{
		String sql = "INSERT INTO Customer_Transaction " 
				   + "   (Customer_ID, Billing_Date, Shipping_Date , Due_Date, Amount_Charged, Review, Service_ID, Employee_ID, Current_Balance, Payment_Type_ID) "
				   + "VALUES "
				   + "	 (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try 
		{
			for(int i = 0; i < customer.getCustomerTransactions().size(); i++)
			{
				CustomerTransaction currentCustomerTransactions = customer.getCustomerTransactions().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, customer.getCustomerID());
				ps.setDate(2, Date.valueOf(currentCustomerTransactions.TransactionDetail.BillingDate));
				ps.setDate(3, Date.valueOf(currentCustomerTransactions.TransactionDetail.ShippingDate));
				ps.setDate(4, Date.valueOf(currentCustomerTransactions.TransactionDetail.DueDate));
				ps.setBigDecimal(5, currentCustomerTransactions.TransactionDetail.AmountCharged);
				ps.setString(6, currentCustomerTransactions.Review);
				ps.setInt(7, currentCustomerTransactions.Service.ServiceID);
				ps.setInt(8, currentCustomerTransactions.Employee.getEmployeeID());
				ps.setBigDecimal(9, currentCustomerTransactions.TransactionDetail.CurrentBalance);
				ps.setInt(10, currentCustomerTransactions.TransactionDetail.PaymentType.PaymentTypeID);
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }	
	}
	
	@Override
	public void addAll(List<Customer> customers)
	{
		for (Customer customer : customers)
		{
			add(customer);
		}
	}
	
	@Override
	public Customer getByID(int customerID)
	{
		return context.stream()
				.filter(e -> e.getCustomerID() == customerID)
				.collect(Collectors.toList())
				.get(0);
	}

	@Override
	public List<Customer> getAll()
	{
		return context;
	}
	
	@Override
	public void delete(Customer customer)
	{
		String sql = "DELETE FROM Customer "
				   + "WHERE Customer_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customer.getCustomerID());
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public void deleteAll(List<Customer> customers)
	{
		for (Customer customer : customers)
		{
			delete(customer);
		}
	}

	@Override
	public void update(Customer customer)
	{
		String sql = "UPDATE Customer "
				+    "SET "
				+    "	First_Name = ?, "
				+    "	Last_Name = ?, "
				+    "	Email = ?, "
				+    "WHERE Customer_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, customer.getCustomerProfile().customerName.firstName);
			ps.setString(2, customer.getCustomerProfile().customerName.lastName);
			ps.setString(3, customer.getCustomerProfile().customerEmail);
			
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
	}

	@Override
	 public void save()
	{
		context.clear();
		seedRepository();
	}
	
}
