package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.CommentRepository}.
 * Should not be used in multi-threading contexts.
 *
 * @author Teodora Tanase
 */
public class InMemoryCommentRepository implements CommentRepository {

	private List<Comment> comments = new ArrayList<>();

	@Override
	public long create(Comment comment) {
		comments.add(comment);
		return comments.size() - 1;
	}

	@Override
	public Comment get(long id) {
		if (id < 0 || id >= comments.size()) {
			throw new InvalidIdException();
		}
		return comments.get((int) id);
	}

	@Override
	public List<Comment> getAllForNews(long newsId) {
		List<Comment> selectedComments = new ArrayList<>();
		for (Comment comment : comments) {
			if (comment.getNewsId() == newsId) {
				selectedComments.add(comment);
			}
		}
		return selectedComments;
	}

	@Override
	public boolean delete(long id) {
		if (id < 0 || id >= comments.size()) {
			throw new InvalidIdException();
		}
		comments.set((int) id, null);
		return true;
	}
}
