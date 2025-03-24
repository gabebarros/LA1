/*
 * Class: MusicStoreTest.java
 * 
 * This class contains the unit tests for the UserDB.java class.
 */
package test.database;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.database.UserDB;
import main.model.User;

class UserDBTest {

	@Test
	void testAddUser_Success() {
		UserDB db = new UserDB();
		
		try {
			assertTrue(db.addUser("Gabe", "123"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testAddUser_UsernameTaken() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
			assertFalse(db.addUser("Gabe", "1234"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetUser_Found() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User gabe = db.getUser("Gabe");
		assertEquals(gabe.getUsername(), "Gabe");
	}
	
	@Test
	void testGetUser_NotFound() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User soph = db.getUser("Soph");
		assertNull(soph);
	}
	
	@Test
	void testLoginSuccessful_Success() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertTrue(db.loginSuccessful("Gabe", "123"));
	}
	
	@Test
	void testLoginUnSuccessful_WrongPassword() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertFalse(db.loginSuccessful("Gabe", "1234"));
	}
	
	@Test
	void testLoginUnSuccessful_WrongUsername() {
		UserDB db = new UserDB();
		
		try {
			db.addUser("Gabe", "123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertFalse(db.loginSuccessful("Gabriel", "123"));
	}

}
