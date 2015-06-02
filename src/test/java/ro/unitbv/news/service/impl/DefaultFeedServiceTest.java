package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.validator.FeedValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Rares Smeu
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultFeedServiceTest {

	private static final Long ID = 1L;
	private static final long INVALID_ID = -1;

	private static final String NAME = "name";

	@Mock
	private FeedRepository repository;

	@Mock
	private FeedValidator feedValidator;

	@Test
	public void testCreateWithErrors() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		ValidationResult result = new ValidationResult();
		result.addError(new FieldError(NAME, Error.MIN_LENGTH_NOT_REACHED));
		when(feedValidator.validate(any())).thenReturn(result);

		Response<Long> response = service.create(new Feed());

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors(), is(result.getErrors()));
	}

	@Test
	public void testCreateWithoutErrors() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		when(feedValidator.validate(any())).thenReturn(new ValidationResult());
		when(repository.create(any())).thenReturn(ID);

		Response<Long> response = service.create(new Feed());

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(ID));
	}

	@Test
	public void testGetAllNullUser() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		Response<List<Feed>> response = service.getAll(null);

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors().size(), is(1));
	}

	@Test
	public void testGetAllCorrectUser() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		List<Feed> feeds = new ArrayList<>();
		when(repository.getAllForOwner(ID)).thenReturn(feeds);

		User user = new User();
		user.setId(ID);
		Response<List<Feed>> response = service.getAll(user);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(feeds));
	}

	@Test
	public void testGetWithErrors() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		when(repository.get(INVALID_ID)).thenThrow(new InvalidIdException());

		Response<Feed> response = service.get(INVALID_ID);

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors().size(), is(1));
	}

	@Test
	public void testGetWithoutErrors() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		Feed feed = new Feed();
		when(repository.get(ID)).thenReturn(feed);

		Response<Feed> response = service.get(ID);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(feed));
	}
}