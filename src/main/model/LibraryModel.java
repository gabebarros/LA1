package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	private ArrayList<PlayList> playlists;
	// Need a PlayList class
	
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.favorites = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
	}

	
	public Album getAlbumByTitle(String title, boolean print){
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
				if (print) {
					printAlbum(aCopy);
				}
				return aCopy;
			}
		}
		
		if (print) {
			System.out.println("No albums with this title");
		}
		
		return null;
	}
	
	public Song getSongByTitle(String title, boolean print){
		for (Song s : this.songs) {
			if (s.getTitle().equals(title)) {
				if (print) {
					printSong(s);
				}		
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				return copySong;
			}
		}
		
		if (print) {
			System.out.println("No songs with this title");
		}
		return null;
	}
	
	public ArrayList<Album> getAlbumsByArtist(String artist, boolean print){
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
			if (print) {
				System.out.println("No albums by this artist");
			}
			
			return null;
		}
		
		if (print) {
			for (Album a : searchedAlbums) {
				printAlbum(a);
			}
		}
		
		return searchedAlbums;
	}
	
	public ArrayList<Song> getSongsByArtist(String artist, boolean print){
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
			if (print) {
				System.out.println("No songs by this artist");
			}
			
			return null;
		}
		
		if (print) {
			for (Song s : searchedSongs) {
				printSong(s);
			}
		}
	
		return searchedSongs;
	}
	
	public void addSong(String songTitle) {
		MusicStore ms = new MusicStore();
		
		Song songToAdd = ms.getSongByTitle(songTitle, false);	
		
		// check if song exists
		if (songToAdd != null) {
			// add song, artist, and album to library
			
			if (getSongByTitle(songTitle, false) == null) {
				Song copySong = new Song(songToAdd.getTitle(), songToAdd.getArtist(), songToAdd.getAlbum(), songToAdd.getRating());
				this.songs.add(copySong);
			}
			
			if (!this.artists.contains(songToAdd.getArtist())) {
				this.artists.add(songToAdd.getArtist());
			}
			
			if (getAlbumByTitle(songToAdd.getAlbum(), false) == null) {
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
	}
	
	public void addAlbum(String albumTitle) {
		MusicStore ms = new MusicStore();
		
		Album a = ms.getAlbumByTitle(albumTitle, false);
		
		// add song, artist, album to the library
		if (a != null) {
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			for (Song s : a.getTracklist()) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				copyTracklist.add(copySong);
				addSong(copySong.getTitle());
			}
			
			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
			
			if (getAlbumByTitle(aCopy.getTitle(), false) == null) {
				this.albums.add(aCopy);
			}	
			
			if (!this.artists.contains(aCopy.getArtist())) {
				this.artists.add(aCopy.getArtist());
			}
			
		}
		
	}
	
	// create a blank playlist
	public void makePlaylist(String name) {
		for (PlayList p : this.playlists) {
			if (p.getName().equals(name)) {
				System.out.println("Playlist with this name already exists");
				return;
			}
		}
		
		PlayList newPlayList = new PlayList(name);
		playlists.add(newPlayList);
	}
	
	// print all songs
	public void getSongs() {
		for (Song s : this.songs) {
			System.out.println(s.getTitle());
		}
	}
	
	// print all albums
	public void getAlbums() {
		for (Album a : this.albums) {
			System.out.println(a.getTitle());
		}
	}
	
	// print all artists
	public void getArtists() {
		for (String a : this.artists) {
			System.out.println(a);
		}
	}
	
	// print all favorite songs
	public void getFavorites() {
		for (Song s : this.favorites) {
			System.out.println(s.getTitle());
		}
	}
	
	// print all playlists
	public void getPlaylists() {
		for (PlayList p : this.playlists) {
			System.out.println(p.getName());
		}
	}
	
	// print song + title for every song in playlist
	public void printPlayListByName(String name) {
		boolean printed = false;
		for (PlayList p : this.playlists) {
			if (p.getName().equals(name)) {
				printed = true;
				ArrayList<Song> playlistSongs = p.getSongs();
				
				for (Song s : playlistSongs) {
					System.out.println(s.getArtist() + ": " + s.getTitle());
				}
			}
		}
		
		if (!printed) {
			System.out.println("No playlist with this name");
		}
	}
	
	public void addSongToPlayList(String name, Song s) {
		boolean printed = false;
		for (PlayList p : this.playlists) {
			if (p.getName().equals(name)) {
				printed = true;
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				p.addSong(copySong);
			}
		}
		
		if (!printed) {
			System.out.println("No playlist with this name");
		}
	}
	
	// print a song along with its other information
	private void printSong(Song s) {
		System.out.println("Song title: " + s.getTitle());
		System.out.println("Artist: " + s.getArtist());
		System.out.println("Album: " + s.getAlbum());
		System.out.println();
	}
	
	// print an album along with its other information
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
		
		
		
		lib.addAlbum("19");
		lib.addSong("Tapestry");
		lib.addSong("Tired");
		
		for (Song s : lib.songs) {
			System.out.println(s.getTitle());
		}
		System.out.println();
		
		for (Album a : lib.albums) {
			System.out.println(a.getTitle());
		}
		
		System.out.println();
		for (String s : lib.artists) {
			System.out.println(s);
		}
		
		lib.makePlaylist("my playlist");
		lib.addSongToPlayList("my playlist", ms.getSongByTitle("Tapestry", false));
		
	}

}
