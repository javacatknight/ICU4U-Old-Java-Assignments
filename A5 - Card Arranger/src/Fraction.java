//Fraction class that implements comparable so treeset can sort it

public class Fraction implements Comparable<Fraction> {
	private double num;
	private double den;

	//Constructor 1: used for the given input
	public Fraction(String x) {
		num = Integer.parseInt(x.substring(0, x.indexOf("/")));
		den = Integer.parseInt(x.substring(x.indexOf("/") + 1));
	}

	//Constructor 2 was optional but used to create all fraction objects which were stored in set
	public Fraction(int num, int den) {
		this.num = num;
		this.den = den;
	}

	public double getNum() {
		return num;
	}

	public double getDen() {
		return den;
	}

// Test code for me to be able to see problems by printing set.	
//	public String toString () {
//		return num + "/" + den;
//	}
	
	//Parameters: one other fraction
	//Return: int, which is used in sorting
	//Description, fulfills comparable contract; returns a value based on the comparison of two fractions; that value is used for sorting.
	@Override
	public int compareTo(Fraction b) {
		if (this.num/this.den < b.num/b.den) {
			return -1;
	} else if (this.num/this.den == b.num/b.den){
		return 0;
	} else {
		return 1;
	}
		}
	}
