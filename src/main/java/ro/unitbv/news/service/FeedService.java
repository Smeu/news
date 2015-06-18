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
 * @author Teodora Tanase
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
	 * @return response holding the list of feeds or the errors.
	 */
	Response<List<Feed>> getAll(User user);


	/**
	 * Gets a feed after its id.
	 *
	 * @param id the id of the feed.
	 * @return response holding the feed or the errors.
	 */
	Response<Feed> get(long id);


	/**
	 * Gets the latest news from the feed.
	 * Requires an internet connection;
	 *
	 * @param feed the source of the news.
	 * @return response holding latest news or the errors.
	 */
	Response<List<News>> getNews(Feed feed);

	/**
	 * Deletes a feed after its id.
	 *
	 * @param id        id of the feed to delete.
	 * @param performer user that performs deletion.
	 * @return response holding <code>true</code> if deletion was performed, <code>false</code> if deletion was not
	 * performed or the errors.
	 */
	Response<Boolean> delete(long id, User performer);
}
