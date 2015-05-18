package ro.unitbv.news.service.impl;

import java.util.List;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.validator.FeedValidator;
import ro.unitbv.news.validator.ValidationResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Default implementation for {@link ro.unitbv.news.service.FeedService}
 *
 * @author Rares Smeu
 */
public class DefaultFeedService implements FeedService {

	private FeedRepository repository;

	private FeedValidator validator;

	public DefaultFeedService(FeedRepository repository, FeedValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public Response<Long> create(Feed feed) {
		Response<Long> response = new Response<>();
		ValidationResult result = validator.validate(feed);
		if (result.hasErrors()) {
			response.setErrors(result.getErrors());
			return response;
		}
		response.setResponse(repository.create(feed));
		return response;
	}

	@Override
	public List<Feed> getAll(User user) {
		throw new NotImplementedException();
	}

	@Override
	public Feed get(long id) {
		throw new NotImplementedException();
	}

	@Override
	public List<News> getNews(Feed feed) {
		throw new NotImplementedException();
	}
}
