//description: sorts players by name. used in sort and in search.
import java.util.Comparator;
public class sortByName implements Comparator <Player> {

	@Override
	public int compare(Player x1, Player x2) {
		return x1.getName().toLowerCase().compareTo(x2.getName().toLowerCase());
	}

}
