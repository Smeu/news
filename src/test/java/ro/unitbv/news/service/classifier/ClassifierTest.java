package ro.unitbv.news.service.classifier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.factory.ValidatorFactory;
import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.News;
import ro.unitbv.news.repository.impl.InMemoryCategoryRepository;
import ro.unitbv.news.service.CategoryService;
import ro.unitbv.news.service.impl.DefaultCategoryService;
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
public class ClassifierTest {

	private static final String KEYWORD = "keyword";

	@Mock
	private CategoryValidator categoryValidator;

	private CategoryService categoryService;

	private Classifier classifier;

	@Before
	public void init() {
		categoryService = new DefaultCategoryService(new InMemoryCategoryRepository(), categoryValidator);
		classifier = new Classifier(categoryService);
	}

	@Test
	public void testAssignCategoriesHappyFlow() {
		Category category = new Category();
		category.addKeyword(KEYWORD);
		when(categoryValidator.validate(any(Category.class))).thenReturn(new ValidationResult());
		categoryService.create(category);
		News news = new News();
		news.setContent(KEYWORD);

		classifier.assignCategories(news);

		assertThat(news.getCategories().get(0), is(category));
	}
}
