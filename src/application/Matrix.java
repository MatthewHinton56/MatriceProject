package application;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Matrix {

	private static final Fraction NEGATIVEONE = new Fraction("-1");
	final Fraction[][] matrix, L, U;
	final int matrixRow;
	final int matrixCol;
	final boolean[] pivotRows;
	final boolean[] pivotCols;
	boolean ref;
	boolean rref;
	final boolean augmented;
	//No solution flag set true if matrix is augmented and the form 0 0 0 C where is a non zero value exists.
	boolean noSolution;
	private boolean LU;

	//Constructor Section
	public Matrix(int rows, int cols,boolean augmented) {
		matrix = new Fraction[rows][cols];
		U = new Fraction[rows][cols];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
		for(int row = 0; row < rows; row++) 
			for(int col = 0; col < cols; col++) {
				matrix[row][col] = Fraction.ZERO;
				U[row][col] = Fraction.ZERO;
			}
		L = new Fraction[rows][rows];
		allValuesZero(L);
		
	}

	public Matrix(Fraction[][] mat,boolean augmented) {
		matrix = new Fraction[mat.length][mat[0].length];
		U = new Fraction[mat.length][mat[0].length];
		for(int row = 0;row<matrix.length;row++) {
			System.arraycopy(mat[row], 0, matrix[row], 0, mat[row].length);
			System.arraycopy(mat[row], 0, U[row], 0, mat[row].length);
		}
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
		L = new Fraction[matrixRow][matrixRow];
		allValuesZero(L);
	}

	public Matrix(Vector[] vectors,boolean augmented) {
		matrix = new Fraction[vectors[0].vector.length][vectors.length];
		U = new Fraction[vectors[0].vector.length][vectors.length];
		for(int row = 0;row<matrix.length;row++)
			for(int col = 0;col<matrix[0].length;col++) {
				matrix[row][col] = vectors[col].vector[row];
				U[row][col] = vectors[col].vector[row];
			}
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
		L = new Fraction[matrixRow][matrixRow];
		allValuesZero(L);
	}

	//System of equations constructor
	public Matrix(String system,int totalVariables, int amountOfEquations) {
		matrix = new Fraction[amountOfEquations][totalVariables+1];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		augmented = true;
		for(int row = 0;row<matrix.length;row++)
			for(int col = 0;col<matrix[0].length;col++)
				matrix[row][col] = Fraction.ZERO;
		Scanner systemProcessor = new Scanner(system);
		for(int equationCounter = 0; equationCounter < amountOfEquations; equationCounter++) {
			String equation = systemProcessor.nextLine();
			//System.out.println(equation);
			String[] variables = equation.split(" [+=] ");
			for(int variable = 0;variable < variables.length-1;variable++) {
				String var = variables[variable];
				if(var.indexOf("-") == 0 && var.indexOf("X") == 1)
					var = "-1"+var.substring(1);
				if(var.indexOf("X") == 0)
					var = "1"+var;
				int col = Integer.parseInt(var.substring(var.indexOf("X")+1));
				matrix[equationCounter][col-1] = new Fraction(var.substring(0, var.indexOf("X")));
			}
			matrix[equationCounter][matrixCol-1] = new Fraction(variables[variables.length-1]);
		}
		L = new Fraction[matrixRow][matrixRow];
		U = matrix.clone();
	}


	//Elementary Row Operations

	public void swap(int row1, int row2){
		Fraction[] temp = new Fraction[matrixCol];
		System.arraycopy(matrix[row1], 0, temp, 0, matrixCol);
		System.arraycopy(matrix[row2], 0, matrix[row1], 0, matrixCol);
		System.arraycopy(temp, 0, matrix[row2], 0, matrixCol);
	}

	public void scalarMultipication(int row, Fraction scale) {
		for(int col = 0; col < matrixCol; col++) {
			matrix[row][col] = matrix[row][col].multiply(scale);
		}
	}

	public void scalarDivision(int row, Fraction divisor) {
		for(int col = 0; col < matrixCol; col++){
			matrix[row][col] = matrix[row][col].divide(divisor);
		}
	}
	
	

	//Adds row2 scaled by scale to row1
	public void addRows(int row1, int row2, Fraction scale) {
		for(int col = 0; col < matrixCol; col++){
			matrix[row1][col] = matrix[row1][col].add(matrix[row2][col].multiply(scale));
		}

	}
	
	public static void addRows(Fraction[][] matrix,int matrixCol, int row1, int row2, Fraction scale) {
		for(int col = 0; col < matrixCol; col++){
			matrix[row1][col] = matrix[row1][col].add(matrix[row2][col].multiply(scale));
		}	

	}
	
	public static void swap(Fraction[][] matrix, int matrixCol, int row1, int row2){
		Fraction[] temp = new Fraction[matrixCol];
		System.arraycopy(matrix[row1], 0, temp, 0, matrixCol);
		System.arraycopy(matrix[row2], 0, matrix[row1], 0, matrixCol);
		System.arraycopy(temp, 0, matrix[row2], 0, matrixCol);
	}
	
	public static void allValuesZero(Fraction[][] mat) {
		for(int row = 0; row < mat.length; row++) 
			for(int col = 0; col < mat[0].length; col++) {
				mat[row][col] = Fraction.ZERO;
			}
	}
	
	

	public String toString(){
		StringBuilder builder = new StringBuilder();//uses string builder for efficency
		String format = "%1$"+maxLength()+"s";
		for(int row = 0;row<matrix.length;row++){
			builder.append("|");
			for(int col = 0;col<matrix[0].length;col++)
			{

				builder.append(String.format(format,matrix[row][col]));
				if(col != matrix[0].length-1)
					builder.append(" ");
			}
			builder.append("|\n");
		}
		return builder.toString(); // alter as necessary
	}

	private int maxLength() //calculates the maximum length needed for the toString
	{
		int maxLength = 0;
		for(int row = 0;row<matrix.length;row++)
			for(int col = 0;col<matrix[0].length;col++)
			{
				int x = ("" + matrix[row][col].toString()).length();
				if(x>maxLength)maxLength=x;

			}
		return maxLength+1;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter rows:");
		int rows = scan.nextInt();
		System.out.print("Enter cols:");
		int cols = scan.nextInt();
		scan.nextLine();
		Fraction[][] mat = new Fraction[rows][cols];
		for(int row = 0;row<mat.length;row++)
        	for(int col = 0;col<mat[0].length;col++) {
        		System.out.print("Enter next value: ");
        		String s = scan.nextLine();
        		mat[row][col] = new Fraction(s);
        }
		Matrix matrix = new Matrix(mat, false);
		//System.out.println(matrix);
		//Matrix.determinant((Matrix)matrix.clone());
		System.out.println(matrix);
		matrix.rref();
		System.out.println(matrix);
		
	}

	public Fraction[][] getL() {
		return L.clone();
	}

	public Fraction[][] getU() {
		return U.clone();
	}

	//return 0 if not divisible by leading value, otherwise returns th value of the leading number
	public Fraction rowDivisibleByLeadingValue(int row, int leadingPos) {
		Fraction leading = matrix[row][ leadingPos];
		if(leading.equals(Fraction.ZERO))
			return Fraction.ZERO;
		for(int col =  leadingPos+1; col < matrixCol; col++) {
			if(matrix[row][col].remainder(leading).doubleValue() != 0)
				return Fraction.ZERO;
		}
		return leading;
	}
	public boolean leadingValueIsOne(int row, int col) {
		return matrix[row][col].equals(Fraction.ONE);
	}

	public boolean impossibleRow(int row) {
		for(int col = 0;col < matrixCol-1; col++) {
			if(!matrix[row][col].equals(Fraction.ZERO))
				return false;
		}
		return !matrix[row][matrixCol-1].equals(Fraction.ZERO);
	}

	public boolean allZeroCol(int col, int start) {
		for(int row = start; row < matrixRow; row++){
			if(!matrix[row][col].equals(Fraction.ZERO))
				return false;
		}
		return true;
	}

	public int leadingEntryPosition(int row) {
		for(int col = 0; col < matrixCol;col++) {
			if(!matrix[row][col].equals(Fraction.ZERO))
				return col;
		}
		return -1;
	}
	
	public static int firstNonZeroValue(Fraction[][] mat, int matrixRow, int col, int start) {
		for(int row = start; row < matrixRow; row++ ) {
			if(!mat[row][col].equals(Fraction.ZERO)) 	
				return row;
		}
		return -1;
	}

	public void ref() {
		int matrixCol = (this.augmented) ? this.matrixCol - 1 : this.matrixCol;
		for(int row = 0; row < this.matrixRow; row++)
			if(impossibleRow(row) && augmented)
			{
				noSolution = true;
			}
		if(!ref && !noSolution){
			int numPivots = 0;
			for(int col = 0; col < matrixCol; col++) {
				if(!allZeroCol(col,numPivots)) {
					int pivotRow = -1;
					if(pivotRow == -1) {
						int row = numPivots;
						while(pivotRow == -1) {
							if(!matrix[row][col].equals(Fraction.ZERO)) {
								Fraction divisor = matrix[row][col];
								pivotRow = row;
								//scalarDivision(pivotRow,divisor);
							}
							row++;
						}
					}
					if(pivotRow != numPivots)swap(pivotRow, numPivots);
					pivotRow = numPivots;

					for(int row = pivotRow + 1;row < matrixRow;row++) {
						addRows(row,pivotRow,matrix[row][col].multiply(NEGATIVEONE).divide(matrix[numPivots][col]));
						if(augmented && impossibleRow(row)) {
							
							noSolution = true;
							break;
						}
					}
					if(noSolution)
						break;
					pivotRows[pivotRow] = true;
					pivotCols[col] = true;
					numPivots++;
				}
			}
		}
		ref = true;	
	}

	public void rref() {
		if(!rref) {
			ref();
			for(int row = matrixRow - 1; row >= 0; row--) {
				int leadingEntry = leadingEntryPosition(row);
				if(leadingEntry != -1) {
					Fraction divisor = matrix[row][leadingEntry];
					if(!divisor.equals(Fraction.ONE))scalarDivision(row,divisor);
					for(int rowsAbove = row - 1; rowsAbove >= 0; rowsAbove--) {
						addRows(rowsAbove,row,matrix[rowsAbove][leadingEntry].multiply(NEGATIVEONE));
					}
				}
			}
		}
		rref = true;	
	}
	//PreCondition: rref has been called and matrix is augmented
	public String variableAssignment() {
		StringBuilder builder = new StringBuilder();
		if(noSolution) {
			return "No variable assigment possible.";
		}
		int pivotRow = 0;
		for(int col = 0; col < matrixCol - 1; col++) {
			if(!pivotCols[col]) {
				builder.append("X");
				builder.append((col+1));
				builder.append(" is free");
			} else {
				builder.append("X");
				builder.append((col+1));
				builder.append(" = ");
				builder.append(matrix[pivotRow][matrixCol-1].toString());
				for(int variableCol = col + 1 ;variableCol < matrixCol-1; variableCol++) {
					if(!matrix[pivotRow][variableCol].equals(Fraction.ZERO)) {
						builder.append(" + ");
						builder.append(matrix[pivotRow][variableCol].multiply(NEGATIVEONE));
						builder.append("X");
						builder.append((variableCol+1));
					}
				}
				pivotRow++;
			}
			builder.append("\n");

		}
		return builder.toString();
	}

	//Precondtion  rref() and matric is augmented
	public String vectorForm() {
		String format = "%1$"+maxLength()+"s";
		ArrayList<Vector> vectors = new ArrayList<Vector>();
		Queue<Integer> pivotColumns = new LinkedList<Integer>();
		for(int col = 0; col < matrixCol; col++) {
			if(!pivotCols[col]) {
				pivotColumns.add(col);
				Vector v = new Vector(matrixCol -1);
				for(int row = 0; row < matrixRow; row++) {
					if(col < matrixCol -1)
						v.vector[row] = matrix[row][col].multiply(NEGATIVEONE);
					else 
						v.vector[row] = matrix[row][col];
				}
				if(col< matrixCol -1) {
					v.vector[col] = Fraction.ONE;
				}
				vectors.add(v);
			}
		}
		//System.out.println(vectors.get(1));
		String output = "";
		for(int row = 0;row < vectors.get(0).vector.length;row++) {
			StringBuilder builder = new StringBuilder();
			if(row == matrixRow/2) {
				for(int col = 0; col < vectors.size()-1;col++) {
					int variable = pivotColumns.poll()+1;
					String var = "X"+variable;
					var = String.format("%3s",var);
					builder.append(var);
					builder.append("|");
					builder.append(String.format(format,vectors.get(col).vector[row]));
					builder.append("|");
					if(col!=vectors.size()-2) builder.append(" +");

				}
			} else {
				for(int col = 0; col < vectors.size()-1;col++) {
					builder.append("     ");
					builder.append("|");
					builder.append(String.format(format,vectors.get(col).vector[row]));
					builder.append("|");
					if(col!=vectors.size()-2) builder.append(" +");
				}
			}
			if(!vectors.get(vectors.size()-1).isZeroVector()) {
				if(vectors.size() != 1)
					builder.append(" + ");
				builder.append("|");
				builder.append(String.format(format,vectors.get(vectors.size()-1).vector[row]));
				builder.append("|");
			}
			builder.append("\n");
			output += builder.toString();;
		}
		return output;
	}

	@Override
	public Object clone() {
		Matrix matrix = new Matrix(matrixRow,matrixCol,augmented);
		for(int row = 0; row < matrixRow; row++)
			for(int col = 0; col < matrixCol; col++)
				matrix.matrix[row][col] = this.matrix[row][col];
		return matrix;
	}
	
	public static Matrix augment(Matrix matrix, Vector v) {
		Matrix matrixA = new Matrix(matrix.matrixRow,matrix.matrixCol+1,true);
		for(int row = 0; row < matrix.matrixRow; row++)
		{
			for(int col = 0; col < matrix.matrixCol; col++)
				matrixA.matrix[row][col] = matrix.matrix[row][col];
			matrixA.matrix[row][matrix.matrixCol] = v.vector[row];
		}
		return matrixA;
	}
	//matrix is augmented
	public String[] getAugColumn() {
		String[] augCol = new String[matrixRow];
		for(int row = 0;row < matrixRow; row++) {
			augCol[row] = matrix[row][matrixCol-1].toString();
		}
		return augCol;
	}
	
	//Calculates the Lower and Upper Triangles of Matrix A
	public void LU() {
		if(!LU) {
		int numPivots = 0;
		for(int col = 0; col < matrixCol; col++) {
			if(!allZeroCol(U,matrixRow,col,numPivots)) {
				int pivotRow = firstNonZeroValue(U, matrixRow, col,numPivots);
				swap(U,matrixCol,pivotRow,numPivots);
				pivotRow = numPivots;
				for(int row = pivotRow + 1;row < matrixRow;row++) {
					Fraction coeffiecent = (U[row][col].divide(U[pivotRow][col]));
					L[row][col] = coeffiecent;
					addRows(U, matrixCol, row, pivotRow, coeffiecent.multiply(NEGATIVEONE));
				}
				numPivots++;
			}
		}
		}
		for(int row = 0; row < matrixRow; row++)
			L[row][row] = Fraction.ONE;
		LU = true;
	}

	private static boolean allZeroCol(Fraction[][] mat, int matrixRow, int col, int start) {
		for(int row = start; row < matrixRow; row++){
			if(!mat[row][col].equals(Fraction.ZERO))
				return false;
		}
		return true;
	}
	//Mat is an N X N matrix
	public static Fraction determinant(Matrix mat) {
		if(mat.matrixRow == 2) {
			return mat.matrix[0][0].multiply(mat.matrix[1][1]).subtract(mat.matrix[1][0].multiply(mat.matrix[0][1]));
		}
		
		if(mat.matrixCol == 3) {
			return determinant3X3(mat.matrix);
		}
		
		Fraction[][] sign = new Fraction[mat.matrixRow][mat.matrixRow];
		Fraction val = Fraction.ONE;
		for(int row = 0; row < mat.matrixRow; row++) {
			for(int col = 0; col < mat.matrixRow; col++) {
				sign[row][col] = val;
				val = val.multiply(Matrix.NEGATIVEONE);
			}
			if(mat.matrixRow%2 == 0)val = val.multiply(Matrix.NEGATIVEONE);
		}
		//Determine if a one exists
		int oneX = -1;
		int oneY = -1;
		for(int row = 0;oneX == -1 && row < mat.matrixRow; row++) 
			for(int col = 0;oneX == -1 && col < mat.matrixCol; col++) {
				if(mat.matrix[row][col].equals(Fraction.ONE)) {
					oneX = col;
					oneY = row;
				}
			}
		if(oneX != -1) {
			for(int row = 0; row < mat.matrixRow; row++) {
				if(row != oneY) {
					mat.addRows(row, oneY, mat.matrix[row][oneX].multiply(NEGATIVEONE));
				}
			}
			Matrix recurse = new Matrix(generateRecurseArray(mat,oneY, oneX),false);
			return sign[oneY][oneX].multiply(determinant(recurse));
		} else {
			int rawPos = mat.mostZeroRowOrCol();

			if(rawPos >= mat.matrixRow) {
				int workingCol = rawPos - mat.matrixRow;
				Fraction det = new Fraction("0");
				for(int workingRow = 0; workingRow < mat.matrixRow; workingRow++) {
					if(!mat.matrix[workingRow][workingCol].equals(Fraction.ZERO)) {
					det = det.add(sign[workingRow][workingCol].multiply(determinant(new Matrix(generateRecurseArray(mat,workingRow, workingCol),false)).multiply(mat.matrix[workingRow][workingCol])));
					}
				}
				return det;
			} else {
				int workingRow = rawPos;
				Fraction det = new Fraction("0");
				for(int workingCol = 0; workingCol < mat.matrixCol; workingCol++) {
					if(!mat.matrix[workingRow][workingCol].equals(Fraction.ZERO)) {
					det = det.add(sign[workingRow][workingCol].multiply(determinant(new Matrix(generateRecurseArray(mat,workingRow, workingCol),false)).multiply(mat.matrix[workingRow][workingCol])));
					}
				}
				return det;
			}
			
		}
	}
	
	
	//Basket Weaving
	private static Fraction determinant3X3(Fraction[][] a) {
		Fraction det =  Fraction.ZERO;
		det = det.add(a[0][0].multiply(a[1][1]).multiply(a[2][2]));
		det = det.add(a[0][1].multiply(a[1][2]).multiply(a[2][0]));
		det = det.add(a[0][2].multiply(a[1][0]).multiply(a[2][1]));
		
		det = det.subtract(a[2][0].multiply(a[1][1]).multiply(a[0][2]));
		det = det.subtract(a[2][1].multiply(a[1][2]).multiply(a[0][0]));
		det = det.subtract(a[2][2].multiply(a[1][0]).multiply(a[0][1]));
		return det;
	}

	public static Matrix cramerInverse(Matrix mat) {
		Fraction det = determinant(mat);
		if(!det.equals(new Fraction("0"))) {
			Matrix m = new Matrix(mat.matrixRow,mat.matrixCol,false);
			Vector[] indentity = new Vector[mat.matrixCol];
			for(int i = 0; i < indentity.length; i++) {
				Vector v = new Vector(mat.matrixRow);
				v.vector[i] = Fraction.ONE;
				indentity[i] = v;
			}
			for(int row = 0; row < mat.matrixRow; row++)
				for(int col = 0; col < mat.matrixCol; col++) {
			
					Fraction c = determinant(replaceColumn(mat,indentity[col],row));
					m.matrix[row][col] = c.divide(det);
				}
			return m;
		}
		return null;
	}
	
	public static Matrix replaceColumn(Matrix mat,Vector v, int replaceCol) {
		Matrix ret = new Matrix(mat.matrixRow,mat.matrixCol,false);
		for(int row = 0; row < mat.matrixRow; row++)
			for(int col = 0; col < mat.matrixCol; col++) {
				if(col == replaceCol)
					ret.matrix[row][col] = v.vector[row];
				else
					ret.matrix[row][col] = mat.matrix[row][col];
			}
		
		return ret;
	}
	
	public static Vector cramerRule(Matrix mat, Vector v) { 
		if(!determinant(mat).equals(Fraction.ZERO)) {
		Vector ret = new Vector(v.getNumRows());
		for(int col = 0; col < mat.matrixCol; col++) {
			Fraction x = determinant(replaceColumn(mat, v, col));
			ret.vector[col] = x;
		}
		return ret;
		}
		return null;
	}
	
	
	
	
	private int mostZeroRowOrCol() {
		int zeroCount = 0;
		int pos = 0;
		for(int row = 0; row < matrixRow; row++) {
			int zeroCountTest = zeroCountRow(row);
			if(zeroCountTest > zeroCount) {
				zeroCount = zeroCountTest;
				pos = row;
			}
		}
		
		for(int col = 0; col < matrixCol; col++) {
			int zeroCountTest = zeroCountCol(col);
			if(zeroCountTest > zeroCount) {
				zeroCount = zeroCountTest;
				pos = col+matrixRow;
			}
		}
		return pos;
	}
	
	private int zeroCountRow(int row) {
		int zeroCount = 0;
		for(int col = 0; col < matrixCol; col++) {
			if(matrix[row][col].equals(Fraction.ZERO))
				zeroCount++;
		}
		return zeroCount;
	}
	
	private int zeroCountCol(int col) {
		int zeroCount = 0;
		for(int row = 0; row < matrixCol; row++) {
			if(matrix[row][col].equals(Fraction.ZERO))
				zeroCount++;
		}
		return zeroCount;
	}
	
	private static Fraction[][] generateRecurseArray(Matrix mat, int ignoreRow, int ignoreCol) {
		Fraction[][] recurseArray = new Fraction[mat.matrixRow -1][mat.matrixCol-1];
		int addRow = 0;
		int addCol = 0;
		for(int row = 0;row < mat.matrixRow; row++) 
			for(int col = 0;col < mat.matrixCol; col++) {
				if(row != ignoreRow && col != ignoreCol) {
					recurseArray[addRow][addCol] = mat.matrix[row][col];
					addCol++;
					if(addCol % (mat.matrixCol-1) == 0) {
						addCol = 0;
						addRow++;
					}
				}
			}
		return recurseArray;
	}
	
	public static Matrix inverse(Matrix mat) {
		if(!determinant(mat).equals(Fraction.ZERO)) {
			for(int row = 0; row < mat.matrixRow; row++) {
				Vector v = new Vector(mat.matrixRow);
				v.vector[row] = Fraction.ONE;
				mat = augment(mat, v);
			}
			mat.rref();
			Fraction[][] output = new Fraction[mat.matrixRow][mat.matrixRow];
			for(int row = 0; row < mat.matrixRow; row++)
				for(int col = 0; col < mat.matrixRow; col++) {
					output[row][col] = mat.matrix[row][col + mat.matrixRow];
				}
			//System.out.println(Arrays.deepToString(output));
			Matrix ret = new Matrix(output, mat.augmented);
			return ret;
		}
		return null;
	}
	
	
}
	

