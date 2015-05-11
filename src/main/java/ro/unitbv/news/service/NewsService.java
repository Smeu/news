package ro.unitbv.news.service;

import java.util.List;

import ro.unitbv.news.model.News;
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
	 * @return the saved news.
	 */
	List<News> getNews(User user);

}
