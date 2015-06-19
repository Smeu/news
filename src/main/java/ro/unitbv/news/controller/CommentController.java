package ro.unitbv.news.controller;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.CommentService;
import ro.unitbv.news.service.UserService;

/**
 * Controller for comment component.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class CommentController extends AbstractController {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a dd MMM yy");

	@FXML
	private Label author;
	@FXML
	private Text content;
	@FXML
	private Label date;
	@FXML
	private Button deleteButton;
	@FXML
	private VBox commentHolder;

	private UserService userService = ServiceFactory.getInstance().getUserService();

	private Comment comment;

	private User loggedUser;

	private User owner;

	public void init(Comment comment, User loggedUser) {
		this.comment = comment;
		this.loggedUser = loggedUser;
		Response<User> userResponse = userService.get(comment.getOwnerId());
		author.setText(userResponse.getResponse().getUsername());
		content.setText(comment.getContent());
		date.setText(dateFormat.format(comment.getPostingDate()));
		owner = userResponse.getResponse();
		if (comment.getOwnerId() == loggedUser.getId()) {
			deleteButton.setVisible(true);
		}
	}

	public void deleteComment() {
		CommentService commentService = ServiceFactory.getInstance().getCommentService();
		commentService.delete(comment.getId(), loggedUser);
		Pane parent = (Pane) commentHolder.getParent();
		parent.getChildren().remove(commentHolder);
	}

	public void redirectToUserPage() {
		UserPageController userPageController = redirectTo(Page.USER_PAGE);
		userPageController.init(loggedUser, owner);
	}
}
