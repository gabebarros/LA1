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
	
//	public ArrayList<Album> getAlbumList(){
//		ArrayList<Album> copyAlbumList = new ArrayList<Album>();
//		
//		for (Album a : this.albumList) {
//			ArrayList<Song> copyTracklist = new ArrayList<Song>();
//			for (Song s : a.getTracklist()) {
//				copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
//			}
//			Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
//			copyAlbumList.add(aCopy);
//		}
//		
//		return copyAlbumList;
//	}
//	
	public Album getAlbumByTitle(String title, boolean print){
		for (Album a : this.albumList) {
			if (a.getTitle().equals(title)) {
				ArrayList<Song> copyTracklist = new ArrayList<Song>();
				for (Song s : a.getTracklist()) {
					copyTracklist.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
				
				Album aCopy = new Album(a.getTitle(), a.getArtist(), a.getGenre(), a.getYear(), copyTracklist);
				if (print) {
					printAlbum(aCopy);
				}
				return aCopy;
			}
		}
		
		System.out.println("No album with this title");
		return null;
	}
	
	public Song getSongByTitle(String title, boolean print){
		Song retval = null;
		for (Album a : this.albumList) {
			for (Song s : a.getTracklist()) {
				if (s.getTitle().equals(title)) {
					if (print) {
						printSong(s);
					}
					
					retval = new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating());
				}
			}
		}
		
		if (retval != null) {
			return retval;
		}
		
		System.out.println("No song with this title");
		return null;
	}
	
	public ArrayList<Album> getAlbumsByArtist(String artist, boolean print){
		ArrayList<Album> searchedAlbums = new ArrayList<Album>();
		for (Album a : this.albumList) {
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
			if (print) {
				printAlbum(a);
			}
		}
		return searchedAlbums;
	}
	
	public ArrayList<Song> getSongsByArtist(String artist, boolean print){
		ArrayList<Song> searchedSongs = new ArrayList<Song>();
		for (Album a : this.albumList) {
			for (Song s : a.getTracklist()) {
				if (s.getArtist().equals(artist)) {
					searchedSongs.add(new Song(s.getTitle(), s.getArtist(), s.getAlbum(), s.getRating()));
				}
			}
		}
		
		if (searchedSongs.size() == 0) {
			System.out.println("No songs by this artist");
			return null;
		}
		
		for (Song s : searchedSongs) {
			if (print) {
				printSong(s);
			}
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
}
