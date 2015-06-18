package ro.unitbv.news.service;

import java.util.List;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;

/**
 * Service for operations that use comments.
 *
 * @author Teodora Tanase
 */
public interface CommentService {

	/**
	 * Creates a new comment.
	 *
	 * @param comment comment to be created.
	 * @return response holding id of the newly created comment or the errors.
	 */
	Response<Long> create(Comment comment);

	/**
	 * Gets a comment after its id.
	 *
	 * @param id the id of the comment.
	 * @return response holding the comment or the errors.
	 */
	Response<Comment> get(long id);

	/**
	 * Gets all comments for a piece of news.
	 *
	 * @param news piece of news to retrieve comments for.
	 * @return response holding the list of comments or the errors.
	 */
	Response<List<Comment>> getComments(News news);

	/**
	 * Deletes a comment after its id.
	 *
	 * @param id        id of the comment to delete.
	 * @param performer user that performs deletion.
	 * @return response holding <code>true</code> if deletion was performed, <code>false</code> if deletion was not
	 * performed or the errors.
	 */
	Response<Boolean> delete(long id, User performer);
}
