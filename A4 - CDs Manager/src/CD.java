//DESCRIPTION: CD object, contains title, songs, number of songs, and a total amount of time
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CD {
	private String title;
	private int numberOfSongs;
	private ArrayList<Song> songList = new ArrayList<Song>(); // had to be initialized -> default constructor does not create it
	private Time totalTime = new Time("00:00");

// CONSTRUCTORS
	// 1.3 -> add a CD, 2 parameters
	public CD(String title, int numberOfSongs) {
		this.title = title;
		this.numberOfSongs = numberOfSongs;
	}

	
	// 1.5 -> copy of a cd. songs list and time object should not be linked.
	public CD(CD original) {
		this.title = "Copy" + original.title;
		this.numberOfSongs = original.numberOfSongs;
		this.songList = new ArrayList<Song>(original.songList); // copies arrayList
		this.totalTime = new Time(original.totalTime); //creates new time object
	}

	// 1.6 - > subcopy of cd. adds songs between the indexes, and adds/changes the total time 
	public CD(CD original, int start, int end) {
		this.title = "Sub" + original.title;
		this.numberOfSongs = end - start + 1;

		for (int i = start; i <= end; i++) {
			this.songList.add(original.songList.get(i));
			this.totalTime.changeTime(this.totalTime, original.songList.get(i).getTime(), 1);
		}
	}


//METHODS:
//GETTERS AND SETTERS
	
	// 1.7, 2.2, 2.4, 2.5 -> description: lets other classes change the entire song list / returns: the songlist of the cd
	public ArrayList<Song> getSongList() {
		return this.songList;
	}
	public String getTitle() {
		return this.title;
	}

	public int getNumSongs() {
		return this.numberOfSongs;
	}

	// 2.4 -> remove a song, decrease the number
	public void decNumSongs() {
		this.numberOfSongs--;
	}

//OVERLOADED, for 2.4 -> remove song
	
	//Parameters: index of song to be removed
	//Description: decreases the totaltime, then removes the song from songlist
	public void removeSongs(int start) {
		totalTime.changeTime(totalTime, songList.get(start).getTime(), -1);
		this.songList.remove(start);
	}

	// 2.4 -> remove by title
	// Description: Takes in a string for the title to search by, sorts list, removes through binary search
	public void removeSongs(BufferedReader stdIn) {
		System.out.println("Song title: ");
		String h = "";
		try {
			h = stdIn.readLine();
			h = h.toLowerCase();
		} catch (IOException e) {
		}
		// sort by title
		Collections.sort(songList);/// binSearch uses compareTo anyways
		int found = Collections.binarySearch(songList, new Song(h, null, null, 0, new Time("00:00")));

		// remove by all titles.
		if (found < 0) {
			System.out.println("NOT FOUND.");
		} else {
			int left = found, right = found;
			// binary search only finds one occurrence, not all. check left and right.
			while (left - 1 >= 0) {
				if (songList.get(left - 1).getTitle().equalsIgnoreCase(h)) {
					left--;
				} else {
					break;
				}
			}
			while (right + 1 < songList.size()) {
				if (songList.get(right + 1).getTitle().equalsIgnoreCase(h)) {
					right++;
				} else {
					break;
				}
			}

			for (int i = 0; i <= right - left; i++) {
				totalTime.changeTime(totalTime, songList.get(left).getTime(), -1);
				songList.remove(left);
			}
		}
	}

	

	// 1.2 -> display cd information
	public String toString() {
		return String.format("CD Title: %s%nNumber of Songs: %d%nList of Songs: %n%s%nTotal time: %s", title,
				numberOfSongs, this.listSongTitles(), totalTime.timeToString());
	}

	// 1.3, 2.3 => add song to cd, update time by adding the song's time
	public void addSong(Song newSong) {
		this.songList.add(newSong);
		totalTime.changeTime(totalTime, newSong.getTime(), 1);
	}

	// 1.7, 2.1, 2.4
	// returns string of all the song titles
	public String listSongTitles() {
		String titles = "";
		for (int i = 0; i < songList.size(); i++) {
			titles += (i + 1) + ") " + (songList.get(i)).getTitle() + "\n";
		}
		return titles;
	}

	// 2.3
	//description: adds a song by entering in all 5 fields (checks valid options) 
	public void addSong(BufferedReader stdIn) {
		String title = null;
		String artist = null;
		String genre = null;
		int rating = 0;
		int timeSeconds = 0;

		int stringChoice = 1;

		for (int i = 0; i <= 4; i++) {
			while (true) {
				try {
					if (stringChoice == 1)
						System.out.println("Song title:");
					else if (stringChoice == 2)
						System.out.println("Artist:");
					else if (stringChoice == 3)
						System.out.println("Genre:");
					else if (stringChoice == 4)
						System.out.println("Rating:");
					else
						System.out.println("Time in seconds:");

					String x = stdIn.readLine();
					if (stringChoice == 1)
						title = x;
					else if (stringChoice == 2)
						artist = x;
					else if (stringChoice == 3)
						genre = x;
					else if (stringChoice == 4) {
						rating = Integer.parseInt(x);
						if (rating <= 0 || rating > 5)
							throw new NumberFormatException();
					} else {
						timeSeconds = Integer.parseInt(x);
						if (timeSeconds <= 0)
							throw new NumberFormatException();
					}
					stringChoice++;
					break;

				} catch (NumberFormatException | IOException e) {
					System.out.println("Invalid, re-enter.");
				}
			}

		}
		this.songList.add(new Song(title, artist, genre, rating, new Time(timeSeconds)));

	}

}
