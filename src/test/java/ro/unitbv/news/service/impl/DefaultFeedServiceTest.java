package ro.unitbv.news.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.validator.FeedValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultFeedServiceTest {

	private static final Long ID = 1L;
	@Mock
	private FeedRepository repository;

	@Mock
	private FeedValidator feedValidator;

	@Test
	public void testCreateWithErrors() throws Exception {
		FeedService service = new DefaultFeedService(repository, feedValidator);
		ValidationResult result = new ValidationResult();
		result.addError(new FieldError("name", Error.MIN_LENGTH_NOT_REACHED));
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
		assertThat(response.getResponse(), is(1L));
	}

}