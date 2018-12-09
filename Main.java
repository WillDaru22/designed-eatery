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
import javafx.scene.layout.BorderPane;
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

	private ArrayList<FoodItem> foodItemList; // List which stores the FoodItem objects in the "All Foods" list
	private ArrayList<String> foodNameList; // List which stores the names of the FoodItem objects in the "All Foods"
											// list
	private ObservableList<String> foodNames; // An observable list of the food names. When this list is updated the GUI
												// will update to show changes
	private ObservableList<String> foodInMealNames; // An observable list of the food in meal names. When this list is
													// updated the GUI will update to show changes
	private ObservableList<String> nutritionInEachFood; // An observable list of the nutrition in the selected food.
														// When this list is updated the GUI will update to show changes
	private ObservableList<String> totalNutrition; // An observable list of the nutrition in the whole meal. When this
													// list is updated the GUI will update to show changes

	/*
	 * Creates the GUI and responds to user input.
	 */
	@Override
	public void start(Stage primaryStage) {
		// Creates a new FoodData object
		FoodData foodData = new FoodData();

		// Displays title
		primaryStage.setTitle("Food Query and Meal Analysis");

		// Displays "All Foods" label in the top left
		Text foodLbl = new Text("            All Foods");
		foodLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

		// Sets up the "All Foods" list
		foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
		foodNameList = new ArrayList<String>();
		for (int i = 0; i < foodItemList.size(); i++)
			foodNameList.add(foodItemList.get(i).getName());
		foodNames = FXCollections.observableArrayList(foodNameList);
		ListView<String> listOfFoods = new ListView<String>(foodNames);
		listOfFoods.setMaxWidth(300);
		listOfFoods.setMinWidth(300);
		listOfFoods.setMaxHeight(200);
		listOfFoods.setMinHeight(200);

		// Displays the filter label
		Label filterLbl = new Label("   Filter by: ");
		filterLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 20));

		// Button to filter by name
		Button nameFilter = new Button("Name");
		nameFilter.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		nameFilter.setMinWidth(75);
		nameFilter.setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Handles clicking on the name filter
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

		// Button to filter by nutrition contents
		// TODO: Implement: update the list of all foods when the filter is typed in
		Button nutrientFilter = new Button("Nutrients");
		nutrientFilter.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		nutrientFilter.setMinWidth(75);
		nutrientFilter.setOnAction(new EventHandler<ActionEvent>() {
			/*
			 * Handles clicking on the nutrient filter
			 */
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

		// Button to add the selected food from the foods list to the meal list
		// TODO: Implement: when the user selects the food from the list and clicks this
		// button, the food in meal list button will contain that food
		Button addToMealBtn = new Button("Add to Meal");
		addToMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		addToMealBtn.setMaxSize(300, 38);
		addToMealBtn.setMinSize(300, 38);

		// Button to add a new food to the foods list
		Button addNewFoodBtn = new Button(" + Add New Food");
		addNewFoodBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		addNewFoodBtn.setMaxSize(300, 38);
		addNewFoodBtn.setMinSize(300, 38);
		addNewFoodBtn.setOnAction(new EventHandler<ActionEvent>() {
			/*
			 * Handles clicking on the "Add New Food" button
			 */
			public void handle(ActionEvent event) {
				try {
					BorderPane foodBox = new BorderPane();
					foodBox.setPadding(new Insets(15, 12, 15, 12));
					VBox foodNutrients = new VBox();
					foodNutrients.setPadding(new Insets(15, 12, 15, 12));
					foodNutrients.setSpacing(10);
					TextField foodId = new TextField();
					foodId.setPromptText("ID of the food");
					TextField foodName = new TextField();
					foodName.setPromptText("Name of the food");
					TextField calorieCount = new TextField();
					calorieCount.setPromptText("Number of calories");
					TextField fatCount = new TextField();
					fatCount.setPromptText("Grams of fat");
					TextField carbCount = new TextField();
					carbCount.setPromptText("Grams of carbohydrates");
					TextField fiberCount = new TextField();
					fiberCount.setPromptText("Grams of fiber");
					TextField proteinCount = new TextField();
					proteinCount.setPromptText("Grams of protein");
					foodNutrients.getChildren().addAll(foodId, foodName, calorieCount, fatCount, carbCount, fiberCount,
							proteinCount);
					Button confirmFood = new Button("Add food");
					confirmFood.setMinSize(100, 27);
					foodBox.setLeft(foodNutrients);
					foodBox.setCenter(confirmFood);
					Scene scene = new Scene(foodBox, 300, 300);
					Stage stage = new Stage();
					stage.setTitle("Add New Food");
					stage.setScene(scene);
					stage.show();
					confirmFood.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								FoodItem newFood = new FoodItem(foodId.getText(), foodName.getText());
								newFood.addNutrient(new String("calories"),
										new Double(Double.parseDouble(calorieCount.getText())));
								newFood.addNutrient(new String("fat"),
										new Double(Double.parseDouble(fatCount.getText())));
								newFood.addNutrient(new String("carbohydrate"),
										new Double(Double.parseDouble(carbCount.getText())));
								newFood.addNutrient(new String("fiber"),
										new Double(Double.parseDouble(fiberCount.getText())));
								newFood.addNutrient(new String("protein"),
										new Double(Double.parseDouble(proteinCount.getText())));
								foodData.addFoodItem(newFood);
								foodItemList.clear();
								foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
								foodNames.setAll(FXCollections.observableArrayList(foodNameList));
								foodNameList.clear();
								for (int i = 0; i < foodItemList.size(); i++)
									foodNameList.add(foodItemList.get(i).getName());
								foodNames.clear();
								foodNames.setAll(FXCollections.observableArrayList(foodNameList));
								stage.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Button to load foods to the foods list from a data file
		Button loadFromFileBtn = new Button("Load from File");
		loadFromFileBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		loadFromFileBtn.setMaxSize(300, 38);
		loadFromFileBtn.setMinSize(300, 38);
		loadFromFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			/*
			 * Handles clicking on the "Load from File" button
			 */
			public void handle(ActionEvent event) {
				try {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Open food data file");
					File selectedFile = fileChooser.showOpenDialog(primaryStage);
					foodData.loadFoodItems(selectedFile.getPath());
					foodItemList.clear();
					foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
					foodNameList.clear();
					for (int i = 0; i < foodItemList.size(); i++)
						foodNameList.add(foodItemList.get(i).getName());
					foodNames.clear();
					foodNames.setAll(FXCollections.observableArrayList(foodNameList));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Button to save foods from the foods list to a designated file
		Button saveToFileBtn = new Button("Save to File");
		saveToFileBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
		saveToFileBtn.setMaxSize(300, 38);
		saveToFileBtn.setMinSize(300, 38);
		saveToFileBtn.setOnAction(new EventHandler<ActionEvent>() {
			/*
			 * Handles clicking on the "Save to File" button
			 */
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

		// Displays "Current Meal" label in the top right
		Text currentMealLbl = new Text("                             Current Meal");
		currentMealLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

		// Sets up the food in meal list
		// TODO: Implement: update when a food is added or removed from the list
		foodInMealNames = FXCollections.observableArrayList("Asparagus", "Cranberries", "Gravy", "Green beans",
				"Mashed potatoes", "Turkey");
		ListView<String> listOfFoodsInMeal = new ListView<String>(foodInMealNames);
		listOfFoodsInMeal.setMaxWidth(300);
		listOfFoodsInMeal.setMinWidth(300);
		listOfFoodsInMeal.setMaxHeight(391);
		listOfFoodsInMeal.setMinHeight(391);

		// Button which removes the selected food from the food in meal list
		// TODO: Implement: when a food is selected from the food in meal list and this
		// button is clicked, remove the food from the list
		Button removeFoodFromMealBtn = new Button(" - Remove from Meal");
		removeFoodFromMealBtn.setPrefSize(300, 38);
		removeFoodFromMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

		// Displays the "Nutrition for:" label
		Label nutritionLbl = new Label("Nutrition for: ");
		nutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

		// Displays the selected food from the food in meal list
		// TODO: Implement: when a food is selected from the food in meal list the name
		// of the food will be displayed
		Label foodNutritionLbl = new Label("Asparagus");
		foodNutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 20));

		// Sets up the nutrition in selected food list
		// TODO: Implement: when a food is selected from the food in meal list the
		// nutrition for that food will display here
		ObservableList<String> nutritionInEachFood = FXCollections.observableArrayList("Calories: 20", "Fat: 0",
				"Carbohydrates: 4", "Fiber: 2", "Protein: 60");
		ListView<String> nutritionList = new ListView<String>(nutritionInEachFood);
		nutritionList.setMaxWidth(320);
		nutritionList.setMinWidth(320);
		nutritionList.setMaxHeight(128);
		nutritionList.setMinHeight(128);

		// Displays the "Total Nutrition" label
		// TODO: Change to button if we want the user to manually compute the total
		// nutrition or keep the label if we want it to update every time a food is
		// added to the food in meal list
		Label totalNutritionLbl = new Label("Total Nutrition");
		totalNutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

		// Sets up the total nutrition list
		// TODO: Implement: either update when the user clicks on the "Total Nutrition"
		// button or update automatically when the user adds a new food to the food in
		// meal list
		totalNutrition = FXCollections.observableArrayList("Calories: 2500", "Fat: 300", "Carbohydrates: 200",
				"Fiber: 30", "Protein: 60");
		ListView<String> totalNutritionList = new ListView<String>(totalNutrition);
		totalNutritionList.setMaxWidth(320);
		totalNutritionList.setMinWidth(320);
		totalNutritionList.setMaxHeight(128);
		totalNutritionList.setMinHeight(128);

		// Horizontal box which stores the filter buttons
		HBox filterHbox = new HBox();
		filterHbox.setSpacing(10);
		filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter);

		// Vertical box for the left side of the application
		VBox vbox1 = new VBox();
		vbox1.setMaxWidth(300);
		vbox1.setMinWidth(300);
		vbox1.setSpacing(12);
		vbox1.getChildren().addAll(foodLbl, filterHbox, listOfFoods, addToMealBtn, addNewFoodBtn, loadFromFileBtn,
				saveToFileBtn);

		// Line which divides the "All Foods" section from the "Current Meal" section
		Line divider = new Line();
		divider.setStartX(200.0f);
		divider.setStartY(0.0f);
		divider.setEndX(200.0f);
		divider.setEndY(1000.0f);

		// Vertical box which contains the food in meal list and the remove food button
		VBox foodListAndRemoveVbox = new VBox();
		foodListAndRemoveVbox.setSpacing(12);
		foodListAndRemoveVbox.setMaxWidth(300);
		foodListAndRemoveVbox.setMinWidth(300);
		foodListAndRemoveVbox.getChildren().addAll(listOfFoodsInMeal, removeFoodFromMealBtn);

		// Vertical box which contains the nutrition facts for selected foods and the
		// total nutrition of the meal
		VBox nutritionVbox = new VBox();
		nutritionVbox.setSpacing(16);
		nutritionVbox.setMaxWidth(300);
		nutritionVbox.getChildren().addAll(nutritionLbl, foodNutritionLbl, nutritionList, totalNutritionLbl,
				totalNutritionList);

		// Horizontal box which contains the two vertical boxes which make up the
		// "Current Meal" section
		HBox displayFoodNutritionHbox = new HBox();
		displayFoodNutritionHbox.setSpacing(20);
		displayFoodNutritionHbox.getChildren().addAll(foodListAndRemoveVbox, nutritionVbox);

		// Vertical box for the right side of the application
		VBox vbox2 = new VBox();
		vbox2.setMaxWidth(600);
		vbox2.setSpacing(12);
		vbox2.getChildren().addAll(currentMealLbl, displayFoodNutritionHbox);

		// Horizontal box which contains the two sides of the GUI and the dividing line
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		hbox.getChildren().addAll(vbox1, divider, vbox2);

		// Sets the scene and shows the stage
		primaryStage.setScene(new Scene(hbox, 1000, 500));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
