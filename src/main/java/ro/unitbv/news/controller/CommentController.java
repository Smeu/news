package ro.unitbv.news.controller;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	private UserService userService = ServiceFactory.getInstance().getUserService();

	private Comment comment;

	private User loggedUser;

	public void init(Comment comment) {
		this.comment = comment;
		loggedUser = new User(); // TODO: assign logged user
		Response<User> userResponse = userService.get(comment.getOwnerId());
		author.setText(userResponse.getResponse().getUsername());
		content.setText(comment.getContent());
		date.setText(dateFormat.format(comment.getPostingDate()));
		if (comment.getOwnerId() == loggedUser.getId()) {
			deleteButton.setVisible(true);
		}
	}

	public void deleteComment() {
		CommentService commentService = ServiceFactory.getInstance().getCommentService();
		commentService.delete(comment.getId(), loggedUser);
	}

	public void redirectToUserPage() {
		// TODO REDIRECT
	}
}
