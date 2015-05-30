package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.impl.InMemoryNewsRepository;
import ro.unitbv.news.service.NewsService;

/**
 * Default implementation for {@link ro.unitbv.news.service.NewsService}.
 *
 * @author Teodora Tanase
 */
public class DefaultNewsService implements NewsService {

	private static final String USER = "user";

	private NewsRepository repository = new InMemoryNewsRepository();

	@Override
	public Response<List<News>> getNews(User user) {
		if (user == null) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(USER, Error.FIELD_IS_MANDATORY));
			return new Response<>(errors);
		}
		return new Response<>(repository.getAllForOwner(user.getId()));
	}
}
