package ro.unitbv.news.repository;

import java.util.List;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;

/**
 * Repository for {@link ro.unitbv.news.model.Category}.
 *
 * @author Teodora Tanase
 */
public interface CategoryRepository {

	/**
	 * Creates a new category.
	 *
	 * @param category category to be created.
	 * @return id of the newly created category.
	 */
	long create(Category category);

	/**
	 * Gets a category after its id.
	 *
	 * @param id id of the category.
	 * @return the category.
	 */
	Category get(long id);

	/**
	 * Retrieves all categories.
	 *
	 * @return categories.
	 */
	List<Category> getAll();
}
