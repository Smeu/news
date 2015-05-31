package ro.unitbv.news.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ro.unitbv.news.model.News;
import ro.unitbv.news.parser.exception.RssParsingException;
import static org.junit.Assert.assertEquals;

/**
 * @author Teodora Tanase
 */
public class RssFeedParserTest {

	private static final String INVALID_PATH = "src/test/resources/file.txt";
	private static final String VALID_PATH = "src/test/resources/rss.xml";

	private static final String TITLE = "mSpy admits hacking and data theft";
	private static final String CONTENT = "A company offering software that allows people to spy on others admits it has been hacked and had thousands of customer records leaked online.";
	private static final String URL = "url";

	private RssFeedParser parser;

	@Before
	public void init() {
		parser = new RssFeedParser();
	}

	@Test(expected = RssParsingException.class)
	public void testRetrieveNewsUnsuccessful() throws Exception {
		try (InputStream inputStream = new FileInputStream(INVALID_PATH)) {
			parser.retrieveNews(inputStream);
		}
	}

	@Test
	public void testRetrieveNewsSuccessful() throws Exception {
		List<News> newsList;
		try (InputStream inputStream = new FileInputStream(VALID_PATH)) {
			newsList = parser.retrieveNews(inputStream);
		}

		assertEquals(TITLE, newsList.get(0).getTitle());
		assertEquals(CONTENT, newsList.get(0).getContent());
		assertEquals(URL, newsList.get(0).getUrl());
	}
}
