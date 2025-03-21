/*
 * Class: LibraryModel.java
 * 
 * This class represents the user's library in the application. Users can add
 * songs/albums, create playlists, rate songs, search for songs/playlists, etc.
 * This class interacts with the MusicStore class in order to get songs/albums.
 * All getter methods return copies of the data, leading to a well-encapsulated
 * class.
 * 
 */
package main.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import main.database.MusicStore;

public class LibraryModel {
	
	protected ArrayList<Song> songs;
	protected ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<PlayList> playlists;
	
	private LinkedList<Song> recentlyPlayed; // Stores the last 10 songs played
	private PriorityQueue<Song> frequentlyPlayed; // Stores the top 10 most played 
	
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.playlists = new ArrayList<PlayList>();
		this.recentlyPlayed = new LinkedList<>();
		this.frequentlyPlayed = new PriorityQueue<>(10, Comparator.comparingInt(Song::getPlayCount).reversed());
		
		loadPlayHistory(); // Load play history on startup
	}
	
	public void playSong(String title) {
		Song song = getSongByTitle(title);
		if (song == null) {
			System.out.println("Song not found.");
			return;
		}
		
		song.play(); // Increase play count
		
		// Add to Recently Played
		recentlyPlayed.remove(song); // Avoid duplicates
		recentlyPlayed.addFirst(song);
		if (recentlyPlayed.size() > 10) {
			recentlyPlayed.removeLast(); // Keep only the last 10
		}
		
		// Add to Frequently Played
		frequentlyPlayed.remove(song); // Remove old entry
		frequentlyPlayed.add(song); // Reinsert with updated play count
		if (frequentlyPlayed.size() > 10) {
			frequentlyPlayed.poll(); // Keep only the top 10; .poll removes and returns head of queue
		}
	}
	
	public List<Song> getRecentlyPlayed() {
		return new ArrayList<>(recentlyPlayed);
	}
	
	public List<Song> getFrequentlyPlayed() {
		List<Song> sortedList = new ArrayList<>(frequentlyPlayed);
		sortedList.sort(Comparator.comparingInt(Song::getPlayCount).reversed());
		return sortedList;
	}
	
	// Save Play History before Exiting
	public void savePlayHistory() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("playHistory.dat"))) {
			out.writeObject(recentlyPlayed);
			out.writeObject(new ArrayList<>(frequentlyPlayed)); // Convert PriorityQueue to List before saving
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Load Play History When Application Starts
	@SuppressWarnings("unchecked")
	public void loadPlayHistory() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("playHistory.dat"))) {
			recentlyPlayed = ((LinkedList<Song>) in.readObject());
			frequentlyPlayed = new PriorityQueue<>(10, Comparator.comparingInt(Song::getPlayCount).reversed());
			frequentlyPlayed.addAll((List<Song>) in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("No previous play history found.");
		}
	}
	
	// Call this when exiting the program to save the history
	public void shutdown() {
		savePlayHistory();
	}
	
	// returns album with title 'title' if it exists, else returns null
	public Album getAlbumByTitle(String title){
		for (Album a : this.albums) {
			if (a.getTitle().toLowerCase().equals(title.toLowerCase())) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
					if (s.isFavorite()) {
						copySong.markFavorite();
					}
					copyTracklist.add(copySong);
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				return aCopy;
			}
		}
		
		return null;
	}
	
	// returns song with title 'title' if it exists, else returns null
	public Song getSongByTitle(String title) {
		for (Song s : this.songs) {
			if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {		
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				return copySong;
			}
		}
		
		return null;
	}
	
	// returns album list by artist if they exist else, returns null
	public ArrayList<Album> getAlbumsByArtist(String artist){
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for (Album a : this.albums) {
			if (a.getArtist().toLowerCase().equals(artist.toLowerCase())) {
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
		
		return searchedAlbums;
	}
	
	// returns song list by artist if they exist else, returns null
	public ArrayList<Song> getSongsByArtist(String artist){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		
		for (Song s : this.songs) {
			if (s.getArtist().toLowerCase().equals(artist.toLowerCase())) {
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
	
		return searchedSongs;
	}
	
	// add song by song title
	public void addSong(String songTitle) {
		MusicStore ms = new MusicStore();
		
		if (ms.getSongByTitle(songTitle).size() == 0) {
			return;
		}
		
		Song songToAdd = ms.getSongByTitle(songTitle).get(0);	
		
		// check if song exists
		if (songToAdd != null) {
			// add song, artist, and album to library
			
			if (getSongByTitle(songTitle) == null) {
				Song copySong = new Song(songToAdd.getTitle(), songToAdd.getArtist(), songToAdd.getAlbum(), songToAdd.getRating());
				this.songs.add(copySong);
			}
			
			if (!this.artists.contains(songToAdd.getArtist())) {
				this.artists.add(songToAdd.getArtist());
			}
			
			if (getAlbumByTitle(songToAdd.getAlbum()) == null) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				Album a = ms.getAlbumByTitle(songToAdd.getAlbum());
				for (Song s : a.getTracklist()) {
					Song copySongAlbum = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
					copyTracklist.add(copySongAlbum);
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				albums.add(aCopy);
			}	
		}	
	}
	
	// add album by album title. Also adds all the songs on the album
	public void addAlbum(String albumTitle) {
		MusicStore ms = new MusicStore();
		
		Album a = ms.getAlbumByTitle(albumTitle);
		
		// add song, artist, album to the library
		if (a != null) {
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			for (Song s : a.getTracklist()) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				copyTracklist.add(copySong);
				addSong(copySong.getTitle());
			}
			
			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
			
			if (getAlbumByTitle(aCopy.getTitle()) == null) {
				this.albums.add(aCopy);
			}	
			
			if (!this.artists.contains(aCopy.getArtist())) {
				this.artists.add(aCopy.getArtist());
			}
			
		}
		
	}
	
	// create a blank playlist
	public PlayList makePlaylist(String name) {
		for (PlayList p : this.playlists) {
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				return null;
			}
		}
		
		PlayList newPlayList = new PlayList(name);
		playlists.add(newPlayList);
		
		return newPlayList;
	}
	
	// return all songs
	public ArrayList<Song> getSongs() {
		ArrayList<Song> copyList = new ArrayList<Song>();
		for (Song s : this.songs) {	
			Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
			if (s.isFavorite()) {
				copySong.markFavorite();
			}
			copyList.add(copySong);
		}
		
		return copyList;	
	}
	
	// return all albums
	public ArrayList<Album> getAlbums() {
		ArrayList<Album> copyAlbums = new ArrayList<Album>();
		for (Album a : this.albums) {
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			for (Song s : a.getTracklist()) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				copyTracklist.add(copySong);
			}
			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
			copyAlbums.add(aCopy);
		}
		
		return copyAlbums;
	}
	
	// return all artists
	public ArrayList<String> getArtists() {
		ArrayList<String> artists = new ArrayList<String>();
		
		for (String a : this.artists) {
			artists.add(a);
		}
		
		return artists;
	}
	
	// return all favorite songs
	public ArrayList<Song> getFavorites() {
		ArrayList<Song> copyList = new ArrayList<Song>();
		for (Song s : this.songs) {	
			Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
			if (s.isFavorite()) {
				copySong.markFavorite();
				copyList.add(copySong);
			}
		}
		
		return copyList;
	}
	
	// return all playlists
	public ArrayList<PlayList> getPlaylists() {
		ArrayList<PlayList> copyList = new ArrayList<PlayList>();
		for (PlayList p : this.playlists) {
			ArrayList<Song> copyTracklist = new ArrayList<Song>();
			for (Song s : p.getSongs()) {
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				if (s.isFavorite()) {
					copySong.markFavorite();
				}
				copyTracklist.add(copySong);
			}
			PlayList pCopy = new PlayList(p.getName(), copyTracklist);
			copyList.add(pCopy);
		}
		
		return copyList;
	}
	
	// print song + title for every song in playlist
	public PlayList getPlayListByName(String name) {
		for (PlayList p : this.playlists) {
			if (p.getName().toLowerCase().equals(name.toLowerCase())) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : p.getSongs()) {
					Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
					if (s.isFavorite()) {
						copySong.markFavorite();
					}
					copyTracklist.add(copySong);
				}
				PlayList pCopy = new PlayList(p.getName(), copyTracklist);
				return pCopy;
			}
		}
		
		return null;
	}
	
	public boolean addSongToPlayList(String playlist, String song) {
		boolean retval = false;
		
		Song s = this.getSongByTitle(song);
		
		if (s == null) {
			return false;
		}
		
		for (PlayList p : this.playlists) {
			if (p.getName().toLowerCase().equals(playlist.toLowerCase())) {
				retval = true;
				Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				p.addSong(copySong);
			}
		}
		
		return retval;
	}
	
	public boolean removeSongFromPlayList(String playlist, String song) {
		boolean retval = false;
		
		Song s = this.getSongByTitle(song);
		
		if (s == null) {
			return false;
		}
		
		for (PlayList p : this.playlists) {
			if (p.getName().toLowerCase().equals(playlist.toLowerCase())) {
				p.removeSong(s);
				retval = true;
			}
		}
		
		return retval;
	}
	
	public void markAsFavorite(String title) {
		for (Song s : this.songs) {
			if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
				s.markFavorite();
			}
		}
	}
	
	public void rateSong(String title, int rating) {
		for (Song s : this.songs) {
			if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
				s.rate(rating);
				
			}
		}
	}
	
	// returns song list by genre if they exist else, returns null
	public ArrayList<Song> getSongsByGenre(String genre){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		MusicStore ms = new MusicStore();
		
		for (Song s : this.songs) {
			Album a = ms.getAlbumByTitle(s.getAlbum());
			if (a.getGenre().toLowerCase().equals(genre.toLowerCase())) {
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
	
		return searchedSongs;
	}

}
