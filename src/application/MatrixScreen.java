package application;

import java.util.ArrayDeque;
import java.util.ArrayList;
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
	public static final Button det = new Button("Det");
	public static final Button inverse = new Button("Inverse");
	public static final TextField detField = new TextField("N/A");
	public static final Button prev = new Button("Prev");
	public static final Button save = new Button("Save");
	public static final Button load = new Button("Load");
	public static final Button clear = new Button("Clear");
	public static final Button swap = new Button("Swap");
	public static final Button scale = new Button("Scale");
	public static final Button add = new Button("Add");
	public static TextField[][] matrixT;
	public static final Deque<String[][]> prevMatrixStack = new ArrayDeque<String[][]>();
	private static final double BUTTONWIDTH = 75;
	public static String[][] saveMatrix;
	public static void setUp() {
		detField.setMinWidth(150);
		detField.setMaxWidth(150);
		matrixFunctions.getChildren().addAll(swap,scale, add,ref, rref, L, U, det, detField, inverse, prev, save, load, clear);
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
		ref.setMinWidth(BUTTONWIDTH);
		ref.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.ref();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            	}
            }
        });
		rref.setMinWidth(BUTTONWIDTH);
		rref.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.rref();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.matrix[i][q]);
            }
            }
        });
		L.setMinWidth(BUTTONWIDTH);
		L.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
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
            }
        });
		U.setMinWidth(BUTTONWIDTH);
		U.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	prevMatrixStack.add(prevMatrixT);
            	Matrix matrix = new Matrix(mat,false);
            	matrix.LU();
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++)
            			matrixT[i][q].setText(""+matrix.getU()[i][q]);
            }
            }
        });
		det.setMinWidth(BUTTONWIDTH);
		det.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
            			prevMatrixT[i][q] = matrixT[i][q].getText();
            		}
            	Matrix matrix = new Matrix(mat,false);
            	Fraction dec = Matrix.determinant(matrix);
            	detField.setText(dec.toString());
            }
            }
        });
		inverse.setMinWidth(BUTTONWIDTH);
		inverse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
            	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = new Fraction(matrixT[i][q].getText());
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
            }
        });
		prev.setMinWidth(BUTTONWIDTH);
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
		load.setMinWidth(BUTTONWIDTH);
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
		save.setMinWidth(BUTTONWIDTH);
		save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	String[][] mat = new String[matrixT.length][matrixT[0].length];
            	for(int i = 0; i < mat.length; i++)
            		for(int q = 0; q < mat[0].length; q++) {
            			mat[i][q] = matrixT[i][q].getText();
            		}
            	saveMatrix = mat;
            	}
            }
        });
		clear.setMinWidth(BUTTONWIDTH);
		clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	for(int i = 0; i < matrixT.length; i++)
            		for(int q = 0; q < matrixT[0].length; q++) {
            			matrixT[i][q].setText("0");
            		}
            	}
            }
        });
		swap.setMinWidth(BUTTONWIDTH);
		swap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	new SwapPopUp();
            	}
            }
        });
		
		scale.setMinWidth(BUTTONWIDTH);
		scale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            	new ScalePopUp();
            	}
            }
        });
		
		add.setMinWidth(BUTTONWIDTH);
		add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	if(matrixT != null) {
            		new AddPopUp();
            	}
            }
        });
	}
	
	private static class SwapPopUp extends Stage implements EventHandler<ActionEvent> {
		private ComboBox<String> row1;
		private ComboBox<String> row2;
		private Matrix matrix;
		public SwapPopUp() {
			String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
        	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
        	for(int i = 0; i < mat.length; i++)
        		for(int q = 0; q < mat[0].length; q++) {
        			mat[i][q] = new Fraction(matrixT[i][q].getText());
        			prevMatrixT[i][q] = matrixT[i][q].getText();
        		}
        	prevMatrixStack.add(prevMatrixT);
        	matrix = new Matrix(mat,false);
        	String[] numbers = new String[matrix.matrixRow];
			for(int i = 0;i < matrix.matrixRow; i++) {
				numbers[i] = ""+(i+1);
			}
			row1 = new ComboBox<String>(FXCollections.observableArrayList(numbers));
			row1.setPromptText("Row 1");
			row2 = new ComboBox<String>(FXCollections.observableArrayList(numbers));
			row2.setPromptText("Row 2");
			Button button = new Button("Swap");
			button.setOnAction(this);
			//Panel panel = new JPanel();
			HBox box = new HBox();
			box.getChildren().addAll(row1,row2,button);
			this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(MainStage.stage);
            Scene thisScene = new Scene(box, 600, 50);
            this.setScene(thisScene);
            this.show();
		}

		@Override
		public void handle(ActionEvent event) {
			int row1Value = Integer.parseInt(row1.getValue())-1;
			int row2Value = Integer.parseInt(row2.getValue())-1;
			matrix.swap(row1Value, row2Value);
			for(int i = 0; i < matrix.matrixRow; i++)
        		for(int q = 0; q < matrix.matrixCol; q++)
        			matrixT[i][q].setText(""+matrix.matrix[i][q]);
		}
	}
	
	private static class AddPopUp extends Stage implements EventHandler<ActionEvent> {
		private ComboBox<String> row1;
		private ComboBox<String> row2;
		private Matrix matrix;
		private TextField scale;
		public AddPopUp() {
			String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
        	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
        	for(int i = 0; i < mat.length; i++)
        		for(int q = 0; q < mat[0].length; q++) {
        			mat[i][q] = new Fraction(matrixT[i][q].getText());
        			prevMatrixT[i][q] = matrixT[i][q].getText();
        		}
        	prevMatrixStack.add(prevMatrixT);
        	matrix = new Matrix(mat,false);
        	String[] numbers = new String[matrix.matrixRow];
			for(int i = 0;i < matrix.matrixRow; i++) {
				numbers[i] = ""+(i+1);
			}
			row1 = new ComboBox<String>(FXCollections.observableArrayList(numbers));
			row1.setPromptText("Row 1");
			row2 = new ComboBox<String>(FXCollections.observableArrayList(numbers));
			row2.setPromptText("Row 2");
			scale = new TextField();
			scale.setPromptText("Scale");
			Button button = new Button("Swap");
			button.setOnAction(this);
			//Panel panel = new JPanel();
			HBox box = new HBox();
			box.getChildren().addAll(row1,row2,scale,button);
			this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(MainStage.stage);
            Scene thisScene = new Scene(box, 600, 50);
            this.setScene(thisScene);
            this.show();
		}

		@Override
		public void handle(ActionEvent event) {
			int row1Value = Integer.parseInt(row1.getValue())-1;
			int row2Value = Integer.parseInt(row2.getValue())-1;
			try {
			Fraction fraction = new Fraction(scale.getText());
			matrix.addRows(row1Value, row2Value, fraction);
			} catch(NumberFormatException e) {System.out.println("Error");}
			for(int i = 0; i < matrix.matrixRow; i++)
        		for(int q = 0; q < matrix.matrixCol; q++)
        			matrixT[i][q].setText(""+matrix.matrix[i][q]);
		}
	}
	
	private static class ScalePopUp extends Stage implements EventHandler<ActionEvent> {
		private ComboBox<String> row1;
		private Matrix matrix;
		private TextField scale;
		public ScalePopUp() {
			String[][] prevMatrixT = new String[matrixT.length][matrixT[0].length];
        	Fraction[][] mat = new Fraction[matrixT.length][matrixT[0].length];
        	for(int i = 0; i < mat.length; i++)
        		for(int q = 0; q < mat[0].length; q++) {
        			mat[i][q] = new Fraction(matrixT[i][q].getText());
        			prevMatrixT[i][q] = matrixT[i][q].getText();
        		}
        	prevMatrixStack.add(prevMatrixT);
        	matrix = new Matrix(mat,false);
        	String[] numbers = new String[matrix.matrixRow];
			for(int i = 0;i < matrix.matrixRow; i++) {
				numbers[i] = ""+(i+1);
			}
			row1 = new ComboBox<String>(FXCollections.observableArrayList(numbers));
			row1.setPromptText("Row 1");
			scale = new TextField();
			scale.setPromptText("Scale");
			Button button = new Button("Swap");
			button.setOnAction(this);
			//Panel panel = new JPanel();
			HBox box = new HBox();
			box.getChildren().addAll(row1,scale,button);
			this.initModality(Modality.APPLICATION_MODAL);
            this.initOwner(MainStage.stage);
            Scene thisScene = new Scene(box, 600, 50);
            this.setScene(thisScene);
            this.show();
		}

		@Override
		public void handle(ActionEvent event) {
			int row1Value = Integer.parseInt(row1.getValue())-1;
			try {
				Fraction fraction = new Fraction(scale.getText());
				matrix.scalarMultipication(row1Value, fraction);
				} catch(NumberFormatException e) {System.out.println("Error");}
			for(int i = 0; i < matrix.matrixRow; i++)
        		for(int q = 0; q < matrix.matrixCol; q++)
        			matrixT[i][q].setText(""+matrix.matrix[i][q]);
		}
	}
	
}
