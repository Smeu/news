package ro.unitbv.news;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ro.unitbv.news.controller.LoginPageController;
import static ro.unitbv.news.controller.Page.LOGIN_PAGE;

/**
 * @author Rares Smeu
 */
public class Main extends Application {

	private final static Logger log = LoggerFactory.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Login");
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);

		FXMLLoader myLoader = new FXMLLoader(Main.class.getClassLoader().getResource(LOGIN_PAGE.getPagePath()));
		Pane myPane = myLoader.load();
		LoginPageController controller = myLoader.getController();
		controller.setPrimaryStage(primaryStage);

		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
