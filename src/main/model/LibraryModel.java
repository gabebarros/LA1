package main.model;

import java.util.ArrayList;

import main.database.MusicStore;

public class LibraryModel {

	public static void main(String[] args) {
		MusicStore ms = new MusicStore();
		ArrayList<Album> albumlist = ms.getAlbumList();
		
		for (Album a : albumlist) {
			System.out.println(a.getTracklist().get(0).getAlbum());
		}

	}

}
