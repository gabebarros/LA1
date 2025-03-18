package main.model;

public class User {
	
	private String username;
	private LibraryModel library;

	public User(String username) {
		this.username = username;
		this.library = new LibraryModel();
	}
	
	public String getUsername() {
		return username;
	}

	public LibraryModel getLibrary() {
		return library;
	}

}