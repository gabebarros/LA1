package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.model.Song;

class testSong {

	@Test
	void testConstructor1() {
		Song s = new Song("Teardrop", "Massive Attack", "Mezzanine");
		assertTrue(s.getTitle().equals("Teardrop"));
		assertTrue(s.getArtist().equals("Massive Attack"));
		assertTrue(s.getAlbum().equals("Mezzanine"));
	}
	
	@Test
	void testConstructor2() {
		Song s = new Song("Teardrop", "Massive Attack", "Mezzanine", 5);
		assertTrue(s.getTitle().equals("Teardrop"));
		assertTrue(s.getArtist().equals("Massive Attack"));
		assertTrue(s.getAlbum().equals("Mezzanine"));
		assertEquals(s.getRating(), 5);
		assertTrue(s.isFavorite());
		
		Song s2 = new Song("Angel", "Massive Attack", "Mezzanine", 2);
		assertTrue(s2.getTitle().equals("Angel"));
		assertTrue(s2.getArtist().equals("Massive Attack"));
		assertTrue(s2.getAlbum().equals("Mezzanine"));
		assertEquals(s2.getRating(), 2);
		assertFalse(s2.isFavorite());
	}
	
	@Test
	void testRate(){
		Song s = new Song("Teardrop", "Massive Attack", "Mezzanine");
		s.rate(5);
		assertEquals(s.getRating(), 5);
		assertTrue(s.isFavorite());
		
		s.rate(1);
		assertEquals(s.getRating(), 1);
		assertFalse(s.isFavorite());
		
		s.rate(0);
		s.rate(6);
	}
	
	@Test
	void testFavorite(){
		Song s = new Song("Teardrop", "Massive Attack", "Mezzanine");
		assertFalse(s.isFavorite());
		
		s.markFavorite();
		
		assertTrue(s.isFavorite());
	}


}
