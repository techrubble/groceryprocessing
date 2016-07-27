package uk.co.tech.trial.groceries.htmlparsing;

import java.io.IOException;
import java.util.List;

/**
 * ]
 *
 * Created by sclowes
 */
public interface HTMLParserIF {

    List<HTMLElement> getDocumentElementsByClass(String classname);

    HTMLParser getLinkedPageParser(String link) throws IOException;

    long getSizeInBytes();

    long getSizeInKB();
}
