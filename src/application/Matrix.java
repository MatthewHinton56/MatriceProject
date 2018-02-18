package application;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
public class Matrix {

	public static final BigDecimal negativeOne = new BigDecimal("-1");
	public static final BigDecimal roundoff = new BigDecimal(".001");
	public static final BigDecimal roundUpValue = new BigDecimal(".999");
	public static final BigDecimal roundDownValue = new BigDecimal(".0001");
	final BigDecimal[][] matrix;
	final int matrixRow;
	final int matrixCol;
	final boolean[] pivotRows;
	final boolean[] pivotCols;
	boolean ref;
	boolean rref;
	final boolean augmented;
	//No solution flag set true if matrix is augmented and the form 0 0 0 C where is a non zero value exists.
	boolean noSolution;

	//Constructor Section
	public Matrix(int rows, int cols,boolean augmented) {
		matrix = new BigDecimal[rows][cols];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
		for(int row = 0; row < rows; row++) 
			for(int col = 0; col < cols; col++) 
				matrix[row][col] = BigDecimal.ZERO;
	}

	public Matrix(BigDecimal[][] mat,boolean augmented) {
		matrix = new BigDecimal[mat.length][mat[0].length];
		for(int row = 0;row<matrix.length;row++)
			System.arraycopy(mat[row], 0, matrix[row], 0, mat[row].length);
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
	}

	public Matrix(Vector[] vectors,boolean augmented) {
		matrix = new BigDecimal[vectors[0].vector.length][vectors.length];
		for(int row = 0;row<matrix.length;row++)
			for(int col = 0;col<matrix[0].length;col++)
				matrix[row][col] = vectors[col].vector[row];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		this.augmented = augmented;
	}

