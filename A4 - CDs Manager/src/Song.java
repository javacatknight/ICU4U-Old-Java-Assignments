//DESCRIPTION: song has 5 fields; songs can be added within a cd, or a cd can be added to the cdlist and songs are added to the cd first
//Comparable interface allows sort by title as default
public class Song implements Comparable <Song>{
	private String title;
	private String artist;
	private String genre;
	private int rating;
	private Time length;
	
//CONSTRUCTORS:
	//1.3, 2.3 (CD method calls this constructor) -> 
	//Description: add a new song
	//Parameters: 1.3 adds from file, 2.3 adds all fields one by one
	//Returns song so it can be added to the CD
	public Song (String title, String artist, String genre, int rating, Time length) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.rating = rating;
		this.length = length;
	}
	
	//1.6 -> for copyCD, all fields of the new (returned) cd are copied
	public Song (Song original) {
		this.title = original.title;
		this.artist = original.artist;
		this.genre = original.genre;
		this.rating = original.rating;
		this.length = original.length;
	}
	
//GETTERS/SETTERS
	public String getTitle() {
		return this.title;	
	}
	
	//2.4 & something else
	public Time getTime() {
		return this.length;
	}
	//2.5
	public String getArtist() {
		return this.artist;
	}
	
//OTHER METHODS:
	//2.2 -> prints song's fields, returns a string
	public String toString() {
		return String.format("Song Title: %s%nArtist: %s%nGenre:%s%nRating:%d%nTime:%s ", this.title, this.artist, this.genre, this.rating, this.length.timeToString());
	}

	
	
	//1.7, sort by title, 2.5 collections.sort uses these
	//returns
	@Override
	public int compareTo(Song x) {
		return this.title.toLowerCase().compareTo(x.title.toLowerCase());

	}
	//1.7, after you sort, compare the titles to be equal or not.
		@Override
		public boolean equals(Object obj) {
			Song second = (Song) obj;
			if (this.title.toLowerCase().equals(second.title.toLowerCase()))
				return true;
			return false;
		}
	



}


