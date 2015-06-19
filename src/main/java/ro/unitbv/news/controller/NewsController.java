package ro.unitbv.news.controller;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ro.unitbv.news.component.CommentComponent;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.CommentService;
import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.service.UserService;

/**
 * Controller for the news container component.
 *
 * @author Rares Smeu
 */
public class NewsController extends AbstractController {

	@FXML
	private Text title;
	@FXML
	private Text content;
	@FXML
	private Label feedTitle;
	@FXML
	private Button saveButton;
	@FXML
	private Button deleteButton;
	@FXML
	private VBox commentContainer;
	@FXML
	private FlowPane categoriesContainer;
	@FXML
	private VBox newsHolder;

	private CommentService commentService;

	private UserService userService;

	private FeedService feedService;

	private User newsOwner;

	private Feed newsFeed;

	private News news;

	private User user;

	public void init(News news, User user) {
		commentService = ServiceFactory.getInstance().getCommentService();
		userService = ServiceFactory.getInstance().getUserService();
		feedService = ServiceFactory.getInstance().getFeedService();
		this.news = news;
		this.user = user;
		title.setText(news.getTitle());
		content.setText(news.getContent());
		addCategories();
		if (news.getOwnerId() != 0) {
			findNewsOwner();
			if (newsOwner != null) {
				feedTitle.setText(newsOwner.getUsername());
			}
			saveButton.setVisible(false);
			addComments();
			if (news.getOwnerId() == user.getId()) {
				deleteButton.setVisible(true);
			}
		}
		else {
			findNewsFeed();
			if (newsFeed != null) {
				feedTitle.setText(newsFeed.getName());
			}
		}
	}

	private void findNewsOwner() {
		Response<User> response = userService.get(news.getOwnerId());
		if (!response.hasErrors()) {
			newsOwner = response.getResponse();
		}
	}

	private void findNewsFeed() {
		Response<Feed> response = feedService.get(news.getFeedId());
		if (!response.hasErrors()) {
			newsFeed = response.getResponse();
		}
	}

	private void addCategories() {
		news.getCategories().forEach(category -> {
			Label categoryLabel = new Label(category.getName());
			categoryLabel.setCursor(Cursor.HAND);
			categoryLabel.setPadding(new Insets(0, 5, 0, 5));
			categoryLabel.setUnderline(true);
			categoryLabel.setOnMouseClicked(event -> {
				CategoryController controller = redirectTo(Page.CATEGORY_PAGE);
				controller.init(user, category);
			});
			categoriesContainer.getChildren().add(categoryLabel);
		});
	}

	private void addComments() {
		Task task = new Task() {
			@Override
			public void run() {
				while (true) {
					if (shouldStop) {
						return;
					}
					Response<List<Comment>> comments = commentService.getComments(news);
					if (comments.getResponse().size() != commentContainer.getChildren().size() - 1) {
						Platform.runLater(() -> commentContainer.getChildren().clear());
						if (!comments.hasErrors()) {
							for (Comment comment : comments.getResponse()) {
								CommentComponent component = new CommentComponent(comment, user, mainController);
								Platform.runLater(() -> commentContainer.getChildren().add(component.getComponent()));
							}
						}
						Label addComment = new Label("Add Comment");
						addComment.setCursor(Cursor.HAND);
						addComment.setOnMouseClicked(event -> commentDialog());
						Platform.runLater(() -> commentContainer.getChildren().add(addComment));
					}
					try {
						Thread.sleep(5000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		runningTasks.add(task);
		pool.execute(task);
	}


	private void commentDialog() {
		commentDialog("", Collections.emptyList());
	}

	private void commentDialog(String initialText, List<FieldError> errors) {
		TextInputDialog dialog = new TextInputDialog(initialText);
		if (!errors.isEmpty()) {
			FieldError firstError = errors.get(0);
			if (firstError.getError() == Error.MAX_LENGTH_EXCEEDED) {
				dialog.setContentText("Comment should not exceed 1024 characters");
			}
			if (firstError.getError() == Error.INVALID_ID) {
				newsNoLongerExist();
				return;
			}
		}
		dialog.setTitle("Add a comment");
		dialog.setHeaderText("Add a comment");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Comment comment = new Comment();
			comment.setOwnerId(user.getId());
			comment.setNewsId(news.getId());
			comment.setContent(result.get());
			comment.setPostingDate(Calendar.getInstance().getTime());
			Response<Long> response = commentService.create(comment);
			if (response.hasErrors()) {
				commentDialog(result.get(), response.getErrors());
			}
			else {
				addComments();
			}
		}
	}

	private void newsNoLongerExist() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("News");
		alert.setHeaderText("News no longer exists");
		alert.setContentText("Can not comment on the news because it was deleted.");
		alert.showAndWait();
	}

	public void goToNewsSource() throws Exception {
		Stage stage = new Stage();
		stage.setTitle(news.getTitle());
		stage.setWidth(500);
		stage.setHeight(500);
		Scene scene = new Scene(new Group());
		VBox root = new VBox();
		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.load(news.getUrl());
		root.getChildren().add(browser);
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
	}

	public void saveNews() {
		ServiceFactory.getInstance().getNewsService().add(news, user);
		saveButton.setVisible(false);
		deleteButton.setVisible(true);
	}

	public void goToOwner() {
		if (newsOwner != null) {
			UserPageController userPageController = redirectTo(Page.USER_PAGE);
			userPageController.init(user, newsOwner);
		}
		else if (newsFeed != null) {
			FeedPageController feedPageController = redirectTo(Page.FEED_PAGE);
			feedPageController.init(newsFeed, user);
		}
	}

	public void deleteNews() {
		NewsService newsService = ServiceFactory.getInstance().getNewsService();
		newsService.delete(news.getId(), user);
		Pane parent = (Pane) newsHolder.getParent();
		parent.getChildren().remove(newsHolder);
	}
}
