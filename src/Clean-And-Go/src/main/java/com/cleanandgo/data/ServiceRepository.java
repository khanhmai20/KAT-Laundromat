package com.cleanandgo.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;
import com.cleanandgo.models.Service;
import com.cleanandgo.models.ServiceType;

/**
 * Contains functionality for service objects.
 */
public class ServiceRepository extends Repository<Service>
{
	public ServiceRepository(Connection conn)
	{
		super(conn);
		seedRepository();
	}

	@Override
	void seedRepository() 
	{
		String sql = "SELECT * "
				+    "FROM Service "
				+    "JOIN Service_Type";
		
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					int currentServiceID = rs.getInt("Service_ID");

					ServiceType currentServiceType = new ServiceType
					(
							rs.getInt("Service_Type_ID"),
							rs.getString("Type_Name"),
							rs.getString("Type_Description")
					);
					
					Service currentService = new Service
					(
							currentServiceID,
							currentServiceType,
							rs.getString("Service_Name"),
							rs.getString("Service_Description"),
							rs.getDouble("Rate"),
							rs.getTime("Duration") == null ? null : rs.getTime("Duration").toLocalTime().plusHours(8)
					);
					
					context.add(currentService);
				}
			}
		}
		catch (SQLException e) 
		{
	        System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
		
	}

	@Override
	public void add(Service service) 
	{
		String sql = "INSERT INTO Service "
				+    "	(Service_Type_ID, Service_Name, Service_Description, Rate, Duration) "
				+    "VALUES "
				+    "	(?, ?, ?, ?, ?) ";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, service.ServiceType.ServiceTypeID);
			ps.setString(2, service.ServiceName);
			ps.setString(3, service.ServiceDescription);
			ps.setDouble(4, service.Rate);
			ps.setTime(5, Time.valueOf(service.Duration.minusHours(8)));
		
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public void addAll(List<Service> services) 
	{
		for (Service service : services)
		{
			add(service);
		}
	}

	@Override
	public void delete(Service service)
	{
		String sql = "DELETE FROM Service "
				   + "WHERE Service_ID = ?";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, service.ServiceID);
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public void deleteAll(List<Service> services)
	{
		for (Service service : services)
		{
			delete(service);
		}
	}

	@Override
	public void update(Service service) 
	{
		String sql = "UPDATE Service "
				+    "SET "
				+    "	Service_ID = ?, "
				+    "	Service_Type_ID = ?, "
				+    "	Service_Name = ?, "
				+    "	Service_Description = ?, "
				+    "	Rate = ?, "
				+    "	Duration = ?, "
				+    "WHERE Service_ID = ?";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			
			// Set arguments
			ps.setInt(1, service.ServiceID);
			ps.setInt(2, service.ServiceType.ServiceTypeID);
			ps.setString(3, service.ServiceName);
			ps.setString(4, service.ServiceDescription);
			ps.setDouble(5, service.Rate);
			ps.setTime(6, Time.valueOf(service.Duration.minusHours(8)));
			
			ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			System.out.println("\nOperation failed. Please contact administrator.\n");
	    }
	}

	@Override
	public Service getByID(int serviceID) 
	{
		return context.stream()
				.filter(e -> e.ServiceID == serviceID)
				.collect(Collectors.toList())
				.get(0);
	}

	@Override
	public List<Service> getAll() 
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
