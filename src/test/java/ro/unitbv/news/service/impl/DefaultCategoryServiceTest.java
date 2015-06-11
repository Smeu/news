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
import ro.unitbv.news.repository.CategoryRepository;
import ro.unitbv.news.service.CategoryService;
import ro.unitbv.news.validator.CategoryValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultCategoryServiceTest {

	private static final String NAME = "name";

	private static final long ID = 1;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategoryValidator validator;

	private CategoryService service;

	@Before
	public void init() {
		service = new DefaultCategoryService(repository, validator);
	}

	@Test
	public void testCreateWithErrors() throws Exception {
		ValidationResult validationResult = new ValidationResult();
		validationResult.addError(new FieldError(NAME, Error.ILLEGAL_CONTENT));
		when(validator.validate(any(Category.class))).thenReturn(validationResult);

		Response<Long> response = service.create(new Category());

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors(), is(validationResult.getErrors()));
	}

	@Test
	public void testCreateWithoutErrors() throws Exception {
		when(validator.validate(any(Category.class))).thenReturn(new ValidationResult());
		when(repository.create(any(Category.class))).thenReturn(ID);

		Response<Long> response = service.create(new Category());

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(ID));
	}

	@Test
	public void testGetHappyFlow() throws Exception {
		Category category = new Category();
		when(repository.get(ID)).thenReturn(category);

		Response<Category> response = service.get(ID);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(category));
	}

	@Test
	public void testGetAllHappyFlow() throws Exception {
		List<Category> categories = new ArrayList<>();
		when(repository.getAll()).thenReturn(categories);

		Response<List<Category>> response = service.getAll();

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(categories));
	}
}
