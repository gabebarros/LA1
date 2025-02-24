package test.database;

import main.model.Album;
import main.model.Song;
import main.database.MusicStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Need to test printSong and printAlbum for 90% coverage
class MusicStoreTest {
	private MusicStore musicStore;
	private Album mockAlbum1;
	private Album mockAlbum2;
	private Song mockSong1;
	private Song mockSong2;
	
	// Subclass to override the file-based album loading
	class TestMusicStore extends MusicStore {
		protected TestMusicStore(ArrayList<Album> albums) {
			this.albumList = albums; // Directly setting the album list
		}
	}
	
	@BeforeEach
	void setup() {
		// Mock album and song data
		mockSong1 = new Song("Song A", "Artist X", "Album 1");
		mockSong2 = new Song("Song B", "Artist X", "Album 1");
		
		ArrayList<Song> tracklist1 = new ArrayList<>();
		tracklist1.add(mockSong1);
		tracklist1.add(mockSong2);
		
		mockAlbum1 = new Album("Album 1", "Artist X", "Pop", 2020, tracklist1);
		mockAlbum2 = new Album("Album 2", "Artist Y", "Rock", 2018, new ArrayList<>());
		
		ArrayList<Album> mockAlbumList = new ArrayList<>();
		mockAlbumList.add(mockAlbum1);
		mockAlbumList.add(mockAlbum2);
		
		musicStore = new TestMusicStore(mockAlbumList);
	}

	@Test
	void testGetAlbumByTitle_Found() {
		Album result = musicStore.getAlbumByTitle("Album 1", false);
		result = musicStore.getAlbumByTitle("Album 1", true);
		assertNotNull(result);
		assertEquals("Album 1", result.getTitle());
		assertEquals("Artist X", result.getArtist());
	}
	
	@Test
	void testGetAlbumByTitle_NotFound() {
		Album result = musicStore.getAlbumByTitle("Nonexistent Album", false);
		result = musicStore.getAlbumByTitle("Nonexistent Album", true);
		assertNull(result);
	}
	
	@Test
	void testGetSongByTitle_Found() {
		Song result = musicStore.getSongByTitle("Song A", false);
		result = musicStore.getSongByTitle("Song A", true);
		assertNotNull(result);
		assertEquals("Song A", result.getTitle());
		assertEquals("Artist X", result.getArtist());
	}
	
	@Test
	void testGetSongByTitle_NotFound() {
		Song result = musicStore.getSongByTitle("Nonexistent Song", false);
		result = musicStore.getSongByTitle("Nonexistent Song", true);
		
		assertNull(result);
	}
	
	@Test
	void testGetAlbumsByArtist_Found() {
		ArrayList<Album> albums = musicStore.getAlbumsByArtist("Artist X", false);
		albums = musicStore.getAlbumsByArtist("Artist X", true);
		assertNotNull(albums);
		assertEquals(1, albums.size());
		assertEquals("Album 1", albums.get(0).getTitle());
	}
	
	@Test
	void testGetAlbumsByArtist_NotFound() {
		ArrayList<Album> albums = musicStore.getAlbumsByArtist("Unknown Artist", false);
		albums = musicStore.getAlbumsByArtist("Unknown Artist", true);
		assertNull(albums);
	}
	
	@Test 
	void testGetSongsByArtist_Found() {
		ArrayList<Song> songs = musicStore.getSongsByArtist("Artist X", false);
		songs = musicStore.getSongsByArtist("Artist X", true);
		assertNotNull(songs);
		assertEquals(2, songs.size());
		assertEquals("Song A", songs.get(0).getTitle());
		assertEquals("Song B", songs.get(1).getTitle());
	}
	
	@Test
	void testGetSongsByArtist_NotFound() {
		ArrayList<Song> songs = musicStore.getSongsByArtist("Unknown Artist", false);
		songs = musicStore.getSongsByArtist("Unknown Artist", true);
		assertNull(songs);
	}	
}