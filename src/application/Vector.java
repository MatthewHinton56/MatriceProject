package application;
import java.math.BigDecimal;
import java.math.MathContext;

public class Vector {
	//Vector x is represented by a horizontal Array;
	public BigDecimal[] vector;
	
	public Vector(int rows) {
		vector = new BigDecimal[rows];
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
}
