/*
 * Class: UserDB.java
 * 
 * This class serves to manage the pseudo database that stores the user information
 * for the program. It can add users, login, and check if a login is successful
 * given the login information. The actual user data (username, salt, hashed pwd)
 * are stored in a text file called userDB.txt
 */
package main.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

import main.model.User;

public class UserDB {
	
	private static final String USER_DB = "userDB.txt";
	private static HashMap<String, User> users;

	public UserDB() {
		 users = new HashMap<String, User>();
	}
	
	// returns true if successful, else returns false
	public boolean addUser(String username, String password) throws IOException {
		if (users.containsKey(username)) {
			return false;
		}
		
        String salt = generateSalt();
        String hashedPassword = hash(password, salt);

        try  {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DB, true));
            writer.write(username + "," + salt + "," + hashedPassword);
            writer.newLine();
            
            users.put(username, new User(username));
            
            writer.close();
        }
        catch (IOException e) {
        	System.exit(-1);
        }
        
        return true;
    }
	
	// checks if the user exists in the db. if so, return the username, else return null.
	public User getUser(String username) {
		if (users.containsKey(username)) {
			return users.get(username);
		}
		return null;
	}
	
	// checks to see if there exists a created account with this username and pwd
	public boolean loginSuccessful(String username, String password) {
		// first check if user exists
		if (!users.containsKey(username)) {
			return false;
		}
		
		// parse the db file and compare against each user's data
		try (BufferedReader reader = new BufferedReader(new FileReader(USER_DB))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                String storedUsername = info[0];
                String storedSalt = info[1];
                String storedPassword = info[2];
                
                if (username.equals(storedUsername)) {
                	if (storedPassword.equals(hash(password, storedSalt))) {
                		return true;
                	}
                }
            }
        } catch (FileNotFoundException e) {
        	System.exit(-1);
        } catch (IOException e) {
        	System.exit(-1);
        }
		
		return false;
	}
	
	// generates a random 18 character encoded salt
	private static String generateSalt() {
		Random random = new Random();
        byte[] salt = new byte[18];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
	}
	
	// salts and hashes a password using the sha-256 hash algorithm.
	private static String hash(String password, String salt) {
		MessageDigest messageDigest;
		String hashedPassword = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
	        messageDigest.update((password + salt).getBytes());
	        byte[] hashedBytes = messageDigest.digest();
	        hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
		} 
		catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		}
		
		return hashedPassword;
	}

}
