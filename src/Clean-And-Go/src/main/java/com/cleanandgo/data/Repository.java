package com.cleanandgo.data;

import java.util.*;
import java.sql.*;

/**
 * Generic base class for all repositories.
 * @param <T> The type of objects contained in the repository.
 */
public abstract class Repository<T>
{
	Connection conn;
	List<T> context;
	
	protected Repository(Connection conn)
	{
		this.conn = conn;
		this.context = new ArrayList<T>();
	}
	
	/**
	 * Seeds the repository using the appropriate SQL queries.
	 */
	abstract void seedRepository();
	
	/**
	 * Adds the given type to the repository.
	 * @param entity The type to be added.
	 */
	public abstract void add(T entity);
	
	/**
	 * Adds all the types in the given list to the repository.
	 * @param entities The list of types to be added.
	 */
	public abstract void addAll(List<T> entities);

	public abstract void delete(T entity);
	public abstract void deleteAll(List<T> entities);
	
	public abstract void update(T entity);
	
	/**
	 * Retrieves a type by it's id
	 * @param id The ID to be looked up.
	 * @return A type that matches the given ID.
	 */
	public abstract T getByID(int id);
	
	/**
	 * Retrieves all the types contained in the repository.
	 * @return The list of types in the repository.
	 */
	public abstract List<T> getAll();
	
	/**
	 * Reseeds the repository.
	 */
	public abstract void save();

}
