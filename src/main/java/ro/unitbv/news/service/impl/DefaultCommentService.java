package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.service.CommentService;
import ro.unitbv.news.validator.CommentValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation for {@link ro.unitbv.news.service.CommentService}.
 *
 * @author Teodora Tanase
 */
public class DefaultCommentService implements CommentService {

	private static final String NEWS = "news";

	private CommentRepository repository;

	private CommentValidator validator;

	public DefaultCommentService(CommentRepository repository, CommentValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public Response<Long> create(Comment comment) {
		ValidationResult result = validator.validate(comment);
		if (result.hasErrors()) {
			return new Response<>(result.getErrors());
		}
		return new Response<>(repository.create(comment));
	}

	@Override
	public Response<Comment> get(long id) {
		Comment comment = repository.get(id);
		return new Response<>(comment);
	}

	@Override
	public Response<List<Comment>> getComments(News news) {
		if (news == null) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(NEWS, Error.FIELD_IS_MANDATORY));
			return new Response<>(errors);
		}
		return new Response<>(repository.getAllForNews(news.getId()));
	}
}
