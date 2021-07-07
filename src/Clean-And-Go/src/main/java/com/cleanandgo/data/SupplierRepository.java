package com.cleanandgo.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cleanandgo.common.Session;
import com.cleanandgo.models.Address;
import com.cleanandgo.models.AddressType;
import com.cleanandgo.models.Contact;
import com.cleanandgo.models.ContactType;
import com.cleanandgo.models.Product;
import com.cleanandgo.models.SupplierTransaction;
import com.cleanandgo.models.State;
import com.cleanandgo.models.Supplier;
import com.cleanandgo.models.TransactionDetail;
import com.cleanandgo.models.SupplierProduct;
import com.cleanandgo.models.Product;

/**
 * Contains functionality for supplier objects.
 */
public class SupplierRepository extends Repository<Supplier> 
{

	public SupplierRepository(Connection conn)
	{
		super(conn);
		seedRepository();
	}


	@Override
	void seedRepository() 
	{
		String sql = "SELECT * "
				+    "FROM Supplier ";
		
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					int currentSupplierID = rs.getInt("Supplier_ID");
	
					String currentSupplierName = rs.getString("Supplier_Name");

					List<SupplierTransaction> currentSupplierTransactions = getSupplierTransactions(currentSupplierID);
					
					List<Contact> currentSupplierContact = getSupplierContacts(currentSupplierID);
				
					List<Address> currentSupplierAddress = getSupplierAddress(currentSupplierID);
					
					List<Product> currentSupplierProduct = getSupplierProduct(currentSupplierID);
					
					Supplier currentSupplier = new Supplier
					(
							currentSupplierID,
							currentSupplierName,
							currentSupplierTransactions,
							currentSupplierAddress,
							currentSupplierContact,
							currentSupplierProduct
					);
					
					context.add(currentSupplier);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}

	private List<Product> getSupplierProduct(int SupplierID)
	{
		List<Product> SupplierProduct = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Supplier_Product "
				   + "JOIN Supplier "
				   + "USING (Supplier_ID) "
				   + "JOIN Product "
				   + "USING (Product_ID) "
				   + "WHERE Supplier_ID = ?";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, SupplierID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				
				while (rs.next())
				{	
					Product currentProduct = new Product
					(
						rs.getInt("Product_ID"),
						rs.getString("Product_Name"),
						rs.getBigDecimal("List_Price")
					);
					
					SupplierProduct.add(currentProduct);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return SupplierProduct;
	}


	private List<Address> getSupplierAddress(int SupplierID)
	{
		List<Address> SupplierAddress = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Supplier_Address "
				   + "JOIN State "
				   + "	USING (State_ID) "
				   + "JOIN Address_Type "
				   + "  USING (Address_Type_ID) "
				   + "WHERE Supplier_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, SupplierID);
			
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
					
					SupplierAddress.add(currentAddress);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return SupplierAddress;
	}


	private List<Contact> getSupplierContacts(int SupplierID) 
	{
		List<Contact> SupplierContact = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Supplier_Contact "
				   + "JOIN Contact_Type "
				   + "	USING (Contact_Type_ID) "
				   + "WHERE Supplier_ID = ? ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, SupplierID);
			
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
					
					SupplierContact.add(currentContact);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return SupplierContact;
	}


	private List<SupplierTransaction> getSupplierTransactions(int SupplierID) 
	{
		List<SupplierTransaction> SupplierTransactions = new ArrayList<>();
		
		String sql = "SELECT *  "
				   + "FROM Supplier_Transaction  "
				   + "WHERE Supplier_ID = ? ";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, SupplierID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					TransactionDetail currentSupplierTransactionDetail = new TransactionDetail
					(
							rs.getDate("Billing_Date").toLocalDate(), 
							rs.getDate("Shipping_Date").toLocalDate(), 
							rs.getDate("Due_Date") == null ? null : rs.getDate("Due_Date").toLocalDate(),
							rs.getBigDecimal("Amount_Charged"), 
							rs.getBigDecimal("Current_Balance"), 
							null
					);
					
					SupplierTransaction currentSupplierTransaction = new SupplierTransaction
					(
							rs.getInt("Supplier_Transaction_ID"),
							rs.getInt("Supplier_ID"),
							currentSupplierTransactionDetail,
							rs.getString("Transaction_Description"),
							rs.getInt("Order_Quantity"),
							rs.getBigDecimal("Unit_Price")
					);
					
					SupplierTransactions.add(currentSupplierTransaction);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
		return SupplierTransactions;
	}


	@Override
	public void add(Supplier supplier) 
	{
		String sql = "INSERT INTO Employee "
				+    "	(Supplier_ID, Supplier_Name) "
				+    "VALUES "
				+    "	(?, ?) ";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, supplier.getSupplierID());
			ps.setString(2, supplier.getSupplierName());
		
			ps.executeQuery();
			
			addsupplierAddresses(supplier);
			addsupplierTransactions(supplier);
			addsupplierContacts(supplier);
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}
	private void addsupplierTransactions(Supplier supplier)
	{
		String sql = "INSERT INTO supplier_Transaction "
				   + "VALUES "
				   + "	(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try 
		{
			for (int i = 0; i < supplier.getSupplierTransactions().size(); i++)
			{
				SupplierTransaction currentsupplierTransactions = supplier.getSupplierTransactions().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, currentsupplierTransactions.SupplierTransactionID);
				ps.setInt(2, supplier.getSupplierID());
				ps.setDate(3, Date.valueOf(currentsupplierTransactions.SupplierTransactionDetail.BillingDate));
				ps.setDate(4, Date.valueOf(currentsupplierTransactions.SupplierTransactionDetail.ShippingDate));
				ps.setDate(5, Date.valueOf(currentsupplierTransactions.SupplierTransactionDetail.DueDate));
				ps.setString(6, currentsupplierTransactions.TransactionDescription);
				ps.setInt(7, currentsupplierTransactions.Order);
				ps.setBigDecimal(8, currentsupplierTransactions.SupplierTransactionDetail.AmountCharged);
				ps.setBigDecimal(9, currentsupplierTransactions.UnitPrice);
				ps.setBigDecimal(10, currentsupplierTransactions.SupplierTransactionDetail.CurrentBalance);
				
				ps.executeQuery();
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }	
		
	}

