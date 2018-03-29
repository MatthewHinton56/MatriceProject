package application;

import java.util.Arrays;

public class MatrixFunction {

	final Matrix matrix;
	final boolean onto;
	final boolean oneToOne;
	final int domain;
	final int codomain;

	public MatrixFunction(Matrix mat) {
		matrix = (Matrix)mat.clone();
		mat.rref();
		boolean[] pivotRows = mat.pivotRows;
		boolean[] pivotCols = mat.pivotCols;
		System.out.println(Arrays.toString(pivotCols));
		boolean ontoTemp = true;
		int index = 0;
		while(ontoTemp && index < pivotRows.length) {
			if(!pivotRows[index]) {
				ontoTemp = false;
			}
			index++;
		}
		onto = ontoTemp;
		boolean oneToOneTemp = true;
		index = 0;
		while(oneToOneTemp && index < pivotCols.length) {
			if(!pivotCols[index]) {
				oneToOneTemp = false;
			}
			index++;
		}
		oneToOne = oneToOneTemp;
		domain = mat.matrixCol;
		codomain = mat.matrixRow;
	}

	public MatrixFunction(Fraction[][] mat) {
		this(new Matrix(mat, false));
	}

	//Takes in a vector and applies the transformation
	public Vector evaluate(Vector v) {
		Vector output = new Vector(codomain);
		for(int row = 0; row < matrix.matrixRow; row++)
			for(int col = 0; col < matrix.matrixCol; col++)
				output.vector[row] = output.vector[row].add(matrix.matrix[row][col].multiply(v.vector[col]));
		return output;
	}

	//determines if there exists an input for c such that Ax = c
	public boolean isCinRange(Vector c) {
		Matrix aug = Matrix.augment(matrix, c);
		aug.rref();
		return !aug.noSolution;
	}
	//Sets all free variables to 0 to generate possible input
	public String[] generateInputForC(Vector c) {
		Matrix aug = Matrix.augment(matrix, c);
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

}
