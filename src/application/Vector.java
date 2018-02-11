package application;
import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
	//Vector x is represented by a horizontal Array;
	public BigDecimal[] vector;
	
	public Vector(int rows) {
		vector = new BigDecimal[rows];
		for(int row = 0; row < rows; row++) {
			vector[row] = BigDecimal.ZERO;
		}
	}
	
	public Vector(BigDecimal[] vec) {
		vector = new BigDecimal[vec.length];
        for(int row = 0;row<vec.length;row++)
        	vector[row] = vec[row];
	}
	
	public int getNumRows() {
		return vector.length;
	}
	
	public void scalarMultipication(BigDecimal scale) {
		for(int row = 0; row < vector.length; row++) 
			vector[row] = vector[row].multiply(scale);
	}
	
	public void scalarDivision(BigDecimal divisor) {
		for(int row = 0; row < vector.length; row++) 
			vector[row] = vector[row].divide(divisor, MathContext.DECIMAL64);
	}
	
	public boolean isZeroVector() {
		for(int row = 0;row<vector.length;row++)
        	if(!vector[row].equals(BigDecimal.ZERO))
        		return false;
		return true;
	}
	
	public String toString(){
    	StringBuilder builder = new StringBuilder();//uses string builder for efficency
    	String format = "%1$"+maxLength()+"s";
    	for(int row = 0;row<vector.length;row++){
    		builder.append("|");
    	    builder.append(String.format(format,vector[row]));
    		builder.append("|\n");
    	}
        return builder.toString(); // alter as necessary
    }
	
    private int maxLength() //calculates the maximum length needed for the toString
    {
    	int maxLength = 0;
    	for(int row = 0;row<vector.length;row++)
    	{
    			int x = ("" + vector[row].toString()).length();
    			if(x>maxLength)maxLength=x;
    	}		
    	return maxLength+1;
    }
}
