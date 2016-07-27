package uk.co.tech.trial.groceries.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by sclowes
 */
public class GroceryItemListTest {
    @Test
    public void testGetTotal() throws Exception {

        GroceryItemList groceryItemList = new GroceryItemList();

        RipeFruit ripeFruit1 = new RipeFruit();
        ripeFruit1.setUnitPrice(10.00);

        RipeFruit ripeFruit2 = new RipeFruit();
        ripeFruit2.setUnitPrice(13.50);

        RipeFruit ripeFruit3 = new RipeFruit();
        ripeFruit3.setUnitPrice(22.20);

        groceryItemList.addGroceryItem(ripeFruit1);
        groceryItemList.addGroceryItem(ripeFruit2);
        groceryItemList.addGroceryItem(ripeFruit3);

        assertEquals(45.70, groceryItemList.getTotal(), 0);

    }

}