package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class PlayList {
	
	private String name;
	private ArrayList<Song> songs;
	
	public PlayList(String name) {
		this.name = name;
		this.songs = new ArrayList<Song>();
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
	
}
