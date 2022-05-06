//description: time object, holds time of song or cd in two fields: minutes or seconds; may be converted to string, or to int, can be changed/set
public class Time {
	private int minutes;
	private int seconds;

//CONSTRUCTORS
	//1.3 -> file reads in a string
	public Time(String minuteSecond) {
		this.minutes = Integer.parseInt(minuteSecond.substring(0,minuteSecond.indexOf(":")));
		this.seconds = Integer.parseInt(minuteSecond.substring(minuteSecond.indexOf(":")+1));
	}
	
	//1.5 -> copy a CD, return/create a new time object with the same fields
	public Time (Time time) {
		this.minutes = time.minutes;
		this.seconds = time.seconds;
	}

	//2.3 -> when you create a song, you enter seconds as parameter
	public Time (int seconds) {
		this.minutes = seconds/60;
		this.seconds = seconds%60;
	}
	
	
//OTHER METHODS:
	//1.2, 2.2 -> to help when CD.toString() and time.toString()
	//description: converts time to string
	public String timeToString() {
		return	this.minutes + ":" + this.seconds;
	}
	
	//1.3, 1.6 & 2.5 (collections.sort/SortByTime)
	public int timeToInt () {
		return seconds + minutes*60;
	}
	
	//1.3, 1.6 
	//parameters: sign (1, -1) determines if you're adding (add song) or subtracting (remove song) time from song/cd
	//return: none, variable is changed
	//description: find time in seconds (int), sub/add opperation, and then reassign the totalTime variable.
	public void changeTime(Time totalTime, Time songTime, int sign) {
		int timeTotalSeconds = totalTime.timeToInt();
		int timeSongSeconds = songTime.timeToInt();
		if (sign == 1) {
			timeTotalSeconds = timeTotalSeconds + timeSongSeconds;
		} else {
			timeTotalSeconds = timeTotalSeconds - timeSongSeconds;
		}
		
		totalTime.minutes = timeTotalSeconds/60;
		totalTime.seconds = timeTotalSeconds%60;

	}
	
}
