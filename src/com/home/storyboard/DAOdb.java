package com.home.storyboard;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.support.ConnectionSource;

@SuppressWarnings("unused")
public class DAOdb {

	private final static String DATABASE_URL = "jdbc:sqlite:C:/db/testdb.db";
	// we are using the in-memory H2 database "jdbc:sqlite:testdb.db";	
	private ConnectionSource connectionSource;
	private Dao<User, Integer> userDAO;
	private Dao<Profile,Integer> profileDAO;
	private Dao<Story, Integer> storyDAO;
	private String lastError;
	
    
	
	public DAOdb(){
		connectionSource = null;
	try {
		// create our data-source for the database
		
		connectionSource = new JdbcConnectionSource(DATABASE_URL);
		userDAO = DaoManager.createDao(connectionSource, User.class);
		//TableUtils.createTable(connectionSource, User.class);
		profileDAO = DaoManager.createDao(connectionSource, Profile.class);
		//TableUtils.createTable(connectionSource, Profile.class);
		storyDAO = DaoManager.createDao(connectionSource, Story.class);
		//TableUtils.createTable(connectionSource, Story.class);
		}catch(SQLException e){
	    	System.out.println(e.getMessage());
	    }
}
	
	/**
	 * DAO Functions to fetch data
	 */
		
	 public void addStory(Story S) {
		 try{
			 storyDAO.create(S);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    }
	 
	 public List<Story> getStoriesbyUsername(String username) {
		 String uname =username;
		 User user = getUserbyUsername(uname);
		 Integer uid =user.getId();
		 List<Story> storyList=null;
		 try{
			    GenericRawResults<String[]> stories = storyDAO.queryRaw("select count(*) from stories where authorid =1");
		   	     // there should be 1 result
				List<String[]> results = stories.getResults();
				// the results array should have 1 value
				Integer usercount = results.size();
				// this should print the number of orders that have this account-id
				if(usercount<=0){
					System.out.println("No story exist for " +username);
				  }else{
					   storyList = storyDAO.queryBuilder().where().eq(Story.AUTHORID_FIELD_NAME, uid).query();
					   System.out.println(storyList.get(0));
				  }
				}catch(SQLException e){
					lastError = e.getMessage().toString();
				}
				 
		  return storyList;
	    }
	 
	 public void addUser(User U) {
		 try{
			 userDAO.create(U);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    } 
	 
	 public User getUserbyUsername(String username) {
		 // find out how many users are there with the same username
		String uname = username;
		User user = null;
		try{
		long usercount= userDAO.queryRawValue("select count(*) from Users where username = ?", uname); 
		// there should be 1 result
		if(usercount<=0){
			lastError="'User does not exist by username'+' '+uname";
		  }else if(usercount>1){
			lastError="'More than one user exist by same username'+' '+ uname"; 
		  }else{
			  
			  List<User> userList =
				      userDAO.queryBuilder().where()
				         .eq(User.NAME_FIELD_NAME, uname)
				         .query();
			  user = userList.get(0);
		  }
		}catch(Exception e){
			System.out.println(e.getMessage().toString());
			lastError = e.getMessage().toString();
		}
		 
		return user;
	  }
	 
	 
	 public void addProfile(Profile P) {
		 try{
			 profileDAO.create(P);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    } 
	 
	 public Profile getProfilebyUsername(String username) {
		 // find out how many users are there with the same username
		String uname = username;
		Profile profile =null;
		User user = getUserbyUsername(uname);
		Integer uid = user.getId();
		try{
     		// find out how many orders account-id #10 has
			GenericRawResults<String[]> rawResults = profileDAO.queryRaw("select count(*) from profiles where userid = 1");
			// there should be 1 result
			List<String[]> results = rawResults.getResults();
			// the results array should have 1 value
			Integer profcount = results.size();
			// this should print the number of orders that have this account-id
			System.out.println("profiles with userid 1 has " + profcount + "profiles");

		if( profcount <= 0){
			lastError="'Profile does not exist by username'+' '+ username";
		}else if(profcount>1){
			lastError="'More than one profile exist by same username'+' '+ username";
		}else{
			List<Profile> profileList = profileDAO.queryBuilder().where().eq(Profile.USERID_FIELD_NAME, uid).query();
			profile = profileList.get(0);
		     }
		}catch(SQLException e){
			lastError = e.getMessage().toString();
		}
		 
		return profile;
	  }
	 
	 public User Authenticate(String un,String pwd){
		 
		 String uname=un;
		 String passwd=pwd;
		 User user = null;
		 
		 try{
				long usercount= userDAO.queryRawValue("select count(*) from Users where username = ? and password = ?", uname,passwd); 
				// there should be 1 result
				if(usercount<=0){
					lastError="'User does not exist by username'+' '+uname";
				  }else if(usercount>1){
					lastError="'More than one user exist by same username'+' '+ uname"; 
				  }else{
					  
					  List<User> userList =
						      userDAO.queryBuilder().where()
						         .eq(User.NAME_FIELD_NAME, uname)
						         .and()
						         .eq(User.PASSWORD_FIELD_NAME,passwd)
						         .query();
					  user = userList.get(0);
				  }
				}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
				 
				return user;
		    }
		 
		 
		 
		 
	public void close() {
		 if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					lastError = e.getMessage().toString();
				}
			}
	    }
	
	 public String getLastError() { return lastError; }
	
}