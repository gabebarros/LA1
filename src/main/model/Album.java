package main.model;
import java.util.ArrayList;

public class Album {
	
	private String title;
	private String artist;
	private String genre;
	private int year;
	private ArrayList<String> tracklist;
	
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
