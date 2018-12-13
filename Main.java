/**
 * Filename: Main.java
 * 
 * Project: Team Project, Milestone2, main JavaFX GUI interface
 * 
 * Authors: Anders Carlsson, William Wilson, Sungjin Park, Jay Park
 *
 * Semester: Fall 2018
 * 
 * Course: CS400
 * 
 * Lecture: 001
 * 
 * Due Date: Before 10pm on October 8, 2018
 * 
 * Version: 1.0
 * 
 * Credits:
 * 
 * Bugs:
 */

package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/* imported JavaFX add-ons */
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    private ArrayList<FoodItem> foodItemList; // List which stores the FoodItem objects in the "All
                                              // Foods" list
    private ObservableList<String> foodNameObservable; // An observable list of the food names. When
                                                       // this list is
                                                       // updated the GUI will update to show
                                                       // changes
    private String nameFilterString; // String which the food names will be filtered by
    private List<String> nutrientFilterRules; // List of nutrient filter rules
    private ArrayList<FoodItem> foodInMealList; // List which stores the FoodItem objects in the
                                                // "Current Meal" list
    private ObservableList<String> foodInMealObservable; // An observable list of the food in meal
                                                         // names. When this list
                                                         // is updated the GUI will update to show
                                                         // changes
    private ObservableList<String> nutritionInEachFoodObservable; // An observable list of the
                                                                  // nutrition in the selected
                                                                  // food. When this list is updated
                                                                  // the GUI will
                                                                  // update to show changes
    private ObservableList<String> totalNutritionObservable; // An observable list of the nutrition
                                                             // in the whole meal.
                                                             // When this list is updated the GUI
                                                             // will update to show
                                                             // changes
    private Label foodCount; // Label which shows the number of foods displayed in the "All Foods"
                             // list

    /*
     * Creates the GUI and responds to user input.
     */
    @Override
    public void start(Stage primaryStage) {

        // Creates a new FoodData object
        FoodData foodData = new FoodData();

        // Initializes the filter rules
        nutrientFilterRules = new ArrayList<String>();
        nameFilterString = new String();

        // Displays title
        primaryStage.setTitle("Food Query and Meal Analysis");

        // Displays "All Foods" label in the top left
        Text foodLbl = new Text("            All Foods");
        foodLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

        // Sets up the "All Foods" list
        foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
        foodNameObservable = FXCollections.observableArrayList(new ArrayList<String>());
        for (int i = 0; i < foodItemList.size(); i++)
            foodNameObservable.add(foodItemList.get(i).getName());
        ListView<String> listOfFoods = new ListView<String>(foodNameObservable);
        listOfFoods.setMaxWidth(300);
        listOfFoods.setMinWidth(300);
        listOfFoods.setMaxHeight(170);
        listOfFoods.setMinHeight(170);

        // Counter which displays the number of foods in the current food list view
        Label foodCounterLbl = new Label("   Number of foods: ");
        foodCounterLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
        foodCount = new Label(Integer.toString(foodNameObservable.size()));
        foodCount.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

        // Sets up the food in meal list
        foodInMealList = new ArrayList<FoodItem>();
        foodInMealObservable = FXCollections.observableArrayList(new ArrayList<String>());
        ListView<String> listOfFoodsInMeal = new ListView<String>(foodInMealObservable);
        listOfFoodsInMeal.setMaxWidth(300);
        listOfFoodsInMeal.setMinWidth(300);
        listOfFoodsInMeal.setMaxHeight(391);
        listOfFoodsInMeal.setMinHeight(391);

        // Displays the filter label
        Label filterLbl = new Label(" Filters: ");
        filterLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 15));

        // Button to filter by name
        Button nameFilter = new Button("Name");
        nameFilter.setFont(Font.font("Abel", FontWeight.BOLD, 10));
        nameFilter.setMinWidth(42);
        nameFilter.setMaxWidth(42);
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
                    Button confirmName = new Button("Add");
                    confirmName.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
                    confirmName.setPrefSize(100, 20);
                    nameBox.getChildren().addAll(nameFilterField, confirmName);
                    Scene scene = new Scene(nameBox, 260, 50);
                    Stage stage = new Stage();
                    stage.setTitle("Name Filter");
                    stage.setScene(scene);
                    stage.show();
                    confirmName.setOnAction(new EventHandler<ActionEvent>() {
                        /*
                         * Handles clicking on the "Name" confirmation button
                         */
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                nameFilterString = nameFilterField.getText();
                                nameFilter.setTextFill(Color.DARKCYAN);
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

        // Button to filter by nutrition contents
        // TODO: Implement: update the list of all foods when the filter is typed in
        Button nutrientFilter = new Button("Nutrients");
        nutrientFilter.setFont(Font.font("Abel", FontWeight.BOLD, 10));
        nutrientFilter.setMinWidth(62);
        nutrientFilter.setMaxWidth(62);
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
                    Button confirmNutrient = new Button("Add rule");
                    confirmNutrient.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
                    confirmNutrient.setMinSize(100, 27);
                    nutrientBox.getChildren().addAll(nutrientName, comparator, nutrientValue,
                                    confirmNutrient);
                    Scene scene = new Scene(nutrientBox, 520, 50);
                    Stage stage = new Stage();
                    stage.setTitle("Filter By Nutrient");
                    stage.setScene(scene);
                    stage.show();
                    confirmNutrient.setOnAction(new EventHandler<ActionEvent>() {
                        /*
                         * Handles clicking on the "Nutrients" confirmation button
                         */
                        @SuppressWarnings("unchecked")
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Double.parseDouble(nutrientValue.getText());
                                String rule = new String(nutrientName.getText() + " ");
                                rule = rule + comparator.getText() + " " + nutrientValue.getText();
                                nutrientFilterRules.add(rule);
                                nutrientFilter.setTextFill(Color.DARKCYAN);
                                stage.close();
                            } catch (NumberFormatException ef) {
                                // Displays an error box to notify the user than an error of
                                // invalid input has
                                // occured
                                BorderPane errorBox = new BorderPane();
                                errorBox.setPadding(new Insets(15, 12, 15, 12));
                                Label errorLabel = new Label("    Error: Invalid Input.");
                                errorLabel.setFont(
                                                Font.font("Abel", FontWeight.EXTRA_BOLD, 15));
                                Button errorButton = new Button("Ok");
                                errorButton.setMinSize(100, 27);
                                errorBox.setTop(errorLabel);
                                errorBox.setCenter(errorButton);
                                Scene errorScene = new Scene(errorBox, 200, 100);
                                Stage errorStage = new Stage();
                                errorStage.setTitle("Error");
                                errorStage.setScene(errorScene);
                                errorStage.show();
                                errorButton.setOnAction(ActionEvent -> errorStage.close());
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

        // Line which separates the filters from the apply/reset buttons
        Line filterLine = new Line();
        filterLine.setStartX(100.0f);
        filterLine.setStartY(0.0f);
        filterLine.setEndX(100.0f);
        filterLine.setEndY(20.0f);

        // Button to apply the filters
        Button applyFilterBtn = new Button("Apply");
        applyFilterBtn.setFont(Font.font("Abel", FontWeight.BOLD, 10));
        applyFilterBtn.setMinWidth(42);
        applyFilterBtn.setMaxWidth(42);
        applyFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Handles clicking the "Apply" button
             */
            public void handle(ActionEvent event) {
                try {
                    List<FoodItem> filteredList = new ArrayList<FoodItem>();
                    List<FoodItem> filteredList2 = new ArrayList<FoodItem>();
                    if (nameFilterString.length() > 0) {
                        filteredList = foodData.filterByName(nameFilterString);
                    }
                    if (nutrientFilterRules.size() > 0) {
                        filteredList2 = foodData.filterByNutrients(nutrientFilterRules);
                    }
                    if (nameFilterString.length() > 0 && nutrientFilterRules.size() > 0) {
                        filteredList.retainAll(filteredList2);
                        foodItemList = (ArrayList<FoodItem>) filteredList;
                        foodItemList = sortFoodItemListByName(foodItemList);
                        foodNameObservable.clear();
                        for (int i = 0; i < foodItemList.size(); i++) {
                            foodNameObservable.add(foodItemList.get(i).getName());
                        }
                    } else if (nameFilterString.length() > 0) {
                        foodItemList = (ArrayList<FoodItem>) filteredList;
                        foodItemList = sortFoodItemListByName(foodItemList);
                        foodNameObservable.clear();
                        for (int i = 0; i < foodItemList.size(); i++) {
                            foodNameObservable.add(foodItemList.get(i).getName());
                        }
                    } else if (nutrientFilterRules.size() > 0) {
                        foodItemList = (ArrayList<FoodItem>) filteredList2;
                        foodItemList = sortFoodItemListByName(foodItemList);
                        foodNameObservable.clear();
                        for (int i = 0; i < foodItemList.size(); i++) {
                            foodNameObservable.add(foodItemList.get(i).getName());
                        }
                    } else {
                        return;
                    }
                    foodCount.setText(Integer.toString(foodNameObservable.size()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Button to reset the filters
        Button resetFilterBtn = new Button("Reset");
        resetFilterBtn.setFont(Font.font("Abel", FontWeight.BOLD, 10));
        resetFilterBtn.setMinWidth(42);
        resetFilterBtn.setMaxWidth(42);
        resetFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Handles clicking the "Reset" button
             */
            public void handle(ActionEvent event) {
                try {
                    nameFilterString = "";
                    nutrientFilterRules.clear();
                    foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
                    foodItemList = sortFoodItemListByName(foodItemList);
                    foodNameObservable.clear();
                    for (int i = 0; i < foodItemList.size(); i++) {
                        foodNameObservable.add(foodItemList.get(i).getName());
                    }
                    foodCount.setText(Integer.toString(foodNameObservable.size()));
                    nameFilter.setTextFill(Color.BLACK);
                    nutrientFilter.setTextFill(Color.BLACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Button to add the selected food from the foods list to the meal list
        Button addToMealBtn = new Button("Add to Meal");
        addToMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
        addToMealBtn.setMaxSize(300, 38);
        addToMealBtn.setMinSize(300, 38);
        addToMealBtn.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Handles clicking the "Add to Meal" button
             */
            public void handle(ActionEvent event) {
                try {
                    int index = listOfFoods.getSelectionModel().getSelectedIndex();
                    if (index >= 0) {
                        FoodItem selectedFood = foodItemList.get(index);
                        foodInMealList.add(selectedFood);
                        foodInMealList = sortFoodItemListByName(foodInMealList);
                        foodInMealObservable.clear();
                        for (int i = 0; i < foodInMealList.size(); i++)
                            foodInMealObservable.add(foodInMealList.get(i).getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

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
                    foodNutrients.getChildren().addAll(foodId, foodName, calorieCount, fatCount,
                                    carbCount, fiberCount, proteinCount);
                    Button confirmFood = new Button("Add food");
                    confirmFood.setMinSize(100, 27);
                    foodBox.setLeft(foodNutrients);
                    foodBox.setCenter(confirmFood);
                    Scene scene = new Scene(foodBox, 340, 300);
                    Stage stage = new Stage();
                    stage.setTitle("Add New Food");
                    stage.setScene(scene);
                    stage.show();
                    confirmFood.setOnAction(new EventHandler<ActionEvent>() {
                        /*
                         * Handles clicking on the "Add food" confirmation button
                         */
                        public void handle(ActionEvent event) {
                            try {
                                FoodItem newFood =
                                                new FoodItem(foodId.getText(), foodName.getText());
                                if (isNegative(Double
                                                .parseDouble(calorieCount.getText())) == true) {
                                    throw new NumberFormatException();
                                }
                                if (isNegative(Double.parseDouble(fatCount.getText())) == true) {
                                    throw new NumberFormatException();
                                }
                                if (isNegative(Double.parseDouble(carbCount.getText())) == true) {
                                    throw new NumberFormatException();
                                }
                                if (isNegative(Double.parseDouble(fiberCount.getText())) == true) {
                                    throw new NumberFormatException();
                                }
                                if (isNegative(Double
                                                .parseDouble(proteinCount.getText())) == true) {
                                    throw new NumberFormatException();
                                }
                                newFood.addNutrient(new String("calories"), new Double(
                                                Double.parseDouble(calorieCount.getText())));
                                newFood.addNutrient(new String("fat"),
                                                new Double(Double.parseDouble(fatCount.getText())));
                                newFood.addNutrient(new String("carbohydrate"), new Double(
                                                Double.parseDouble(carbCount.getText())));
                                newFood.addNutrient(new String("fiber"), new Double(
                                                Double.parseDouble(fiberCount.getText())));
                                newFood.addNutrient(new String("protein"), new Double(
                                                Double.parseDouble(proteinCount.getText())));
                                foodData.addFoodItem(newFood);
                                if (foodItemList.size() == (foodData.getAllFoodItems().size())) {
                                    foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
                                    foodNameObservable.clear();
                                    for (int i = 0; i < foodItemList.size(); i++) {
                                        foodNameObservable.add(foodItemList.get(i).getName());
                                    }
                                    foodCount.setText(Integer.toString(foodNameObservable.size()));
                                    stage.close();
                                    return;
                                }
                                foodItemList.add(newFood);
                                foodItemList = sortFoodItemListByName(foodItemList);
                                foodNameObservable.clear();
                                for (int i = 0; i < foodItemList.size(); i++)
                                    foodNameObservable.add(foodItemList.get(i).getName());
                                foodCount.setText(Integer.toString(foodNameObservable.size()));
                                stage.close();
                            } catch (NumberFormatException ef) {
                                // Runs only if invalid input
                                try {
                                    // Displays an error box to notify the user than an error of
                                    // invalid input has
                                    // occured
                                    BorderPane errorBox = new BorderPane();
                                    errorBox.setPadding(new Insets(15, 12, 15, 12));
                                    Label errorLabel = new Label("    Error: Invalid Input.");
                                    errorLabel.setFont(
                                                    Font.font("Abel", FontWeight.EXTRA_BOLD, 15));
                                    Button errorButton = new Button("Ok");
                                    errorButton.setMinSize(100, 27);
                                    errorBox.setTop(errorLabel);
                                    errorBox.setCenter(errorButton);
                                    Scene errorScene = new Scene(errorBox, 200, 100);
                                    Stage errorStage = new Stage();
                                    errorStage.setTitle("Error");
                                    errorStage.setScene(errorScene);
                                    errorStage.show();
                                    errorButton.setOnAction(ActionEvent -> errorStage.close());
                                    stage.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
                    FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter(
                                    "Comma Separated Values File", "*.csv");
                    fileChooser.getExtensionFilters().add(csvFilter);
                    fileChooser.setTitle("Open food data file");
                    File selectedFile = fileChooser.showOpenDialog(primaryStage);
                    if (selectedFile.getPath()
                                    .substring(selectedFile.getPath().length() - 4,
                                                    selectedFile.getPath().length())
                                    .toLowerCase().equals(".csv") == false) {
                        BorderPane errorBox = new BorderPane();
                        errorBox.setPadding(new Insets(15, 12, 15, 12));
                        Label errorLabel = new Label("  Error: File must be a .csv file.");
                        errorLabel.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 15));
                        Button errorButton = new Button("Ok");
                        errorButton.setMaxSize(100, 27);
                        errorButton.setMinSize(100, 27);
                        errorBox.setTop(errorLabel);
                        errorBox.setCenter(errorButton);
                        Scene errorScene = new Scene(errorBox, 250, 100);
                        Stage errorStage = new Stage();
                        errorStage.setTitle("Error");
                        errorStage.setScene(errorScene);
                        errorStage.show();
                        errorButton.setOnAction(ActionEvent -> errorStage.close());
                        return;
                    }
                    foodData.loadFoodItems(selectedFile.getPath());
                    foodItemList.clear();
                    foodItemList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
                    foodNameObservable.clear();
                    for (int i = 0; i < foodItemList.size(); i++)
                        foodNameObservable.add(foodItemList.get(i).getName());
                    foodCount.setText(Integer.toString(foodNameObservable.size()));
                } catch (Exception e) {
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
                    Label csvEnding = new Label(".csv");
                    csvEnding.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
                    Button confirmSaveBtn = new Button("Save");
                    confirmSaveBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 13));
                    confirmSaveBtn.setMinSize(100, 27);
                    saveFileHbox.getChildren().addAll(outputFileNameLbl, inputField, csvEnding,
                                    confirmSaveBtn);
                    Scene scene = new Scene(saveFileHbox, 500, 50);
                    Stage stage = new Stage();
                    stage.setTitle("Save to file");
                    stage.setScene(scene);
                    stage.show();
                    confirmSaveBtn.setOnAction(ActionEvent -> {
                        stage.close();
                        foodData.saveFoodItems(inputField.getText() + ".csv");
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Displays "Current Meal" label in the top right
        Text currentMealLbl = new Text("                             Current Meal");
        currentMealLbl.setFont(Font.font("Abel", FontWeight.EXTRA_BOLD, 28));

        // Button which removes the selected food from the food in meal list
        Button removeFoodFromMealBtn = new Button(" - Remove from Meal");
        removeFoodFromMealBtn.setPrefSize(300, 38);
        removeFoodFromMealBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
        removeFoodFromMealBtn.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Handles clicking the "Remove from Meal" button
             */
            public void handle(ActionEvent event) {
                try {
                    int index = listOfFoodsInMeal.getSelectionModel().getSelectedIndex();
                    if (index >= 0) {
                        FoodItem selectedFood = foodInMealList.get(index);
                        foodInMealList.remove(selectedFood);
                        foodInMealList = sortFoodItemListByName(foodInMealList);
                        foodInMealObservable.clear();
                        for (int i = 0; i < foodInMealList.size(); i++)
                            foodInMealObservable.add(foodInMealList.get(i).getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Displays the "Nutrition for:" label
        Label nutritionLbl = new Label("Nutrition for: ");
        nutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 16));

        // Sets up the nutrition in selected food list
        // TODO: Implement: when a food is selected from the food in meal list the
        // nutrition for that food will display here
        nutritionInEachFoodObservable = FXCollections.observableArrayList("Calories: " + 0.0,
                        "Fat: " + 0.0, "Carbohydrates: " + 0.0, "Fiber: " + 0.0, "Protein: " + 0.0);
        ListView<String> nutritionList = new ListView<String>(nutritionInEachFoodObservable);
        nutritionList.setMaxWidth(320);
        nutritionList.setMinWidth(320);
        nutritionList.setMaxHeight(128);
        nutritionList.setMinHeight(128);

        // Displays the selected food from the food in meal list
        Label foodNutritionLbl = new Label();
        listOfFoodsInMeal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            /*
             * Handles clicking the "Remove from Meal" button
             */
            public void handle(MouseEvent event) {
                try {
                    int index = listOfFoodsInMeal.getSelectionModel().getSelectedIndex();
                    if (index >= 0) {
                        FoodItem selectedFood = foodInMealList.get(index);
                        foodNutritionLbl.setText(selectedFood.getName());
                        Double calories = selectedFood.getNutrientValue("calories");
                        Double carbohydrate = selectedFood.getNutrientValue("carbohydrate");
                        Double fat = selectedFood.getNutrientValue("fat");
                        Double protein = selectedFood.getNutrientValue("protein");
                        Double fiber = selectedFood.getNutrientValue("fiber");
                        nutritionInEachFoodObservable.clear();
                        nutritionInEachFoodObservable.addAll("Calories: " + calories, "Fat: " + fat,
                                        "Carbohydrates: " + carbohydrate, "Fiber: " + fiber,
                                        "Protein: " + protein);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        foodNutritionLbl.setFont(Font.font("Abel", FontWeight.BOLD, 18));
        foodNutritionLbl.setTextFill(Color.SLATEGREY);

        // Displays the "Total Nutrition" label
        Label totalNutritionLbl = new Label("Total Nutrition");
        totalNutritionLbl.setFont(Font.font("Abel", FontWeight.NORMAL, 16));
        totalNutritionLbl.setMaxHeight(40);
        totalNutritionLbl.setMinHeight(40);

        // Sets up the total nutrition list
        totalNutritionObservable = FXCollections.observableArrayList("Calories: " + 0.0,
                        "Fat: " + 0.0, "Carbohydrates: " + 0.0, "Fiber: " + 0.0, "Protein: " + 0.0);
        ListView<String> totalNutritionList = new ListView<String>(totalNutritionObservable);
        totalNutritionList.setMaxWidth(320);
        totalNutritionList.setMinWidth(320);
        totalNutritionList.setMaxHeight(128);
        totalNutritionList.setMinHeight(128);

        // Button calculate the total nutrition of the foods in the meal list
        Button totalNutritionBtn = new Button("Calculate Total Nutrition");
        totalNutritionBtn.setFont(Font.font("Abel", FontWeight.NORMAL, 15));
        totalNutritionBtn.setMaxWidth(320);
        totalNutritionBtn.setMinWidth(320);
        totalNutritionBtn.setMaxHeight(38);
        totalNutritionBtn.setMinHeight(38);
        totalNutritionBtn.setOnAction(new EventHandler<ActionEvent>() {
            /*
             * Handles clicking the "Calculate Total Nutrition" button
             */
            public void handle(ActionEvent event) {
                calculateTotal();
            }
        });

        // Horizontal box which stores the filter buttons
        HBox filterHbox = new HBox();
        filterHbox.setSpacing(10);
        filterHbox.getChildren().addAll(filterLbl, nameFilter, nutrientFilter, filterLine,
                        applyFilterBtn, resetFilterBtn);

        // Horizontal box to hold the counter label and value
        HBox foodCounterHbox = new HBox();
        foodCounterHbox.setSpacing(10);
        foodCounterHbox.getChildren().addAll(foodCounterLbl, foodCount);

        // Vertical box for the left side of the application
        VBox vbox1 = new VBox();
        vbox1.setMaxWidth(300);
        vbox1.setMinWidth(300);
        vbox1.setSpacing(12);
        vbox1.getChildren().addAll(foodLbl, filterHbox, listOfFoods, foodCounterHbox, addToMealBtn,
                        addNewFoodBtn, loadFromFileBtn, saveToFileBtn);

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
        nutritionVbox.setSpacing(12);
        nutritionVbox.setMaxWidth(300);
        nutritionVbox.getChildren().addAll(nutritionLbl, foodNutritionLbl, nutritionList,
                        totalNutritionLbl, totalNutritionList, totalNutritionBtn);

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

    /**
     * Private helper method which sorts the FoodInMealList alphanumerically.
     */
    private ArrayList<FoodItem> sortFoodItemListByName(ArrayList<FoodItem> foodItemList) {
        HashMap<String, FoodItem> nameToItemMap = new HashMap<String, FoodItem>();
        ArrayList<String> foodNameList = new ArrayList<String>();
        ArrayList<FoodItem> newFoodItemList = new ArrayList<FoodItem>();
        for (int i = 0; i < foodItemList.size(); i++) {
            foodNameList.add(foodItemList.get(i).getName());
            nameToItemMap.put(foodItemList.get(i).getName(), foodItemList.get(i));
        }
        Collections.sort(foodNameList, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < foodNameList.size(); i++)
            newFoodItemList.add(nameToItemMap.get(foodNameList.get(i)));
        return newFoodItemList;
    }

    /**
     * Checks if a double is negative
     * 
     * @param d double to check
     * @return true if negative, false if not
     */
    private boolean isNegative(double d) {
        return Double.compare(d, 0.0) < 0;
    }

    /**
     * 
     */
    private void calculateTotal() {
        Double totCal = 0.0;
        Double totCarbs = 0.0;
        Double totFat = 0.0;
        Double totPro = 0.0;
        Double totFib = 0.0;
        for (FoodItem i : foodInMealList) {
            totCal += i.getNutrientValue("calories");
            totCarbs += i.getNutrientValue("carbohydrate");
            totFat += i.getNutrientValue("fat");
            totPro += i.getNutrientValue("protein");
            totFib += i.getNutrientValue("fiber");
        }
        totalNutritionObservable.clear();
        totalNutritionObservable.addAll("Calories: " + totCal, "Fat: " + totFat,
                        "Carbohydrates: " + totCarbs, "Fiber: " + totFib, "Protein: " + totPro);
    }

    /**
     * Main method, launches the program.
     * 
     * @param args are the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
