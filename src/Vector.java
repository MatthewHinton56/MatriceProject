import java.math.BigDecimal;

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
}