	private void addsupplierContacts(Supplier supplier)
	{
		String sql = "INSERT INTO Supplier_Contact "
				   + "VALUES "
				   + "	(?, ?, ?) ";
		
		try 
		{
			for(int i = 0; i < supplier.getSupplierContacts().size(); i++)
			{
				Contact currentContact = supplier.getSupplierContacts().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, supplier.getSupplierID());
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
	
	private void addsupplierAddresses(Supplier supplier) 
	{
		String sql = "INSERT INTO Supplier_Address "
				   + "VALUES "
				   + "	(?, ?, ?, ?, ?, ?, ?)";
		
		try 
		{
			for (int i = 0; i < supplier.getSupplierAddresses().size(); i++)
			{
				Address currentAddress = supplier.getSupplierAddresses().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
				
				// Set arguments
				ps.setInt(1, supplier.getSupplierID());
				ps.setInt(2, currentAddress.AddressType.AddressTypeID);
				ps.setString(3, currentAddress.Line1);
				ps.setString(4, currentAddress.Line2);
				ps.setString(5, currentAddress.City);
				ps.setInt(6, currentAddress.State.StateID);
				ps.setString(7, currentAddress.ZipCode);
				
				ps.executeQuery();
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }	
	}
	
	
	// not implement for now
//	private void addsupplierProducts(Supplier supplier)
//	{
//		String sql = "INSERT INTO Supplier_Product "
//				   + "VALUES "
//				   + "	(?, ?) ";
//		
//		try 
//		{
//			for(int i = 0; i < supplier.getProducts().size(); i++)
//			{
//				Product currentProduct = supplier.getProducts().get(i);
//				PreparedStatement ps = conn.prepareStatement(sql);
//				
//				// Set arguments
//				ps.setInt(1, supplier.getSupplierID());
//				ps.setInt(2, supplier.ProductID);
//				
//				ps.executeUpdate();
//			}
//		}
//		catch (SQLException e) 
//		{
//	        System.out.println("\nOperation failed. Please contact administrator.\n");;
//	    }
//	}

	@Override
	public void addAll(List<Supplier> suppliers)
	{
		for (Supplier supplier : suppliers)
		{
			add(supplier);
		}
	}

	@Override
	public void delete(Supplier supplier) 
	{
		String sql = "DELETE FROM Supplier "
				   + "WHERE Supplier_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, supplier.getSupplierID());
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
	}

	@Override
	public void deleteAll(List<Supplier> suppliers) 
	{
		for (Supplier supplier : suppliers)
		{
			delete(supplier);
		}
	}

	@Override
	public void update(Supplier supplier) 
	{
		String sql = "UPDATE Supplier "
				+    "SET "
				+    "	Supplier_Name = ?, "
				+    "WHERE Supplier_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setString(1, supplier.getSupplierName());
			
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");;
	    }
		
	}

	@Override
	public Supplier getByID(int supplierID) 
	{
		return context.stream()
				.filter(e -> e.getSupplierID() == supplierID)
				.collect(Collectors.toList())
				.get(0);
	}

	@Override
	public List<Supplier> getAll()
	{
		return context;
	}

	@Override
	public void save() 
	{
		context.clear();
		seedRepository();
	}
}
