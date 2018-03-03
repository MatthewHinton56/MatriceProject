package application;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LinearCombinationCanvas extends Canvas{
	//Visualizes 2D and 3D linear combinations
	GraphicsContext gc;
	LinearCombination comb;
	int width, height;
	
	private  void generate2DAxis() {
		gc.setGlobalAlpha(1);
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		for(int i = -10; i <= 10; i++) {
			int x = ((width-10)/20)*(i+10);
			int y = ((height-10)/20)*(10-i)+20;
			gc.fillText(i+"", x, height/2);
			gc.fillText(i+"", width/2, y);
		}
		gc.strokeLine(0, height/2, width-1, height/2);
		gc.strokeLine(width/2, 0, width/2, height-1);
	}

	public LinearCombinationCanvas(LinearCombination comb, int width, int height) {
		super(width, height);
		this.comb = comb;
		this.width = width;
		this.height = height;
		this.gc = this.getGraphicsContext2D();
		if(comb.getDimension() == 2) {
			generate2DAxis();
			generate2DPlane();
			//generate2Dpoints();
		}
		gc.setFill(Color.BLACK);
		gc.fill();
	}

	private void generate2Dpoints() {
		
		for(Vector v: comb.getOutputs()) {
			double x = v.vector[0].getDecimal();
			double y = v.vector[1].getDecimal();
			 x = ((width-10)/20)*(x +10);
			 y = ((height-10)/20)*(10 - y)+20;
			 if(x >= width)
				 x = width-1;
			 if(x < 0)
				 x = 0;
			 if(y >= height)
				 y = height-1;
			 if(y < 0)
				 y = 0;
			gc.strokeLine(width/2, height/2, x, y);
		}
		
	}
	
	private void generate2DPlane() {
		gc.setFill(Color.RED);
		gc.setGlobalAlpha(.5);
		double[] pointsX = new double[4];
		double[] pointsY = new double[4];
		int pos = 0;
		for(Vector v: comb.getVectorEdges2D()) {
			double x = v.vector[0].getDecimal();
			double y = v.vector[1].getDecimal();
			 x = ((width-10)/20)*(x +10);
			 y = ((height-10)/20)*(10 - y)+20;
			 pointsX[pos] = x;
			 pointsY[pos] = y;
			 pos++;
		}
		gc.fillPolygon(pointsX, pointsY, 4);
		gc.setStroke(Color.RED);
		gc.setGlobalAlpha(1);
		gc.strokePolygon(pointsX, pointsY, 4);
		System.out.println(Arrays.toString(pointsX));
		System.out.println(Arrays.toString(pointsY));
	}
	
}
