//description: sorts songs by artists, alpha order - > parameters = 2 songs
//implementing comparator -> compare method must be implemented
// returns an int, which is used in sorting order
import java.util.Comparator;
public class SortByTime implements Comparator <Song> {

	@Override
	public int compare(Song song1, Song song2) {
		return song1.getTime().timeToInt()-(song2.getTime()).timeToInt();
	}

}
