package ro.unitbv.news.factory;

import java.lang.reflect.Proxy;

import ro.unitbv.news.service.CategoryService;
import ro.unitbv.news.service.CommentService;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.service.impl.DefaultCategoryService;
import ro.unitbv.news.service.impl.DefaultCommentService;
import ro.unitbv.news.service.impl.DefaultFeedService;
import ro.unitbv.news.service.impl.DefaultNewsService;
import ro.unitbv.news.service.impl.DefaultUserService;
import ro.unitbv.news.service.proxy.ServiceInvocationHandler;

/**
 * Factory for services.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class ServiceFactory {

	private static ServiceFactory serviceFactory;

	private FeedService feedServiceProxy;
	private NewsService newsServiceProxy;
	private UserService userServiceProxy;
	private CommentService commentServiceProxy;
	private CategoryService categoryServiceProxy;

	private ServiceFactory() {
		RepositoryFactory repositoryFactory = new RepositoryFactory();
		ValidatorFactory validatorFactory = new ValidatorFactory();

		FeedService feedService = new DefaultFeedService(repositoryFactory.getFeedRepository(),
				validatorFactory.getFeedValidator());
		NewsService newsService = new DefaultNewsService(repositoryFactory.getNewsRepository());
		UserService userService = new DefaultUserService(repositoryFactory.getUserRepository(),
				validatorFactory.getUserValidator());
		CommentService commentService = new DefaultCommentService(repositoryFactory.getCommentRepository(),
				validatorFactory.getCommentValidator());
		CategoryService categoryService = new DefaultCategoryService(repositoryFactory.getCategoryRepository(),
				validatorFactory.getCategoryValidator());

		feedServiceProxy = (FeedService) Proxy.newProxyInstance(FeedService.class.getClassLoader(), new Class[]
				{FeedService.class}, new ServiceInvocationHandler(feedService));
		newsServiceProxy = (NewsService) Proxy.newProxyInstance(NewsService.class.getClassLoader(), new Class[]
				{NewsService.class}, new ServiceInvocationHandler(newsService));
		userServiceProxy = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]
				{UserService.class}, new ServiceInvocationHandler(userService));
		commentServiceProxy = (CommentService) Proxy.newProxyInstance(CommentService.class.getClassLoader(), new Class[]
				{CommentService.class}, new ServiceInvocationHandler(commentService));
		categoryServiceProxy = (CategoryService) Proxy.newProxyInstance(CategoryService.class.getClassLoader(), new
				Class[]{CategoryService.class}, new ServiceInvocationHandler(categoryService));
	}

	public FeedService getFeedService() {
		return feedServiceProxy;
	}

	public NewsService getNewsService() {
		return newsServiceProxy;
	}

	public UserService getUserService() {
		return userServiceProxy;
	}

	public CommentService getCommentService() {
		return commentServiceProxy;
	}

	public CategoryService getCategoryService() {
		return categoryServiceProxy;
	}

	public static ServiceFactory getInstance() {
		if (serviceFactory == null) {
			serviceFactory = new ServiceFactory();
		}
		return serviceFactory;
	}
}
