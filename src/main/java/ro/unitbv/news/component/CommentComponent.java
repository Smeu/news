package ro.unitbv.news.component;

import ro.unitbv.news.controller.AbstractController;
import ro.unitbv.news.controller.CommentController;
import ro.unitbv.news.model.Comment;

/**
 * Component for a comment
 *
 * @author Rares Smeu
 */
public class CommentComponent extends AbstractComponent {

	public CommentComponent(Comment comment, AbstractController mainController) {
		super(mainController);
		CommentController controller = loadComponent(Component.COMMENT_COMPONENT);
		controller.setMainController(mainController);
		controller.init(comment);
	}

}
