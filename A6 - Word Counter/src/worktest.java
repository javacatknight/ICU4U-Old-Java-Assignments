
public class worktest implements Comparable <worktest>{
	private String word;
	private int frequency = 1;

	public worktest  (String word) {
		this.word = word;
	}
	
	public int getFrequency () {
		return this.frequency;
	}
	public String getWord() {
		return this.word;
	}
	public int compareTo (worktest b) {
		if (this.word.equals(b.word)) {
			//not sure if this is correct this.frequnecy++ but in theory the large frequency means this is added to the treeset;
			//this.frequency++;
			b.frequency++;
			return 0;
		} else if (this.frequency <b.frequency) {
			return -1;			
		} else {
			return 1;
		}
	}
}
