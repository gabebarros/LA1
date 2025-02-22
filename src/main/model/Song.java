package main.model;

public class Song {
	
	private String title;
	private String artist;
	private String album;
	private int rating;
	
	public Song(String title, String artist, String album) {
		this.title = title;
		this.artist = artist;
		this.album = album;
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
	
	public void rate(int value) { // Maybe private for this method?
		this.rating = value;
	}
	
	public int getRating() {
		return this.rating;
	}
}
