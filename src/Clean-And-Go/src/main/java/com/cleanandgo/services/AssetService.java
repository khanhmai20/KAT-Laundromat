package com.cleanandgo.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.text.NumberFormat;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.cleanandgo.common.*;
import com.cleanandgo.data.*;
import com.cleanandgo.models.*;

/**
 * Contains functionality for managing assets.
 */
public class AssetService extends Service<Asset>
{	
	final int VIEW_EXPENSES_SELECTED = 1;
	final int VIEW_CLEANING_EXPENSES_SELECTED = 1;
	final int VIEW_ANNUAL_EXPENSES_SELECTED = 2;
	
	final int VIEW_SUPPLIES_SELECTED = 2;
	final int VIEW_SUPPLIER_PRODUCTS_SELECTED = 1;
	final int VIEW_SAFETY_STOCK_SELECTED = 2;
	
	final int VIEW_EQUIPMENT_SELECTED = 3;
	final int VIEW_INVENTORY_SELECTED = 1;
	final int VIEW_MONTHLY_USAGE_SELECTED = 3;
	final int VIEW_MAINTENANCE_SELECTED = 2;
	
	final int ADD_ASSET_SELECTED = 4;
	
	final int RETURN_SELECTED = 0;
	
	private SupplierRepository supplierRepository;
	
	public AssetService(Connection conn) 
	{
		super(conn);
		this.supplierRepository = RepositoryFactory.getSupplierRepository();
		this.repository = RepositoryFactory.getAssetRepository();
	}

