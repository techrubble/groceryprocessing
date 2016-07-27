package uk.co.tech.trial.groceries.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Grocery Item List
 * - provides Total Unit Cost of current items in the list
 *
 * Created by sclowes
 */
public class GroceryItemList {

    private List<GroceryItem> groceryItemsList = new ArrayList<>();

    @JsonProperty("results")
    public List<GroceryItem> getGroceryItemsList() {
        return groceryItemsList;
    }

    public double getTotal() {
        // BigDecimal is used for Maths to avoid rounding issues with double
        BigDecimal total = new BigDecimal(0);
        //total =  groceryItemsList.stream().map(groceryItem -> groceryItem.getCost()).reduce(total,(total, cost) -> { return total = total + cost;});
        for (GroceryItem groceryItem : groceryItemsList) {
            total = total.add(BigDecimal.valueOf(groceryItem.getUnitPrice()));
        }
        return total.doubleValue();
    }

    public void addGroceryItem(GroceryItem groceryItem) {
        groceryItemsList.add(groceryItem);
    }
}
