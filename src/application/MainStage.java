package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

	public static Stage stage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		MatrixScreen.setUp();
		SystemScreen.setUp();
		FunctionScreen.setUp();
		CombinationScreen.setUp();
		primaryStage.setScene(MatrixScreen.scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
