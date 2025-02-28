package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> songs;
	private ArrayList<Album> albums;
	private ArrayList<String> artists;
	private ArrayList<Song> favorites;
	private ArrayList<PlayList> playlists;
	
	public LibraryModel() {
		this.songs = new ArrayList<Song>();
		this.albums = new ArrayList<Album>();
		this.artists = new ArrayList<String>();
		this.favorites = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
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
				return aCopy;
			}
		}
		
		return null;
	}
	
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
	
	public void addSong(String songTitle) {
		MusicStore ms = new MusicStore();
		
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

}
