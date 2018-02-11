package application;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MatrixScreen {

	public static final BorderPane mainPane = new BorderPane();
	public static final BorderPane pane = new BorderPane();
	public static final Scene scene = new Scene(mainPane,800,400);
	
	public static final VBox box = new VBox();
	
	public static final HBox matrixMaker = new HBox();
	//public static final ScrollPane MatrixMakerScrollPane = new ScrollPane();
	private static final TextField rows = new TextField();
	private static final Label rowsLabel = new Label("Rows:");
	private static final TextField cols = new TextField();
	private static final Label colsLabel = new Label("Columns:");
	public static final Button createMatrix = new Button("Create Matrix");
	
	public static final HBox matrixFunctions = new HBox();
	public static final ScrollPane MatrixFunctionScrollPane = new ScrollPane(matrixFunctions);
	public static final Button ref = new Button("ref");
	public static final Button rref = new Button("rref");
	public static TextField[][] matrixT;
	
	public static void setUp() {
		matrixFunctions.getChildren().addAll(ref,rref);
		MatrixFunctionScrollPane.setFitToHeight(true);
		MatrixFunctionScrollPane.setFitToWidth(true);
		pane.setBottom(matrixFunctions);
		
		pane.setCenter(box);
		matrixMaker.getChildren().add(rowsLabel);
		matrixMaker.getChildren().add(rows);
		matrixMaker.getChildren().add(colsLabel);
		matrixMaker.getChildren().add(cols);
		matrixMaker.getChildren().add(createMatrix);
		createMatrix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	box.getChildren().removeAll(box.getChildren());
            	int row = Integer.parseInt(rows.getText());
            	int col = Integer.parseInt(cols.getText());
            	matrixT = new TextField[row][col];
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
            	ScrollPane sp = new ScrollPane(grid);
            	sp.setFitToHeight(true);
            	sp.setFitToWidth(true);
            	box.getChildren().add(sp);
            	
            }
        });
		pane.setTop(matrixMaker);
		mainPane.setCenter(pane);
		mainPane.setLeft(new SideBar());
		
		ref.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            	Matrix matrix = new Matrix(mat,false);
            	matrix.ref();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            }
        });
		rref.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            	Matrix matrix = new Matrix(mat,false);
            	matrix.rref();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            }
        });
		
	}
}
