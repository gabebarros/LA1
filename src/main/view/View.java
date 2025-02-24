package main.view;

import java.util.Scanner;

import main.database.MusicStore;
import main.model.LibraryModel;

public class View {
	
	public static void main(String[] args) {
		MusicStore ms = new MusicStore();
		LibraryModel library = new LibraryModel();
		
		Scanner scanner = new Scanner(System.in);  // use to get user input
	     
	     while (true) {
	    	System.out.println("What would you like to do?");
	    	System.out.println("");
	    	String input = scanner.nextLine().toLowerCase();  // store user input

	        if (input.equals("quit")) {
	        	break;
	        }
	        
	        if (input.equals("search music store")) {
	        	System.out.println("Do you want to search for song(s) or album(s)?");
	        	System.out.println("");
	        	
	        	input = scanner.nextLine().toLowerCase().strip();
	        	
	        	if (input.equals("song") || input.equals("songs")) {
	        		System.out.println("By title or artist?");
		        	System.out.println("");
		        	input = scanner.nextLine().toLowerCase().strip();
		        	
		        	if (input.equals("artist")) {
		        		System.out.println("Which artist?");
			        	System.out.println("");
			        	input = scanner.nextLine().strip();
			        	
			        	ms.getSongsByArtist(input, true);
		        	}
		        	else if (input.equals("title")) {
		        		System.out.println("Which song?");
			        	System.out.println("");
			        	input = scanner.nextLine().strip();
			        	
			        	System.out.println(input);
			        	
			        	ms.getSongByTitle(input, true);
		        	}
		        	else {
		        		System.out.println("This is not an option");
		        		System.out.println("");
		        	}
	        	}
	        	else if (input.equals("album") || input.equals("albums")) {
	        		System.out.println("By title or artist?");
		        	System.out.println("");
		        	
		        	input = scanner.nextLine().strip();
		        	
		        	if (input.equals("artist")) {
		        		System.out.println("Which artist?");
			        	System.out.println("");
			        	input = scanner.nextLine().strip();
			        	
			        	ms.getAlbumsByArtist(input, true);
		        	}
		        	else if (input.equals("title")) {
		        		System.out.println("Which album?");
			        	System.out.println("");
			        	input = scanner.nextLine().strip();
			        	
			        	ms.getAlbumByTitle(input, true);
		        	}
	        	else {
	        		System.out.println("This is not an option");
	        		System.out.println("");
	        	}
	        }
	        }
	     
	     }
	     
	  scanner.close();  
	 }
}
