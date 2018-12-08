package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the backend for managing all the operations associated with FoodItems
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
    List<FoodItem> foodItemList = new ArrayList<FoodItem>();
    this.foodItemList = foodItemList;

    HashMap<String, BPTree<Double, FoodItem>> indexes =
        new HashMap<String, BPTree<Double, FoodItem>>();
    this.indexes = indexes;
  }


  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
   */
  @Override
  public void loadFoodItems(String filePath) {
    // TODO : Complete
  }
  
  /**
   * Gets all the food items that have name containing the substring.
   * 
   * Matching should be CASE-INSENSITIVE.
   * The whole substring should be present in the name of FoodItem object.
   * Substring will be strictly alphanumeric (a-zA-Z0-9_)
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
          System.out.println(foodItemList.get(i).getName() + "is on filter list");
        }
      }
    }
    if (filter.size() == 0) {
      System.out.println("Item not found");
    }
    return filter;
  }

  /**
   * Gets all the food items that fulfill ALL the provided rules
   * 
   * 1.Multiple rules can contain the same nutrient. E.g. ["calories >= 50.0", "calories <= 200.0",
   * "fiber == 2.5"]
   * 2.A FoodItemADT object MUST satisfy ALL the provided rules i to be returned in the filtered 
   * list.
   *
   * @param rules list of rules
   * @return list of filtered food items; if no food item matched, return empty list
   */
  @Override
  public List<FoodItem> filterByNutrients(List<String> rules) {
    List<List<FoodItem>> filter = rules.stream().map(filterRule -> filterRule.split(" "))
        .map(rule -> this.indexes.get(rule[0]).rangeSearch(Double.parseDouble(rule[2]), rule[1]))
        .collect(Collectors.toList());
    if (filter.size() > 0) {
      filter.forEach(list -> filter.get(0).retainAll(list));
      List<FoodItem> filteredItem = filter.get(0);
      return filteredItem;
    } else {
      List<FoodItem> emptyList = new ArrayList<FoodItem>();
      return emptyList;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
   */
  @Override
  public void addFoodItem(FoodItem foodItem) {
    // TODO : Complete
  }

  /*
   * (non-Javadoc)
   * 
   * @see skeleton.FoodDataADT#getAllFoodItems()
   */
  @Override
  public List<FoodItem> getAllFoodItems() {
    // TODO : Complete
    return null;
  }


  @Override
  public void saveFoodItems(String filename) {
    // TODO Auto-generated method stub
    
  }

}