	//System of equations constructor
	public Matrix(String system,int totalVariables, int amountOfEquations) {
		matrix = new BigDecimal[amountOfEquations][totalVariables+1];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
		augmented = true;
		for(int row = 0;row<matrix.length;row++)
			for(int col = 0;col<matrix[0].length;col++)
				matrix[row][col] = BigDecimal.ZERO;
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
				matrix[equationCounter][col-1] = new BigDecimal(var.substring(0, var.indexOf("X")));
			}
			matrix[equationCounter][matrixCol-1] = new BigDecimal(variables[variables.length-1]);
		}

	}


	//Elementary Row Operations

	public void swap(int row1, int row2){
		BigDecimal[] temp = new BigDecimal[matrixCol];
		System.arraycopy(matrix[row1], 0, temp, 0, matrixCol);
		System.arraycopy(matrix[row2], 0, matrix[row1], 0, matrixCol);
		System.arraycopy(temp, 0, matrix[row2], 0, matrixCol);
	}

	public void scalarMultipication(int row, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++) {
			matrix[row][col] = matrix[row][col].multiply(scale);
			boolean roundDown = matrix[row][col].abs().remainder(BigDecimal.ONE).compareTo(roundDownValue) == -1;
			boolean remainderNotZero = !matrix[row][col].abs().remainder(BigDecimal.ONE).equals(BigDecimal.ZERO);
			if(matrix[row][col].abs().remainder(BigDecimal.ONE).compareTo(roundUpValue) == 1 
					||(roundDown&&remainderNotZero)) 
				matrix[row][col] = new BigDecimal(matrix[row][col].floatValue()+"");
			if(matrix[row][col].compareTo(BigDecimal.ZERO) == 0)
				matrix[row][col] = BigDecimal.ZERO;
			if(matrix[row][col].abs().compareTo(roundoff) == -1)
				matrix[row][col] = BigDecimal.ZERO;

		}
	}

	public void scalarDivision(int row, BigDecimal divisor) {
		for(int col = 0; col < matrixCol; col++){
			matrix[row][col] = matrix[row][col].divide(divisor, MathContext.DECIMAL64);
			boolean roundDown = matrix[row][col].abs().remainder(BigDecimal.ONE).compareTo(roundDownValue) == -1;
			boolean remainderNotZero = !matrix[row][col].abs().remainder(BigDecimal.ONE).equals(BigDecimal.ZERO);
			if(matrix[row][col].abs().remainder(BigDecimal.ONE).compareTo(roundUpValue) == 1 
					||(roundDown&&remainderNotZero)) 
				matrix[row][col] = new BigDecimal(matrix[row][col].floatValue()+"");
			if(matrix[row][col].compareTo(BigDecimal.ZERO) == 0)
				matrix[row][col] = BigDecimal.ZERO;
			if(matrix[row][col].abs().compareTo(roundoff) == -1)
				matrix[row][col] = BigDecimal.ZERO;
			

		}
	}
	//Adds row2 scaled by scale to row1
	public void addRows(int row1, int row2, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++){
			matrix[row1][col] = matrix[row1][col].add(matrix[row2][col].multiply(scale));
			boolean roundDown = matrix[row1][col].abs().remainder(BigDecimal.ONE).compareTo(roundDownValue) == -1;
			boolean remainderNotZero = !matrix[row1][col].abs().remainder(BigDecimal.ONE).equals(BigDecimal.ZERO);
			if(matrix[row1][col].abs().remainder(BigDecimal.ONE).compareTo(roundUpValue) == 1 
					||(roundDown&&remainderNotZero)) 
				matrix[row1][col] = new BigDecimal(matrix[row1][col].floatValue()+"");
			if(matrix[row1][col].compareTo(BigDecimal.ZERO) == 0)
				matrix[row1][col] = BigDecimal.ZERO;
			if(matrix[row1][col].abs().compareTo(roundoff) == -1)
				matrix[row1][col] = BigDecimal.ZERO;
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

		/*System.out.print("Enter rows:");
		int rows = scan.nextInt();
		System.out.print("Enter cols:");
		int cols = scan.nextInt();
		scan.nextLine();
		BigDecimal[][] mat = new BigDecimal[rows][cols];
		for(int row = 0;row<mat.length;row++)
        	for(int col = 0;col<mat[0].length;col++) {
        		System.out.print("Enter next value: ");
        		String s = scan.nextLine();
        		mat[row][col] = new BigDecimal(s);
        }
		Matrix matrix = new Matrix(mat, true);
		System.out.println(matrix);
		matrix.ref();
		System.out.println(matrix);
		matrix.rref();
		System.out.println(matrix);
		System.out.println(matrix.variableAssignment());*/
		String system = "3X1 + 5X2 + -4X3 = 7\n-3X1 + -2X2 + 4X3 = -1\n6X1 + X2 + -8X3 = -4";
		Matrix matrix = new Matrix(system,3,3);
		System.out.println(matrix);
		matrix.rref();
		System.out.println(matrix);
		System.out.println(matrix.vectorForm());
	}

	//return 0 if not divisible by leading value, otherwise returns th value of the leading number
	public BigDecimal rowDivisibleByLeadingValue(int row, int leadingPos) {
		BigDecimal leading = matrix[row][ leadingPos];
		if(leading.equals(BigDecimal.ZERO))
			return BigDecimal.ZERO;
		for(int col =  leadingPos+1; col < matrixCol; col++) {
			if(matrix[row][col].remainder(leading).doubleValue() != 0)
				return BigDecimal.ZERO;
		}
		return leading;
	}
	public boolean leadingValueIsOne(int row, int col) {
		return matrix[row][col].equals(BigDecimal.ONE);
	}

	public boolean impossibleRow(int row) {
		for(int col = 0;col < matrixCol-1; col++) {
			if(!matrix[row][col].equals(BigDecimal.ZERO))
				return false;
		}
		return !matrix[row][matrixCol-1].equals(BigDecimal.ZERO);
	}

	public boolean allZeroCol(int col, int start) {
		for(int row = start; row < matrixRow; row++){
			if(!matrix[row][col].equals(BigDecimal.ZERO))
				return false;
		}
		return true;
	}

	public int leadingEntryPosition(int row) {
		for(int col = 0; col < matrixCol;col++) {
			if(!matrix[row][col].equals(BigDecimal.ZERO))
				return col;
		}
		return -1;
	}

	public void ref() {
		if(!ref){
			int numPivots = 0;
			for(int col = 0; col < matrixCol; col++) {
				if(!allZeroCol(col,numPivots)) {
					int pivotRow = -1;
					//System.out.println(this);
					//identify if a one is a non pivot row.
					//First identify if 1 in any row
					for(int row = numPivots;pivotRow == -1 &&  row < matrixRow; row++) {
						if(leadingValueIsOne(row, col))
							pivotRow = row;
					}
					//If no row with one,find row where all values are have the front value as divisor 
					if(pivotRow == -1) {
						for(int row = numPivots;pivotRow == -1 && row < matrixRow; row++) {
							BigDecimal divisor = rowDivisibleByLeadingValue(row, col);
							if(!divisor.equals(BigDecimal.ZERO)) {
								pivotRow = row;
								scalarDivision(pivotRow,divisor);
							}	
						}
					}
					if(pivotRow == -1) {
						int row = numPivots;
						while(pivotRow == -1) {
							if(!matrix[row][col].equals(BigDecimal.ZERO)) {
								BigDecimal divisor = matrix[row][col];
								pivotRow = row;
								scalarDivision(pivotRow,divisor);
							}
							row++;
						}
					}
					if(pivotRow != numPivots)swap(pivotRow, numPivots);
					pivotRow = numPivots;

					for(int row = pivotRow + 1;row < matrixRow;row++) {
						addRows(row,pivotRow,matrix[row][col].multiply(negativeOne));
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
			for(int row = matrixRow - 1; row > 0; row--) {
				int leadingEntry = leadingEntryPosition(row);
				if(leadingEntry != -1) {
					for(int rowsAbove = row - 1; rowsAbove >= 0; rowsAbove--) {
						addRows(rowsAbove,row,matrix[rowsAbove][leadingEntry].multiply(negativeOne));
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
					if(!matrix[pivotRow][variableCol].equals(BigDecimal.ZERO)) {
						builder.append(" + ");
						builder.append(matrix[pivotRow][variableCol].multiply(negativeOne));
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
						v.vector[row] = matrix[row][col].multiply(negativeOne);
					else 
						v.vector[row] = matrix[row][col];
				}
				if(col< matrixCol -1) {
					v.vector[col] = BigDecimal.ONE;
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
}
