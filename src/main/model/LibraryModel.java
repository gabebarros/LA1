package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	// Need a PlayList class

	
	public Album getAlbumByTitle(String title){
		for (Album a : this.albums) {
			if (a.getTitle().equals(title)) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				printAlbum(aCopy);
				return aCopy;
			}
		}
		
		System.out.println("No album with this title");
		return null;
	}
	
	public Song getSongByTitle(String title){
		for (Song s : this.songs) {
			if (s.getTitle().equals(title)) {
				printSong(s);		
				return new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
			}
		}
		
		System.out.println("No song with this title");
		return null;
	}
	
	public ArrayList<Album> getAlbumsByArtist(String artist){
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for (Album a : this.albums) {
			if (a.getArtist().equals(artist)) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				searchedAlbums.add(aCopy);
			}
		}
		
		if (searchedAlbums.size() == 0) {
			System.out.println("No albums by this artist");
			return null;
		}
		
		for (Album a : searchedAlbums) {
			printAlbum(a);
		}
		
		return searchedAlbums;
	}
	
	public ArrayList<Song> getSongsByArtist(String artist){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		
		for (Song s : songs) {
			if (s.getArtist().equals(artist)) {
				searchedSongs.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
			}
			
		}
		
		if (searchedSongs.size() == 0) {
			System.out.println("No songs by this artist");
			return null;
		}
		
		for (Song s : searchedSongs) {
			printSong(s);
		}
		
		return searchedSongs;
	}
	
	private void printSong(Song s) {
		System.out.println("Song title: " + s.getTitle());
		System.out.println("Artist: " + s.getArtist());
		System.out.println("Album: " + s.getAlbum());
		System.out.println();
	}
	
	private void printAlbum(Album a) {
		System.out.println("Album title: " + a.getTitle());
		System.out.println("Artist: " + a.getArtist());
		System.out.println("Genre: " + a.getGenre());
		System.out.println("Year: " + a.getYear());
		System.out.println("Tracklist:");
		
		for (Song s : a.getTracklist()) {
			System.out.println(s.getTitle());
		}
		System.out.println();
	}
	
	
	
	public static void main(String[] args) {
		MusicStore ms = new MusicStore();
		//ArrayList<Album> albumlist = ms.getAlbumList();
		
		//System.out.println("MusicStore:"); // Placeholder code; better suited for View.java
		
		//for (Album a : albumlist) {
		//	System.out.println(a.getTracklist().get(0).getAlbum());
		//}

		ms.getAlbumByTitle("Tapestry", true);
		
	}

}
