package application;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CombinationScreen {

	public static final BorderPane mainPane = new BorderPane();
	public static final BorderPane pane = new BorderPane();
	public static final Scene scene = new Scene(mainPane,1000,400);
	
	public static final VBox box = new VBox();
	
	public static final HBox matrixMaker = new HBox();
	//public static final ScrollPane MatrixMakerScrollPane = new ScrollPane();
	private static final TextField dimension = new TextField();
	private static final Label dimensionLabel = new Label("Dimension:");
	private static final TextField amountOfVectors = new TextField();
	private static final Label amountOfVectorsLabel = new Label("Amount of Vectors:");
	public static final Button generateCombination = new Button("Create Fields");
	public static final Button createCombination = new Button("Create Combination");
	
	public static final HBox combinationFunctions = new HBox();
	public static final ScrollPane combinationFunctionScrollPane = new ScrollPane(combinationFunctions);
	
	public static final Label independent = new Label("Independent ");
	public static final Button existence = new Button("Is C in Range?");
	public static final Label existenceLabel = new Label("C is in Range ");
	
	public static final Button generateInput = new Button("generate Input for c");
	public static final Button evaluate = new Button("Evaulate function using V");
	
	public static final Button visualize = new Button("Visualize");
	public static LinearCombination combo;
	public static TextField[][] matrixT;
	public static TextField[] coeffiecents;
	private static final double BUTTONWIDTH = 75;
	public static int dimensionCount, vectorCount;
	public static void setUp() {
		//detField.setMinWidth(150);
		//detField.setMaxWidth(150);
		dimensionLabel.setMinWidth(75);
		amountOfVectorsLabel.setMinWidth(75);
		combinationFunctions.getChildren().addAll(independent, existence,existenceLabel,generateInput,evaluate,visualize);
		combinationFunctionScrollPane.setFitToHeight(true);
		combinationFunctionScrollPane.setFitToWidth(true);
		combinationFunctionScrollPane.setMinHeight(50);
		pane.setBottom(combinationFunctionScrollPane);
		
		pane.setCenter(box);
		matrixMaker.getChildren().add(dimensionLabel);
		matrixMaker.getChildren().add(dimension);
		matrixMaker.getChildren().add(amountOfVectorsLabel);
		matrixMaker.getChildren().add(amountOfVectors);
		matrixMaker.getChildren().add(generateCombination);
		matrixMaker.getChildren().add(createCombination);
		generateCombination.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	combo = null;
        		independent.setStyle("-fx-background-color:white");
        		existenceLabel.setStyle("-fx-background-color:white");
        	box.getChildren().removeAll(box.getChildren());
        	dimensionCount = Integer.parseInt(dimension.getText());
        	vectorCount = Integer.parseInt(amountOfVectors.getText());
        	matrixT = new TextField[Math.max(dimensionCount, vectorCount)][vectorCount+1];
        	coeffiecents = new TextField[vectorCount];
        	GridPane grid = new GridPane();
        	
        		for(int q = 0; q < vectorCount; q++)
        		{ 
        			TextField textField = new TextField("0");
        			textField.setMinWidth(50);
        			textField.setMinHeight(50);
        			grid.add(textField, 3*q, dimensionCount/2);
        			coeffiecents[q] = textField;
        		}
        	
        	
        	for(int i = 0; i < dimensionCount; i++)
        		for(int q = 0; q < vectorCount; q++)
        		{ 
        			TextField textField = new TextField("0");
        			textField.setMinWidth(50);
        			textField.setMinHeight(50);
        			grid.add(textField, 3*q+1, i);
        			matrixT[i][q] = textField;
        		}
        	for(int i = 0; i < dimensionCount; i++)
        		for(int q = 1; q <= vectorCount-1; q++)
        		{ 
        			Label textField = new Label("+");
        			textField.setMinWidth(20);
        			textField.setMinHeight(50);
        			grid.add(textField, -1+3*q, i);
        		}
        	for(int ardimensionCount = 0; ardimensionCount < vectorCount; ardimensionCount++) {
        		Label label = new Label(">");
        		label.setMinWidth(20);
        		grid.add(label, vectorCount*3-1, ardimensionCount);
        	}
        	
        	for(int vectorV = 0; vectorV < dimensionCount; vectorV++) {
        		TextField textField = new TextField("0");
    			textField.setMinWidth(50);
    			textField.setMinHeight(50);
    			grid.add(textField, vectorCount*3, vectorV);
    			matrixT[vectorV][vectorCount] = textField;
        	}
        	
        	ScrollPane sp = new ScrollPane(grid);
        	sp.setFitToHeight(true);
        	sp.setFitToWidth(true);
        	box.getChildren().add(sp);	
            	
            }
        });
		
		createCombination.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Vector[] mat = new Vector[vectorCount];
            		for(int q = 0; q < mat.length; q++) {
            			mat[q] = new Vector(dimensionCount);
            			for(int i = 0; i < mat[0].getNumRows(); i++) {
            			mat[q].vector[i] = new Fraction(matrixT[i][q].getText());
            			}
            		}
            	combo = new LinearCombination(mat);
            	if(combo.linearIndepenent()) {
            		independent.setStyle("-fx-background-color:red");
            	}
            	else {
            		independent.setStyle("-fx-background-color:white");
            	}
            }
            	
         });
		
		existence.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(combo != null) {
				Vector v = new Vector(dimensionCount);
				for(int dimensionCountO = 0; dimensionCountO < dimensionCount; dimensionCountO++) {
					v.vector[dimensionCountO] = new Fraction(matrixT[dimensionCountO][vectorCount].getText());
				}
				if(combo.isCinRange(v)) {
					existenceLabel.setStyle("-fx-background-color:red");
				} else {
					existenceLabel.setStyle("-fx-background-color:white");
				}
			}
			
		}
	
	});
	
	generateInput.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(combo != null) {
				Vector v = new Vector(dimensionCount);
				for(int dimensionCountO = 0; dimensionCountO < dimensionCount; dimensionCountO++) {
					v.vector[dimensionCountO] = new Fraction(matrixT[dimensionCountO][vectorCount].getText());
				}
				String[] input = combo.generateInputForC(v);
				for(int i = 0; i < input.length; i++) {
					coeffiecents[i].setText(input[i]);
				}
			}
			
		}
	
	});
	
	evaluate.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(combo != null) {
				Vector v = new Vector(vectorCount);
				for(int i = 0; i < vectorCount; i++) {
					v.vector[i] = new Fraction(coeffiecents[i].getText());
				}
				v = combo.evaluate(v);
				String[] output = v.getVector();
				for(int dimensionCountO = 0; dimensionCountO < dimensionCount; dimensionCountO++) {
					matrixT[dimensionCountO][vectorCount].setText(output[dimensionCountO]);
				}
			}
			
		}
	
	});
		
	visualize.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			if(combo != null && combo.getDimension() == 2) {
				new VisualizationStage(combo);
			}
			
		}
	
	});
		
		pane.setTop(matrixMaker);
		mainPane.setCenter(pane);
		mainPane.setLeft(new SideBar());
	}
	
	
	private static class VisualizationStage extends Stage {

		

		public VisualizationStage(LinearCombination combo) {
			LinearCombinationCanvas canvas = new LinearCombinationCanvas(combo, 400, 400);
			//Panel panel = new JPanel();
			HBox box = new HBox();
			box.getChildren().add(canvas);
			this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(MainStage.stage);
            Scene thisScene = new Scene(box, 400, 400);
            this.setScene(thisScene);
            this.show();
		}
	}
}