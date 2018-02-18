package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SystemScreen {

	public static final TextArea systemInput = new TextArea();
	public static final TextArea variableOutput = new TextArea();
	
	public static final VBox box = new VBox();
	
	private static final TextField variables = new TextField();
	private static final Label variablesLabel = new Label("# of Variables");
	private static final TextField equations = new TextField();
	private static final Label equationLabel = new Label("# of Equations");
	public static final Button solveSystem = new Button("Solve System");
	
	public static final RadioButton variableForm = new RadioButton("Variables");
	public static final RadioButton vectorForm = new RadioButton("Vectors");
	public static final ToggleGroup forms = new ToggleGroup();
	
	public static final BorderPane mainPane = new BorderPane();
	public static final BorderPane pane = new BorderPane();
	public static final BorderPane systemPane = new BorderPane();
	
	public static final Scene scene = new Scene(mainPane,800,400);
	
	public static void setUp() {
		box.getChildren().addAll(variablesLabel,variables,equationLabel,equations,solveSystem,variableForm,vectorForm);
		systemPane.setRight(box);
		systemPane.setCenter(systemInput);
		pane.setTop(systemPane);
		pane.setBottom(variableOutput);
		mainPane.setCenter(pane);
		mainPane.setLeft(new SideBar());
		
		variableForm.setToggleGroup(forms);
		variableForm.setUserData("Variables");
		vectorForm.setToggleGroup(forms);
		vectorForm.setUserData("Vectors");
		variableForm.setSelected(true);
		
		solveSystem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	String system = systemInput.getText();
            	int amountOfVariables = Integer.parseInt(variables.getText());
            	int amountOfEquations = Integer.parseInt(equations.getText());
            	Matrix matrix = new Matrix(system,amountOfVariables,amountOfEquations);
            	matrix.rref();
            	switch(forms.getSelectedToggle().getUserData().toString()) {
            	case "Variables": variableOutput.setText(matrix.variableAssignment()); break;
            	case "Vectors": variableOutput.setText(matrix.vectorForm()); break;
            	}
            	
            }
        });
	}
	
}
