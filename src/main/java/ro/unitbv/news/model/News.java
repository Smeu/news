package ro.unitbv.news.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model for news.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class News {

	private long id;

	private long ownerId;

	private long feedId;

	private String title;

	private String content;

	private String url;

	private Date date;

	private List<Category> categories;

	public News() {
		categories = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void addCategory(Category category) {
		categories.add(category);
	}

	@Override
	public String toString() {
		return "News{" +
				"id=" + id +
				", ownerId=" + ownerId +
				", feedId=" + feedId +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", url='" + url + '\'' +
				", date=" + date +
				'}';
	}
}
