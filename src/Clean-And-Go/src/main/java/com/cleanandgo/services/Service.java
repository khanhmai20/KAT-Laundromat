package com.cleanandgo.services;

import java.sql.Connection;
import java.util.List;
import java.util.function.Predicate;

import com.cleanandgo.data.Repository;

/**
 * Represents a service of the given type.
 * @param <T> The type of service
 */
public abstract class Service<T>
{
	private Connection conn;
	protected Repository<T> repository;
	
	protected Service(Connection conn) 
	{
		this.conn = conn;
	}
	
	/**
	 * Launches the service 
	 */
	public abstract void launch();
	
	/**
	 * Creates the type the service is associated with.
	 */
	abstract void create();
	
	abstract void createAll();
	
	abstract void get(int id);
	abstract void getAll();
	
	abstract void update();
	
	abstract void remove();
	abstract void removeAll();
	
	abstract <U> List<T> filter(Predicate<U> predicate);
	
	
	
}
