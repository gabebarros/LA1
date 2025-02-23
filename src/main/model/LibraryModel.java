package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	// Need a PlayList class

	public static void main(String[] args) {
		MusicStore ms = new MusicStore();
		//ArrayList<Album> albumlist = ms.getAlbumList();
		
		System.out.println("MusicStore:"); // Placeholder code; better suited for View.java
		
		//for (Album a : albumlist) {
		//	System.out.println(a.getTracklist().get(0).getAlbum());
		//}

		
	}

}
