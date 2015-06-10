package ro.unitbv.news.controller;

import java.awt.*;
import java.net.URI;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ro.unitbv.news.component.CommentComponent;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.CommentService;

/**
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
	private VBox commentContainer;

	private CommentService commentService;

	private News news;

	private User user;

	public void init(News news, User user) {
		commentService = ServiceFactory.getInstance().getCommentService();
		this.news = news;
		this.user = user;
		title.setText(news.getTitle());
		content.setText(news.getContent());
		feedTitle.setText("feed title");
		if (news.getOwnerId() != 0) {
			saveButton.setVisible(false);
			addComments();
		}
	}

	private void addComments() {
		commentContainer.getChildren().clear();
		Response<List<Comment>> comments = commentService.getComments(news);
		if (!comments.hasErrors()) {
			for (Comment comment : comments.getResponse()) {
				CommentComponent component = new CommentComponent(comment);
				commentContainer.getChildren().add(component.getComponent());
			}
		}
		Label addComment = new Label("Add Comment");
		addComment.setCursor(Cursor.HAND);
		addComment.setOnMouseClicked(event -> commentDialog());
		commentContainer.getChildren().add(addComment);
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
		Desktop.getDesktop().browse(new URI(news.getUrl()));
	}

	public void saveNews() {
		ServiceFactory.getInstance().getNewsService().add(news, user);
		saveButton.setVisible(false);
	}
}
