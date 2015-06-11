package ro.unitbv.news.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ro.unitbv.news.component.NewsContainer;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.FeedService;

/**
 * Controller for a feed's page.
 *
 * @author Teodora Tanase
 */
public class FeedPageController extends AbstractController {

	private Feed feed;

	private User loggedUser;

	private FeedService feedService;

	@FXML
	private Label feedLabel;

	@FXML
	private Button homepageButton;

	@FXML
	private VBox feedPageContainer;

	public FeedPageController() {
		feedService = ServiceFactory.getInstance().getFeedService();
	}

	public void init(Feed feed, User loggedUser) {
		this.feed = feed;
		this.loggedUser = loggedUser;
		feedLabel.setText(feed.getName());
		NewsContainer newsContainer = new NewsContainer(loggedUser, this);
		List<News> newsList = getAllNews();
		newsContainer.setNews(newsList);
		feedPageContainer.getChildren().add(newsContainer.getComponent());
	}

	private List<News> getAllNews() {
		List<News> newsList = new ArrayList<>();
		newsList.addAll(feedService.getNews(feed).getResponse());
		Collections.sort(newsList, (first, second) -> second.getDate().compareTo(first.getDate()));
		return newsList;
	}

	public void redirectToHomepage() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(loggedUser);
	}
}
