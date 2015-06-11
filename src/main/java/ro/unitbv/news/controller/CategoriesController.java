package ro.unitbv.news.controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.CategoryService;

/**
 * @author Rares Smeu
 */
public class CategoriesController extends AbstractController {

	@FXML
	private VBox categoriesContainer;

	@FXML
	private Button homepageButton;

	private User user;

	private CategoryService categoryService;

	public CategoriesController() {
		categoryService = ServiceFactory.getInstance().getCategoryService();
	}

	public void init(User user) {
		this.user = user;
		for (Category category : categoryService.getAll().getResponse()) {
			showCategory(category);
		}
		addCategoryUI(user);
	}

	private void addCategoryUI(User user) {
		Button addCategoryButton = new Button("Add Category");
		addCategoryButton.setOnMouseClicked(event -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Category");
			dialog.setHeaderText("Add a Category");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				Category category = new Category();
				category.setName(result.get());
				categoryService.create(category);
				refreshPage();
			}
		});
		Separator separator = new Separator(Orientation.HORIZONTAL);
		separator.setPadding(new Insets(0,0,20,0));
		categoriesContainer.getChildren().add(separator);
		categoriesContainer.getChildren().add(addCategoryButton);
	}

	private void refreshPage() {
		CategoriesController categoriesController = redirectTo(Page.CATEGORIES_PAGE);
		categoriesController.init(user);
	}

	private void showCategory(Category category) {
		Label label = new Label(category.getName());
		categoriesContainer.getChildren().add(label);
		FlowPane pane = new FlowPane();
		category.getKeywords().forEach(keyword -> {
			Label keywordLabel = new Label(keyword + "  ");
			pane.getChildren().add(keywordLabel);
		});
		categoriesContainer.getChildren().add(pane);
		Button addKeywordButton = new Button("Add keyword");
		addKeywordButton.setOnMouseClicked(event -> {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Keyword");
			dialog.setHeaderText("Add a keyword");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				categoryService.addKeyword(category, result.get());
				refreshPage();
			}
		});
		categoriesContainer.getChildren().add(addKeywordButton);
		Separator separator = new Separator(Orientation.HORIZONTAL);
		separator.setPadding(new Insets(10,0,0,0));
		categoriesContainer.getChildren().add(separator);
	}

	public void redirectToHomepage() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(user);
	}
}
