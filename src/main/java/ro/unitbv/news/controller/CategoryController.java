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

/**
 * Controller for Category Page.
 *
 * @author Rares Smeu
 */
public class CategoryController extends AbstractController{

	@FXML
	private Label categoryLabel;

	@FXML
	private VBox container;

	@FXML
	private Button homepageButton;

	private CategoryService categoryService;

	private User user;

	private Category category;

	public CategoryController() {
		categoryService = ServiceFactory.getInstance().getCategoryService();
	}

	public void init(User user, Category category) {
		this.user = user;
		this.category = category;
		categoryLabel.setText("- Category -\n" + category.getName());
		NewsContainer newsContainer = new NewsContainer(user, this);
		List<News> newsList = getAllNews();
		newsContainer.setNews(newsList);
		container.getChildren().add(newsContainer.getComponent());
	}

	private List<News> getAllNews() {
		List<News> newsList = new ArrayList<>();
		newsList.addAll(categoryService.getNews(category, user).getResponse());
		Collections.sort(newsList, (first, second) -> second.getDate().compareTo(first.getDate()));
		return newsList;
	}

	public void redirectToHomepage() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(user);
	}
}
