package ro.unitbv.news.controller;

/**
 * Enum for maintaining the pages sources.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public enum Page {

	LOGIN_PAGE("Login", "pages/login.fxml"),

	CREATE_USER_PAGE("Create User", "pages/createUser.fxml"),

	ADD_FEED_PAGE("Add Feed", "pages/addFeed.fxml"),

	HOME_PAGE("Home Page", "pages/homepage.fxml");

	private String pagePath;

	private String title;

	Page(String title, String pagePath) {
		this.pagePath = pagePath;
		this.title = title;
	}

	public String getPagePath() {
		return pagePath;
	}

	public String getTitle() {
		return title;
	}
}
