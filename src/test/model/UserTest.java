package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.LibraryModel;
import main.model.User;

class UserTest {

	@Test
	void testGetUsername() {
		User u1 = new User("Gabe");
		assertEquals(u1.getUsername(), "Gabe");
	}
	
	@Test
	void testGetlibrary() {
		User u1 = new User("Gabe");
		LibraryModel userLib = u1.getLibrary();
		LibraryModel testLib = new LibraryModel();
		assertTrue(userLib.getClass() == testLib.getClass());
	}

}
