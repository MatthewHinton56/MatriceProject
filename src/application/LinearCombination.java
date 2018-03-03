package application;

import java.util.ArrayList;

public class LinearCombination {

	private Vector[] vectors;
	private ArrayList<Vector> outputs;
	private int dimension;
	private int lowerBound, upperBound;
	
	
	public LinearCombination(Vector[] vectors) {
		this.vectors = vectors;
		dimension = vectors[0].getNumRows();
		outputs = new ArrayList<Vector>();
		lowerBound = -5;
		upperBound = 5;
		generateOutputs();
	}

	public LinearCombination(Vector[] vectors, int lowerBound, int upperBound) {
		this.vectors = vectors;
		dimension = vectors[0].getNumRows();
		outputs = new ArrayList<Vector>();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		generateOutputs();
	}

	private void generateOutputs() {
		int pos = 0;
		Fraction[] fractions = new Fraction[vectors.length];
		generateOutputs(pos,fractions);
	}

	private void generateOutputs(int pos, Fraction[] fractions) {
		if(pos == vectors.length) {
			Vector v = new Vector(dimension);
			for(int i = 0; i < vectors.length; i++) {
				//System.out.println(vectors[i] + ""+ fractions[i]);
				v = v.add(vectors[i].scale(fractions[i]));
			}	
			outputs.add(v);
		} else {
			for(int frac = lowerBound; frac <= upperBound; frac++) {
				Fraction scale = new Fraction(""+frac);
				fractions[pos] = scale;
				generateOutputs(pos+1, fractions);
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Vector v: vectors) {
			s += v.toString()+"\n";
		}
		s += outputs.toString();
		return s;
	}
	
	public Vector[] getVectors() {
		return vectors;
	}

	public ArrayList<Vector> getOutputs() {
		return outputs;
	}

	public int getDimension() {
		return dimension;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public static void main(String[] args) {
		Fraction[] frac1 = new Fraction[2];
		frac1[0] = Fraction.ONE;
		frac1[1] = Fraction.ZERO;
		Vector v1 = new Vector(frac1);
		Fraction[] frac2 = new Fraction[2];
		frac2[1] = Fraction.ONE;
		frac2[0] = Fraction.ZERO;
		Vector v2 = new Vector(frac2);
		Vector[] vectors = new Vector[2];
		vectors[0] = v1;
		vectors[1] = v2;
		LinearCombination comb = new LinearCombination(vectors);
		System.out.println(comb);
	}
	
	//2D bounds methods, allows for the creation of the 2D plane
	
	public Vector getMaxXY() {
		Vector max = outputs.get(0);
		for(Vector v: outputs) {
			if(v.vector[0].compareTo(max.vector[0]) >= 0 && v.vector[1].compareTo(max.vector[1]) >= 0)
				max = v;
		}
		return max;
	}
	
	public Vector getMaxXMinY() {
		Vector max = outputs.get(0);
		for(Vector v: outputs) {
			if(v.vector[0].compareTo(max.vector[0]) >= 0 && v.vector[1].compareTo(max.vector[1]) <= 0)
				max = v;
		}
		return max;
	}
	
	public Vector getMinXMaxY() {
		Vector max = outputs.get(0);
		for(Vector v: outputs) {
			if(v.vector[0].compareTo(max.vector[0]) <= 0 && v.vector[1].compareTo(max.vector[1]) >= 0)
				max = v;
		}
		return max;
	}
	
	public Vector getMinXY() {
		Vector max = outputs.get(0);
		for(Vector v: outputs) {
			if(v.vector[0].compareTo(max.vector[0]) <= 0 && v.vector[1].compareTo(max.vector[1]) <= 0)
				max = v;
		}
		return max;
	}
	
	public Vector[] getVectorEdges2D() {
		Vector v1 = getMaxXMinY();
		Vector v2 = getMaxXY();
		Vector v3 = getMinXMaxY();
		Vector v4 = getMinXY();
		Vector[] vec = {v1,v2,v3,v4};
		return vec;
	}
	
	
	
	
}
