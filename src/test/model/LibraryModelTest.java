/*
 * Class: LibraryModelTest.java
 * 
 * This class contains the unit tests for the LibraryModel.java class.
 */
package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.model.Album;
import main.model.LibraryModel;
import main.model.Song;
import main.model.PlayList;

import java.util.ArrayList;

//import org.junit.jupiter.api.Test;

class LibraryModelTest {
	private LibraryModel library;
	private Album mockAlbum1;
	private Album mockAlbum2;
	private Song mockSong1;
	private Song mockSong2;
	
	// Subclass to set initial test data
	class TestLibraryModel extends LibraryModel {
		protected TestLibraryModel(ArrayList<Album> albums, ArrayList<Song> songs) {
			this.albums = albums;
			this.songs = songs;
		}
	}

	@BeforeEach
	void setUp() {
		// Create mock songs
		mockSong1 = new Song("Song A", "Artist X", "Album 1", 5);
		mockSong2 = new Song("Song B", "Artist X", "Album 1", 4);

		ArrayList<Song> tracklist1 = new ArrayList<>();
		tracklist1.add(mockSong1);
		tracklist1.add(mockSong2);

		mockAlbum1 = new Album("Album 1", "Artist X", "Pop", 2022, tracklist1);
		mockAlbum2 = new Album("Album 2", "Artist Y", "Rock", 2018, new ArrayList<>());
		
		ArrayList<Album> mockAlbumList = new ArrayList<>();
		mockAlbumList.add(mockAlbum1);
		mockAlbumList.add(mockAlbum2);
		
		ArrayList<Song> mockSongList = new ArrayList<>();
		mockSongList.add(mockSong1);
		mockSongList.add(mockSong2);
		
		// Using test subclass to initialize the library with predefined data
		library = new TestLibraryModel(mockAlbumList, mockSongList);
	}

	@Test
	void testGetAlbumByTitle_Found() {
		Album foundAlbum = library.getAlbumByTitle("Album 1");
		assertNotNull(foundAlbum);
		assertEquals(mockAlbum1.getTitle(), foundAlbum.getTitle());
	}

	@Test
	void testGetAlbumByTitle_NotFound() {
		Album notFoundAlbum = library.getAlbumByTitle("Unknown Album");
		assertNull(notFoundAlbum);
	}

	@Test
	void testGetSongByTitle_Found() {
		Song foundSong = library.getSongByTitle("Song A");
		assertNotNull(foundSong);
		assertEquals(mockSong1.getTitle(), foundSong.getTitle());
	}

	@Test
	void testGetSongByTitle_NotFound() {
		Song notFoundSong = library.getSongByTitle("Unknown Song");
		assertNull(notFoundSong);
	}

	@Test
	void testGetAlbumsByArtist_Found() {
		ArrayList<Album> artistAlbum = library.getAlbumsByArtist("Artist X");
		assertNotNull(artistAlbum);
		assertEquals(1, artistAlbum.size());
		assertEquals(mockAlbum1.getTitle(), artistAlbum.get(0).getTitle());
	}
	
	@Test
	void testGetAlbumsByArtist_NotFound() {
		ArrayList<Album> artistAlbum = library.getAlbumsByArtist("Unknown Artist");
		assertNull(artistAlbum);
	}
	
	@Test
	void testGetSongsByArtist_Found() {
		ArrayList<Song> artistSongs = library.getSongsByArtist("Artist X");
		assertNotNull(artistSongs);
		assertEquals(2, artistSongs.size());
		assertEquals(mockSong1.getTitle(), artistSongs.get(0).getTitle());
	}
	
	@Test
	void testGetSongsByArtist_NotFound() {
		ArrayList<Song> artistSongs = library.getSongsByArtist("Unknown Artist");
		assertNull(artistSongs);
	}
	
	@Test 
	void testAddAlbum_Success() {
		library.addAlbum("Tapestry");
		assertNotNull(library.getAlbumByTitle("Tapestry"));
		
		System.out.println(library.getAlbumByTitle("Tapestry").getTitle());
	}
	
	@Test
	void testAddAlbum_Not_Found() {
		library.addAlbum("Album 3");
		assertNull(library.getAlbumByTitle("Album 3"));
	}
	
	@Test
	void testMakePlaylist_Success() {
		PlayList newPlaylist = library.makePlaylist("Test Playlist");
		assertNotNull(newPlaylist);
		assertEquals("Test Playlist", newPlaylist.getName());
	}
	
	@Test
	void testMakePlaylist_Duplicate() {
		library.makePlaylist("Test Playlist");
		PlayList duplicatePlaylist = library.makePlaylist("Test Playlist");
		assertNull(duplicatePlaylist);
	}
	
	@Test
	void testMarkAsFavorite() {
		library.markAsFavorite("Song A");
		ArrayList<Song> favorites = library.getFavorites();
		assertNotNull(favorites);
		assertEquals(1, favorites.size());
		assertEquals("Song A", favorites.get(0).getTitle());
	}
	
	@Test
	void testRateSong() {
		library.rateSong("Song A", 3);
		Song ratedSong = library.getSongByTitle("Song A");
		assertEquals(3, ratedSong.getRating());
	}
	
	@Test
	void testGetArtists() {
		library.addAlbum("Tapestry");
		ArrayList<String> artists = library.getArtists();
		assertNotNull(artists);
		assertEquals(1, artists.size());
		assertTrue(artists.contains("Carol King"));
	}
	
