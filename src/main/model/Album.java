package main.model;
import java.util.ArrayList;

public class Album {
	
	private final String title;
	private final String artist;
	private final String genre;
	private final int year;
	private final ArrayList<String> tracklist;
	
	public Album(String title, String artist, String genre, int year, ArrayList<String> tracklist) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.tracklist = tracklist;
	}
	
	public String getTitle() {
		return this.title;
	}
	
}
