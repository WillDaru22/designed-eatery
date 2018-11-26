package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Food Query and Meal Analysis");
		Text foodLbl = new Text("All Foods");
		foodLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		ObservableList<String> foodNames = FXCollections.observableArrayList("Apples", "Asparagus", "Bacon", "Bananas",
				"Celery", "Cranberries", "Eggs", "Gravy", "Green beans");
		ListView<String> listOfFoods = new ListView<String>(foodNames);
		listOfFoods.setPrefSize(150, 120);
		HBox filterHbox = new HBox();
		filterHbox.setSpacing(10);
		Label filterLbl = new Label("Filter: ");
		CheckBox nameFilter = new CheckBox("Name");
		nameFilter.setIndeterminate(false);
		CheckBox nutrientFilter = new CheckBox("Nutrients");
		nutrientFilter.setIndeterminate(false);
		filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter);
		VBox vbox = new VBox();
		vbox.setMaxWidth(200);
		vbox.setSpacing(12);
		Button addToMealBtn = new Button("Add to Meal");
		addToMealBtn.setPrefSize(200, 20);
		Button addNewFoodBtn = new Button(" + Add New Food");
		addNewFoodBtn.setPrefSize(200, 20);
		Button removeFoodBtn = new Button(" - Remove Food");
		removeFoodBtn.setPrefSize(200, 20);
		Button loadFromFileBtn = new Button("Load from File");
		loadFromFileBtn.setPrefSize(200, 20);
		Button saveToFileBtn = new Button("Save to File");
		saveToFileBtn.setPrefSize(200, 20);
		vbox.getChildren().addAll(foodLbl, filterHbox, listOfFoods, addToMealBtn, addNewFoodBtn, removeFoodBtn,
				loadFromFileBtn, saveToFileBtn);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(vbox);
		primaryStage.setScene(new Scene(hbox, 800, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
