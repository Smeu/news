package ro.unitbv.news.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.parser.RssFeedParser;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.validator.FeedValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation for {@link ro.unitbv.news.service.FeedService}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class DefaultFeedService implements FeedService {

	private static final String ID = "id";
	private static final String USER = "user";
	private static final String URL = "url";

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
	public Response<List<Feed>> getAll(User user) {
		if (user == null) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(USER, Error.FIELD_IS_MANDATORY));
			return new Response<>(errors);
		}
		return new Response<>(repository.getAllForOwner(user.getId()));
	}

	@Override
	public Response<Feed> get(long id) {
		try {
			Feed feed = repository.get(id);
			return new Response<>(feed);
		}
		catch (InvalidIdException e) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(ID, Error.INVALID_ID));
			return new Response<>(errors);
		}
	}

	@Override
	public Response<List<News>> getNews(Feed feed) {
		try {
			URL url = new URL(feed.getUrl());
			URLConnection connection = url.openConnection();
			RssFeedParser parser = new RssFeedParser();
			try (InputStream inputStream = connection.getInputStream()) {
				List<News> newsList = parser.retrieveNews(inputStream);
				for (News news : newsList) {
					news.setFeedId(feed.getId());
				}
				return new Response<>(newsList);
			}
		}
		catch (IOException e) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(URL, Error.URL_NOT_AVAILABLE));
			return new Response<>(errors);
		}
	}
}
