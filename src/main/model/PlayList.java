/*
 * Class: PlayList.java
 * 
 * This class represents one playlist in the musicStore / libraryModel. The class
 * contains two attributes: name and tracklist. This class contains a constructor, 
 * getters, and methods to add/remove songs to the playlist. If there are multiple
 * occurrences of a song in a playlist, the remove method will remove only one.
 */
package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlayList {
	
	private String name;
	private ArrayList<Song> songs;
	
	public PlayList(String name) {
		this.name = name;
		this.songs = new ArrayList<Song>();
	}
	
	public PlayList(String name, ArrayList<Song> songs) {
		this.name = name;
		this.songs = songs;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Song> getSongs() {
		ArrayList<Song> copy = new ArrayList<Song>();
		
		for (Song s : this.songs) {
			Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum());
			if (s.getRating() != 0) {
				copySong.rate(s.getRating());
			}
			
			int i = 0;
			while (i < s.getPlayCount()) {
				copySong.play();
				i++;
			}
			copy.add(copySong);
		}
		
		return copy;
	}
	
	public void addSong(Song s) {
		if (!songInPlaylist(s.getTitle())) { // Check if song is already in playlist
			Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum());
		
			if (s.getRating() != 0) {
				copySong.rate(s.getRating());
			}

			int i = 0;
			while (i < s.getPlayCount()) {
				copySong.play();
				i++;
			}
			
			this.songs.add(copySong);
		}
	}
	
	// inserts a song into a specific index of the playlist
	public void insertSong(Song s, int index) {
		if (!songInPlaylist(s.getTitle())) { // Check if song is already in playlist
			Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum());
		
			if (s.getRating() != 0) {
				copySong.rate(s.getRating());
			}
			
			int i = 0;
			while (i < s.getPlayCount()) {
				copySong.play();
				i++;
			}
			
			this.songs.add(index, copySong);
		}
	}
	
	// only removes 1 song if there are multiple copies
	public void removeSong(Song s) {
		for (Song cur : this.songs) {
			if (cur.getTitle().equals(s.getTitle())) {
				this.songs.remove(cur);
				break;
			}
		}
	}
	
	// removes the first song in the playlist
	public void removeFirstSong() {
		if (this.songs.size() > 0) {
			this.songs.remove(0);
		}
	}
	
	// removes the last song in the playlist
	public void removeLastSong() {
		if (this.songs.size() > 0) {
			this.songs.remove(this.songs.size()-1);
		}
	}
	
	/**
	 * given a song, calculates the top 10 most frequently played and removes any extra
	 * only used for the frequently played playlist
	 */
	public void updateFrequentlyPlayed(Song song) {
		// play song if song is already in playlist
		if (this.songInPlaylist(song.getTitle())) {
			for (Song s : this.songs) {
				if (s.getTitle().equals(song.getTitle())) {
					s.play();
				}
			}
		}
		else {
			this.addSong(song);
		}
		
		this.songs.sort(Comparator.comparingInt(Song::getPlayCount).reversed()); // Reinsert with updated play count
		
		
		if (this.songs.size() > 10) {
			this.removeLastSong(); // Keep only the top 10
		}
	}
	
	/**
	 * Shuffles the songs in the PlayList.
	 */
	public void shuffle() {
		Collections.shuffle(songs);
	}
	
	// checks if a song is in the playlist. returns true/false
	public boolean songInPlaylist(String title) {
		for (Song s : this.songs) {
			if (s.getTitle().equals(title)) {
				return true;
			}
		}
		
		return false;
	}
}