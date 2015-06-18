package ro.unitbv.news.service;

import java.util.List;

import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;

/**
 * Service for operations with {@link ro.unitbv.news.model.News}
 *
 * @author Rares Smeu
 */
public interface NewsService {

	/**
	 * Gets all the saved news from a user.
	 *
	 * @param user the user from which to get the news.
	 * @return response holding the saved news or the errors, in case there are any.
	 */
	Response<List<News>> getAll(User user);

	/**
	 * Saves a news for a user.
	 *
	 * @param news news to be saved.
	 * @param user owner of the news.
	 * @return response holding the errors, in case there are any.
	 */
	Response<Void> add(News news, User user);

	/**
	 * Deletes a piece of news after its id.
	 *
	 * @param id        id of the news to delete.
	 * @param performer user that performs deletion.
	 * @return response holding <code>true</code> if deletion was performed, <code>false</code> if deletion was not
	 * performed or the errors.
	 */
	Response<Boolean> delete(long id, User performer);
}
