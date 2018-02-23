package application;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

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
	public static final Button L = new Button("L");
	public static final Button U = new Button("U");
	public static final Button det = new Button("Determinate");
	public static final Button inverse = new Button("Inverse");
	public static final TextField detField = new TextField("N/A");
	public static final Button prev = new Button("Prev");
	public static final Button save = new Button("Save");
	public static final Button load = new Button("Load");
	public static final Button clear = new Button("Clear");
	public static TextField[][] matrixT;
	public static final Deque<String[][]> prevMatrixStack = new ArrayDeque<String[][]>();
	public static String[][] saveMatrix;
	public static void setUp() {
		detField.setMinWidth(150);
		detField.setMaxWidth(150);
		matrixFunctions.getChildren().addAll(ref, rref, L, U, det, detField, inverse, prev, save, load, clear);
		MatrixFunctionScrollPane.setFitToHeight(true);
		MatrixFunctionScrollPane.setFitToWidth(true);
		MatrixFunctionScrollPane.setMinHeight(50);
		pane.setBottom(MatrixFunctionScrollPane);
		
		pane.setCenter(box);
		matrixMaker.getChildren().add(rowsLabel);
		matrixMaker.getChildren().add(rows);
		matrixMaker.getChildren().add(colsLabel);
		matrixMaker.getChildren().add(cols);
		matrixMaker.getChildren().add(createMatrix);
		createMatrix.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	prevMatrixStack.clear();
            	saveMatrix = null;
            	detField.setText("N/A");
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
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
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
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.rref();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            }
        });
		
		L.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.LU();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat.length; q++)
            			matrixT[i][q].setText(""+matrix.getL()[i][q]);
            	for(int i = 0; i < mat.length; i++)
            		for(int q = mat.length; q < mat[0].length; q++)
            			matrixT[i][q].setText("N/A");
            }
        });
		U.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.LU();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.getU()[i][q]);
            }
        });
		
		det.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	Matrix matrix = new Matrix(mat,false);
            	BigDecimal dec = Matrix.determinant(matrix);
            	detField.setText(dec.toString());
            }
        });
		
		inverse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	BigDecimal[][] mat = new BigDecimal[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new BigDecimal(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix = Matrix.inverse(matrix);
            	if(matrix != null) {
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat.length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            	} else {
            		for(int i = 0; i < mat.length; i++)
                		for(int q = 0; q < mat[0].length; q++)
                			matrixT[i][q].setText("N/A");
            	}
            }
        });
		
		prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(!prevMatrixStack.isEmpty()) {
            		String[][] prevMatrixT = prevMatrixStack.pop();
            		for(int i = 0; i < prevMatrixT.length; i++)
                		for(int q = 0; q < prevMatrixT[0].length; q++)
                			matrixT[i][q].setText(""+prevMatrixT[i][q]);
            	}
            }
        });
		
		load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(saveMatrix != null) {
            		for(int i = 0; i < saveMatrix.length; i++)
                		for(int q = 0; q < saveMatrix[0].length; q++)
                			matrixT[i][q].setText(""+saveMatrix[i][q]);
            	}
            }
        });
		
		save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String[][] mat = new String[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = matrixT[i][q].getText();
            		}
            	saveMatrix = mat;
            }
        });
		
		clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	for(int i = 0; i < matrixT.length; i++)
            		for(int q = 0; q < matrixT[0].length; q++) {
            			matrixT[i][q].setText("0");
            		}
            }
        });
	}
}
