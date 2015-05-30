package ro.unitbv.news.repository.impl;

import java.util.List;

import org.junit.Test;

import ro.unitbv.news.model.News;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Teodora Tanase
 */
public class InMemoryNewsRepositoryTest {

	private static final long INVALID_ID = -1;
	private static final long FIRST_ID = 0;
	private static final long OWNER_ID = 1;

	private NewsRepository repository = new InMemoryNewsRepository();

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		repository.get(INVALID_ID);
	}

	@Test
	public void testGetValidId() throws Exception {
		News addedNews = new News();
		repository.create(addedNews);

		News retrievedNews = repository.get(FIRST_ID);
		assertThat(retrievedNews, is(addedNews));
	}

	@Test
	public void testGetAllForUser() throws Exception {
		News addedNews = new News();
		addedNews.setOwnerId(OWNER_ID);
		repository.create(addedNews);

		List<News> newsList = repository.getAllForOwner(OWNER_ID);
		assertEquals(OWNER_ID, newsList.get((int) FIRST_ID).getOwnerId());
	}
}
