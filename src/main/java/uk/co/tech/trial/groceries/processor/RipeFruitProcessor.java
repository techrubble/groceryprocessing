package uk.co.tech.trial.groceries.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.tech.trial.groceries.htmlparsing.HTMLElement;
import uk.co.tech.trial.groceries.htmlparsing.HTMLParser;
import uk.co.tech.trial.groceries.htmlparsing.HTMLParserIF;
import uk.co.tech.trial.groceries.model.GroceryItemList;
import uk.co.tech.trial.groceries.model.RipeFruit;

import java.io.IOException;
import java.util.List;

/**
 * Main processor for Ripe Fruit; scrapes data from screen
 * using HTMLParser
 *
 * Created by sclowes
 */
public class RipeFruitProcessor {

    private static final String PRODUCT_CLASS = "product ";
    private static final String PRODUCT_INFO = "productInfo";
    private static final String LINK_TAG = "a";
    private static final String HREF_ATTR = "href";
    private static final String PRICE_PER_UNIT = "pricePerUnit";
    private static final String PRICE_PER_UNIT_PREFIX = "&pound";
    private static final String PRICE_PER_UNIT_SUFFIX = "/unit";

    private HTMLParserIF htmlParser;
    private GroceryItemList groceryItemList;

    public RipeFruitProcessor(HTMLParserIF htmlParser) {
        this.htmlParser = htmlParser;
    }

    public GroceryItemList process() {
        groceryItemList = new GroceryItemList();
        List<HTMLElement> products = htmlParser.getDocumentElementsByClass(PRODUCT_CLASS);
        products.forEach(product -> processProducts(product));
        return groceryItemList;
    }

    private void processProducts(HTMLElement productElement) {
        RipeFruit ripeFruit = new RipeFruit();
        List<HTMLElement> productInfoElements = productElement.getChildrenByClass(PRODUCT_INFO);

        // should only be one productInfo/pricePerUnit
        ripeFruit.setTitle(productInfoElements.get(0).getText());

        extractUnitPrice(ripeFruit, productElement);

        extractLinkDetails(ripeFruit, productElement);

        groceryItemList.addGroceryItem(ripeFruit);
    }

    private void extractUnitPrice(RipeFruit ripeFruit, HTMLElement productElement) {
        // should only be one pricePerUnit in list
        HTMLElement pricePerUnitElement = productElement.getChildrenByClass(PRICE_PER_UNIT).get(0);
        String pricePerUnit = pricePerUnitElement.getText().replace(PRICE_PER_UNIT_PREFIX, "").replace(PRICE_PER_UNIT_SUFFIX, "");
        ripeFruit.setUnitPrice(Double.parseDouble(pricePerUnit));
    }

    private void extractLinkDetails(RipeFruit ripeFruit, HTMLElement productElement) {
        // should only be one link tag in list
        HTMLElement linkElement = productElement.getChildrenByTag(LINK_TAG).get(0);
        long linkPageSize = 0;
        String linkDescription = "";
        try {
            HTMLParser linkParser = htmlParser.getLinkedPageParser(linkElement.getAttribute(HREF_ATTR));
            linkPageSize = linkParser.getSizeInKB();
            HTMLElement productTextElement = linkParser.getDocumentElementsByClass("productText").get(0);
            linkDescription = productTextElement.getText();
        } catch (IOException e) {
            // Can't reach link so size is zero
        }

        ripeFruit.setDetailsSize(linkPageSize);
        ripeFruit.setDescription(linkDescription);
    }

    public String getGroceryItemListAsJSONString(boolean prettyOutput) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        String jsonGroceryItemList = "";
        try {
            if (prettyOutput) {
                jsonGroceryItemList = jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(groceryItemList);
            } else {

                jsonGroceryItemList = jsonObjectMapper.writeValueAsString(groceryItemList);
            }
        } catch (JsonProcessingException e) {
            // Return empty string in case of failure
        }
        return jsonGroceryItemList;
    }

}
