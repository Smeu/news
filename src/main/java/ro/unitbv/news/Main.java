package ro.unitbv.news;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ro.unitbv.news.controller.LoginPageController;
import static ro.unitbv.news.controller.Page.LOGIN_PAGE;

/**
 * @author Rares Smeu
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Login");
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		primaryStage.setOnCloseRequest(t -> {
			Platform.exit();
			System.exit(0);
		});

		FXMLLoader myLoader = new FXMLLoader(Main.class.getClassLoader().getResource(LOGIN_PAGE.getPagePath()));
		Pane myPane = myLoader.load();
		LoginPageController controller = myLoader.getController();
		controller.setPrimaryStage(primaryStage);

		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}


}
