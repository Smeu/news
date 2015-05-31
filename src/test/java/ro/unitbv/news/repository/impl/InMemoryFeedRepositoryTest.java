package ro.unitbv.news.repository.impl;

import org.junit.Test;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Teodora Tanase
 */
public class InMemoryFeedRepositoryTest {

	private static final long INVALID_ID = -1;
	private static final long VALID_ID = 0;

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		FeedRepository repository = new InMemoryFeedRepository();
		repository.get(INVALID_ID);
	}

	@Test
	public void testGetValidId() throws Exception {
		FeedRepository repository = new InMemoryFeedRepository();
		Feed addedFeed = new Feed();
		repository.create(addedFeed);

		Feed retrievedFeed = repository.get(VALID_ID);
		assertThat(retrievedFeed, is(addedFeed));
	}
}
