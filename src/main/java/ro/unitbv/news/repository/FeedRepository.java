package ro.unitbv.news.repository;

import ro.unitbv.news.model.Feed;

/**
 * Repository for {@link ro.unitbv.news.model.Feed}
 *
 * @author Rares Smeu
 */
public interface FeedRepository {

	/**
	 * Creates a new feed.
	 *
	 * @param feed feed to be created.
	 * @return the id of the newly created feed.
	 */
	long create(Feed feed);

}
