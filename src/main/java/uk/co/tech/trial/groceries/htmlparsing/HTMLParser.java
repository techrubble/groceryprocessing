package uk.co.tech.trial.groceries.htmlparsing;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * HTML parser that hides the use of JSoup to do the hard work
 * Created by sclowes
 */
public class HTMLParser implements HTMLParserIF {

    public final static String WEB_PARSER_TYPE = "web";
    public final static String FILE_PARSER_TYPE = "file";
    private final static int WEB_REQUEST_TIMEOUT = 60000;
    private static boolean fileBased = false;
    private Document webPageDocument;
    private long documentSize;

    public HTMLParser(String parserType, String uri) throws IOException {
        switch (parserType)

        {
            case FILE_PARSER_TYPE:
                buildFileParser(uri);
                break;
            case WEB_PARSER_TYPE:
                buildURLParser(uri);
                break;
            default:
                throw new IllegalArgumentException("Invalid parserType: " + parserType);
        }

    }

    private void buildFileParser(String filename) throws IOException {

        File webPageFile = new File(this.getClass().getClassLoader().getResource(filename).getFile());

        Document webPageDocument = Jsoup.parse(webPageFile, "UTF-8");
        HTMLParser.fileBased = true;
        this.webPageDocument = webPageDocument;
        this.documentSize = webPageFile.length();

    }

    private void buildURLParser(String url) throws IOException {

        Document webPageDocument = Jsoup.connect(url).timeout(WEB_REQUEST_TIMEOUT).get();
        HTMLParser.fileBased = false;
        this.webPageDocument = webPageDocument;
        this.documentSize = getWebPageSize(url);

    }

    public long getWebPageSize(String url) {
        long webPageSize = 0;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(WEB_REQUEST_TIMEOUT)
                .setConnectTimeout(WEB_REQUEST_TIMEOUT)
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGetRequest = new HttpGet(url);
            HttpResponse httpGetResponse = httpClient.execute(httpGetRequest);
            webPageSize = EntityUtils.toByteArray(httpGetResponse.getEntity()).length;
        } catch (IOException e) {
            // failure treat size as zero
        }
        return webPageSize;
    }

    @Override
    public List<HTMLElement> getDocumentElementsByClass(String classname) {

        List<HTMLElement> wrappedElementList = new ArrayList<>();

        Elements htmlElements = webPageDocument.getElementsByAttributeValue("class", classname);
        htmlElements.forEach(element -> wrappedElementList.add(new HTMLElement(element)));
        return wrappedElementList;

    }

    @Override
    public HTMLParser getLinkedPageParser(String link) throws IOException {
        HTMLParser htmlParser;

        if (fileBased) {

            htmlParser = new HTMLParser("file", link);

        } else {
            htmlParser = new HTMLParser("web", link);
        }

        return htmlParser;
    }

    @Override
    public long getSizeInBytes() {
        return documentSize;
    }

    @Override
    public long getSizeInKB() {
        return Math.round(documentSize / 1024);
    }
}
