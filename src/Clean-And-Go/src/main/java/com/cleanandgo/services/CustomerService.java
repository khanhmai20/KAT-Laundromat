package com.cleanandgo.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import com.cleanandgo.common.*;
import com.cleanandgo.models.*;
import com.cleanandgo.data.*;

/**
 * Contains functionality for managing customers and services.
 */
public class CustomerService extends Service<Customer>
{
	private ServiceRepository serviceRepository;
	
	public CustomerService(Connection conn) 
	{
		super(conn);
		this.repository = RepositoryFactory.getCustomerRepository();
		this.serviceRepository = RepositoryFactory.getServiceRepository();
		
	}

	@Override
	public void launch() 
	{
		boolean continueCustomerService = true;
		
		while (continueCustomerService)
		{
			System.out.println("\n" + Graphics.CustomerServicesMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case 0: // Go back to main menu.
					{
						return;
					}
					case 1: // Add customer
					{
						createAll();
						break;
					}
					case 2: // Add service
					{
						createAllServices();
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
	

	@Override
	void create() 
	{
		CustomerProfile customerProfile = getCustomerProfile();
		List<CustomerTransaction> customerTransactions = getCustomerTransaction();
		List<Address> customerAddresses = getCustomerAddress();
		List<Contact> customerContacts = getCustomerContacts();
		Customer customer = new Customer(customerProfile,customerTransactions,customerContacts,customerAddresses);
		
		this.repository.add(customer);
		this.repository.save();
		
	}

	private CustomerProfile getCustomerProfile() {
		
		System.out.println("\nProfile");
		System.out.println("--------------------------------------------------------");
		
		String firstName = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's first name: ");
		String lastName = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's last name: ");
		Name customerName = new Name(firstName, lastName);
		
		String email = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's email: ");
		CustomerProfile profile = new CustomerProfile(customerName,email);
		return profile;
	}
	
	private List<CustomerTransaction> getCustomerTransaction() {
		List<CustomerTransaction> currentCustomerTransaction = new ArrayList<>();
		
		System.out.println("\nCustomer Transaction");
		System.out.println("--------------------------------------------------------");
		
		while (true)
		{
			
			LocalDate billingDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the billing date (YYYY-MM-DD): "));
			LocalDate shippingDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the shipping date (YYYY-MM-DD): "));
			LocalDate dueDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the due date (YYYY-MM-DD): "));
			BigDecimal amountCharged = new BigDecimal(InputHandler.promptUser(InputHandler.isNumber, "-> Please enter the amount charged: "));
			BigDecimal CurrentBalance = new BigDecimal(InputHandler.promptUser(InputHandler.isNumber, "-> Please enter the current balance: "));
			int paymentTypeId = Integer.parseInt(InputHandler.promptUser(InputHandler.isNumber, "-> Please enter the payment type Id: "));
			PaymentType paymentType = new PaymentType(paymentTypeId); 
			
			TransactionDetail transactionDetail = new TransactionDetail(billingDate,shippingDate,dueDate,amountCharged,CurrentBalance,paymentType);
			
			String review = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter your comment: ");
			
			int employeeId = Integer.parseInt(InputHandler.promptUser(InputHandler.isNumber, "-> Please enter the employee's Id: "));
			Employee employee = new Employee(employeeId,null,null,null,null,null);
			
			int serviceID =  Integer.parseInt(InputHandler.promptUser(InputHandler.isNumber, "-> Please enter the service Id: "));
			
			ServiceType serviceType = new ServiceType(1); 
			
			com.cleanandgo.models.Service service = new com.cleanandgo.models.Service(serviceID,serviceType,null,null,0,null);
			
			CustomerTransaction customerTransaction = new CustomerTransaction(0,transactionDetail,review,employee,service);
			
			currentCustomerTransaction.add(customerTransaction);
			
			boolean addMoreTransaction = InputHandler.promptUser("\n-> Do you want to add more transaction? (Y/N) ")
					.toUpperCase()
					.contentEquals("Y");
			
			if (!addMoreTransaction)
			{
				break;
			}
		}
		
		return currentCustomerTransaction;
	}
	
	private List<Address> getCustomerAddress() {
		List<Address> addresses =  new ArrayList<>();
		
		System.out.println("\nAddresses");
		System.out.println("--------------------------------------------------------");
		for (int i = 1; i <= AddressType.addressTypes.size(); i++)
		{
			
			AddressType currentAddressType = new AddressType(i);
			
			String line1 = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's %s address: ", currentAddressType.TypeName);
			String line2 = InputHandler.promptUser("-> Please enter any additional info about the customer's %s address: ", currentAddressType.TypeName);
			String city =  InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's city: ");
			String state = InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the customer's state: ");
			String zipcode = InputHandler.promptUser(InputHandler.isValidZipcode, "-> Please enter the customer's zipcode: ");
			
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
	
	private List<Contact> getCustomerContacts() {
		System.out.println("\nContacts");
		System.out.println("--------------------------------------------------------");
		
		List<Contact> contacts = new ArrayList<>();
		
		for (int i = 1; i <= ContactType.contactTypes.size(); i++)
		{
			ContactType currentContactType = new ContactType(i);
			
			String phoneNumber = InputHandler.promptUser(InputHandler.isValidPhoneNumber, "-> Please enter the customer's %s number (with dashes): ", currentContactType.TypeName);
			
			Contact currentContact = new Contact(currentContactType, phoneNumber);
			
			contacts.add(currentContact);
			
			if (i != ContactType.contactTypes.size())
			{
				boolean addMoreContacts = InputHandler.promptUser("\n-> Do you want to add more contact methods? (Y/N) ")
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
			
			continueAdding = InputHandler.promptUser("\n-> Do you want to add another customer? (Y/N) ")
								.toUpperCase()
								.contentEquals("Y");

		}
		
	}
	
	void createService()
	{
		ServiceType serviceType = getServiceType();
		
		String serviceName = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the name of the service: ");
		String serviceDescription = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the description of the service: ");
		LocalTime duration = LocalTime.parse(InputHandler.promptUser(InputHandler.isValidLocalTime, "-> Please enter the duration in 24 hour format (HH:MM): "));
		double rate = Double.valueOf((InputHandler.promptUser(InputHandler.isNotBlank, "-> Please enter the service's rate: ")));
		
		com.cleanandgo.models.Service newService = new com.cleanandgo.models.Service(
				serviceType,
				serviceName,
				serviceDescription,
				rate,
				duration	
		);
		
		this.serviceRepository.add(newService);
		this.serviceRepository.save();	
	}
	
	private ServiceType getServiceType() {
		int serviceTypeID = Integer.valueOf(InputHandler.promptUser(InputHandler.isValidServiceType, "Please enter (1) Washer, (2) Dryer, (3) Drop Off, (4) Dry Cleaning "));
		return new ServiceType(serviceTypeID);
	}

	private void createAllServices() 
	{
		boolean continueAdding = true;
		
		while(continueAdding)
		{
			try 
			{
				createService();
			} 
			catch (IllegalArgumentException ex) 
			{
				System.out.println("Service was not add.");
//				continue;
			}
			catch (IndexOutOfBoundsException ex)
			{
				System.out.println("Service was not add.");
//				continue;
			}
			
			continueAdding = InputHandler.promptUser("\n-> Do you want to add another service? (Y/N) ")
								.toUpperCase()
								.contentEquals("Y");
		}
		
	}

	@Override
	void get(int id) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	void getAll()
	{
		
	}

	@Override
	void update() 
	{
		
	}

	@Override
	void remove() 
	{
	}

	@Override
	void removeAll()
	{
		
	}

	@Override
	<U> List<Customer> filter(Predicate<U> predicate) 
	{
		return null;
	}

}
