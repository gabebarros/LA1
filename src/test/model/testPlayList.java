/*
 * Class: testPlayList.java
 * 
 * This class contains the unit tests for the PlayList.java class.
 */
package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.model.PlayList;
import main.model.Song;

class testPlayList {

	@Test
	void testConstructorAndGetters() {
		PlayList testPL = new PlayList("Unspecified vibe");
		assertTrue(testPL.getName().equals("Unspecified vibe"));
		assertEquals(testPL.getSongs().size(), 0);
	}
	
	@Test
	void testAddSongs() {
		PlayList testPL = new PlayList("Unspecified vibe");
		
		Song track1 = new Song("Talk 2 me nice", "Bladee", "Icedancer");
		Song track2 = new Song("Juna", "Clairo", "Charm");
		Song track3 = new Song("TRUST", "JPEGMAFIA", "LP");
		
		track1.rate(5);
		
		testPL.addSong(track1);
		testPL.addSong(track2);
		testPL.addSong(track3);
		
		ArrayList<Song> tracklist = testPL.getSongs();
		
		assertTrue(tracklist.get(0).getTitle().equals("Talk 2 me nice"));
		assertTrue(tracklist.get(1).getTitle().equals("Juna"));
		assertTrue(tracklist.get(2).getTitle().equals("TRUST"));
	}
	
	@Test
	void testRemoveSongs() {
		PlayList testPL = new PlayList("Unspecified vibe");
		
		Song track1 = new Song("Talk 2 me nice", "Bladee", "Icedancer");
		Song track2 = new Song("Juna", "Clairo", "Charm");
		Song track3 = new Song("TRUST", "JPEGMAFIA", "LP");
		
		track1.rate(5);
		
		testPL.addSong(track1);
		testPL.addSong(track2);
		testPL.addSong(track3);
		
		testPL.removeSong(track3);
		
		ArrayList<Song> tracklist = testPL.getSongs();
		
		assertEquals(tracklist.size(), 2);
		
		assertTrue(tracklist.get(0).getTitle().equals("Talk 2 me nice"));
		assertTrue(tracklist.get(1).getTitle().equals("Juna"));
		
		PlayList testPL2 = new PlayList("Unspecified function");
		testPL2.removeSong(track3);
		
		ArrayList<Song> tracklist2 = testPL2.getSongs();
		assertEquals(tracklist2.size(), 0);

	}

}
