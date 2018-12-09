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

/* imported JavaFX add-ons*/
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.stage.Stage;

/**
 * Food query and meal analysis program using graphic user interface.
 * 
 * @author Anders Carlsson, William Wilson, Sungjin Park, Jay Park
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		// display title
		primaryStage.setTitle("Food Query and Meal Analysis");

		// display vertical box at top left
		VBox vbox1 = new VBox();
		vbox1.setMaxWidth(200);
		vbox1.setMinWidth(200);
		vbox1.setSpacing(12);

		// text in vertical box at topmost
		Text foodLbl = new Text("        All Foods");
		foodLbl.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));

		// food list in vertical box. This is hard coded at the moment TODO: import from
		// file
		ObservableList<String> foodNames = FXCollections.observableArrayList("Apples", "Asparagus", "Avacado", "Bacon",
				"Bananas", "Bok Choy", "Celery", "Chocolate", "Cranberries", "Eggs", "Fries", "Gravy", "Green beans",
				"Ham", "Ice cream", "Jelly", "Lemons", "Lobster", "Mashed potatoes", "Melons", "Noodles", "Pineapples",
				"Ribs", "Steak", "Turkey", "Yogurt");
		ListView<String> listOfFoods = new ListView<String>(foodNames);
		listOfFoods.setPrefSize(150, 120);

		// horizontal box that filters by names and nutrients
		HBox filterHbox = new HBox();
		filterHbox.setSpacing(10);
		Label filterLbl = new Label("Filter: ");
		Button nameFilter = new Button("Name");
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

		// CheckBox nameFilter = new CheckBox("Name");
		// nameFilter.setIndeterminate(false);

		// nutrient button that filters by nutritional contents
		Button nutrientFilter = new Button("Nutrients");
		nutrientFilter.setOnAction(new EventHandler<ActionEvent>() {

			// event handle method that enables user to filter through nutrients
			public void handle(ActionEvent event) {
				try {

					HBox nutrientBox = new HBox();
					nutrientBox.setPadding(new Insets(15, 12, 15, 12));
					nutrientBox.setSpacing(10);
					TextField nutrientName = new TextField();
					nutrientName.setPromptText("Nutrient");
					nutrientName.setMaxSize(100, 27);
					nutrientName.setMinSize(100, 27);
					TextField comparator = new TextField();
					comparator.setPromptText("Comparator");
					comparator.setMaxSize(100, 27);
					comparator.setMinSize(100, 27);
					TextField nutrientValue = new TextField();
					nutrientValue.setPromptText("Nutrient value");
					nutrientValue.setMaxSize(120, 27);
					nutrientValue.setMinSize(120, 27);
					Button confirmNutrient = new Button("Filter");
					confirmNutrient.setMinSize(100, 27);
					nutrientBox.getChildren().addAll(nutrientName, comparator, nutrientValue, confirmNutrient);
					Scene scene = new Scene(nutrientBox, 500, 50);
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
		// CheckBox nutrientFilter = new CheckBox("Nutrients");
		// nutrientFilter.setIndeterminate(false);
		filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter);

		// additional buttons for modifying food / meal
		Button addToMealBtn = new Button("Add to Meal");
		addToMealBtn.setPrefSize(200, 20);
		Button addNewFoodBtn = new Button(" + Add New Food");
		addNewFoodBtn.setPrefSize(200, 20);
		addNewFoodBtn.setOnAction(new EventHandler<ActionEvent>() {
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
		            foodNutrients.getChildren().addAll(foodId, foodName, calorieCount, fatCount, carbCount, fiberCount, proteinCount);
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
	                            FoodItem newFood = new FoodItem(foodId.getText(),foodName.getText());
	                            newFood.addNutrient(new String("calories"), new Double(Double.parseDouble(calorieCount.getText())));
	                            newFood.addNutrient(new String("fat"), new Double(Double.parseDouble(fatCount.getText())));
	                            newFood.addNutrient(new String("carbohydrate"), new Double(Double.parseDouble(carbCount.getText())));
	                            newFood.addNutrient(new String("fiber"), new Double(Double.parseDouble(fiberCount.getText())));
	                            newFood.addNutrient(new String("protein"), new Double(Double.parseDouble(proteinCount.getText())));
	                            foodList.add(newFood);
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
		//Button removeFoodBtn = new Button(" - Remove Food");
		//removeFoodBtn.setPrefSize(200, 20);
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

		// text for diaplying current meal
		Text currentMealLbl = new Text("                Current Meal");
		currentMealLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

		HBox displayFoodNutritionHbox = new HBox();
		displayFoodNutritionHbox.setSpacing(20);

		VBox foodListAndRemoveVbox = new VBox();
		foodListAndRemoveVbox.setSpacing(12);
		foodListAndRemoveVbox.setMaxWidth(200);

		// food list for a meal. Hard coded at the moment. TODO: add more food / import
		// from file
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
		vbox2.getChildren().addAll(currentMealLbl, displayFoodNutritionHbox);

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
