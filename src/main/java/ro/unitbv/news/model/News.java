package ro.unitbv.news.model;

import java.util.Date;

/**
 * Model for news.
 *
 * @author Rares Smeu
 */
public class News {

	private long id;

	private long ownerId;

	private long feedId;

	private String title;

	private String content;

	private String url;

	private Date date;

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
