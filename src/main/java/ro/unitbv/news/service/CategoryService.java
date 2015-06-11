package ro.unitbv.news.service;

import java.util.List;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;

/**
 * Service for operations with {@link ro.unitbv.news.model.Category}.
 *
 * @author Teodora Tanase
 */
public interface CategoryService {

	/**
	 * Creates a new category.
	 *
	 * @param category category to be created.
	 * @return response holding the id of the newly created category or the errors.
	 */
	Response<Long> create(Category category);

	/**
	 * Gets a category after its id.
	 *
	 * @param id the id of the category.
	 * @return response holding the category or the errors.
	 */
	Response<Category> get(long id);

	/**
	 * Gets all categories.
	 *
	 * @return response holding the categories or the errors.
	 */
	Response<List<Category>> getAll();

	/**
	 * Adds a keyword to a category.
	 *
	 * @param category in what category to add the keyword.
	 * @param keyword  the keyword for the category.
	 * @return errors containing potential errors.
	 **/
	Response addKeyword(Category category, String keyword);

	/**
	 * Retrieves all news from a certain category that are linked to the specified user.
	 *
	 * @param category category for news.
	 * @param user     user for those news.
	 * @return response holding the news or the errors.
	 */
	Response<List<News>> getNews(Category category, User user);
}
