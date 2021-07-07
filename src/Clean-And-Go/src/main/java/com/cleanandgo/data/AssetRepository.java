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
import com.cleanandgo.models.Asset;
import com.cleanandgo.models.AssetMaintenanceSchedule;
import com.cleanandgo.models.AssetManagement;
import com.cleanandgo.models.AssetProfile;
import com.cleanandgo.models.AssetType;
import com.cleanandgo.models.Employee;

/**
 * Contains functionality for asset objects.
 */
public class AssetRepository extends Repository<Asset>
{
	public AssetRepository(Connection conn)
	{
		super(conn);
		seedRepository();
	}

	@Override
	void seedRepository()
	{
		String sql = "SELECT * "
				+    "FROM Asset "
				+    "JOIN Asset_Type "
				+	"	USING (Asset_Type_ID)";
		
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					int currentAssetID = rs.getInt("Asset_ID");
					
					AssetType currentAssetType = new AssetType
					(
								rs.getInt("Asset_Type_ID"),
								rs.getString("Type_Name"),
								rs.getString("Type_Description")
					);
					
					AssetProfile currentAssetProfile = new AssetProfile
					(
							rs.getString("Asset_Name"),
							rs.getString("Asset_Brand"),
							rs.getString("Asset_Description"),
							rs.getString("Safety_Information"),
							rs.getInt("Current_Inventory"),
							rs.getDate("Purchase_Date").toLocalDate(),
							rs.getBigDecimal("Purchase_Price")
					);
					
					List<AssetManagement> currentAssetManagements = getAssetManagements(currentAssetID);
					
					List<AssetMaintenanceSchedule> currentAssetMaintenanceSchedules = getAssetMaintenanceSchedules(currentAssetID);
					
					Asset currentAsset = new Asset
					(
							currentAssetID,
							currentAssetType,
							currentAssetProfile,
							currentAssetManagements,
							currentAssetMaintenanceSchedules
					);
					
					context.add(currentAsset);
				}
				
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	private List<AssetManagement> getAssetManagements(int currentAssetID)
	{
		List<AssetManagement> assetManagements = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Asset_Management "
				   + "JOIN Asset "
				   + "USING (Asset_ID) "
				   + "JOIN Employee "
				   + "USING (Employee_ID) "
				   + "WHERE Asset_Management_ID = ?";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, currentAssetID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{					
					Asset currentAsset = new Asset
					(
							rs.getInt("Asset_ID"),
							null,
							null,
							null,
							null
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
					
					AssetManagement currentAssetManagements = new AssetManagement
					(
							rs.getInt("Asset_Management_ID"),
							currentEmployee,
							currentAsset,
							rs.getDate("Use_Date").toLocalDate()
					);
					
					assetManagements.add(currentAssetManagements);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
		return assetManagements;
	}
	
	private List<AssetMaintenanceSchedule> getAssetMaintenanceSchedules(int currentAssetID)
	{
		List<AssetMaintenanceSchedule> assetMaintenanceSchedules = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM Asset_Maintenance_Schedule "
				   + "JOIN Asset "
				   + "USING (Asset_ID) "
				   + "WHERE Asset_ID = ?";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, currentAssetID);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{					
					Asset currentAsset = new Asset
					(
							rs.getInt("Asset_ID"),
							null,
							null,
							null,
							null
					);
					
					AssetMaintenanceSchedule currentAssetMaintenanceSchedules = new AssetMaintenanceSchedule
					(
							currentAsset,
							rs.getDate("Next_Maintenance").toLocalDate()
					);
					
					assetMaintenanceSchedules.add(currentAssetMaintenanceSchedules);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
		return assetMaintenanceSchedules;
	}

	@Override
	public void add(Asset asset)
	{
		String sql = "INSERT INTO Asset "
				+    "	(Asset_Type_ID, Asset_Name, Asset_Brand, Asset_Description, Safety_Information, Current_Inventory, Purchase_Date, Purchase_Price) "
				+    "VALUES "
				+    "	(?, ?, ?, ?, ?, ?, ?, ?) ";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, asset.getAssetType().AssetTypeID);
			ps.setString(2, asset.getAssetProfile().AssetName);
			ps.setString(3, asset.getAssetProfile().AssetBrand);
			ps.setString(4, asset.getAssetProfile().AssetDescription);
			ps.setString(5, asset.getAssetProfile().SafetyInformation);
			ps.setInt(6, asset.getAssetProfile().CurrentInventory);
			ps.setDate(7, Date.valueOf(asset.getAssetProfile().PurchaseDate));
			ps.setBigDecimal(8, asset.getAssetProfile().PurchasePrice);
			
			ps.executeUpdate();
			asset.setAssetID(getNewAssetID());
			addAssetManagements(asset);
			addAssetMaintenanceSchedules(asset);	
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	private int getNewAssetID() 
	{
		String sql = "SELECT Asset_ID "
				   + "FROM Asset  "
				   + "ORDER BY Asset_ID DESC LIMIT 1 ";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					return rs.getInt("Asset_ID");
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

	private void addAssetManagements(Asset asset)
	{
		String sql = "INSERT INTO Asset_Management "
				   + "	(Employee_ID, Asset_ID, Use_Date) "
				   + "VALUES "
				   + "	(?, ?, ?)";
		try 
		{
			for (int i = 0; i < asset.getAssetManagements().size(); i++)
			{
				AssetManagement currentAssetManagements = asset.getAssetManagements().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
			
				// Set arguments
				ps.setInt(1, currentAssetManagements.Employee.getEmployeeID());
				ps.setInt(2, asset.getAssetID());
				ps.setDate(3, Date.valueOf(currentAssetManagements.UseDate));
				ps.executeUpdate();
			
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
	}
	
	private void addAssetMaintenanceSchedules(Asset asset)
	{
		String sql = "INSERT INTO Asset_Maintenance_Schedule "
				   + "VALUES "
				   + "	(?, ?)";
		try 
		{
			for (int i = 0; i < asset.getAssetMaintenanceSchedules().size(); i++)
			{
				AssetMaintenanceSchedule currentAssetMaintenanceSchedules = asset.getAssetMaintenanceSchedules().get(i);
				PreparedStatement ps = conn.prepareStatement(sql);
			
				// Set arguments
				ps.setInt(1, asset.getAssetID());
				ps.setDate(2, Date.valueOf(currentAssetMaintenanceSchedules.NextMaintenance));
				
				ps.executeUpdate();
			}
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public void addAll(List<Asset> assets)
	{
		for (Asset asset : assets)
		{
			add(asset);
		}
	}

	@Override
	public void delete(Asset asset)
	{
		String sql = "DELETE FROM Asset "
				   + "WHERE Asset_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, asset.getAssetID());
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public void deleteAll(List<Asset> assets) 
	{
		for (Asset asset : assets)
		{
			delete(asset);
		}
	}

	@Override
	public void update(Asset asset)
	{
		String sql = "UPDATE Asset "
				+    "SET "
				+    "	Asset_ID = ?, "
				+    "	Asset_Type = ?, "
				+    "	Asset_Name = ?, "
				+    "	Asset_Brand = ?, "
				+    "	Asset_Description = ?, "
				+    "	Safety_Information = ?, "
				+    "	Current_Inventory = ?, "
				+    "	Purchase_Date = ?, "
				+    "	Purchase_Price = ?, "
				+    "WHERE Asset_ID = ?";
		try 
		{
			this.conn = Session.connect();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, asset.getAssetID());
			ps.setInt(2, asset.getAssetType().AssetTypeID);
			ps.setString(3, asset.getAssetProfile().AssetName);
			ps.setString(4, asset.getAssetProfile().AssetBrand);
			ps.setString(5, asset.getAssetProfile().AssetDescription);
			ps.setString(6, asset.getAssetProfile().SafetyInformation);
			ps.setInt(7, asset.getAssetProfile().CurrentInventory);
			ps.setDate(8, Date.valueOf(asset.getAssetProfile().PurchaseDate));
			ps.setBigDecimal(9, asset.getAssetProfile().PurchasePrice);
			
			ps.executeQuery();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
	}

	@Override
	public Asset getByID(int assetID)
	{
		return context.stream()
				.filter(e -> e.getAssetID() == assetID)
				.collect(Collectors.toList())
				.get(0);
	}

	@Override
	public List<Asset> getAll()
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
