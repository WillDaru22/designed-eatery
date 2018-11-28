package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Food Query and Meal Analysis");
		VBox vbox1 = new VBox();
		vbox1.setMaxWidth(200);
		vbox1.setMinWidth(200);
		vbox1.setSpacing(12);
		Text foodLbl = new Text("           All Foods");
		foodLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		ObservableList<String> foodNames = FXCollections.observableArrayList("Apples", "Asparagus", "Avacado", "Bacon",
				"Bananas", "Bok Choy", "Celery", "Chocolate", "Cranberries", "Eggs", "Fries", "Gravy", "Green beans",
				"Ham", "Ice cream", "Jelly", "Lemons", "Lobster", "Mashed potatoes", "Melons", "Noodles", "Pineapples",
				"Ribs", "Steak", "Turkey", "Yogurt");
		ListView<String> listOfFoods = new ListView<String>(foodNames);
		listOfFoods.setPrefSize(150, 120);
		HBox filterHbox = new HBox();
		filterHbox.setSpacing(10);
		Label filterLbl = new Label("Filter: ");
		Button nameFilter = new Button("Name");
		nameFilter.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        try {
		            
		            HBox nameBox = new HBox();
		            nameBox.setPadding(new Insets(15, 12, 15, 12));
		            nameBox.setSpacing(10);
		            TextField nameFilterField = new TextField();
		            Button confirmName = new Button("Filter");
		            confirmName.setPrefSize(100, 20);
		            nameBox.getChildren().addAll(nameFilterField, confirmName);
		            Scene scene = new Scene(nameBox, 260, 50);
		            Stage stage = new Stage();
		            stage.setTitle("Filter By Name");
		            stage.setScene(scene);
		            stage.show();
		            confirmName.setOnAction(ActionEvent -> stage.close());
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});
		//CheckBox nameFilter = new CheckBox("Name");
		//nameFilter.setIndeterminate(false);
		Button nutrientFilter = new Button("Nutrients");
		nutrientFilter.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		        try {
		            
		            HBox nutrientBox = new HBox();
		            nameBox.setPadding(new Insets(15, 12, 15, 12));
		            nameBox.setSpacing(10);
                    	    CheckBox vegetableFilter = new CheckBox("Vegetables");
                            vegetableFilter.setIndeterminate(false);
                            Button confirmNutrient = new Button("Filter");
                            confirmNutrient.setPrefSize(100, 20);
                            nutrientBox.getChildren().addAll(vegetableFilter, confirmNutrient);
		            Scene scene = new Scene(nutrientBox, 260, 50);
		            Stage stage = new Stage();
		            stage.setTitle("Filter By Nutrient");
		            stage.setScene(scene);
		            stage.show();
		            confirmNutrient.setOnAction(ActionEvent -> stage.close());
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});
		//CheckBox nutrientFilter = new CheckBox("Nutrients");
		//nutrientFilter.setIndeterminate(false);
		filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter);
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
		vbox1.getChildren().addAll(foodLbl, filterHbox, listOfFoods, addToMealBtn, addNewFoodBtn, removeFoodBtn,
				loadFromFileBtn, saveToFileBtn);
		Line divider = new Line();
		divider.setStartX(200.0f);
		divider.setStartY(0.0f);
		divider.setEndX(200.0f);
		divider.setEndY(400.0f);
		VBox vbox2 = new VBox();
		vbox2.setMaxWidth(600);
		vbox2.setSpacing(12);
		HBox currentMealHbox = new HBox();
		currentMealHbox.setSpacing(10);
		currentMealHbox.setMaxHeight(400);
		Text currentMealLbl = new Text("     Current Meal: ");
		currentMealLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		Button mealName = new Button("Thanksgiving");
		mealName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		currentMealHbox.getChildren().addAll(currentMealLbl, mealName);
		HBox displayFoodNutritionHbox = new HBox();
		displayFoodNutritionHbox.setSpacing(20);
		VBox foodListAndRemoveVbox = new VBox();
		foodListAndRemoveVbox.setSpacing(12);
		foodListAndRemoveVbox.setMaxWidth(200);
		ObservableList<String> foodInMealNames = FXCollections.observableArrayList("Asparagus", "Cranberries", "Gravy",
				"Green beans", "Mashed potatoes", "Turkey");
		ListView<String> listOfFoodsInMeal = new ListView<String>(foodInMealNames);
		listOfFoodsInMeal.setMaxWidth(200);
		listOfFoodsInMeal.setMinWidth(200);
		listOfFoodsInMeal.setMaxHeight(300);
		listOfFoodsInMeal.setMinHeight(300);
		Button removeFoodFromMealBtn = new Button(" - Remove from Meal");
		removeFoodFromMealBtn.setPrefSize(200, 20);
		foodListAndRemoveVbox.getChildren().addAll(listOfFoodsInMeal, removeFoodFromMealBtn);
		VBox nutritionVbox = new VBox();
		nutritionVbox.setSpacing(12);
		nutritionVbox.setMaxWidth(300);
		HBox nutritionForFoodHbox = new HBox();
		Label nutritionLbl = new Label("Nutrition for: ");
		Label foodNutritionLbl = new Label("Asparagus");
		nutritionForFoodHbox.getChildren().addAll(nutritionLbl, foodNutritionLbl);
		ObservableList<String> nutritionInEachFood = FXCollections.observableArrayList("Calories: 20", "Fat: 0",
				"Carbohydrates: 4", "Fiber: 2", "Protein: 60");
		ListView<String> nutritionList = new ListView<String>(nutritionInEachFood);
		nutritionList.setMaxWidth(320);
		nutritionList.setMinWidth(320);
		nutritionList.setMaxHeight(134);
		nutritionList.setMinHeight(134);
		Label totalNutritionLbl = new Label("Total Nutrition");
		ObservableList<String> totalNutrition = FXCollections.observableArrayList("Calories: 2500", "Fat: 300",
				"Carbohydrates: 200", "Fiber: 30", "Protein: 60");
		ListView<String> totalNutritionList = new ListView<String>(totalNutrition);
		totalNutritionList.setMaxWidth(320);
		totalNutritionList.setMinWidth(320);
		totalNutritionList.setMaxHeight(134);
		totalNutritionList.setMinHeight(134);
		nutritionVbox.getChildren().addAll(nutritionForFoodHbox, nutritionList, totalNutritionLbl, totalNutritionList);
		displayFoodNutritionHbox.getChildren().addAll(foodListAndRemoveVbox, nutritionVbox);
		vbox2.getChildren().addAll(currentMealHbox, displayFoodNutritionHbox);
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		hbox.getChildren().addAll(vbox1, divider, vbox2);
		primaryStage.setScene(new Scene(hbox, 800, 400));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
