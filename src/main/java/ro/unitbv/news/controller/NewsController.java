package ro.unitbv.news.controller;

import java.awt.*;
import java.net.URI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;

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

	private News news;

	private User user;

	public void init(News news, User user) {
		this.news = news;
		this.user = user;
		title.setText(news.getTitle());
		content.setText(news.getContent());
		feedTitle.setText("feed title");
		if (news.getOwnerId() != 0) {
			saveButton.setVisible(false);
		}
	}

	public void goToNewsSource() throws Exception {
		Desktop.getDesktop().browse(new URI(news.getUrl()));
	}

	public void saveNews() {
		ServiceFactory.getInstance().getNewsService().add(news, user);
		saveButton.setVisible(false);
	}
}
