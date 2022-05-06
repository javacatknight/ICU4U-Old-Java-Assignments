//Description: player objects each have a copy of 4 attributes: score, power, name, rank. together, the entire player class has one max/total rank.
public class Player implements Comparable <Player>{
	private int score;
	private String name;
	private String power;
	private int rank;
	static private int total;

	//constructor, returns nothing but creates an instance of the object with the initialized fields
	public Player (int score, String name, String power) {
		this.score = score;
		this.name = name;
		this.power = power;
	}
	
	//getter and setter methods that are public so they can be called to edit private/unviewable fields.
	public static void setTotal (int paraTotal) {
		total = paraTotal;
	}
	public void setRank (int rank) {
		this.rank = rank;
	}
	
	public int getScore () {
		return this.score;
	}
	public String getName() {
		return this.name;
	}
	public String getPower() {
		return this.power;
	}

	//Overrided class that returns all fields (formmated) as a string.
	public String toString () { 
		return String.format(" Name: %s%n Power: %s%n Score: %d%n Ranking: %d out of %d", this.name, this.power, this.score, this.rank, total);
	}

	//used to sort by score (which gives rank), implements comparable interface to use compareTo method
	//returns an int that signifies which object is bigger.
	@Override
	public int compareTo(Player x) {
		return x.score - this.score;
	}




}
