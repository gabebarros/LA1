package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Album;
import main.model.LibraryModel;
import main.model.Song;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

//import org.junit.jupiter.api.Test;

class LibraryModelTest {
	private LibraryModel library;
	private Song testSong;
	private Album testAlbum;
	
	@BeforeEach
	void setUp() {
		library = new LibraryModel();
		
		ArrayList<Song> tracklist = new ArrayList<>();
		testSong = new Song("Test Song", "Test Artist", "Test Album", 5);
		tracklist.add(testSong);
		
		testAlbum = new Album("Test Album", "Test Artist", "Pop", 2022, tracklist);
		library.addAlbum("Test Album");
		
	}

	@Test
	void testGetAlbumByTitle_Found() {
		Album foundAlbum = library.getAlbumByTitle("Test Album");
		assertNotNull(foundAlbum);
		assertEquals(testAlbum, foundAlbum.getTitle());
	}
	
	@Test
	void testGetAlbumByTitle_NotFound() {
		Album notFoundAlbum = library.getAlbumByTitle("Unknown Album");
		assertNull(notFoundAlbum);
	}
	
	@Test
	void testGetSongByTitle_Found() {
		Song foundSong = library.getSongByTitle("Test Song");
		assertNotNull(foundSong);
		assertEquals(testSong, foundSong.getTitle());
	}
	
	@Test
	void testGetSongByTitle_NotFound() {
		Song notFoundSong = library.getSongByTitle("Unknown Song");
		assertNull(notFoundSong);
	}
	
	@Test
	void testGetAlbumsByArtist_Found() {
		ArrayList<Album> artistAlbum = library.getAlbumsByArtist("Test Artist");
		assertNotNull(artistAlbum);
	}

}
