package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	// Need a PlayList class
	
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.favorites = new ArrayList<Song>();
	}

	
	public Album getAlbumByTitle(String title){
		for (Album a : this.albums) {
			if (a.getTitle().equals(title)) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
					if (s.isFavorite()) {
						copySong.markFavorite();
					}
					copyTracklist.add(copySong);
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				printAlbum(aCopy);
				return aCopy;
			}
		}
		
		return null;
	}
	
	public Song getSongByTitle(String title){
		for (Song s : this.songs) {
			if (s.getTitle().equals(title)) {
				printSong(s);		
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				return copySong;
			}
		}
		
		return null;
	}
	
	public ArrayList<Album> getAlbumsByArtist(String artist){
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for (Album a : this.albums) {
			if (a.getArtist().equals(artist)) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
					if (s.isFavorite()) {
						copySong.markFavorite();
					}
					copyTracklist.add(copySong);
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				searchedAlbums.add(aCopy);
			}
		}
		
		if (searchedAlbums.size() == 0) {
			return null;
		}
		
		for (Album a : searchedAlbums) {
			printAlbum(a);
		}
		
		return searchedAlbums;
	}
	
	public ArrayList<Song> getSongsByArtist(String artist){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		
		for (Song s : this.songs) {
			if (s.getArtist().equals(artist)) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				searchedSongs.add(copySong);
			}
			
		}
		
		if (searchedSongs.size() == 0) {
			return null;
		}
		
		for (Song s : searchedSongs) {
			printSong(s);
		}
		
		return searchedSongs;
	}
	
	// TODO: handle duplicate adds
	public void addSong(String songTitle) {
		MusicStore ms = new MusicStore();
		
		Song songToAdd = ms.getSongByTitle(songTitle, false);		
		
		// check if song exists
		if (songToAdd != null) {
			// add song, artist, and album to library
			
			Song copySong = new Song(songToAdd.getTitle(), songToAdd.getArtist(), songToAdd.getAlbum(), songToAdd.getRating());
			this.songs.add(copySong);
			this.artists.add(songToAdd.getArtist());
			
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			Album a = ms.getAlbumByTitle(songToAdd.getAlbum(), false);
			for (Song s : a.getTracklist()) {
				Song copySongAlbum = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				copyTracklist.add(copySongAlbum);
			}
			
			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
			albums.add(aCopy);
		}
		
	}
	
	// TODO: handle duplicate adds
	public void addAlbum(String albumTitle) {
		MusicStore ms = new MusicStore();
		
		Album a = ms.getAlbumByTitle(albumTitle, false);
		
		// add song, artist, album to the library
		if (a != null) {
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			for (Song s : a.getTracklist()) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				copyTracklist.add(copySong);
				this.songs.add(copySong);
			}
			
			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
			
			this.albums.add(aCopy);
			this.artists.add(aCopy.getArtist());
			
		}
		
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
		LibraryModel lib = new LibraryModel();
		//ArrayList<Album> albumlist = ms.getAlbumList();
		
		//System.out.println("MusicStore:"); // Placeholder code; better suited for View.java
		
		//for (Album a : albumlist) {
		//	System.out.println(a.getTracklist().get(0).getAlbum());
		//}

		//Song tapestry = ms.getSongByTitle("Tapestry", true);
		
		
		
		lib.addAlbum("Tapestry");
		lib.addAlbum("Tapestry");
		
		for (Song s : lib.songs) {
			System.out.println(s.getTitle());
		}
		
		for (Album a : lib.albums) {
			System.out.println(a.getTitle());
		}
		
	}

}
