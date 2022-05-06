import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//Description: Determines the number of reduced fractions between 0 and 1 when given input is the lowest possible denominator.
//Additionally gives the number of reduced fractions between the interval of [lower bounds, higherbounds] inclusive
public class B {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));
		
		//Program does not end:
		while (true) {
			System.out.println("Enter the maximum denominator: " );
			int d = Integer.parseInt(stdin.readLine());
			System.out.println("Enter the lower limit: " );
			String low = stdin.readLine();
			System.out.println("Enter the upper limit: " );
			String high = stdin.readLine();
			

			SortedSet<Fraction> tree = new TreeSet <Fraction> (); //how come TreeSet<> tree = new TreeSet also works?
			for (int i = 2; i<= d; i++) {
				for (int q = 1; q<i; q++) {
					tree.add(new Fraction(q,i));
				}
			}
			
			tree.add(new Fraction(0,1));
			tree.add(new Fraction(1,1));
			System.out.println("Total number of fractions: " + tree.size());
			
			tree = tree.subSet(new Fraction (low), new Fraction(high));
			System.out.println("Number of Fractions between " + low + " and " + high + " inclusive: " + (tree.size() + 1)); //if inclusive, technically should +1 but i followed the example
			
		}
	}
}