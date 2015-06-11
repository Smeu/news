package ro.unitbv.news.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a category.
 *
 * @author Teodora Tanase
 */
public class Category {

	private long id;

	private String name;

	private List<String> keywords;

	public Category() {
		name = "";
		keywords = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Category)) {
			return false;
		}
		Category category = (Category) object;
		return name.equals(category.name);
	}
}
