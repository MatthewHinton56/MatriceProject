package application;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class EigenMatrix extends Matrix {

	//public TreeMap<Fraction, Vector[]> evalToEVector;
	public Fraction[] characteristicsPolynomial;
	public static String lambda = new String(Character.toChars(0x03BB));

	public EigenMatrix(Fraction[][] mat, boolean augmented) {
		super(mat, augmented);
		EigenFinderSetUp();
	}

	public EigenMatrix(int rows, int cols, boolean augmented) {
		super(rows, cols, augmented);
		EigenFinderSetUp();
	}

	public EigenMatrix(Vector[] vectors, boolean augmented) {
		super(vectors, augmented);
		EigenFinderSetUp();
	}

	public void EigenFinderSetUp() {
		boolean[][] eigenArray = new boolean[this.matrixRow][this.matrixCol];
		for(int i = 0; i < matrixRow;i++)
			eigenArray[i][i] = true;
		characteristicsPolynomial = charFunction(this.matrix,eigenArray,this.matrixRow+1);
	}

	public static Fraction[] charFunction2X2(Fraction[][] mat,boolean[][] eigenMat, int arraySize) {
		Fraction[] characteristicTemp = new Fraction[arraySize];
		for(int z = 0; z < arraySize; z++)
			characteristicTemp[z] = Fraction.ZERO;
		//a x b cases
		if(eigenMat[0][0] && eigenMat[1][1]) {
			characteristicTemp[2] = Fraction.ONE;
			characteristicTemp[1] = mat[0][0].multiply(Fraction.NEGATIVEONE).add(mat[1][1].multiply(Fraction.NEGATIVEONE));
			characteristicTemp[0] = mat[0][0].multiply(mat[1][1]);
		}  else if(eigenMat[0][0]) {
			characteristicTemp[1] = mat[1][1].multiply(Fraction.NEGATIVEONE);
			characteristicTemp[0] = mat[0][0].multiply(mat[1][1]);
		} else if(eigenMat[1][1]) {
			characteristicTemp[1] = mat[0][0].multiply(Fraction.NEGATIVEONE);
			characteristicTemp[0] = mat[0][0].multiply(mat[1][1]);
		} else {
			characteristicTemp[0] = mat[0][0].multiply(mat[1][1]);
		}
		if(eigenMat[0][1] && eigenMat[1][0]) {
			characteristicTemp[2] = characteristicTemp[2].subtract(Fraction.ONE);
			characteristicTemp[1] = characteristicTemp[1].subtract(mat[1][0].multiply(Fraction.NEGATIVEONE).add(mat[0][1].multiply(Fraction.NEGATIVEONE)));
			characteristicTemp[0] = characteristicTemp[0].subtract(mat[0][1].multiply(mat[1][0]));

		} else if(eigenMat[0][1]) {
			characteristicTemp[1] = characteristicTemp[1].subtract(mat[1][0].multiply(Fraction.NEGATIVEONE));
			characteristicTemp[0] = characteristicTemp[0].subtract(mat[0][1].multiply(mat[1][0]));
		} else if(eigenMat[1][0]) {
			characteristicTemp[1] = characteristicTemp[1].subtract(mat[0][1].multiply(Fraction.NEGATIVEONE));
			characteristicTemp[0] = characteristicTemp[0].subtract(mat[0][1].multiply(mat[1][0]));
		} else {
			characteristicTemp[0] = characteristicTemp[0].subtract(mat[0][1].multiply(mat[1][0]));
		}
		return characteristicTemp;
	}

	public static Fraction[] charFunction(Fraction[][] mat,boolean[][] eigenMat, int arraySize) {
		if(mat.length == 2) {
			Fraction[] recFrac =  charFunction2X2(mat,eigenMat,arraySize);
			return recFrac;
		}
		Fraction[] sign = new Fraction[mat.length];
		Fraction val = Fraction.ONE;
		for(int row = 0; row < mat.length; row++) {
			sign[row] = val;
			val = val.multiply(Fraction.NEGATIVEONE);
		}
		Fraction[] characteristicTemp = new Fraction[arraySize];
		for(int z = 0; z < arraySize; z++)
			characteristicTemp[z] = Fraction.ZERO;
		for(int i = 0; i < mat.length; i++) {
			if(eigenMat[0][i] || !mat[0][i].equals(Fraction.ZERO)) {
				boolean[][] recEigenMat = EigenMatrix.generateEigenArray(eigenMat, 0, i);
				Fraction[][] recMat = EigenMatrix.generateFracArray(mat, 0, i);
				Fraction[] recFrac = charFunction(recMat,recEigenMat,arraySize);
				for(int q = 0; q < arraySize; q++) {
					if(eigenMat[0][i] && q >= 1) 
						characteristicTemp[q] = characteristicTemp[q].add(recFrac[q-1].multiply(Fraction.NEGATIVEONE).multiply(sign[i]));
					characteristicTemp[q] = characteristicTemp[q].add(recFrac[q].multiply(mat[0][i]).multiply(sign[i]));
				}

			}
		}
		return characteristicTemp;
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
		EigenMatrix matE = new EigenMatrix(mat,false);
		System.out.println(createPolynomial(matE.characteristicsPolynomial));
		//System.out.println(Character.toChars(0x03BB));
	}

	public static String createPolynomial(Fraction[] charFunction) {
		int biggestValue = charFunction.length-1;
		String output = "";
		for(;biggestValue >= 10; biggestValue--) {
			if(charFunction[biggestValue].equals(Fraction.ONE))
				output+= lambda+"^"+biggestValue;
			else if(charFunction[biggestValue].equals(Fraction.NEGATIVEONE))
				output+= "-"+lambda+"^"+biggestValue;
			else if(!charFunction[biggestValue].equals(Fraction.ZERO))
				output+= charFunction[biggestValue]+lambda+"^"+biggestValue;
			if(!charFunction[biggestValue].equals(Fraction.ZERO))output+=" + ";
		}
		for(;biggestValue > 3; biggestValue--) {
			int power = 0x2070+biggestValue;
			if(charFunction[biggestValue].equals(Fraction.ONE))
				output+= lambda+ new String(Character.toChars(power));
			else if(charFunction[biggestValue].equals(Fraction.NEGATIVEONE))
				output+= "-"+lambda+ new String(Character.toChars(power));
			else if(!charFunction[biggestValue].equals(Fraction.ZERO))
				output+= charFunction[biggestValue]+lambda+new String(Character.toChars(power));
			if(!charFunction[biggestValue].equals(Fraction.ZERO))output+=" + ";
		}

		if(charFunction.length > 3 && charFunction[3].equals(Fraction.ONE))
			output+= lambda+ new String(Character.toChars(0x00B3));
		else if(charFunction.length > 3 && charFunction[3].equals(Fraction.NEGATIVEONE))
			output+= "-"+lambda+ new String(Character.toChars(0x00B3));
		else if(charFunction.length > 3 && !charFunction[3].equals(Fraction.ZERO))
			output+= charFunction[3]+lambda+new String(Character.toChars(0x00B3));		
		if(charFunction.length > 3 && !charFunction[3].equals(Fraction.ZERO))output+=" + ";
		if(charFunction[2].equals(Fraction.ONE))
			output+= lambda+ new String(Character.toChars(0x00B2));
		else if(charFunction[2].equals(Fraction.NEGATIVEONE))
			output+= "-"+lambda+ new String(Character.toChars(0x00B2));
		else if(!charFunction[2].equals(Fraction.ZERO))
			output+= charFunction[2]+lambda+new String(Character.toChars(0x00B2));	
		if(!charFunction[2].equals(Fraction.ZERO))output+=" + ";
		if(charFunction[1].equals(Fraction.ONE))
			output+= lambda;
		else if(charFunction[1].equals(Fraction.NEGATIVEONE))
			output+= "-"+lambda;
		else if(!charFunction[1].equals(Fraction.ZERO))
			output+= charFunction[1]+lambda;
		if(!charFunction[1].equals(Fraction.ZERO))output+=" + ";
		if(!charFunction[0].equals(Fraction.ZERO)) {
			output+=charFunction[0];
		}
		output+=" = 0";
		return output;
	}

	public static boolean[][] generateEigenArray(boolean[][] eigenMat, int ignoreRow, int ignoreCol) {
		boolean[][] recurseArray = new boolean[eigenMat.length -1][eigenMat[0].length -1];
		int addRow = 0;
		int addCol = 0;
		for(int row = 0;row < eigenMat.length; row++) 
			for(int col = 0;col < eigenMat[0].length; col++) {
				if(row != ignoreRow && col != ignoreCol) {
					recurseArray[addRow][addCol] = eigenMat[row][col];
					addCol++;
					if(addCol % (eigenMat[0].length-1) == 0) {
						addCol = 0;
						addRow++;
					}
				}
			}
		return recurseArray;
	}

	public static Fraction[][] generateFracArray(Fraction[][] eigenMat, int ignoreRow, int ignoreCol) {
		Fraction[][] recurseArray = new Fraction[eigenMat.length -1][eigenMat[0].length -1];
		int addRow = 0;
		int addCol = 0;
		for(int row = 0;row < eigenMat.length; row++) 
			for(int col = 0;col < eigenMat[0].length; col++) {
				if(row != ignoreRow && col != ignoreCol) {
					recurseArray[addRow][addCol] = eigenMat[row][col];
					addCol++;
					if(addCol % (eigenMat[0].length-1) == 0) {
						addCol = 0;
						addRow++;
					}
				}
			}
		return recurseArray;
	}

	public boolean isValidEVal(Fraction eVal) {
		Matrix indentity = new Matrix(this.matrixRow,this.matrixCol,false);
		for(int i = 0; i < this.matrixRow; i++)
			indentity.matrix[i][i] = eVal.multiply(Fraction.NEGATIVEONE);
		Matrix t = this.add(indentity);
		return (Matrix.determinant(t).equals(Fraction.ZERO));

	}

	public Vector[] generateEigenSpace(Fraction eVal) {
		if(isValidEVal(eVal)) {
			Matrix indentity = new Matrix(this.matrixRow,this.matrixCol,false);
			for(int i = 0; i < this.matrixRow; i++)
				indentity.matrix[i][i] = eVal.multiply(Fraction.NEGATIVEONE);
			Matrix t = this.add(indentity);
			Vector[] nulA = nulA(t);
			return nulA;
		}
		return null;
	}


}
