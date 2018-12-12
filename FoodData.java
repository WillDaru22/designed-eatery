package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the backend for managing all the operations associated
 * with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {

	// List of all the food items.
	private List<FoodItem> foodItemList;

	// Map of nutrients and their corresponding index
	private HashMap<String, BPTree<Double, FoodItem>> indexes;

	/**
	 * Public constructor
	 */
	public FoodData() {
		foodItemList = new ArrayList<FoodItem>();
		indexes = new HashMap<String, BPTree<Double, FoodItem>>();
		indexes.put("calories", new BPTree<Double, FoodItem>());
		indexes.put("fat", new BPTree<Double, FoodItem>());
		indexes.put("carbohydrate", new BPTree<Double, FoodItem>());
		indexes.put("fiber", new BPTree<Double, FoodItem>());
		indexes.put("protein", new BPTree<Double, FoodItem>());
	}

	/**
     * Loads the data in the .csv file
     * 
     * file format:
     * <id1>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     * <id2>,<name>,<nutrient1>,<value1>,<nutrient2>,<value2>,...
     * 
     * Example:
     * 556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     * 
     * Note:
     *  1. All the rows are in valid format.
     *  2. All IDs are unique.
     *  3. Names can be duplicate.
     *  4. All columns are strictly alphanumeric (a-zA-Z0-9_).
     *  5. All food items will strictly contain 5 nutrients in the given order:    
     *     calories,fat,carbohydrate,fiber,protein
     *  6. Nutrients are CASE-INSENSITIVE. 
     * 
     * @param filePath path of the food item data file 
     *        (e.g. folder1/subfolder1/.../foodItems.csv) 
     */
	@Override
	public void loadFoodItems(String filePath) {
		try {
			File csvFile = new File(filePath);
			Scanner sc = new Scanner(csvFile);
			foodItemList.clear();
			indexes.replace("calories", new BPTree<Double, FoodItem>());
			indexes.replace("fat", new BPTree<Double, FoodItem>());
			indexes.replace("carbohydrate", new BPTree<Double, FoodItem>());
			indexes.replace("fiber", new BPTree<Double, FoodItem>());
			indexes.replace("protein", new BPTree<Double, FoodItem>());
			while (sc.hasNextLine()) {
				// Boolean which is changed to false if something is wrong with the format of
				// the current line
				boolean handleThisLine = true;
				String[] row = sc.nextLine().split(",");
				// Makes sure the file is formatted correctly
				if (row.length != 12 || row[2].toLowerCase().equals("calories") == false
						|| row[4].toLowerCase().equals("fat") == false
						|| row[6].toLowerCase().equals("carbohydrate") == false
						|| row[8].toLowerCase().equals("fiber") == false
						|| row[10].toLowerCase().equals("protein") == false)
					handleThisLine = false;
				// Makes sure the ID is unique
				if (handleThisLine)
					for (int i = 0; i < foodItemList.size(); i++)
						if (row[0].equals(foodItemList.get(i).getID()))
							handleThisLine = false;
				// If the line can be handled, it is converted into a FoodItem
				if (handleThisLine) {
					FoodItem newFood = new FoodItem(row[0], row[1]);
					for (int i = 2; i < 12; i += 2) {
						newFood.addNutrient(row[i].toLowerCase(), Double.parseDouble(row[i + 1]));
						BPTree<Double, FoodItem> bptreeCopy = indexes.get(row[i].toLowerCase());
						bptreeCopy.insert(Double.parseDouble(row[i + 1]), newFood);
						indexes.replace(row[i].toLowerCase(), bptreeCopy);
					}
					foodItemList.add(newFood);
				}
			}
			sc.close();
			sortFoodItemList();
		} catch (FileNotFoundException e) {
			// TODO: Display message on GUI
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: Display message on GUI
		}
	}

	/**
	 * Private helper method which sorts the FoodItemList alphanumerically.
	 */
	private void sortFoodItemList() {
		HashMap<String, FoodItem> nameToItemMap = new HashMap<String, FoodItem>();
		ArrayList<String> foodNameList = new ArrayList<String>();
		ArrayList<FoodItem> newFoodItemList = new ArrayList<FoodItem>();
		for (int i = 0; i < foodItemList.size(); i++) {
			foodNameList.add(foodItemList.get(i).getName());
			nameToItemMap.put(foodItemList.get(i).getName(), foodItemList.get(i));
		}
		Collections.sort(foodNameList);
		for (int i = 0; i < foodNameList.size(); i++)
			newFoodItemList.add(nameToItemMap.get(foodNameList.get(i)));
		foodItemList = newFoodItemList;
	}

	/**
     * Gets all the food items that have name containing the substring.
     * 
     * Example:
     *     All FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     *         556540ff5d613c9d5f5935a9,Stewarts_PremiumDarkChocolatewithMintCookieCrunch,calories,280,fat,18,carbohydrate,34,fiber,3,protein,3
     *     Substring: soy
     *     Filtered FoodItem
     *         51c38f5d97c3e6d3d972f08a,Similac_FormulaSoyforDiarrheaReadytoFeed,calories,100,fat,0,carbohydrate,0,fiber,0,protein,3
     * 
     * Note:
     *     1. Matching should be CASE-INSENSITIVE.
     *     2. The whole substring should be present in the name of FoodItem object.
     *     3. substring will be strictly alphanumeric (a-zA-Z0-9_)
     * 
     * @param substring substring to be searched
     * @return list of filtered food items; if no food item matched, return empty list
     */
	@Override
	public List<FoodItem> filterByName(String substring) {
		List<FoodItem> filter = new ArrayList<>();
		if (substring != null) {
			for (int i = 0; i < foodItemList.size(); i++) {
				if (foodItemList.get(i).getName().toLowerCase().contains(substring.toLowerCase())) {
					filter.add(foodItemList.get(i));
				}
			}
		}
		return filter;
	}

	/**
     * Gets all the food items that fulfill ALL the provided rules
     *
     * Format of a rule:
     *     "<nutrient> <comparator> <value>"
     * 
     * Definition of a rule:
     *     A rule is a string which has three parts separated by a space:
     *         1. <nutrient>: Name of one of the 5 nutrients [CASE-INSENSITIVE]
     *         2. <comparator>: One of the following comparison operators: <=, >=, ==
     *         3. <value>: a double value
     * 
     * Note:
     *     1. Multiple rules can contain the same nutrient.
     *         E.g. ["calories >= 50.0", "calories <= 200.0", "fiber == 2.5"]
     *     2. A FoodItemADT object MUST satisfy ALL the provided rules i
     *        to be returned in the filtered list.
     *
     * @param rules list of rules
     * @return list of filtered food items; if no food item matched, return empty list
     */
  @Override
  public List<FoodItem> filterByNutrients(List<String> rules) {
    String ruleArray[] = rules.get(0).split(" "); // split array into individual strings
    List<FoodItem> filterList1 =
        indexes.get(ruleArray[0]).rangeSearch(Double.parseDouble(ruleArray[2]), ruleArray[1]);
    List<FoodItem> filterList2 = new ArrayList<FoodItem>();
    for (int i = 1; i < rules.size(); i++) {
      ruleArray = rules.get(i).split(" ");
      filterList2 =
          indexes.get(ruleArray[0]).rangeSearch(Double.parseDouble(ruleArray[2]), ruleArray[1]);
      for (int j = 0; j < filterList1.size(); j++) {
        if (!(filterList2.contains(filterList1.get(j)))) {
          filterList1.remove(j);
          j = j - 1;
        }
      }
    }
    return filterList1;
  }

	/**
     * Adds a food item to the loaded data.
     * @param foodItem the food item instance to be added
     */
	@Override
	public void addFoodItem(FoodItem foodItem) {
		foodItemList.add(foodItem);
		BPTree<Double, FoodItem> calorieBPTree = indexes.get("calories");
		calorieBPTree.insert(foodItem.getNutrientValue("calories"), foodItem);
		indexes.put("calories", calorieBPTree);
		BPTree<Double, FoodItem> fatBPTree = indexes.get("fat");
		fatBPTree.insert(foodItem.getNutrientValue("fat"), foodItem);
		indexes.put("fat", fatBPTree);
		BPTree<Double, FoodItem> carbBPTree = indexes.get("carbohydrate");
		carbBPTree.insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
		indexes.put("carbohydrate", carbBPTree);
		BPTree<Double, FoodItem> fiberBPTree = indexes.get("fiber");
		fiberBPTree.insert(foodItem.getNutrientValue("fiber"), foodItem);
		indexes.put("fiber", fiberBPTree);
		BPTree<Double, FoodItem> proteinBPTree = indexes.get("protein");
		proteinBPTree.insert(foodItem.getNutrientValue("protein"), foodItem);
		indexes.put("protein", proteinBPTree);
		sortFoodItemList();
	}

	/**
     * Gets the list of all food items.
     * @return list of FoodItem
     */
	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemList;
	}

	/**
	 * Save the list of food items in ascending order by name
	 * 
	 * @param filename
	 *            name of the file where the data needs to be saved
	 */
	@Override
	public void saveFoodItems(String filename) {
		String outputFile = "";
		for (int i = 0; i < foodItemList.size(); i++) {
			outputFile += foodItemList.get(i).getID() + "," + foodItemList.get(i).getName() + ",calories,"
					+ foodItemList.get(i).getNutrientValue("calories") + ",fat,"
					+ foodItemList.get(i).getNutrientValue("fat") + ",carbohydrate,"
					+ foodItemList.get(i).getNutrientValue("carbohydrate") + ",fiber,"
					+ foodItemList.get(i).getNutrientValue("fiber") + ",protein,"
					+ foodItemList.get(i).getNutrientValue("protein") + ",\n";
		}
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(outputFile);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO: Display message on GUI
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: Display message on GUI
			e.printStackTrace();
		}
	}
}
