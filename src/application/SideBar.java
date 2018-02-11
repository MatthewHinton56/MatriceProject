package application;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SideBar extends ToolBar{
	
	public SideBar() {
		VBox box = new VBox();
		ScrollPane sPane = new ScrollPane(box);
		Button matrix = new Button("Matrix");
		Button system = new Button("System");
		matrix.setOnAction(e -> MainScreen.stage.setScene(MatrixScreen.scene));
		system.setOnAction(e -> MainScreen.stage.setScene(SystemScreen.scene));
		box.getChildren().addAll(matrix,system);
		this.getItems().add(sPane);
	}
	
}
