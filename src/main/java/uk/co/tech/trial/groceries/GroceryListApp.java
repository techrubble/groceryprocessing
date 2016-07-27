package uk.co.tech.trial.groceries;

import uk.co.tech.trial.groceries.htmlparsing.HTMLParser;
import uk.co.tech.trial.groceries.model.GroceryItemList;
import uk.co.tech.trial.groceries.processor.RipeFruitProcessor;

import java.io.IOException;

/**
 * Hello world!
 */
public class GroceryListApp {
    public static void main(String[] args) throws IOException {

        RipeFruitProcessor ripeFruitProcessor;
        ripeFruitProcessor = new RipeFruitProcessor(new HTMLParser(HTMLParser.WEB_PARSER_TYPE, args[0]));
        GroceryItemList groceryItemList = ripeFruitProcessor.process();
        System.out.println(ripeFruitProcessor.getGroceryItemListAsJSONString(true));
    }
}
