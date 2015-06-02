package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.service.CommentService;
import ro.unitbv.news.validator.CommentValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultCommentServiceTest {

	private static final Long VALID_ID = 0L;
	private static final Long INVALID_ID = -1L;

	private static final String CONTENT = "content";

	@Mock
	private CommentRepository repository;

	@Mock
	private CommentValidator validator;

	private CommentService service;

	@Before
	public void init() {
		service = new DefaultCommentService(repository, validator);
	}

	@Test
	public void testCreateWithErrors() throws Exception {
		ValidationResult result = new ValidationResult();
		result.addError(new FieldError(CONTENT, Error.MAX_LENGTH_EXCEEDED));
		when(validator.validate(any())).thenReturn(result);

		Response<Long> response = service.create(new Comment());

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors(), is(result.getErrors()));
	}

	@Test
	public void testCreateWithoutErrors() throws Exception {
		when(validator.validate(any())).thenReturn(new ValidationResult());
		when(repository.create(any())).thenReturn(VALID_ID);

		Response<Long> response = service.create(new Comment());

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(VALID_ID));
	}

	@Test
	public void testGetWithErrors() throws Exception {
		when(repository.get(INVALID_ID)).thenThrow(new InvalidIdException());

		Response<Comment> response = service.get(INVALID_ID);

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors().size(), is(1));
	}

	@Test
	public void testGetWithoutErrors() throws Exception {
		Comment comment = new Comment();
		when(repository.get(VALID_ID)).thenReturn(comment);

		Response<Comment> response = service.get(VALID_ID);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(comment));
	}

	@Test
	public void testGetCommentsNullNews() throws Exception {
		Response<List<Comment>> response = service.getComments(null);

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors().size(), is(1));
	}

	@Test
	public void testGetCommentsValidNews() throws Exception {
		List<Comment> comments = new ArrayList<>();
		when(repository.getAllForNews(VALID_ID)).thenReturn(comments);

		News news = new News();
		news.setId(VALID_ID);
		Response<List<Comment>> response = service.getComments(news);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(comments));
	}
}
