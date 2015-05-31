package ro.unitbv.news.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ro.unitbv.news.model.News;
import ro.unitbv.news.parser.exception.RssParsingException;

/**
 * Parser for a rss feed.
 *
 * @author Teodora Tanase
 */
public class RssFeedParser {

	private final static Logger log = LoggerFactory.getLogger(RssFeedParser.class);

	private static final String ITEM = "item";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String LINK = "link";

	/**
	 * Retrieves news elements from a stream.
	 *
	 * @param inputStream stream to retrieve news from.
	 * @return a list of {@link ro.unitbv.news.model.News}.
	 */
	public List<News> retrieveNews(InputStream inputStream) {
		List<News> newsList = new ArrayList<>();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(inputStream);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName(ITEM);
			for (int index = 0; index < nodeList.getLength(); index++) {
				Node node = nodeList.item(index);
				newsList.add(parseNewsItem(node));
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e) {
			log.error("Inaccessible or badly formatted {}", e);
			throw new RssParsingException();
		}
		return newsList;
	}

	private News parseNewsItem(Node node) {
		if (node.getNodeType() != Node.ELEMENT_NODE) {
			throw new RssParsingException();
		}
		Element element = (Element) node;
		News news = new News();
		NodeList titles = element.getElementsByTagName(TITLE);
		NodeList descriptions = element.getElementsByTagName(DESCRIPTION);
		NodeList links = element.getElementsByTagName(LINK);
		if (titles.getLength() > 0) {
			news.setTitle(titles.item(0).getTextContent());
		}
		if (descriptions.getLength() > 0) {
			news.setContent(descriptions.item(0).getTextContent());
		}
		if (links.getLength() > 0) {
			news.setUrl(links.item(0).getTextContent());
		}
		return news;
	}
}
