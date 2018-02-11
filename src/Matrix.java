import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;
public class Matrix {
	
	public static final BigDecimal negativeOne = new BigDecimal("-1");

	final BigDecimal[][] matrix;
	final int matrixRow;
	final int matrixCol;
	final boolean[] pivotRows;
	final boolean[] pivotCols;
	boolean ref;
	boolean rref;
	//Constructor Section
	public Matrix(int rows, int cols) {
		matrix = new BigDecimal[rows][cols];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
	}
	
	public Matrix(BigDecimal[][] mat) {
		matrix = new BigDecimal[mat.length][mat[0].length];
        for(int row = 0;row<matrix.length;row++)
        	System.arraycopy(mat[row], 0, matrix[row], 0, mat[row].length);
        matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
	}
	
	public Matrix(Vector[] vectors) {
		matrix = new BigDecimal[vectors[0].vector.length][vectors.length];
        for(int row = 0;row<matrix.length;row++)
        	for(int col = 0;col<matrix[0].length;col++)
        		matrix[row][col] = vectors[col].vector[row];
        matrixRow = matrix.length;
		matrixCol = matrix[0].length;
		pivotRows = new boolean[matrixRow];
		pivotCols = new boolean[matrixCol];
	}
	
	//Elementary Row Operations
	
	public void swap(int row1, int row2){
		BigDecimal[] temp = new BigDecimal[matrixCol];
		System.arraycopy(matrix[row1], 0, temp, 0, matrixCol);
		System.arraycopy(matrix[row2], 0, matrix[row1], 0, matrixCol);
		System.arraycopy(temp, 0, matrix[row2], 0, matrixCol);
	}
	
	public void scalarMultipication(int row, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++) 
			matrix[row][col] = matrix[row][col].multiply(scale);
	}
	
	public void scalarDivision(int row, BigDecimal divisor) {
		for(int col = 0; col < matrixCol; col++) 
			matrix[row][col] = matrix[row][col].divide(divisor, MathContext.DECIMAL64);
	}
	//Adds row2 scaled by scale to row1
	public void addRows(int row1, int row2, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++) 
			matrix[row1][col] = matrix[row1][col].add(matrix[row2][col].multiply(scale));
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
		BigDecimal[][] mat = new BigDecimal[rows][cols];
		for(int row = 0;row<mat.length;row++)
        	for(int col = 0;col<mat[0].length;col++) {
        		System.out.print("Enter next value");
        		String s = scan.nextLine();
        		mat[row][col] = new BigDecimal(s);
        }
		Matrix matrix = new Matrix(mat);
		System.out.println(matrix);
		matrix.ref();
		System.out.println(matrix);
		matrix.rref();
		System.out.println(matrix);
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
	
	public boolean leadingValueIsNotZero(int row, int col) {
		return !matrix[row][col].equals(BigDecimal.ZERO);
	}
	
	public boolean allZeroRow(int row) {
		for(int col = 0;col < matrixCol; col++) {
			if(!matrix[row][col].equals(BigDecimal.ZERO))
				return false;
		}
		return true;
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
	
	//unaugmented
	public void ref() {
		if(!ref){
		int numPivots = 0;
		for(int col = 0; col < matrixCol; col++) {
			if(!allZeroCol(col,numPivots)) {
			int pivotRow = -1;
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
			}
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
}
