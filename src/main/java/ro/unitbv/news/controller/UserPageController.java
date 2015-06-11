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
import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.CategoryService;
import ro.unitbv.news.service.NewsService;
import sun.font.Decoration;

/**
 * Controller for a user's page.
 *
 * @author Teodora Tanase
 */
public class UserPageController extends AbstractController {

	private NewsService newsService;

	private User user;

	@FXML
	private Label usernameLabel;

	@FXML
	private VBox userPageContainer;

	@FXML
	private Button homepageButton;

	public UserPageController() {
		newsService = ServiceFactory.getInstance().getNewsService();
	}

	public void init(User user) {
		this.user = user;
		usernameLabel.setText(user.getUsername() + "\n- My News -");
		NewsContainer newsContainer = new NewsContainer(user);
		List<News> newsList = getAllNews();
		newsContainer.setNews(newsList);
		userPageContainer.getChildren().add(newsContainer.getComponent());
	}

	private List<News> getAllNews() {
		List<News> newsList = new ArrayList<>();
		newsList.addAll(newsService.getAll(user).getResponse());
		Collections.sort(newsList, (first, second) -> second.getDate().compareTo(first.getDate()));
		return newsList;
	}

	public void redirectToHomepage() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(user);
	}
}
