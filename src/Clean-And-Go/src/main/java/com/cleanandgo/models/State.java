package com.cleanandgo.models;

/**
 * Represents a state struct.
 */
public class State
{
	public int StateID;
	public String StateName;
	public String StateCode;
	
	public State(int stateID, String stateName, String stateCode) 
	{
		this.StateID = stateID;
		this.StateName = stateName;
		this.StateCode = stateCode;
	}
}	
