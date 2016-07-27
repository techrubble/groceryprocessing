package uk.co.tech.trial.groceries.htmlparsing;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for HTMLParser
 *
 * Created by sclowes
 */
public class HTMLParserTest {

    private HTMLParserIF htmlParser;

    @Before
    public void setup() throws IOException {

        htmlParser = new HTMLParser(HTMLParser.FILE_PARSER_TYPE, "test-products.html");
    }

    @Test
    public void testGetElementsByClass() {
        List<HTMLElement> products = htmlParser.getDocumentElementsByClass("product ");
        assertEquals(7, products.size());
    }

    @Test
    public void testGetChildElementsByClass() {
        List<HTMLElement> products = htmlParser.getDocumentElementsByClass("product ");
        List<HTMLElement> productInfoElements = products.get(0).getChildrenByClass("productInfo");
        assertEquals(1, productInfoElements.size());
    }

    @Test
    public void testGetElementTextForDescription() {
        List<HTMLElement> products = htmlParser.getDocumentElementsByClass("product ");

        List<HTMLElement> productInfoElements = products.get(0).getChildrenByClass("productInfo");
        assertEquals("Sainsbury's Apricot Ripe & Ready x5", productInfoElements.get(0).getText());
    }

    @Test
    public void testGetLinkHREFAttribute() {

        List<HTMLElement> products = htmlParser.getDocumentElementsByClass("product ");
        List<HTMLElement> productInfoElements = products.get(0).getChildrenByClass("productInfo");
        HTMLElement productInfo = productInfoElements.get(0);
        List<HTMLElement> linkElements = productInfo.getChildrenByTag("a");
        String linkElementHref = linkElements.get(0).getAttribute("href");
        assertTrue(linkElementHref.length() > 0);

    }

    @Test
    public void testGetElementTextForUnitPrice() {

        List<HTMLElement> products = htmlParser.getDocumentElementsByClass("product ");
        List<HTMLElement> pricePerUnitElements = products.get(0).getChildrenByClass("pricePerUnit");

        assertEquals("&pound3.50/unit", pricePerUnitElements.get(0).getText());

    }

    @Test
    public void testGetSize() {
        long size = htmlParser.getSizeInBytes();
        assertEquals(132006, size);
    }

    @Test
    public void testGetSizeKB() {
        long size = htmlParser.getSizeInKB();
        assertEquals(128, size);
    }
}
