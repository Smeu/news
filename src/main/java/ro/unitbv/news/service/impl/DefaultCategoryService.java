package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.*;
import ro.unitbv.news.repository.CategoryRepository;
import ro.unitbv.news.service.CategoryService;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.validator.CategoryValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation for {@link ro.unitbv.news.service.CategoryService}.
 *
 * @author Teodora Tanase
 */
public class DefaultCategoryService implements CategoryService {

	private static final String PERFORMER = "performer";

	private CategoryRepository repository;

	private CategoryValidator validator;

	public DefaultCategoryService(CategoryRepository repository, CategoryValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public Response<Long> create(Category category) {
		ValidationResult validationResult = validator.validate(category);
		if (validationResult.hasErrors()) {
			return new Response<>(validationResult.getErrors());
		}
		long id = repository.create(category);
		return new Response<>(id);
	}

	@Override
	public Response<Category> get(long id) {
		Category category = repository.get(id);
		return new Response<>(category);
	}

	@Override
	public Response<List<Category>> getAll() {
		List<Category> categories = repository.getAll();
		return new Response<>(categories);
	}

	@Override
	public Response<List<News>> getNews(Category category, User user) {
		List<News> selectedNews = new ArrayList<>();
		selectedNews.addAll(findForCategoryAndUser(category, user));
		for (User followedUser : user.getFollowedUsers()) {
			selectedNews.addAll(findForCategoryAndUser(category, followedUser));
		}
		return new Response<>(selectedNews);
	}

	@Override
	public Response addKeyword(Category category, String keyword) {
		repository.addKeyword(category, keyword);
		return new Response();
	}

	private List<News> findForCategoryAndUser(Category category, User user) {
		NewsService newsService = ServiceFactory.getInstance().getNewsService();
		FeedService feedService = ServiceFactory.getInstance().getFeedService();
		List<News> selectedNews = new ArrayList<>();
		List<News> savedNews = newsService.getAll(user).getResponse();
		selectedNews.addAll(findForCategory(category, savedNews));
		List<Feed> personalFeeds = feedService.getAll(user).getResponse();
		for (Feed feed : personalFeeds) {
			List<News> feedNews = feedService.getNews(feed).getResponse();
			selectedNews.addAll(findForCategory(category, feedNews));
		}
		return selectedNews;
	}

	private List<News> findForCategory(Category category, List<News> newsList) {
		List<News> selectedNews = new ArrayList<>();
		for (News news : newsList) {
			if (news.getCategories().contains(category)) {
				selectedNews.add(news);
			}
		}
		return selectedNews;
	}

	@Override
	public Response<Boolean> delete(long id, User performer) {
		if (!performer.getType().equals(UserType.ADMIN)) {
			List<FieldError> errorList = new ArrayList<>();
			errorList.add(new FieldError(PERFORMER, ro.unitbv.news.model.Error.FAILED_REQUEST));
			return new Response<>(errorList);
		}
		return new Response<>(repository.delete(id));
	}
}
