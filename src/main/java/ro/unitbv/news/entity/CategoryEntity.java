package ro.unitbv.news.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Database entity for a category.
 *
 * @author Teodora Tanase
 */
@Entity
@Table(name = "category")
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "category_keyword", joinColumns = @JoinColumn(name = "category_id"))
	private List<String> keywords;

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

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void addKeyword(String keyword) {
		if (keywords == null) {
			keywords = new ArrayList<>();
		}
		keywords.add(keyword);
	}
}
