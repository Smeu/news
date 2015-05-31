package ro.unitbv.news.controller;

/**
 * Enum for maintaining the pages sources.
 *
 * @author Rares Smeu
 */
public enum Page {

	LOGIN_PAGE("Login", "pages/login.fxml"),

	CREATE_USER_PAGE("Create User", "pages/createUser.fxml");

	private String pagePath;

	private String title;

	private Page(String title, String pagePath) {
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
