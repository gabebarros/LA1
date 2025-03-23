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

import main.database.MusicStore;

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
			copy.add(copySong);
		}
		
		return copy;
	}
	
	public void addSong(Song s) {
		Song copySong = new Song(s.getTitle(), s.getArtist(), s.getAlbum());
		
		if (s.getRating() != 0) {
			copySong.rate(s.getRating());
		}
			
		this.songs.add(copySong);
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
	
	/**
	 * Shuffles the songs in the PlayList.
	 */
	public void shuffle() {
		Collections.shuffle(songs);
	}
	
	public boolean songInPlaylist(String title) {
		for (Song s : this.songs) {
			if (s.getTitle().equals(title)) {
				return true;
			}
		}
		
		return false;
	}
}