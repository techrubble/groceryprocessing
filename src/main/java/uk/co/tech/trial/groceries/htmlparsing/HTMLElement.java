package uk.co.tech.trial.groceries.htmlparsing;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper/Decorator for Jsoup Element class
 *
 * Created by sclowes
 */
public class HTMLElement {

    private Element htmlElement;

    public HTMLElement(Element htmlElement) {
        this.htmlElement = htmlElement;
    }

    public List<HTMLElement> getChildrenByClass(String classname) {

        Elements htmlElements = htmlElement.getElementsByClass(classname);
        return convertToHTMLElements(htmlElements);
    }

    public String getText() {
        return htmlElement.text();
    }

    public List<HTMLElement> getChildrenByTag(String tagName) {
        Elements htmlElements = htmlElement.getElementsByTag(tagName);
        return convertToHTMLElements(htmlElements);
    }

    public String getAttribute(String attributeName) {
        return htmlElement.attr(attributeName);
    }

    private List<HTMLElement> convertToHTMLElements(Elements htmlElements) {
        List<HTMLElement> wrappedElementList = new ArrayList<>();
        htmlElements.forEach(element -> wrappedElementList.add(new HTMLElement(element)));
        return wrappedElementList;
    }
}
