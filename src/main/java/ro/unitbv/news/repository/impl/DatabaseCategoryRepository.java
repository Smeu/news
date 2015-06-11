package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import ro.unitbv.news.entity.CategoryEntity;
import ro.unitbv.news.model.Category;
import ro.unitbv.news.repository.CategoryRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;

/**
 * Database implementation for {@link ro.unitbv.news.repository.CategoryRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseCategoryRepository implements CategoryRepository {

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Category category) {
		CategoryEntity categoryEntity = converter.toCategoryEntity(category);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(categoryEntity);
			session.getTransaction().commit();
			return categoryEntity.getId();
		}
		finally {
			session.close();
		}
	}

	@Override
	public Category get(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			CategoryEntity categoryEntity = (CategoryEntity) session.get(CategoryEntity.class, id);
			if (categoryEntity == null) {
				throw new InvalidIdException();
			}
			session.getTransaction().commit();
			return converter.toCategoryModel(categoryEntity);
		}
		finally {
			session.close();
		}
	}

	@Override
	public List<Category> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			List<CategoryEntity> categoryEntities = session.createQuery("from CategoryEntity").list();
			session.getTransaction().commit();
			List<Category> categories = new ArrayList<>();
			for (CategoryEntity categoryEntity : categoryEntities) {
				categories.add(converter.toCategoryModel(categoryEntity));
			}
			return categories;
		}
		finally {
			session.close();
		}
	}
}
