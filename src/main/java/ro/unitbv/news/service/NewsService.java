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
	Response<List<News>> getNews(User user);
}
