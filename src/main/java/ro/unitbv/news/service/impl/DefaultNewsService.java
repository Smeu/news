package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.service.classifier.Classifier;

/**
 * Default implementation for {@link ro.unitbv.news.service.NewsService}.
 *
 * @author Teodora Tanase
 */
public class DefaultNewsService implements NewsService {

	private static final String USER = "user";
	private static final String PERFORMER = "performer";

	private NewsRepository repository;

	private Classifier classifier;

	public DefaultNewsService(NewsRepository repository) {
		this.repository = repository;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	@Override
	public Response<List<News>> getAll(User user) {
		if (user == null) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(USER, Error.FIELD_IS_MANDATORY));
			return new Response<>(errors);
		}
		List<News> newsList = repository.getAllForOwner(user.getId());
		for (News news : newsList) {
			classifier.assignCategories(news);
		}
		return new Response<>(newsList);
	}

	@Override
	public Response<Void> add(News news, User user) {
		news.setOwnerId(user.getId());
		repository.create(news);
		return new Response<>();
	}

	@Override
	public Response<Boolean> delete(long id, User performer) {
		News news = repository.get(id);
		if (news == null) {
			return new Response<>(false);
		}
		if (performer.getId() != news.getOwnerId()) {
			List<FieldError> errorList = new ArrayList<>();
			errorList.add(new FieldError(PERFORMER, Error.FAILED_REQUEST));
			return new Response<>(errorList);
		}
		return new Response<>(repository.delete(id));
	}
}
