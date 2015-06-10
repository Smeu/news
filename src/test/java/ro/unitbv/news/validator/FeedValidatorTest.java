package ro.unitbv.news.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static ro.unitbv.news.model.Error.URL_NOT_AVAILABLE;

@RunWith(MockitoJUnitRunner.class)
public class FeedValidatorTest {

	@Mock
	private UrlValidator urlValidator;

	@Test
	public void testValidateWithValidInput() throws Exception {
		FeedValidator validator = new FeedValidator(urlValidator);
		Feed feed = getValidFeed();
		when(urlValidator.validate(anyString())).thenReturn(new ValidationResult());

		ValidationResult result = validator.validate(feed);

		assertThat(result.hasErrors(), is(false));
	}

	@Test
	public void testValidateWithInvalidInput() throws Exception {
		FeedValidator validator = new FeedValidator(urlValidator);
		Feed feed = getInvalidFeed();
		ValidationResult urlResult = new ValidationResult();
		urlResult.addError(new FieldError("url", URL_NOT_AVAILABLE));
		when(urlValidator.validate(anyString())).thenReturn(urlResult);

		ValidationResult result = validator.validate(feed);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(4));
	}


	private Feed getValidFeed() {
		Feed feed = new Feed();
		feed.setName("FeedName");
		feed.setDescription("Some Description");
		feed.setUrl("url");
		return feed;
	}

	private Feed getInvalidFeed() {
		Feed feed = new Feed();
		feed.setName(" ");
		feed.setDescription("Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters "
				+ "Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters "
				+ "Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters "
				+ "Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters Over 256 Characters ");
		feed.setUrl("url");
		return feed;
	}
}