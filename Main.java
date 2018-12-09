/**
 * Filename:   Main.java
 * Project:    Team Project, Milestone2, main JavaFX GUI interface
 * Authors:    Anders Carlsson, William Wilson, Sungjin Park, Jay Park
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    001
 * 
 * Due Date:   Before 10pm on October 8, 2018
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       no known bugs, but not complete either
 */

package application;

import java.io.File;
import java.util.ArrayList;

/* imported JavaFX add-ons*/
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Food query and meal analysis program using graphic user interface.
 * 
 * @author Anders Carlsson, William Wilson, Sungjin Park, Jay Park
 *
 */
public class Main extends Application {
	private ArrayList<FoodItem> foodItemList;
	ArrayList<String> foodNameList;
	ObservableList<String> foodNames;
	ListView<String> listOfFoods;

	@Override
	public void start(Stage primaryStage) {
		FoodData foodData = new FoodData();

		// display title
		primaryStage.setTitle("Food Query and Meal Analysis");

		// display vertical box at top left
		VBox vbox1 = new VBox();
		vbox1.setMaxWidth(300);
		vbox1.setMinWidth(300);
		vbox1.setSpacing(12);

		// text in vertical box at topmost
		Text foodLbl = new Text("            All Foods");
		foodLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

		// food list in vertical box.
		foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
		foodNameList = new ArrayList<String>();
		for (int i = 0; i < foodItemList.size(); i++)
			foodNameList.add(foodItemList.get(i).getName());
		foodNames = FXCollections.observableArrayList(foodNameList);
		listOfFoods = new ListView<String>(foodNames);
		listOfFoods.setMaxWidth(300);
		listOfFoods.setMinWidth(300);
		listOfFoods.setMaxHeight(200);
		listOfFoods.setMinHeight(200);

		// horizontal box that filters by names and nutrients
		HBox filterHbox = new HBox();
		filterHbox.setSpacing(10);
		Label filterLbl = new Label("   Filter by: ");
		filterLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 20));
		Button nameFilter = new Button("Name");
		nameFilter.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		nameFilter.setMinWidth(75);
		nameFilter.setOnAction(new EventHandler<ActionEvent>() {

			/**
			 * event handle method on clicking name filter
			 */
			public void handle(ActionEvent event) {
				try {

					HBox nameBox = new HBox();
					nameBox.setPadding(new Insets(15, 12, 15, 12));
					nameBox.setSpacing(10);
					TextField nameFilterField = new TextField();
					Button confirmName = new Button("Filter");
					confirmName.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					confirmName.setPrefSize(100, 20);
					nameBox.getChildren().addAll(nameFilterField, confirmName);
					Scene scene = new Scene(nameBox, 260, 50);
					Stage stage = new Stage();
					stage.setTitle("Filter By Name");
					stage.setScene(scene);
					stage.show();
					confirmName.setOnAction(ActionEvent -> stage.close());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// nutrient button that filters by nutritional contents
		Button nutrientFilter = new Button("Nutrients");
		nutrientFilter.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		nutrientFilter.setMinWidth(75);
		nutrientFilter.setOnAction(new EventHandler<ActionEvent>() {

			// event handle method that enables user to filter through nutrients
			public void handle(ActionEvent event) {
				try {

					HBox nutrientBox = new HBox();
					nutrientBox.setPadding(new Insets(15, 12, 15, 12));
					nutrientBox.setSpacing(10);
					TextField nutrientName = new TextField();
					nutrientName.setPromptText("Nutrient");
					nutrientName.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					nutrientName.setMaxSize(120, 27);
					nutrientName.setMinSize(120, 27);
					TextField comparator = new TextField();
					comparator.setPromptText("Comparator");
					comparator.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					comparator.setMaxSize(120, 27);
					comparator.setMinSize(120, 27);
					TextField nutrientValue = new TextField();
					nutrientValue.setPromptText("Nutrient value");
					nutrientValue.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					nutrientValue.setMaxSize(120, 27);
					nutrientValue.setMinSize(120, 27);
					Button confirmNutrient = new Button("Filter");
					confirmNutrient.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					confirmNutrient.setMinSize(100, 27);
					nutrientBox.getChildren().addAll(nutrientName, comparator, nutrientValue, confirmNutrient);
					Scene scene = new Scene(nutrientBox, 520, 50);
					Stage stage = new Stage();
					stage.setTitle("Filter By Nutrient");
					stage.setScene(scene);
					stage.show();
					confirmNutrient.setOnAction(ActionEvent -> stage.close());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter);

		// additional buttons for modifying food / meal
		Button addToMealBtn = new Button("Add to Meal");
		addToMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		addToMealBtn.setMaxSize(300, 38);
		addToMealBtn.setMinSize(300, 38);
		Button addNewFoodBtn = new Button(" + Add New Food");
		addNewFoodBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		addNewFoodBtn.setMaxSize(300, 38);
		addNewFoodBtn.setMinSize(300, 38);

		Button loadFromFileBtn = new Button("Load from File");
		loadFromFileBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		loadFromFileBtn.setMaxSize(300, 38);
		loadFromFileBtn.setMinSize(300, 38);
		loadFromFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Open food data file");
					File selectedFile = fileChooser.showOpenDialog(primaryStage);
					foodData.loadFoodItems(selectedFile.getPath());
					foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
					foodNameList = new ArrayList<String>();
					for (int i = 0; i < foodItemList.size(); i++)
						foodNameList.add(foodItemList.get(i).getName());
					foodNames.clear();
					foodNames.setAll(FXCollections.observableArrayList(foodNameList));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button saveToFileBtn = new Button("Save to File");
		saveToFileBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		saveToFileBtn.setMaxSize(300, 38);
		saveToFileBtn.setMinSize(300, 38);
		saveToFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					HBox saveFileHbox = new HBox();
					saveFileHbox.setPadding(new Insets(15, 12, 15, 12));
					saveFileHbox.setSpacing(10);
					Label outputFileNameLbl = new Label("Name of output file");
					outputFileNameLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
					TextField inputField = new TextField();
					Button confirmSaveBtn = new Button("Save");
					confirmSaveBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 13));
					confirmSaveBtn.setMinSize(100, 27);
					saveFileHbox.getChildren().addAll(outputFileNameLbl, inputField, confirmSaveBtn);
					Scene scene = new Scene(saveFileHbox, 450, 50);
					Stage stage = new Stage();
					stage.setTitle("Save to file");
					stage.setScene(scene);
					stage.show();
					confirmSaveBtn.setOnAction(ActionEvent -> {
						stage.close();
						foodData.saveFoodItems(inputField.getText());
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		vbox1.getChildren().addAll(foodLbl, filterHbox, listOfFoods, addToMealBtn, addNewFoodBtn, loadFromFileBtn,
				saveToFileBtn);

		// Line which divides the "All Foods" section from the "Current Meal" section
		Line divider = new Line();
		divider.setStartX(200.0f);
		divider.setStartY(0.0f);
		divider.setEndX(200.0f);
		divider.setEndY(1000.0f);

		// Vbox for the "Current Meal" section
		VBox vbox2 = new VBox();
		vbox2.setMaxWidth(600);
		vbox2.setSpacing(12);

		// text for diaplying current meal
		Text currentMealLbl = new Text("                             Current Meal");
		currentMealLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

		HBox displayFoodNutritionHbox = new HBox();
		displayFoodNutritionHbox.setSpacing(20);

		VBox foodListAndRemoveVbox = new VBox();
		foodListAndRemoveVbox.setSpacing(12);
		foodListAndRemoveVbox.setMaxWidth(300);
		foodListAndRemoveVbox.setMinWidth(300);

		// food list for a meal. Hard coded at the moment. TODO: add more food / import
		// from file
		ObservableList<String> foodInMealNames = FXCollections.observableArrayList("Asparagus", "Cranberries", "Gravy",
				"Green beans", "Mashed potatoes", "Turkey");

		ListView<String> listOfFoodsInMeal = new ListView<String>(foodInMealNames);
		listOfFoodsInMeal.setMaxWidth(300);
		listOfFoodsInMeal.setMinWidth(300);
		listOfFoodsInMeal.setMaxHeight(391);
		listOfFoodsInMeal.setMinHeight(391);

		Button removeFoodFromMealBtn = new Button(" - Remove from Meal");
		removeFoodFromMealBtn.setPrefSize(300, 38);
		removeFoodFromMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

		foodListAndRemoveVbox.getChildren().addAll(listOfFoodsInMeal, removeFoodFromMealBtn);

		VBox nutritionVbox = new VBox();
		nutritionVbox.setSpacing(12);
		nutritionVbox.setMaxWidth(300);

		HBox nutritionForFoodHbox = new HBox();
		Label nutritionLbl = new Label("Nutrition for: ");
		nutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		Label foodNutritionLbl = new Label("Asparagus");
		foodNutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		nutritionForFoodHbox.getChildren().addAll(nutritionLbl, foodNutritionLbl);

		ObservableList<String> nutritionInEachFood = FXCollections.observableArrayList("Calories: 20", "Fat: 0",
				"Carbohydrates: 4", "Fiber: 2", "Protein: 60");

		ListView<String> nutritionList = new ListView<String>(nutritionInEachFood);
		nutritionList.setMaxWidth(320);
		nutritionList.setMinWidth(320);
		nutritionList.setMaxHeight(128);
		nutritionList.setMinHeight(128);

		Label totalNutritionLbl = new Label("Total Nutrition");
		totalNutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

		ObservableList<String> totalNutrition = FXCollections.observableArrayList("Calories: 2500", "Fat: 300",
				"Carbohydrates: 200", "Fiber: 30", "Protein: 60");

		ListView<String> totalNutritionList = new ListView<String>(totalNutrition);
		totalNutritionList.setMaxWidth(320);
		totalNutritionList.setMinWidth(320);
		totalNutritionList.setMaxHeight(128);
		totalNutritionList.setMinHeight(128);
		nutritionVbox.getChildren().addAll(nutritionForFoodHbox, nutritionList, totalNutritionLbl, totalNutritionList);
		displayFoodNutritionHbox.getChildren().addAll(foodListAndRemoveVbox, nutritionVbox);
		vbox2.getChildren().addAll(currentMealLbl, displayFoodNutritionHbox);

		HBox hbox = new HBox();
		hbox.setSpacing(20);
		hbox.getChildren().addAll(vbox1, divider, vbox2);
		primaryStage.setScene(new Scene(hbox, 1000, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
