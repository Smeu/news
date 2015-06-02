package ro.unitbv.news.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentValidatorTest {

	private static final String CONTENT = "content";
	private static final String VALID_CONTENT = "valid content";

	@Mock
	private StringFieldValidator fieldValidator;

	private CommentValidator commentValidator;

	@Before
	public void init() {
		commentValidator = new CommentValidator(fieldValidator);
	}

	@Test
	public void testValidateWithInvalidInput() throws Exception {
		ValidationResult fieldResult = new ValidationResult();
		fieldResult.addError(new FieldError(CONTENT, Error.MAX_LENGTH_EXCEEDED));
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(fieldResult);
		Comment comment = new Comment();
		comment.setContent(createLongContent());

		ValidationResult result = commentValidator.validate(comment);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(1));
	}

	private String createLongContent() {
		return new String(new char[1025]);
	}

	@Test
	public void testValidateWithValidInput() throws Exception {
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(new ValidationResult());
		Comment comment = new Comment();
		comment.setContent(VALID_CONTENT);

		ValidationResult result = commentValidator.validate(comment);
		assertThat(result.hasErrors(), is(false));
	}
}
