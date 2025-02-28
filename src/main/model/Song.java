/*
 * Class: Song.java
 * 
 * This class represents one song in the musicStore / libraryModel. The class
 * contains instance variables such as title, artists, rating, etc. There are
 * two constructors, one also takes a rating. This is useful for making copies
 * of song objects that have been rated. The class containes getters, and methods
 * for rating and marking songs as favorite.
 */
package main.model;

public class Song {
	
	private String title;
	private String artist;
	private String album;
	private int rating;
	private boolean favorite;
	
	public Song(String title, String artist, String album) {
		this.title = title;
		this.artist = artist;
		this.album = album;
	}
	
	public Song(String title, String artist, String album, int rating) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.rating = rating;
		if (rating == 5) {
			this.favorite = true;
		}
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getArtist() {
		return this.artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public boolean isFavorite() {
		return this.favorite;
	}
	
	public void markFavorite() {
		this.favorite = true;
	}
	
	public void rate(int value) { 
		this.rating = value;
		
		if (this.rating == 5) {
			this.favorite = true;
		}
		else {
			this.favorite = false;
		}
	}
	
}