	@Override
	public void launch() 
	{
		boolean continueAssetService = true;
		
		while (continueAssetService)
		{
			System.out.println("\n" + Graphics.AssetMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case RETURN_SELECTED: 
					{
						return;
					}
					case VIEW_EXPENSES_SELECTED: 
					{
						viewExpensesMenu();
						break;
					}
					case VIEW_SUPPLIES_SELECTED: 
					{
						
						viewSuppliesMenu();
						break;
					}
					case VIEW_EQUIPMENT_SELECTED:
					{
						viewEquipmentMenu();
						break;
					}
					case ADD_ASSET_SELECTED:
					{
						createAll();
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
	
	private void viewExpensesMenu() 
	{
		boolean continueViewingExpenses = true;
		
		while (continueViewingExpenses)
		{
			System.out.println("\n" + Graphics.AssetExpensesMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case RETURN_SELECTED: // Go back to main menu.
					{
						return;
					}
					case VIEW_CLEANING_EXPENSES_SELECTED: 
					{
						viewCleaningSupplyExpenses();
						break;
					}
					case VIEW_ANNUAL_EXPENSES_SELECTED: // Search for employee.
					{
						viewAnnualExpensesReport();
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
	
	private void viewEquipmentMenu()
	{
		boolean continueViewingEquipment = true;
		
		while (continueViewingEquipment)
		{
			System.out.println("\n" + Graphics.AssetEquipmentMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case RETURN_SELECTED: // Go back to main menu.
					{
						return;
					}
					case VIEW_INVENTORY_SELECTED: // View inventory.
					{
						viewAssetInventory();
						break;
					}
					case VIEW_MAINTENANCE_SELECTED: // View asset maintenance schedule.
					{
						viewAssetMaintenanceSchedule();
						break;
					}
					case VIEW_MONTHLY_USAGE_SELECTED: // View monthly asset usage.
					{
						viewMonthlyAssetUsage();
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

	private void viewMonthlyAssetUsage() 
	{
		List<Asset> assets = this.repository.getAll()
								.stream()
								.filter(asset -> asset.getAssetType().AssetTypeID == 1)
								.collect(Collectors.toList());
		
		System.out.println("\nMonthly Equipment Usage");
		System.out.println("--------------------------------------------------------");
		
		for (int i = 0; i < assets.size(); i++) 
		{ 
			String assetName = assets.get(i).getAssetProfile().AssetName;
			List<AssetManagement> assetManagement = assets.get(i).getAssetManagements();
			double monthlyUsage = assetManagement.size() * 1.0 / 12;
			
			System.out.printf
			(
					"%s %.2f uses per month%n",
					assetName, 
					monthlyUsage
			);
		}
			
	}

	private void viewAssetMaintenanceSchedule() 
	{
		DayOfWeek firstDayOfWeek = WeekFields.of(Locale.US).getFirstDayOfWeek();
		DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
		
		LocalDate first = LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
		LocalDate last = LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
		
		List<Asset> assets = this.repository.getAll().stream()
								.filter(asset -> asset.getAssetType().AssetTypeID == 1)
								.collect(Collectors.toList());
		
		System.out.println("\nWeekly Maintenance Schedule(s)");
		System.out.println("--------------------------------------------------------");
		
		
		for (int i = 0; i < assets.size(); i++) { 
			String assetName = assets.get(i).getAssetProfile().AssetName;
			
			List<AssetMaintenanceSchedule> weeklyMaintenanceSchedules = assets.get(i)
																			.getAssetMaintenanceSchedules()
																			.stream()
																			.filter(schedule -> schedule.NextMaintenance.isBefore(last) && schedule.NextMaintenance.isAfter(first))
																			.collect(Collectors.toList());
			System.out.println(assetName);
			
			if (weeklyMaintenanceSchedules.isEmpty()) 
			{ 
				System.out.println("\tNo Schedule");
			}
			else 
			{ 
				weeklyMaintenanceSchedules.forEach(schedule -> System.out.println("\t" + schedule.NextMaintenance));
			}
			
			System.out.println();
		}
	}

	private void viewAssetInventory() 
	{
		System.out.println("\nTotal Inventory");
		System.out.println("--------------------------------------------------------");
		
		List<Asset> equipment = this.repository.getAll().stream()
								.filter(a -> (a.getAssetType().AssetTypeID == 1))
								.collect(Collectors.toList());
		
		List<Asset> supplies = this.repository.getAll().stream()
								.filter(a -> (a.getAssetType().AssetTypeID == 2))
								.collect(Collectors.toList());
		
		int equipmentCount = equipment.stream()
				.map(asset -> asset.getAssetProfile().CurrentInventory)
				.reduce(0,Integer::sum);
		
		int suppliesCount = supplies.stream()
				.map(asset -> asset.getAssetProfile().CurrentInventory)
				.reduce(0,Integer::sum);
		
		System.out.println("Total Equipment: " + equipmentCount);
		System.out.println("Total Supplies: " + suppliesCount);
		
	}

	private void viewAnnualExpensesReport() 
	{
		int currentYear = LocalDate.now().getYear();
		
		System.out.println("\nAnnual Expenses Report");
		System.out.println("--------------------------------------------------------");
		BigDecimal employeeSalaries = RepositoryFactory.getEmployeeRepository().getAll()
					.stream()
					.map(employee -> employee.getEmployeeProfile().salary)
					.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
		
		BigDecimal equipmentPrices = this.repository.getAll()
					.stream()
					.filter(asset -> asset.getAssetType().AssetTypeID == 1)
					.filter(asset -> asset.getAssetProfile().PurchaseDate.getYear() == currentYear)
					.map(asset -> asset.getAssetProfile().PurchasePrice)
					.reduce(BigDecimal.ZERO, (b1, b2) -> b1.add(b2));
		
		BigDecimal estimatedMaintenaceCost = equipmentPrices.multiply(new BigDecimal("2"));
		
		
		System.out.printf("%n%nSalaries: %s%n", NumberFormat.getCurrencyInstance().format(employeeSalaries));
		System.out.printf("Equipment: %s%n", NumberFormat.getCurrencyInstance().format(equipmentPrices));
		System.out.printf("Estimated Maintenance Cost: %s%n", NumberFormat.getCurrencyInstance().format(estimatedMaintenaceCost));
	}

	private void viewCleaningSupplyExpenses() 
	{
		
		List<Asset> assets = this.repository.getAll()
				.stream()
				.filter (asset -> asset.getAssetType().AssetTypeID == 2)
				.filter(asset -> asset.getAssetProfile().PurchaseDate.getYear() == LocalDate.now().getYear())
				.collect(Collectors.toList());
		
		BigDecimal totalCleaningSupplyExpenses = assets.stream()
				.map(a -> a.getAssetProfile().PurchasePrice)
				.reduce(BigDecimal.ZERO,(b1, b2) -> b1.add(b2));
		
		System.out.println("\nCleaning Supply Expenses");
		System.out.println("--------------------------------------------------------");
		System.out.printf
		(		"Total Expenses for %d: %s%n", 
				LocalDate.now().getYear(),
				NumberFormat.getCurrencyInstance().format(totalCleaningSupplyExpenses)
		);
		
	}

	private void viewSuppliesMenu() 
	{
		boolean continueViewingSupplies = true;
		
		while (continueViewingSupplies)
		{
			System.out.println("\n" + Graphics.AssetSuppliesMenu);
			int userInput = Integer.valueOf(InputHandler.promptUser("-> Please pick an option: "));
			
			try 
			{
				switch (userInput) 
				{
					case RETURN_SELECTED: // Go back to main menu.
					{
						return;
					}
					case VIEW_SUPPLIER_PRODUCTS_SELECTED: // View supplier products.
					{
						viewSupplierProducts();
						break;
					}
					case VIEW_SAFETY_STOCK_SELECTED: // View safety stock.
					{
						viewSafetyStock();
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

	private void viewSafetyStock() 
	{
		int safetyStock = 20;
		List<Asset> assets = this.repository.getAll()
			.stream()
			.filter(asset -> asset.getAssetProfile().CurrentInventory <= safetyStock)
			.collect(Collectors.toList());
		
		 System.out.println("\nInventory Under Safety");
		 System.out.println("--------------------------------------------------------");
		 
		 assets.forEach(asset -> 
		 	System.out.printf(
		 			"%s: %d%n",
		 			asset.getAssetProfile().AssetName,
		 			asset.getAssetProfile().CurrentInventory
 			)
		);
		

	}

	private void viewSupplierProducts() 
	{	
		 System.out.println("\nSuppliers");
		 System.out.println("--------------------------------------------------------");
		 
		 this.supplierRepository.getAll()
		 	.forEach(supplier -> System.out.println(supplier.getSupplierName()));
		 
		String supplierName = InputHandler.promptUser(InputHandler.isNotBlank, "\n-> Please enter the name of the supplier: "); 
		List<Supplier> suppliers = this.supplierRepository.getAll()
								.stream()
								.filter(supplier -> supplier.getSupplierName().contentEquals(supplierName))
								.collect(Collectors.toList());
				
		 System.out.printf("%n%s Products%n", supplierName);
		 System.out.println("--------------------------------------------------------");
		 
		if(suppliers.isEmpty())
		{
			System.out.println("No products found for suppier");
		}
		else
		{
			suppliers.get(0)
				.getProducts() 
				.forEach(product -> 
					System.out.printf
						(
							"%s: %s%n",
							product.ProductName,
							NumberFormat.getCurrencyInstance().format(product.ListPrice) 
						)
					);
		}
	}

	@Override
	void create() 
	{
		 AssetType assetType = getAssetType();
		 AssetProfile assetProfile = getAssetProfile();
		 List<AssetManagement> assetManagements = getAssetManagements();
		 List<AssetMaintenanceSchedule> assetMaintenanceSchedules = getAssetMaintenanceSchedules();
		 
		 Asset asset = new Asset(assetType, assetProfile, assetManagements, assetMaintenanceSchedules);
		 
		 this.repository.add(asset);
		 this.repository.save();
	}

	private AssetProfile getAssetProfile() 
	{	
		 System.out.println("\nAsset Information");
		 System.out.println("--------------------------------------------------------");
		 
		 String assetName = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the name of the asset: ");
		 String assetBrand = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the brand of the asset: ");
		 String assetDescription = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the description for the asset: ");
		 String safetyInformation = InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the safety information for the asset: ");
		 int currentInventory = Integer.valueOf(InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the inventory amount for the asset: "));
		 LocalDate purchaseDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "Please enter the purchase date for the asset (YYYY-MM-DD): "));
		 BigDecimal purchasePrice = new BigDecimal(InputHandler.promptUser(InputHandler.isNotBlank, "Please enter the purchase price of the asset: "));
		 
		 
		return new AssetProfile(assetName, assetBrand, assetDescription, safetyInformation, currentInventory, purchaseDate, purchasePrice);
	}

	private List<AssetManagement> getAssetManagements() 
	{
		List<AssetManagement> assetManagements = new ArrayList<>();
		
		boolean addMoreManagements = InputHandler.promptUser("\n-> Do you want to add management info for this asset? (Y/N) ")
				.toUpperCase()
				.contentEquals("Y");
		
		if (!addMoreManagements)
		{
			return assetManagements;
		}
		
		System.out.println("\nAsset Management Information");
		System.out.println("--------------------------------------------------------");
		
		while (true)
		{
			int employeeID = Integer.valueOf(InputHandler.promptUser(InputHandler.isValidEmployeeID, "-> Please enter the employee's ID: "));
			LocalDate useDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the use date (YYYY-MM-DD): "));
			
			Employee currentEmployee = new Employee(employeeID, null, null, null, null, null);
			Asset currentAsset = new Asset(0, null, null, null, null);
			
			AssetManagement currentManagement = new AssetManagement(currentEmployee, currentAsset, useDate);
			assetManagements.add(currentManagement);
			
			addMoreManagements = InputHandler.promptUser("\n-> Do you want to add more management info? (Y/N) ")
					.toUpperCase()
					.contentEquals("Y");
			
			if (!addMoreManagements)
			{
				break;
			}
		}
		
		return assetManagements;
	}
	
	private List<AssetMaintenanceSchedule> getAssetMaintenanceSchedules() 
	{
		List<AssetMaintenanceSchedule> assetMaintenanceSchedules = new ArrayList<>();
		
		boolean addMoreSchedules = InputHandler.promptUser("\n-> Do you want to add maintenance info for this asset? (Y/N) ")
				.toUpperCase()
				.contentEquals("Y");
		
		if (!addMoreSchedules)
		{
			return assetMaintenanceSchedules;
		}
		
		System.out.println("\nAsset Maintenance Information");
		System.out.println("--------------------------------------------------------");
		
		while (true)
		{
			LocalDate nextMaintenanceDate = LocalDate.parse(InputHandler.promptUser(InputHandler.isValidLocalDate, "-> Please enter the next maintenance date (YYYY-MM-DD): "));
			
			Asset currentAsset = new Asset(0, null, null, null, null);
			
			AssetMaintenanceSchedule currentSchedule = new AssetMaintenanceSchedule(currentAsset, nextMaintenanceDate);
			assetMaintenanceSchedules.add(currentSchedule);
			
			addMoreSchedules = InputHandler.promptUser("\n-> Do you want to add more maintenance info? (Y/N) ")
					.toUpperCase()
					.contentEquals("Y");
			
			if (!addMoreSchedules)
			{
				break;
			}
		}
		
		return assetMaintenanceSchedules;
	}

	private AssetType getAssetType() 
	{
		int assetTypeID = Integer.valueOf(InputHandler.promptUser(InputHandler.isValidAssetType, "Do you want to add (1) equipment or (2) cleaning supplies? "));
		return new AssetType(assetTypeID);
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
				System.out.println("Failed to create asset. ");
//				continue;
			}
			
			continueAdding = InputHandler.promptUser("\n-> Do you want to add another asset? (Y/N) ")
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
		// TODO Auto-generated method stub
		
	}


	@Override
	void update() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	void remove()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	void removeAll()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	<U> List<Asset> filter(Predicate<U> predicate) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
