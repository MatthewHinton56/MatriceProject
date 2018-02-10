import java.math.BigDecimal;

public class Matrix {
	

	final BigDecimal[][] matrix;
	final int matrixRow;
	final int matrixCol;
	//Constructor Section
	public Matrix(int rows, int cols) {
		matrix = new BigDecimal[rows][cols];
		matrixRow = matrix.length;
		matrixCol = matrix[0].length;
	}
	
	public Matrix(double[][] mat) {
		matrix = new BigDecimal[mat.length][mat[0].length];
        for(int row = 0;row<matrix.length;row++)
        	System.arraycopy(mat[row], 0, matrix[row], 0, mat[row].length);
        matrixRow = matrix.length;
		matrixCol = matrix[0].length;
	}
	
	public Matrix(Vector[] vectors) {
		matrix = new BigDecimal[vectors[0].vector.length][vectors.length];
        for(int row = 0;row<matrix.length;row++)
        	for(int col = 0;col<matrix[0].length;col++)
        		matrix[row][col] = vectors[col].vector[row];
        matrixRow = matrix.length;
		matrixCol = matrix[0].length;
	}
	
	//Elementary Row Operations
	
	public void swap(int row1, int row2){
		double[] temp = new double[matrixCol];
		System.arraycopy(matrix[row1], 0, temp, 0, matrixCol);
		System.arraycopy(matrix[row2], 0, matrix[row1], 0, matrixCol);
		System.arraycopy(temp, 0, matrix[row2], 0, matrixCol);
	}
	
	public void scalarMultipication(int row, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++) 
			matrix[row][col].multiply(scale);
	}
	//Adds row2 scaled by scale to row1
	public void addRows(int row1, int row2, BigDecimal scale) {
		for(int col = 0; col < matrixCol; col++) 
			matrix[row1][col].add(matrix[row2][col].multiply(scale));
	}
	
    public String toString(){
    	StringBuilder builder = new StringBuilder();//uses string builder for efficency
    	String format = "%1$"+maxLength()+".2f";
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
    			int x = ("" + matrix[row][col]).length();
    			if(x>maxLength)maxLength=x;
    			
    		}
    	return maxLength+1;
    }
    public static void main(String[] args) {
		System.out.println("Hello");
	}
	
	//return 0 if not divisible by leading value, otherwise returns th value of the leading number
	public BigDecimal rowDivisibleByLeadingValue(int row) {
		BigDecimal leading = matrix[row][0];
		for(int col = 1; col < matrixCol; col++) {
			if(matrix[row][col].remainder(leading).doubleValue() != 0)
				return new BigDecimal("0");
		}
		return leading;
	}
	public boolean leadingValueIsOne(int row) {
		return matrix[row][0] == 1;
	}
}
