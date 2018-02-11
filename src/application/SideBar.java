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
		matrix.setOnAction(e -> MainScreen.stage.setScene(MatrixScreen.scene));
		box.getChildren().add(matrix);
		this.getItems().add(sPane);
	}
	
}