	@Test
	void testGetSongs() {
		ArrayList<Song> songs = library.getSongs();
		assertNotNull(songs);
		assertEquals(2, songs.size());
		assertEquals(mockSong1.getTitle(), songs.get(0).getTitle());
		assertEquals(mockSong2.getTitle(), songs.get(1).getTitle());
	}
	
	@Test
	void testGetAlbums() {
		ArrayList<Album> albums = library.getAlbums();
		assertNotNull(albums);
		assertEquals(2, albums.size());
		assertEquals(mockAlbum1.getTitle(), albums.get(0).getTitle());
		assertEquals(mockAlbum2.getTitle(), albums.get(1).getTitle());
	}
	
	@Test
	void testGetPlaylists() {
		library.makePlaylist("Workout Mix");
		library.makePlaylist("Study Mix");
		
		ArrayList<PlayList> playlists = library.getPlaylists();
		assertNotNull(playlists);
		assertEquals(4, playlists.size());
	}
	
	@Test
	void testGetPlaylistsByName_Found() {
		library.makePlaylist("Study Mix");
		PlayList foundPlaylist = library.getPlayListByName("Study Mix");
		assertNotNull(foundPlaylist);
		assertEquals("Study Mix", foundPlaylist.getName());
	}
	
	@Test
	void testGetPlaylistsByName_NotFound() {
		PlayList foundPlaylist = library.getPlayListByName("Unknown Playlist");
		assertNull(foundPlaylist);
	}
	
	@Test
	void testGetFavorites() {

		library.markAsFavorite("Song A");
		ArrayList<Song> favorites = library.getFavorites();
		assertNotNull(favorites);
		assertEquals(1, favorites.size());
		assertEquals("Song A", favorites.get(0).getTitle());
	}
	
	@Test
	void testAddSongToPlaylistSuccess() {
		library.makePlaylist("Study Mix");
		boolean added = library.addSongToPlayList("Study Mix", "Song A");
		assertTrue(added);
	}
	
	@Test
	void testAddSongToPlaylist_NotFound() {
		boolean added = library.addSongToPlayList("Study Mix", "Unknown Song");
		assertFalse(added);
	}
	
	@Test
	void testRemoveSongFromPlaylist_Success() {
		library.makePlaylist("Study Mix");
		library.addSongToPlayList("Study Mix", "Song A");
		boolean removed = library.removeSongFromPlayList("Study Mix", "Song A");
		assertTrue(removed);
	}
	
	@Test
	void testRemoveSongFromPlaylist_NotFound() {
		boolean removed = library.removeSongFromPlayList("Study Mix", "Unknown Song");
		assertFalse(removed);
	}
	
	@Test
	void testGetSongsByGenre_Success() {
		LibraryModel lib = new LibraryModel();
		
		lib.addSong("tapestry");
		lib.addSong("beautiful");
		lib.rateSong("tapestry", 5);
		ArrayList<Song> rock_songs = lib.getSongsByGenre("Rock");
		
		assertEquals(rock_songs.size(), 2);
	}
	
	@Test
	void testGetSongsByGenre_NoSongs() {
		LibraryModel lib = new LibraryModel();
		
		lib.addSong("tapestry");
		lib.addSong("beautiful");
		
		ArrayList<Song> pop_songs = lib.getSongsByGenre("Pop");
		
		assertNull(pop_songs);
	}
	
	@Test
	void testPlaySong() {
		LibraryModel lib = new LibraryModel();
		// Add 10+ songs and play them for full coverage
		lib.addSong("tapestry"); // valid song
		lib.playSong("tapestry");
		lib.addSong("Politik"); 
		lib.playSong("Politik"); 
		lib.addSong("In My Place");
		lib.playSong("In My Place");
		lib.addSong("The Scientist");
		lib.playSong("The Scientist");
		lib.addSong("Clocks");
		lib.playSong("Clocks");
		lib.addSong("Daylight");
		lib.playSong("Daylight");
		lib.addSong("Green Eyes");
		lib.playSong("Green Eyes");
		lib.addSong("A Whisper");
		lib.playSong("A Whisper");
		lib.addSong("A Rush of Blood to the Head");
		lib.playSong("A Rush of Blood to the Head");
		lib.addSong("Amsterdam");
		lib.playSong("Amsterdam");
		lib.addSong("Warning Sign");
		lib.playSong("Warning Sign");
		lib.playSong("Warning Sign");
		
		lib.addSong("blanket");  // invalid song
		lib.playSong("blanket");
		
		lib.getRecentlyPlayed();
		lib.getFrequentlyPlayed();
		lib.shutdown(); // Need user login data for full coverage (load/save)
		
	}
	
	@Test
	void testSortingSongs() {
		library.printSortedSongs("title", true);
		library.printSortedSongs("artist", false);
		library.printSortedSongs("rating", true);
	}
	
	@Test
	void testRemoveSongAndAlbum() {
		library.removeSong("Song A", "Artist X");
		library.removeAlbum("Album 1");
	}
	
	@Test
	void testShuffle() {
		library.shuffleSongs();
		library.iterator();
		PlayList newPlaylist = library.makePlaylist("Test Playlist");
		PlayList anotherPlaylist = library.makePlaylist("Cool Playlist");
		library.shufflePlayList("Test Playlist");
		library.shufflePlayList("PlayList"); // Playlist not found
	}
	
}