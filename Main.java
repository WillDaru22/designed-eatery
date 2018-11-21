package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    Label greetingLabel = new Label("Hello World!");
			BorderPane root = new BorderPane();
			root.setTop(greetingLabel);
			Scene scene = new Scene(root,400,400);
			primaryStage.setTitle("First JavaFX Program");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
