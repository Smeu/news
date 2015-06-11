package ro.unitbv.news.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

	@ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<NewsEntity> newsList;

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

	public List<NewsEntity> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsEntity> newsList) {
		this.newsList = newsList;
	}
}
