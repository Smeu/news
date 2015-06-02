package ro.unitbv.news.repository;

import java.util.List;

import ro.unitbv.news.model.Comment;

/**
 * Repository for {@link ro.unitbv.news.model.Comment}.
 *
 * @author Teodora Tanase
 */
public interface CommentRepository {

	/**
	 * Creates a new comment.
	 *
	 * @param comment comment to be created.
	 * @return the id of the newly created comment.
	 */
	long create(Comment comment);

	/**
	 * Gets a comment after its id.
	 *
	 * @param id the id of the comment.
	 * @return the comment.
	 */
	Comment get(long id);

	/**
	 * Gets all comments belonging to the specified news id.
	 *
	 * @param newsId news id to retrieve comment for.
	 * @return list of comments belonging to that news.
	 */
	List<Comment> getAllForNews(long newsId);
}
