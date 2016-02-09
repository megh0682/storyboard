package com.home.storyboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Main sample routine to show how to do basic operations with the package.
 * 
 * <p>
 * <b>NOTE:</b> We use asserts in a couple of places to verify the results but if this were actual production code, we
 * would have proper error handling.
 * </p>
 */
public class DAOdb {

// we are using the in-memory H2 database
	private final static String DATABASE_URL = "jdbc:sqlite:testdb.db";

	private Dao<User, Integer> userDAO;

	public static void main(String[] args) throws Exception {
		// turn our static method into an instance of Main
		new DAOdb().doMain(args);
	}

	private void doMain(String[] args) throws Exception {
		ConnectionSource connectionSource = null;
		try {
			// create our data-source for the database
			connectionSource = new JdbcConnectionSource(DATABASE_URL);
			// setup our database and DAOs
			setupDatabase(connectionSource);
			// read and write some data
			readWriteData();
			// do a bunch of bulk operations
			readWriteBunch();
			// show how to use the SelectArg object
			useSelectArgFeature();
			// show how to use the SelectArg object
			useTransactions(connectionSource);
			System.out.println("\n\nIt seems to have worked\n\n");
		} finally {
			// destroy the data source which should close underlying connections
			if (connectionSource != null) {
				connectionSource.close();
			}
		}
	}

	/**
	 * Setup our database and DAOs
	 */
	private void setupDatabase(ConnectionSource connectionSource) throws Exception {

		userDAO = DaoManager.createDao(connectionSource, User.class);

		// if you need to create the table
		TableUtils.createTable(connectionSource, User.class);
	}

	/**
	 * Read and write some example data.
	 */
	private void readWriteData() throws Exception {
		// create an instance of Account
		String username = "Anirudh";
		User user = new User(username);

		// persist the account object to the database
		userDAO.create(user);
		int id = user.getId();
		verifyDb(id, user);

		// assign a password
		user.setPassword("123456");
		// update the database after changing the object
		userDAO.update(user);
		verifyDb(id, user);

		// query for all items in the database
		List<User> users = userDAO.queryForAll();
		assertEquals("Should have found 1 account matching our query", 1, users.size());
		verifyAccount(user, users.get(0));

		// loop through items in the database
		int userCount = 0;
		for (User user2 : userDAO) {
			verifyAccount(user, user2);
			userCount++;
		}
		assertEquals("Should have found 1 account in for loop", 1, userCount);

		// construct a query using the QueryBuilder
		QueryBuilder<User, Integer> statementBuilder = userDAO.queryBuilder();
		// shouldn't find anything: name LIKE 'hello" does not match our account
		statementBuilder.where().like(User.NAME_FIELD_NAME, "hello");
		users = userDAO.query(statementBuilder.prepare());
		assertEquals("Should not have found any accounts matching our query", 0, users.size());

		// should find our account: name LIKE 'Jim%' should match our account
		statementBuilder.where().like(User.NAME_FIELD_NAME, username.substring(0, 3) + "%");
		users = userDAO.query(statementBuilder.prepare());
		assertEquals("Should have found 1 account matching our query", 1, users.size());
		verifyAccount(user, users.get(0));

		// delete the account since we are done with it
		//userDAO.delete(user);
		// we shouldn't find it now
		//assertNull("account was deleted, shouldn't find any", userDAO.queryForId(id));
	}

	/**
	 * Example of reading and writing a large(r) number of objects.
	 */
	private void readWriteBunch() throws Exception {

		Map<String, User> users = new HashMap<String, User>();
		for (int i = 1; i <= 100; i++) {
			String username = Integer.toString(i);
			User user = new User(username);
			// persist the account object to the database, it should return 1
			userDAO.create(user);
			users.put(username, user);
		}

		// query for all items in the database
		List<User> all = userDAO.queryForAll();
		assertEquals("Should have found same number of accounts in map", users.size()+1, all.size());
		for (User user : all) {
			//assertTrue("Should have found account in map", users.containsValue(user));
			//verifyAccount(users.get(user.getUserName()), user);
		}

		// loop through items in the database
		int userCount = 0;
		for (User user : userDAO) {
			//assertTrue("Should have found account in map", users.containsValue(user));
			//verifyAccount(users.get(user.getUserName()), user);
			userCount++;
		}
		assertEquals("Should have found the right number of accounts in for loop", users.size()+1, userCount);
	}

	/**
	 * Example of created a query with a ? argument using the {@link SelectArg} object. You then can set the value of
	 * this object at a later time.
	 */
	private void useSelectArgFeature() throws Exception {

		String name1 = "foo";
		String name2 = "bar";
		String name3 = "baz";
		assertEquals(1, userDAO.create(new User(name1)));
		assertEquals(1, userDAO.create(new User(name2)));
		assertEquals(1, userDAO.create(new User(name3)));

		QueryBuilder<User, Integer> statementBuilder = userDAO.queryBuilder();
		SelectArg selectArg = new SelectArg();
		// build a query with the WHERE clause set to 'name = ?'
		statementBuilder.where().like(User.NAME_FIELD_NAME, selectArg);
		PreparedQuery<User> preparedQuery = statementBuilder.prepare();

		// now we can set the select arg (?) and run the query
		selectArg.setValue(name1);
		List<User> results = userDAO.query(preparedQuery);
		assertEquals("Should have found 1 account matching our query", 1, results.size());
		assertEquals(name1, results.get(0).getUserName());

		selectArg.setValue(name2);
		results = userDAO.query(preparedQuery);
		assertEquals("Should have found 1 account matching our query", 1, results.size());
		assertEquals(name2, results.get(0).getUserName());

		selectArg.setValue(name3);
		results = userDAO.query(preparedQuery);
		assertEquals("Should have found 1 account matching our query", 1, results.size());
		assertEquals(name3, results.get(0).getUserName());
	}

	/**
	 * Example of created a query with a ? argument using the {@link SelectArg} object. You then can set the value of
	 * this object at a later time.
	 */
	private void useTransactions(ConnectionSource connectionSource) throws Exception {
		System.out.println(connectionSource.getDatabaseType().getDatabaseName());
 		String name = "trans1";
		final User user = new User(name);
		assertEquals(1, userDAO.create(user));

		TransactionManager transactionManager = new TransactionManager(connectionSource);
		try {
			// try something in a transaction
			transactionManager.callInTransaction(new Callable<Void>() {
				public Void call() throws Exception {
					
					// assign a password
					user.setPassword("123456");
					// update the database after changing the object
					userDAO.update(user);
					//assertEquals(1, userDAO.delete(user));
					// we do the delete
					//assertNull(userDAO.queryForId(user.getId()));
					// but then (as an example) we throw an exception which rolls back the delete
					throw new Exception("We throw to roll back!!");
				}
			});
			fail("This should have thrown");
		} catch (SQLException e) {
			// expected
		}

		assertNotNull(userDAO.queryForId(user.getId()));
	}

	/**
	 * Verify that the account stored in the database was the same as the expected object.
	 */
	private void verifyDb(int id, User expected) throws SQLException, Exception {
		// make sure we can read it back
		User user = userDAO.queryForId(id);
		if (user == null) {
			throw new Exception("Should have found rowid '" + id + "' in the database");
		}
		verifyAccount(expected, user);
	}

	/**
	 * Verify that the account is the same as expected.
	 */
	private static void verifyAccount(User expected, User user) {
		assertEquals("expected name does not equal account name", expected, user);
		assertEquals("expected password does not equal account name", expected.getPassword(), user.getPassword());
	}
}