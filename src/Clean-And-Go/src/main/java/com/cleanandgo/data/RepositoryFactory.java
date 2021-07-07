package com.cleanandgo.data;

import com.cleanandgo.common.Graphics;
import com.cleanandgo.common.Session;

/**
 * Factory to handle all repositories.
 * 
 * Threads are used to initialize the repo while displaying a progress notification.
 */
public class RepositoryFactory 
{
	private static CustomerRepository customerRepository;
	private static EmployeeRepository employeeRepository;
	private static AssetRepository assetRepository;
	private static ServiceRepository serviceRepository;
	private static SupplierRepository supplierRepository;

	static
	{
		
		customerRepository = null;
		employeeRepository = null;
		assetRepository = null;
		serviceRepository = null;
		supplierRepository = null;
	}

	/**
	 * @return the customerRepository
	 */
	public static CustomerRepository getCustomerRepository()
	{
		if (customerRepository == null)
		{
			Thread t2 = new Thread(() -> 
				{
					customerRepository = new CustomerRepository(Session.connect());
				}
			);
	
			t2.start();
			Graphics.showProgressBar("\n-> Please wait");
			
			try 
			{
				t2.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return customerRepository;
	}

	/**
	 * @return the employeeRepository
	 */
	public static EmployeeRepository getEmployeeRepository() 
	{
		if (employeeRepository == null)
		{
			Thread t2 = new Thread(() -> 
				{
					employeeRepository = new EmployeeRepository(Session.connect());
				}
			);
		
			t2.start();
			Graphics.showProgressBar("\n-> Please wait");
			
			try
			{
				t2.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return employeeRepository;
	}

	/**
	 * @return the assetRepository
	 */
	public static AssetRepository getAssetRepository()
	{
		
		if (assetRepository == null)
		{
			Thread t2 = new Thread(() -> 
				{
					assetRepository = new AssetRepository(Session.connect());
				}
			);
		
			t2.start();
			Graphics.showProgressBar("\n-> Please wait");
			
			try 
			{
				t2.join();
			} 
			catch (InterruptedException e)
			{	
				e.printStackTrace();
			}
		}
		
		return assetRepository;
	}

	/**
	 * @return the serviceRepository
	 */
	public static ServiceRepository getServiceRepository() 
	{
		
		if (serviceRepository == null)
		{
			Thread t2 = new Thread(() -> 
				{
					serviceRepository = new ServiceRepository(Session.connect());
				}
			);
			
			t2.start();
			Graphics.showProgressBar("\n-> Please wait");
			
			try 
			{
				t2.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return serviceRepository;
	}

	/**
	 * @return the supplierRepository
	 */
	public static SupplierRepository getSupplierRepository() 
	{
		
		if (supplierRepository == null)
		{
			Thread t2 = new Thread(() -> 
				{
					supplierRepository = new SupplierRepository(Session.connect());
				}
			);
			
			t2.start();
			Graphics.showProgressBar("\n-> Please wait");
			
			try 
			{
				t2.join();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		return supplierRepository;
	}
}
