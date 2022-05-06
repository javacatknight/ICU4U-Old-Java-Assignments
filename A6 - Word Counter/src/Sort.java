import java.util.Comparator;

public class Sort implements Comparator <Word> {

	@Override
	public int compare(Word o1, Word o2) {
		// TODO Auto-generated method stub
		Word w1 = (Word) o1;
		Word w2 = (Word) o2;
		
		if (w1.getFrequency() < w2.getFrequency())
			return -1;
		else if (w1.getFrequency() > w2.getFrequency())
			return 1;
		else
			return 0;
		
	}

}
