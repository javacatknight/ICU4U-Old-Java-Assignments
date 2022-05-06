//Catherine Qu, 12/6
//Description: Finds the number of unique substrings within a given string. 
import java.io.*;
import java.util.*;

public class A {
	public static void main(String[] args) throws IOException {

		System.out.println("Finding the number of Substrings");
		BufferedReader fileIn = new BufferedReader(new FileReader("fileName.txt"));
		int x = Integer.parseInt(fileIn.readLine());
		int numSub;
		String string;
		//final long startTime = System.currentTimeMillis();
		
		for (int i = 0; i<x; i++) {
			numSub = 0;
			string  = fileIn.readLine();
			if (string.length() == 0) {
				System.out.println("String:\n No. of Substrings: 0");
				continue;
			} 
			
			Set <String> set = new HashSet <String>();
			//where n is the length; q is the number of times to go through   1...string.length()-1;
			for (int n = 1; n<string.length(); n++) {
				for (int index = 0; index <=string.length()-n; index++) {		
					set.add(string.substring(index, index+n));
				}
			}
			numSub = set.size() +2; 		//including "" and string
			System.out.println("String: "+ string + "\nNo. of Substrings: " + numSub);
			
		}
		fileIn.close();
//		final long endTime = System.currentTimeMillis();
//		System.out.println("Total execution time: " + (endTime - startTime));

	}
}