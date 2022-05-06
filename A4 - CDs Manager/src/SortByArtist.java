//description: sorts songs by artists, alpha order -> parameters must be 2 songs
	//implementing comparator -> compare method must be implemented
// returns an int, which is used in sorting order
import java.util.Comparator;
public class SortByArtist implements Comparator <Song> {

	public int compare(Song song1, Song song2) {
		return song1.getArtist().compareTo(song2.getArtist());
	}

}
