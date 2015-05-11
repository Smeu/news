package ro.unitbv.news.service;

import java.util.List;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;

/**
 * Service for operations that use feeds.
 *
 * @author Rares Smeu
 */
public interface FeedService {

	/**
	 * Creates a new feed.
	 *
	 * @param feed to be created.
	 * @return response holding id of the newly feed or the errors.
	 */
	Response<Long> create(Feed feed);

	/**
	 * Gets all the feeds from a user.
	 *
	 * @param user user from which to bring the feeds.
	 * @return the list of feeds.
	 */
	List<Feed> getAll(User user);


	/**
	 * Gets a feed after its id.
	 *
	 * @param id the id of the feed.
	 * @return the feed.
	 */
	Feed get(long id);


	/**
	 * Gets the latest news from the feed.
	 * Requires an internet connection;
	 *
	 * @param feed the source of the news.
	 * @return latest news.
	 */
	List<News> getNews(Feed feed);

}
