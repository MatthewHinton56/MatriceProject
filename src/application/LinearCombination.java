package application;

import java.util.ArrayList;

public class LinearCombination {

	private Vector[] vectors;
	private Vector[] basis;
	private ArrayList<Vector> outputs;
	private int dimension;
	private int lowerBound, upperBound;
	
	
	public LinearCombination(Vector[] vectors) {
		this.vectors = vectors;
		dimension = vectors[0].getNumRows();
		outputs = new ArrayList<Vector>();
		lowerBound = -5;
		upperBound = 5;
		basis = colA(vectors);
		if(dimension <= 3)
		generateOutputs();
	}

	public LinearCombination(Vector[] vectors, int lowerBound, int upperBound) {
		this.vectors = vectors;
		dimension = vectors[0].getNumRows();
		outputs = new ArrayList<Vector>();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		if(dimension <= 3)
		generateOutputs();
	}

	private void generateOutputs() {
		int pos = 0;
		Fraction[] fractions = new Fraction[basis.length];
		generateOutputs(pos,fractions);
	}

	private void generateOutputs(int pos, Fraction[] fractions) {
		if(pos == basis.length) {
			Vector v = new Vector(dimension);
			for(int i = 0; i < basis.length; i++) {
				v = v.add(basis[i].scale(fractions[i]));
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
	
	public boolean linearIndepenent() {
		Matrix m = new Matrix(this.vectors, false);
		m.ref();
		int index = 0;
		boolean independent = true;
		while(independent && index < m.pivotCols.length) {
			if(!m.pivotCols[index]) {
				independent = false;
			}
			index++;
		}
		return independent;
		}

	public Vector evaluate(Vector v) {
		Vector ret = new Vector(v.getNumRows());
		for(int i = 0; i < v.getNumRows(); i++) {
			ret = ret.add(this.vectors[i].scale(v.vector[i]));
		}
		return ret;
	}

	public String[] generateInputForC(Vector v) {
		Matrix mat = new Matrix(this.vectors,false);
		Matrix aug = Matrix.augment(mat, v);
		aug.rref();
		if(aug.noSolution) {
			String[] augCol = new String[aug.matrixRow];
			for(int row = 0; row < augCol.length; row++) {
				augCol[row] = "NaN";
			}
			return augCol;
		}
		
		return aug.getAugColumn();
	}

	public boolean isCinRange(Vector v) {
		Matrix mat = new Matrix(this.vectors,false);
		mat = Matrix.augment(mat, v);
		mat.rref();
		return !mat.noSolution;
	}
	//Implemented to reduce bigO of output generation from X^Y with X being the bounds of scalar values, and Y being the amount of vectors
	//to X^V with V being the dimension.
	public static Vector[] colA(Vector[] vectors) {
		Matrix m  = new Matrix(vectors, false);
		m.ref();
		int pivotCount = 0;
		for(boolean b: m.pivotCols)
			pivotCount += (b) ? 1 : 0;
		Vector[] col = new Vector[pivotCount];
		int pos = 0;
		for(int i = 0; i < m.pivotCols.length; i++) {
			if(m.pivotCols[i]) {
				col[pos] = vectors[i];
				pos++;
			}
		}
		return col;
	}
 	
	
	
}
