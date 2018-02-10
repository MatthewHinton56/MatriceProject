
public class Vector {
	//Vector x is represented by a horizontal Array;
	public double[] vector;
	
	public Vector(int rows) {
		vector = new double[rows];
	}
	
	public Vector(double[] vec) {
		vector = new double[vec.length];
        for(int row = 0;row<vec.length;row++)
        	vector[row] = vec[row];
	}
	
	public int getNumRows() {
		return vector.length;
	}
}
