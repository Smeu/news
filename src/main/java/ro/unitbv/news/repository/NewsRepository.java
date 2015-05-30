package ro.unitbv.news.repository;

import java.util.List;

import ro.unitbv.news.model.News;

/**
 * Repository for {@link ro.unitbv.news.model.News}.
 *
 * @author Teodora Tanase
 */
public interface NewsRepository {

	/**
	 * Creates a new news piece.
	 *
	 * @param news news piece to be created.
	 * @return the id of the newly created news piece.
	 */
	long create(News news);

	/**
	 * Gets a news piece for its id.
	 *
	 * @param id id of the news piece.
	 * @return the news piece.
	 */
	News get(long id);

	/**
	 * Gets all pieces of news belonging to the specified owner id.
	 *
	 * @param ownerId owner id to retrieve news for.
	 * @return list of news belonging to that owner.
	 */
	List<News> getAllForOwner(long ownerId);
}
