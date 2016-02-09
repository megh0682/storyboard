package com.home.storyboard;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Example user object that is persisted to disk by the DAO and other example classes.
 */
@DatabaseTable(tableName = "Users")
public class User {

// for QueryBuilder to be able to find the fields
	public static final String NAME_FIELD_NAME = "username";
	public static final String PASSWORD_FIELD_NAME = "password";
	public static final String PROFILE_ID = "profileid";
	public static final String ROW_ID = "rowid";
	//@DatabaseField(generatedId = true)
	//private int rowid;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String username;

	@DatabaseField(columnName = PASSWORD_FIELD_NAME)
	private String password;
	
	@DatabaseField(columnName = PROFILE_ID)
	private String profileid;

	@DatabaseField(generatedId = true)
	private int id;
	
	
	User() {
		// all persisted classes must define a no-arg constructor with at least package visibility
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		return username.equals(((User) other).username);
	}
}