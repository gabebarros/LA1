package main.database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.model.Album;

public class MusicStore {
	private ArrayList<Album> albumList;
	
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
			    
			    ArrayList<String> tracklist = new ArrayList<String>();
				
				while((line = reader.readLine()) != null) {
				    tracklist.add(line);
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
	
	public static void main(String[]args) {
		ArrayList<Album> albumList = constructAlbumList();
				
		for (Album a : albumList) {
			System.out.println(a.getTitle());
		}
	}
}
