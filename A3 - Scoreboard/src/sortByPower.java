//description: sorts by power, used in sort and in binary search (telling it how the object is sorted).
import java.util.Comparator;
public class sortByPower implements Comparator <Player> {

	@Override
	public int compare(Player x1, Player x2) {
		return x1.getPower().toLowerCase().compareTo(x2.getPower().toLowerCase());
	}

}