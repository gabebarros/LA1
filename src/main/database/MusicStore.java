/*
 * Class: MusicStore.java
 * 
 * This class parses the given txt files and creates all the corresponding
 * album and song objects. Other classes can interact with this class by
 * using the provided methods to get album by title/artist and get song by
 * title/artist.
 */
package main.database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.model.Album;
import main.model.Song;

public class MusicStore {
	protected ArrayList<Album> albumList;
	
	public MusicStore() {
		ArrayList<Album> albumList = constructAlbumList();
		
		this.albumList = albumList;
	}
	
	/*
	 * This method parses the txt files in the albums.txt folder, creates Album
	 * and Song objects based on these, and returns the entire list of albums.
	 */
	private static ArrayList<Album> constructAlbumList() {
		File albums = new File("albums/albums.txt");  // hold albums.txt
		BufferedReader reader = null;
		
		// set reader to albums.txt
		try {
			reader = new BufferedReader(new FileReader(albums));
		} catch (FileNotFoundException e) {
			System.out.println("albums.txt could not be found");
            System.exit(-1);
		}
		
		String line = null;  // content of one line of the albums file
		ArrayList<String> albumFileNameList = new ArrayList<String>();
		
		// read each line of albums.txt and construct a list of the album file names
        try {
			while((line = reader.readLine()) != null) {
			    String albumName = line.split(",")[0];  // extract album title
			    String artist = line.split(",")[1];  // extract artist
			    
			    String albumFileName = albumName + "_" + artist + ".txt";
			    
			    albumFileNameList.add(albumFileName);
			}
		} catch (IOException e) {
			System.out.println("Error reading from albums.txt");
            System.exit(-1);
		}
        
        ArrayList<Album> albumList = new ArrayList<Album>();  // list of album objects
        
        // construct each album object and add it to albumList
        for (String albumFileName : albumFileNameList) {
        	File curAlbumFile = new File("albums/" + albumFileName);
        	
        	try {
				reader = new BufferedReader(new FileReader(curAlbumFile));
				
				String firstLine = reader.readLine();
				String albumName = firstLine.split(",")[0];  
			    String artist = firstLine.split(",")[1]; 
			    String genre = firstLine.split(",")[2];  
			    int year = Integer.parseInt(firstLine.split(",")[3]); 
			    
			    ArrayList<Song> tracklist = new ArrayList<Song>();
				
				while((line = reader.readLine()) != null) {
				    tracklist.add(new Song(line, artist, albumName));
				}
				
				Album curAlbum = new Album(albumName, artist, genre, year, tracklist);
				albumList.add(curAlbum);
				
			} catch (FileNotFoundException e) {
				System.out.println("File " + albumFileName + " could not be found");
				System.exit(-1);
			} catch (IOException e) {
                System.out.println("Error reading file: " + albumFileName);
                System.exit(-1);
            }
        }
        
        return albumList;
	}
	
	/*
	 * This method returns the album with the given title, if it exists. If not,
	 * it returns null.
	 */
	public Album getAlbumByTitle(String title){
		for (Album a : this.albumList) {
			if (a.getTitle().toLowerCase().equals(title.toLowerCase())) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				return aCopy;
			}
		}
		
		return null;
	}
	
	/*
	 * This method returns the song(s) with the given title, if it exists. If not,
	 * it returns an empty arraylist.
	 */
	public ArrayList<Song> getSongByTitle(String title){
		ArrayList<Song> songList = new ArrayList<Song>();
		for (Album a : this.albumList) {
			for (Song s : a.getTracklist()) {
				if (s.getTitle().toLowerCase().equals(title.toLowerCase())) {
					songList.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
			}
		}
		
		return songList;
	}
	
	/*
	 * This method returns the album(s) by the given artist, if any exist. If not,
	 * it returns null.
	 */
	public ArrayList<Album> getAlbumsByArtist(String artist){
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for (Album a : this.albumList) {
			if (a.getArtist().toLowerCase().equals(artist.toLowerCase())) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
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
	
	/*
	 * This method returns the songs(s) by the given artist, if any exist. If not,
	 * it returns null.
	 */
	public ArrayList<Song> getSongsByArtist(String artist){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		for (Album a : this.albumList) {
			for (Song s : a.getTracklist()) {
				if (s.getArtist().toLowerCase().equals(artist.toLowerCase())) {
					searchedSongs.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
			}
		}
		
		if (searchedSongs.size() == 0) {
			return null;
		}
		
		return searchedSongs;
	}
	
}
