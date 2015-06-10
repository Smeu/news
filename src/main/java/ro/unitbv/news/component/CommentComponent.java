package ro.unitbv.news.component;

import ro.unitbv.news.controller.CommentController;
import ro.unitbv.news.model.Comment;

/**
 * @author Rares Smeu
 */
public class CommentComponent extends AbstractComponent {

	public CommentComponent(Comment comment) {
		CommentController controller = loadComponent(Component.COMMENT_COMPONENT);
		controller.init(comment);
	}

}
