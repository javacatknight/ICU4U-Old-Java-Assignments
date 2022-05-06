//Description: Word object has two fields, and when a new <String, Word> object is added when the word already exists, the value/Word.frequency is updated
public class Word implements Comparable<Word> {
	private String word;
	private int frequency = 1;

	public Word(String word) {
		this.word = word;
	}

	//getters are used for the display
	public int getFrequency() {
		return this.frequency;
	}

	public String getWord() {
		return this.word;
	}
	
	//new key/value pair gets updated frequency
	public void addFrequency(int x) {
		this.frequency = x + 1;
	}

	// hashmap uses the equals methods but actually i only used containsKey (string) and so this is unnecessary aha
	// word objects are the same if the string word is the same.
//	@Override
//	public boolean equals(Object o) {
//		Word secondWord = (Word) o;
//		if (this.word.equals(secondWord.word)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	// used for treeset sorting;
	@Override
	public int compareTo(Word o) {
		if (this.frequency < o.frequency)
			return 1;
		else //if (this.frequency > o.frequency)
			return -1;
		//if frequency is the same, it doesn't matter
		
	}


}
