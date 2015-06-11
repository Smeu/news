package ro.unitbv.news.repository.impl;

import org.junit.Before;
import org.junit.Test;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.repository.CategoryRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Teodora Tanase
 */
public class InMemoryCategoryRepositoryTest {

	private static final long INVALID_ID = -1;
	private static final long VALID_ID = 0;

	private CategoryRepository repository;

	@Before
	public void init() {
		repository = new InMemoryCategoryRepository();
	}

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		repository.get(INVALID_ID);
	}

	@Test
	public void testGetValidId() throws Exception {
		Category addedCategory = new Category();
		repository.create(addedCategory);

		Category retrievedCategory = repository.get(VALID_ID);

		assertThat(retrievedCategory, is(addedCategory));
	}
}
