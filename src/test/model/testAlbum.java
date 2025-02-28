/*
 * Class: testAlbum.java
 * 
 * This class contains the unit tests for the Album.java class.
 */
package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.model.Album;
import main.model.Song;

class testAlbum {

	@Test
	void testConstructorAndGetters() {
		ArrayList<Song> tracklist = new ArrayList<Song>();
		
		Song track1 = new Song("Peroxide", "ecco2k", "E");
		Song track2 = new Song("CC", "ecco2k", "E");
		Song track3 = new Song("Blue Eyes", "ecco2k", "E");
		tracklist.add(track1);
		tracklist.add(track2);
		tracklist.add(track3);
		
		Album e = new Album("E", "ecco2k", "Drain", 2019, tracklist);
		
		assertTrue(e.getTitle().equals("E"));
		assertTrue(e.getArtist().equals("ecco2k"));
		assertTrue(e.getGenre().equals("Drain"));
		assertEquals(e.getYear(), 2019);
	}
	
	@Test
	void testGetTracklist() {
		ArrayList<Song> tracklist = new ArrayList<Song>();
		
		Song track1 = new Song("Peroxide", "ecco2k", "E");
		Song track2 = new Song("CC", "ecco2k", "E");
		Song track3 = new Song("Blue Eyes", "ecco2k", "E");
		tracklist.add(track1);
		tracklist.add(track2);
		tracklist.add(track3);
		
		Album e = new Album("E", "ecco2k", "Drain", 2019, tracklist);
		
		ArrayList<Song> tracklistTest = e.getTracklist();
		
		assertTrue(tracklistTest.get(0).getTitle().equals("Peroxide"));
		assertTrue(tracklistTest.get(1).getTitle().equals("CC"));
		assertTrue(tracklistTest.get(2).getTitle().equals("Blue Eyes"));
	}

}
