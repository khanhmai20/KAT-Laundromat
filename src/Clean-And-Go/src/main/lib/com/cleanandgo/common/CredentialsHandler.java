package com.cleanandgo.common;
import org.mindrot.jbcrypt.*;

import com.cleanandgo.models.Name;

/**
 * Contains functionality to deal with user credentials.
 */
public class CredentialsHandler
{
	/**
	 * Hashes and salts a password using BCrypt
	 * @param password The plaintext password to be hashed.
	 * @return A salted and hashed password.
	 */
	public static String getHashedPassword(String password)
	{
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	/**
	 * Compares a plaintext password against a hashed password.
	 * @param password The plaintext password to be compared
	 * @param hashedPassword The hashed password to be compared
	 * @return True if the plaintext password can be hashed into the hashed password, false otherwise.
	 */
	public static boolean checkHashedPassword(String password, String hashedPassword)
	{
		return BCrypt.checkpw(password, hashedPassword);
	}
	
	/**
	 * Formats a user name according to business specifications.
	 * @param name The employee's name.
	 * @return A formatted string containing the user name.
	 */
	public static String getUserName(Name name)
	{
		return String.format
		(
				"%c%s@cleango",
				name.firstName.toLowerCase().charAt(0),
				name.lastName.toLowerCase()
		);
	}
}
