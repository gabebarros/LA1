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
	
	public void rate(int value) { // Maybe private for this method?
		if (value < 1 || value > 5) {
			System.out.println("Ratings must be between 1 and 5");
			return;
		}
		
		this.rating = value;
		
		if (value == 5) {
			this.favorite = true;
		}
	}
	
}
