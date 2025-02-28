/*
 * Class: Album.java
 * 
 * This class represents one album in the musicStore / libraryModel. The class
 * contains attributes such as album title, genre, tracklist, and more. This is
 * a simple class, only containing a constructor and getters
 */
package main.model;
import java.util.ArrayList;


public class Album {
	
	private final String title;
	private final String artist;
	private final String genre;
	private final int year;
	private final ArrayList<Song> tracklist;
	
	public Album(String title, String artist, String genre, int year, ArrayList<Song> tracklist) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.tracklist = tracklist;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getGenre() {
		return this.genre;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public ArrayList<Song> getTracklist() {
		ArrayList<Song> copyTracklist = new ArrayList<Song>();
		
		for (Song s : this.tracklist) {
			Song sCopy = new Song(s.getTitle(), s.getArtist(), s.getAlbum());
			copyTracklist.add(sCopy);
		}
		
		return copyTracklist;
	}
	
}
