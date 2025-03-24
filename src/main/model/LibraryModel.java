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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import main.database.MusicStore;

public class LibraryModel implements Iterable<Song> {
	
	protected ArrayList<Song> songs;
	protected ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<PlayList> playlists;
	
	private PlayList recentlyPlayed; // Stores the last 10 songs played
	private PlayList frequentlyPlayed; // Stores the top 10 most played
	
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.playlists = new ArrayList<PlayList>();
		this.recentlyPlayed = new PlayList("Recently Played");
		this.frequentlyPlayed = new PlayList("Frequently Played");
		
		// add automatic playlists
		this.playlists.add(new PlayList("Favorite songs"));
		this.playlists.add(new PlayList("Top Rated"));
		this.playlists.add(this.recentlyPlayed);
		this.playlists.add(this.frequentlyPlayed);
		
	}
	
	public void playSong(String title) {
		Song song = getSongByTitle(title);
		if (song == null) {
			System.out.println("Song not found.");
			return;
		}
		
		song.play(); // Increase play count
		
		// Add to Recently Played
		if (recentlyPlayed.songInPlaylist(song.getTitle())) {
			recentlyPlayed.removeSong(song);
			recentlyPlayed.insertSong(song, 0);
		}
		else {
			recentlyPlayed.insertSong(song, 0);
		}
		
		if (recentlyPlayed.getSongs().size() > 10) {
			recentlyPlayed.removeLastSong(); // Keep only the first 10
		}
		
		// Add to Frequently Played
		frequentlyPlayed.updateFrequentlyPlayed(song);
	}
	
	public PlayList getRecentlyPlayed() {
		return recentlyPlayed;
	}
	
	public PlayList getFrequentlyPlayed() {
		return frequentlyPlayed;
	}
	
	/**
	 * Returns a list of songs sorted by the specified criterion and order.
	 * @param criterion: "title", "artist", or "rating"
	 * @param ascending True for ascending order, false for descending order
	 * @return A sorted list of songs.
	 */
	public List<Song> getSortedSongs(String criterion, boolean ascending) {
		List<Song> sortedSongs = new ArrayList<>(songs); // Copy list to avoid modifying original
		
		Comparator<Song> comparator;
		
		switch (criterion.toLowerCase()) {
			case "title":
				comparator = Comparator.comparing(Song::getTitle, String.CASE_INSENSITIVE_ORDER);
				break;
			case "artist":
				comparator = Comparator.comparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);
				break;
			case "rating":
				comparator = Comparator.comparingInt(Song::getRating);
				break;
			default:
				throw new IllegalArgumentException("Invalid sorting criterion. Use 'title', 'artist', or 'rating'.");
		}
		
		if (!ascending) {
			comparator = comparator.reversed(); // Reverse for descending order
		}
		
		sortedSongs.sort(comparator);
		return sortedSongs;
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
				return s;
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
			
			// check if we need to add to genre playlist
			String playlistName = (this.getAlbumByTitle(songToAdd.getAlbum()).getGenre() + " Playlist");
			String genreName = (this.getAlbumByTitle(songToAdd.getAlbum()).getGenre());
			
			if (this.getPlayListByName(playlistName) != null) {
				this.addSongToPlayList(playlistName, songToAdd.getTitle());
			}
			else {
				ArrayList<Song> genreSongs = new ArrayList<Song>();
				for (Song s : this.songs) {
					if (this.getAlbumByTitle(s.getAlbum()).getGenre().equals(genreName)){
						genreSongs.add(s);
					}
				}
				
				if (genreSongs.size() >= 10) {
					PlayList genrePL = new PlayList(playlistName);
					
					for (Song s : genreSongs) {
						Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
						
						genrePL.addSong(copySong);
					}
					
					this.playlists.add(genrePL);
				}
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
	
	/**
	 * Removes a song by title and artist.
	 * @param title The title of the song.
	 * @param artist The artist of the song.
	 * @return True if removed, false if not found.
	 */
	public boolean removeSong(String title, String artist) {
		Song songToRemove = null;
		for (Song song : songs)  {
			if (song.getTitle().equalsIgnoreCase(title) && song.getArtist().equalsIgnoreCase(artist)) {
				songToRemove = song;
				break;
			}
		}
		
		if (songToRemove != null) {
			songs.remove(songToRemove);
			
			// Check if the album is now empty
			for (Song song : songs) {
				if (song.getAlbum().equalsIgnoreCase(songToRemove.getAlbum())) {
					return true; // Album still has songs, no need to remove it
				}
			}
			
			// If no remaining songs are found, remove the album
			removeAlbum(songToRemove.getAlbum()); // Remove album if it's empty
			return true;
		}
			
		return false;	
	}
	
	/**
	 * Removes an album by title.
	 * @param title The title of the album.
	 * @return True if removed, false if not found.
	 * @param title
	 */
	public boolean removeAlbum(String title) {
		Album albumToRemove = null;
		for (Album album : albums) {
			if (album.getTitle().equalsIgnoreCase(title)) {
				albumToRemove = album;
				break;
			}
		}
		
		if (albumToRemove != null) {
			albums.remove(albumToRemove);
			songs.removeIf(song -> song.getAlbum().equalsIgnoreCase(title));
			return true;
		}
		return false;
	}
	
	public void markAsFavorite(String title) {
		for (Song s : this.songs) {
			if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
				s.markFavorite();
				
				// add to favorite songs if it isn't already
				if (!this.getPlayListByName("Favorite Songs").songInPlaylist(s.getTitle())) {
					this.addSongToPlayList("Favorite Songs", s.getTitle());
				}
			}
		}
	}
	
	public void rateSong(String title, int rating) {
		for (Song s : this.songs) {
			if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
				s.rate(rating);
				
				// add to playlists if it isn't already
				if (rating == 4 || rating == 5) {
					
					if (!this.getPlayListByName("Top Rated").songInPlaylist(s.getTitle())) {
						this.addSongToPlayList("Top Rated", s.getTitle());
					}
					
					if (rating == 5) {
						
						if (!this.getPlayListByName("Favorite Songs").songInPlaylist(s.getTitle())) {
							this.addSongToPlayList("Favorite Songs", s.getTitle());
						}
					}
				}
				
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
	
	/**
	 * Shuffles the songs in the library.
	 */
	public void shuffleSongs() {
		Collections.shuffle(songs);
	}
	
	/**
	 * Returns an iterator to allow for-each loops over shuffled songs.
	 */
	@Override
	public Iterator<Song> iterator() {
		List<Song> shuffledSongs = new ArrayList<>(songs);
		Collections.shuffle(shuffledSongs);
		return shuffledSongs.iterator();
	}
	
	/**
	 * Shuffles the songs in a specific playlist.
	 * @param playlistName The name of the playlist to shuffle.
	 */
	public void shufflePlayList(String playlistName) {
		for (PlayList p : playlists) {
			if (p.getName().equalsIgnoreCase(playlistName)) {
				p.shuffle();
				return;
			}
		}
		System.out.println("Playlist not found.");
	}

}
