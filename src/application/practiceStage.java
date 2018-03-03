package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class practiceStage extends Application {


	public static Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		Fraction[] frac1 = new Fraction[2];
		frac1[0] = new Fraction("2");
		frac1[1] = Fraction.ONE;
		Vector v1 = new Vector(frac1);
		Fraction[] frac2 = new Fraction[2];
		frac2[1] = Fraction.ONE;
		frac2[0] = Fraction.ZERO;
		Vector v2 = new Vector(frac2);
		Vector[] vectors = new Vector[2];
		vectors[0] = v1;
		vectors[1] = v2;
		LinearCombination comb = new LinearCombination(vectors,-10,10);
		LinearCombinationCanvas canvas = new LinearCombinationCanvas(comb,400,400);
		Pane pane = new Pane();
		pane.getChildren().add(canvas);
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
