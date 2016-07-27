package uk.co.tech.trial.groceries.processor;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import uk.co.tech.trial.groceries.htmlparsing.HTMLParser;
import uk.co.tech.trial.groceries.model.GroceryItemList;
import uk.co.tech.trial.groceries.model.RipeFruit;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for RipeFruitProcessor
 *
 * Created by sclowes
 */
public class RipeFruitProcessorTest {

    private RipeFruitProcessor ripeFruitProcessor;

    @Before
    public void setup() throws IOException {
        ripeFruitProcessor = new RipeFruitProcessor(new HTMLParser(HTMLParser.FILE_PARSER_TYPE, "test-products.html"));
    }

    @Test
    public void testProcess() {
        GroceryItemList groceryItemList = ripeFruitProcessor.process();
        assertEquals(7, groceryItemList.getGroceryItemsList().size());
        assertEquals(15.10, groceryItemList.getTotal(), 0);
    }

    @Test
    public void testRipeFruitDetails() {
        GroceryItemList groceryItemList = ripeFruitProcessor.process();

        RipeFruit ripeFruit = (RipeFruit) groceryItemList.getGroceryItemsList().get(0);
        assertEquals("Sainsbury's Apricot Ripe & Ready x5", ripeFruit.getTitle());
        assertEquals("Apricots", ripeFruit.getDescription());
        assertEquals(3.5, ripeFruit.getUnitPrice(), 0);
        assertEquals("51kb", ripeFruit.getSize());
    }


    @Test
    public void testGroceryItemListAsJSONString() throws JSONException, IOException {
        GroceryItemList groceryItemList = ripeFruitProcessor.process();
        // assert various elements
        String jsonGroceryItemList = ripeFruitProcessor.getGroceryItemListAsJSONString(false);
        String expectedJson = "{\"total\":15.1,\"results\":[{\"title\":\"Sainsbury's Apricot Ripe & Ready x5\",\"description\":\"Apricots\",\"size\":\"51kb\",\"unit_price\":3.5},{\"title\":\"Sainsbury's Avocado Ripe & Ready XL Loose 300g\",\"description\":\"Avocados\",\"size\":\"52kb\",\"unit_price\":1.5},{\"title\":\"Sainsbury's Avocado, Ripe & Ready x2\",\"description\":\"Avocados\",\"size\":\"59kb\",\"unit_price\":1.8},{\"title\":\"Sainsbury's Avocados, Ripe & Ready x4\",\"description\":\"Avocados\",\"size\":\"52kb\",\"unit_price\":3.2},{\"title\":\"Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)\",\"description\":\"Conference\",\"size\":\"51kb\",\"unit_price\":1.5},{\"title\":\"Sainsbury's Golden Kiwi x4\",\"description\":\"Gold Kiwi\",\"size\":\"52kb\",\"unit_price\":1.8},{\"title\":\"Sainsbury's Kiwi Fruit, Ripe & Ready x4\",\"description\":\"Kiwi\",\"size\":\"53kb\",\"unit_price\":1.8}]}";
        JSONAssert.assertEquals(expectedJson, jsonGroceryItemList, false);

    }
}
