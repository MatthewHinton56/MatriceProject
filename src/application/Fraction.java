package application;

import java.math.BigDecimal;

public class Fraction implements Comparable<Fraction>{

	public static boolean printModeFraction = true;
	public static final Fraction ONE = new Fraction("1");
	public static final Fraction ZERO = new Fraction("0");
	public static final Fraction NEGATIVEONE = new Fraction("-1");
	final long numerator;
	final long denominator;
	
	public Fraction(String input) {
		if(input.equals("0/0") || !isValid(input)) {
			numerator = 1;
			denominator = 1;
		} else if(input.indexOf("/") == -1 && input.indexOf(".") == -1) {
			numerator = Long.parseLong(input);
			denominator = 1;
		} else if(input.indexOf(".") > -1) {
			String split[] = input.split("\\."); 
			int b = split[1].length(); 
			 long denominatorTemp = (long) Math.pow(10, b); 
			long numeratorTemp = (long) (Double.parseDouble(input) * denominatorTemp); 											
			long gcd = getGCD(denominatorTemp, numeratorTemp); 
			if(numeratorTemp < 0 && denominatorTemp < 0) {
				numeratorTemp *= -1;
				denominatorTemp *= -1;
			} else if(denominatorTemp < 0) {
				numeratorTemp *= -1;
				denominatorTemp *= -1;
			}
			denominator = denominatorTemp/gcd;
			numerator = numeratorTemp/gcd;
		} else {
			String split[] = input.split("/"); 
			 long denominatorTemp = Long.parseLong(split[1]);
			long numeratorTemp = Long.parseLong(split[0]);										
			long gcd = getGCD(denominatorTemp, numeratorTemp); 
			if(numeratorTemp < 0 && denominatorTemp < 0) {
				numeratorTemp *= -1;
				denominatorTemp *= -1;
			} else if(denominatorTemp < 0) {
				numeratorTemp *= -1;
				denominatorTemp *= -1;
			}
			denominator = denominatorTemp/gcd;
			numerator = numeratorTemp/gcd;
			
		}
		
	}
	
	private static boolean isValid(String input) {
		if(input.length() == 0)
			return false;
		int slashCount = 0;
		int dotCount = 0;
		for(int i = 0; i < input.length();i++) {
			char c = input.charAt(i);
			if(c == '.')
				dotCount++;
			if(c == '/')
				slashCount++;
			if(i == 0) {
				if(!isNumberZero(c)) 
					return false;	
			} else {
				if(!isNumberNonZero(c))
					return false;
			}
		}
		if(slashCount > 1)
			return false;
		if(dotCount > 1)
			return false;
		return !((dotCount == 1) && (slashCount == 1));
	}
	
	public static boolean isNumberNonZero(char c) {
		return c >= 46 && c <= 57;
	}
	
	public static boolean isNumberZero(char c) {
		return c >= 45 && c <= 57;
	}

	private Fraction(long numerator, long denominator) {
		this(numerator, denominator, true);
	}
	
	
	//reduce controlled
	private Fraction(long numerator, long denominator, boolean b) {
		if(b) {
			long gcd = getGCD(denominator, numerator); 
			denominator = denominator/gcd;
			numerator = numerator/gcd;
		}
		if(numerator < 0 && denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		} else if(denominator < 0) {
			numerator *= -1;
			denominator *= -1;
		}
		
		
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public static long getGCD(long n1, long n2) {
		n1 = Math.abs(n1);
		n2 = Math.abs(n2);
		if (n2 == 0) {
			return n1;
		}
		return getGCD(n2, n1 % n2);
	}

	public static long getLCM(long n1, long n2) {
		n1 = Math.abs(n1);
		n2 = Math.abs(n2);
		return (n1 * n2)/getGCD(n1,n2);
	}
	

	@Override
	public int compareTo(Fraction o) {
		Fraction frac = fixSign(this.subtract(o));
		if(frac.numerator < 0)
			return -1;
		if(frac.numerator > 0)
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		Fraction frac = fixSign((Fraction) obj);
		return this.numerator == frac.numerator && this.denominator == frac.denominator;
	}


	@Override
	public String toString() {
		if(denominator == 1)
			return numerator+"";
		
		if(printModeFraction) {
			return numerator+ "/"+denominator;
		}
		double numerator = (double)this.numerator;
		return "" + (numerator/denominator);
	}


	public Fraction multiply(Fraction fraction) {
		return fixSign(new Fraction(this.numerator * fraction.numerator, this.denominator * fraction.denominator));
	}


	public Fraction subtract(Fraction subtract) {
		long lcm = getLCM(this.denominator, subtract.denominator);
		Fraction frac1 = fixSign(this.scale(lcm/this.denominator));
		Fraction frac2 = fixSign(subtract.scale(lcm/subtract.denominator));
		return  fixSign(new Fraction(frac1.numerator - frac2.numerator,lcm));
	}


	public Fraction add(Fraction add) {
		long lcm = getLCM(this.denominator, add.denominator);
		Fraction frac1 = fixSign(this.scale(lcm/this.denominator));
		Fraction frac2 = fixSign(add.scale(lcm/add.denominator));
		return new Fraction(frac1.numerator + frac2.numerator, lcm);
	}


	public Long remainder(Fraction leading) {
		long lcm = getLCM(this.denominator, leading.denominator);
		Fraction frac1 = fixSign(this.scale(lcm/this.denominator));
		Fraction frac2 = fixSign(leading.scale(lcm/leading.denominator));
		
		return frac1.numerator % frac2.numerator;
	}


	public Fraction divide(Fraction divisor) {
		return this.multiply(divisor.reciprocal());
	}


	public Fraction abs() {
		return fixSign(new Fraction(Math.abs(numerator), Math.abs(denominator)));
	}
	
	public Fraction scale(long scale) {
		return fixSign(new Fraction(this.numerator * scale, this.denominator * scale, false));
	}
	
	public Fraction reciprocal() {
		return fixSign(new Fraction(this.denominator, this.numerator));
	}
	
	public static void main(String[] args) {
	}
	
	private static Fraction fixSign(Fraction frac) {
		if(frac.numerator < 0 && frac.denominator < 0) {
			return new Fraction(Math.abs(frac.numerator), Math.abs(frac.denominator),false);
		} else if(frac.numerator < 0) {
			return new Fraction(frac.numerator, frac.denominator,false);
		} else if(frac.denominator < 0) {
			return new Fraction(frac.numerator * -1, frac.denominator * -1,false);
		} else {
			return new Fraction(frac.numerator , frac.denominator ,false);
		}
	}
	
	public double getDecimal() {
		return ((double)numerator)/denominator;
	}
	
}
