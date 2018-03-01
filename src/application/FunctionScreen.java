package application;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FunctionScreen {
	
	public static final BorderPane mainPane = new BorderPane();
	public static final BorderPane pane = new BorderPane();
	public static final Scene scene = new Scene(mainPane,1000,400);
	
	public static final VBox box = new VBox();
	
	public static final HBox matrixMaker = new HBox();
	//public static final ScrollPane MatrixMakerScrollPane = new ScrollPane();
	private static final TextField rows = new TextField();
	private static final Label rowsLabel = new Label("Rows:");
	private static final TextField cols = new TextField();
	private static final Label colsLabel = new Label("Cols:");
	public static final Button createMatrix = new Button("Create Matrix");
	public static final Button createFunction = new Button("Create Function");
	
	public static final HBox matrixFunctions = new HBox();
	public static final ScrollPane MatrixFunctionScrollPane = new ScrollPane(matrixFunctions);
	
	public static final Label onto = new Label("Onto ");
	public static final Label oneToOne = new Label("OneToOne");
	
	public static final Button existence = new Button("Is C in Range?");
	public static final Label existenceLabel = new Label("C is in Range ");
	
	public static final Button generateInput = new Button("generate Input for c");
	public static int row, col;
	public static final Button evaluate = new Button("Evaulate function using V");
	public static MatrixFunction function;
	public static TextField[][] matrixT;
	
	public static void setUp() {
		matrixFunctions.getChildren().addAll(onto, oneToOne, existence, existenceLabel, generateInput, evaluate);
		existence.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(function != null) {
					Vector v = new Vector(row);
					for(int rowO = 0; rowO < row; rowO++) {
						v.vector[rowO] = new Fraction(matrixT[rowO][col+1].getText());
					}
					if(function.isCinRange(v)) {
						existenceLabel.setStyle("-fx-background-color:red");
					}
				}
				
			}
		
		});
		
		generateInput.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(function != null) {
					Vector v = new Vector(row);
					for(int rowO = 0; rowO < row; rowO++) {
						v.vector[rowO] = new Fraction(matrixT[rowO][col+1].getText());
					}
					String[] input = function.generateInputForC(v);
					for(int row = 0; row < input.length; row++) {
						matrixT[row][col].setText(input[row]);
					}
				}
				
			}
		
		});
		
		evaluate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(function != null) {
					Vector v = new Vector(col);
					for(int row = 0; row < col; row++) {
						v.vector[row] = new Fraction(matrixT[row][col].getText());
					}
					v = function.evaluate(v);
					String[] output = v.getVector();
					for(int rowO = 0; rowO < row; rowO++) {
						matrixT[rowO][col+1].setText(output[rowO]);
					}
				}
				
			}
		
		});
		
		
		MatrixFunctionScrollPane.setFitToHeight(true);
		MatrixFunctionScrollPane.setFitToWidth(true);
		pane.setBottom(matrixFunctions);
		pane.setCenter(box);
		matrixMaker.getChildren().add(rowsLabel);
		matrixMaker.getChildren().add(rows);
		matrixMaker.getChildren().add(colsLabel);
		matrixMaker.getChildren().add(cols);
		matrixMaker.getChildren().add(createMatrix);
		matrixMaker.getChildren().add(createFunction);
		createMatrix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	function = null;
            		onto.setStyle("-fx-background-color:white");
            		oneToOne.setStyle("-fx-background-color:white");
            	box.getChildren().removeAll(box.getChildren());
            	row = Integer.parseInt(rows.getText());
            	col = Integer.parseInt(cols.getText());
            	matrixT = new TextField[Math.max(row, col)][col+2];
            	GridPane grid = new GridPane();
            	for(int i = 0; i < row; i++)
            		for(int q = 0; q < col; q++)
            		{ 
            			TextField textField = new TextField("0");
            			textField.setMinWidth(50);
            			textField.setMinHeight(50);
            			grid.add(textField, q, i);
            			matrixT[i][q] = textField;
            		}
            	for(int spacer = 0; spacer < col; spacer++)
            		grid.add(new Label(" "), col, spacer);
            	for(int vectorV = 0; vectorV < col; vectorV++) {
            		TextField textField = new TextField("0");
        			textField.setMinWidth(50);
        			textField.setMinHeight(50);
        			grid.add(textField, col+1, vectorV);
        			matrixT[vectorV][col] = textField;
            	}
            	for(int arrow = 0; arrow < col; arrow++)
            		grid.add(new Label(">"), col+2, arrow);
            	
            	for(int vectorV = 0; vectorV < row; vectorV++) {
            		TextField textField = new TextField("0");
        			textField.setMinWidth(50);
        			textField.setMinHeight(50);
        			grid.add(textField, col+3, vectorV);
        			matrixT[vectorV][col+1] = textField;
            	}
            	
            	ScrollPane sp = new ScrollPane(grid);
            	sp.setFitToHeight(true);
            	sp.setFitToWidth(true);
            	box.getChildren().add(sp);	
            }
        });
		
		createFunction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Fraction[][] mat = new Fraction[row][col];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
            	function = new MatrixFunction(mat);
            	if(function.onto)
            		onto.setStyle("-fx-background-color:red");
            	if(function.oneToOne)
            		oneToOne.setStyle("-fx-background-color:red");
            }
            	
         });
		
		pane.setTop(matrixMaker);
		mainPane.setCenter(pane);
		mainPane.setLeft(new SideBar());
	}
}
