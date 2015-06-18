package ro.unitbv.news.repository;

import java.util.List;

import ro.unitbv.news.model.Feed;

/**
 * Repository for {@link ro.unitbv.news.model.Feed}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public interface FeedRepository {

	/**
	 * Creates a new feed.
	 *
	 * @param feed feed to be created.
	 * @return the id of the newly created feed.
	 */
	long create(Feed feed);

	/**
	 * Gets all feeds for the specified owner id.
	 *
	 * @param ownerId owner id to retrieve feeds for.
	 * @return a list of feeds belonging to that owner.
	 */
	List<Feed> getAllForOwner(long ownerId);

	/**
	 * Gets a feed for its id.
	 *
	 * @param id id of the feed.
	 * @return the feed for that id.
	 */
	Feed get(long id);

	/**
	 * Deletes a feed after its id.
	 *
	 * @param id id of the feed to be deleted.
	 * @return <code>true</code> if deletion was performed or false otherwise.
	 */
	boolean delete(long id);
}
