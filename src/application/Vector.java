package application;
import java.math.MathContext;

public class Vector {

	//Vector x is represented by a horizontal Array;
	public Fraction[] vector;
	
	public Vector(int rows) {
		vector = new Fraction[rows];
		for(int row = 0; row < rows; row++) {
			vector[row] = Fraction.ZERO;
		}
	}
	
	public Vector(Fraction[] vec) {
		vector = new Fraction[vec.length];
        for(int row = 0;row<vec.length;row++) {
        	vector[row] = vec[row];
        	if(vec[row] == null)
        		vector[row] = Fraction.ZERO;
        }
	}
	
	public int getNumRows() {
		return vector.length;
	}
	
	public void scalarMultipication(Fraction scale) {
		for(int row = 0; row < vector.length; row++) 
			vector[row] = vector[row].multiply(scale);
	}
	
	public void scalarDivision(Fraction divisor) {
		for(int row = 0; row < vector.length; row++) 
			vector[row] = vector[row].divide(divisor);
	}
	
	public boolean isZeroVector() {
		for(int row = 0;row<vector.length;row++)
        	if(!vector[row].equals(Fraction.ZERO))
        		return false;
		return true;
	}
	
	public Vector add(Vector v) {
		Vector ret = new Vector(v.getNumRows());
		for(int row = 0; row < v.getNumRows(); row++)
			ret.vector[row] = this.vector[row].add(v.vector[row]);
		return ret;
	}
	
	public Vector scale(Fraction scale) {
		Vector ret = new Vector(vector.length);
		for(int row = 0; row < vector.length; row++) 
			ret.vector[row] = vector[row].multiply(scale);
		return ret;
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
    
    public String[] getVector() {
		String[] augCol = new String[vector.length];
		for(int row = 0;row < augCol.length; row++) {
			augCol[row] = vector[row].toString();
		}
		return augCol;
	}
    
    @Override
	public boolean equals(Object obj) {
    	Vector v = (Vector) obj;
    	if(v.getNumRows() != this.getNumRows())
    		return false;
		for(int pos = 0; pos < v.getNumRows(); pos++) {
			if(!this.vector[pos].equals(v.vector[pos]))
				return false;
		}
		return true;
	}
}
