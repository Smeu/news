package ro.unitbv.news.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;

/**
 * Database implementation for {@link ro.unitbv.news.repository.CommentRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseCommentRepository implements CommentRepository {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseCommentRepository.class);

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Comment comment) {
		return 0;
	}

	@Override
	public Comment get(long id) {
		return null;
	}

	@Override
	public List<Comment> getAllForNews(long newsId) {
		return null;
	}
}
