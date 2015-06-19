package ro.unitbv.news.repository;

import java.util.List;

import ro.unitbv.news.model.Category;

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

	/**
	 * Adds a keyword to a category.
	 *
	 * @param category in what category to add the keyword.
	 * @param keyword  the keyword for the category.
	 **/
	void addKeyword(Category category, String keyword);

	/**
	 * Deletes a category after its id.
	 *
	 * @param id id of the category to be deleted.
	 * @return <code>true</code> if deletion was performed or <code>false</code> otherwise.
	 */
	boolean delete(long id);
}
