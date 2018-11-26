package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	// @Override
	// public void start(Stage primaryStage) {
	// try {
	// BorderPane root = new BorderPane();
	// Scene scene = new Scene(root, 600, 400); // width, height
	// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	// primaryStage.setScene(scene);
	// primaryStage.show();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	TextArea logging;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Food Query and Meal Analysis");
		logging = new TextArea();
		logging.setMaxWidth(350);
		logging.setMaxHeight(350);
		Label foodLbl = new Label(" All Foods");

		// Button btn = new Button();
		// btn.setText("Say 'Hello World'");
		// btn.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(ActionEvent event) {
		// System.out.println("Hello World!");
		// }
		// });
		ObservableList<String> foodNames = FXCollections.observableArrayList("Apples", "Asparagus", "Bacon", "Bananas",
				"Celery", "Cranberries", "Eggs", "Gravy", "Green beans");
		ListView<String> listView = new ListView<String>(foodNames);
		listView.setPrefSize(150, 120);
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.getChildren().addAll(foodLbl, listView);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vbox);
		primaryStage.setScene(new Scene(hbox, 800, 400));
		// StackPane root = new StackPane();
		// root.getChildren().add(btn);
		// primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
