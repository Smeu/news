package ro.unitbv.news.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ro.unitbv.news.component.NewsContainer;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.service.UserService;

/**
 * Controller for the home page.
 *
 * @author Rares Smeu
 */
public class HomePageController extends AbstractController {

	@FXML
	private VBox homePageContainer;

	@FXML
	private Label username;

	private User user;

	private FeedService feedService;

	private UserService userService;

	private NewsService newsService;

	public HomePageController() {
		feedService = ServiceFactory.getInstance().getFeedService();
		userService = ServiceFactory.getInstance().getUserService();
		newsService = ServiceFactory.getInstance().getNewsService();
	}

	public void init(User user) {
		this.user = user;
		username.setText(user.getUsername());
		homePageContainer.setAlignment(Pos.TOP_CENTER);
		List<News> news = getAllNews(user);
		if (news.size() == 0) {
			Label label = new Label("No News");
			label.setPadding(new Insets(25, 25, 25, 25));
			label.setFont(new Font(20));
			label.setTextFill(Color.GRAY);
			homePageContainer.getChildren().add(label);
			return;
		}
		NewsContainer container = new NewsContainer(user, this);
		container.setNews(news);
		homePageContainer.getChildren().add(container.getComponent());
	}

	private List<News> getAllNews(User user) {
		List<News> news = new ArrayList<>();
		news.addAll(newsService.getAll(user).getResponse());
		for (Feed feed : feedService.getAll(user).getResponse()) {
			Response<List<News>> response = feedService.getNews(feed);
			if (!response.hasErrors()) {
				news.addAll(response.getResponse());
			}
		}
		for (User followedUser : userService.getFollowedUsers(user.getId()).getResponse()) {
			Response<List<News>> response = newsService.getAll(followedUser);
			if (!response.hasErrors()) {
				news.addAll(response.getResponse());
			}
		}
		Collections.sort(news, (first, second) -> second.getDate().compareTo(first.getDate()));
		return news;
	}

	public void addFeed() {
		AddFeedPageController controller = redirectTo(Page.ADD_FEED_PAGE);
		controller.setUser(user);
	}

	public void users() {
		UsersController controller = redirectTo(Page.USERS_PAGE);
		controller.init(user, userService.getAll().getResponse());
	}

	public void loadOwnNews() {
		UserPageController controller = redirectTo(Page.USER_PAGE);
		controller.init(user, user);
	}

	public void categories() {
		CategoriesController controller = redirectTo(Page.CATEGORIES_PAGE);
		controller.init(user);
	}

	public void logout() {
		redirectTo(Page.LOGIN_PAGE);
	}

	public void redirectToFeeds() {
		FeedsController feedsController = redirectTo(Page.FEEDS_PAGE);
		feedsController.init(user);
	}
}
