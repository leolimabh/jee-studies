package com.leandro.app.category.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.leandro.app.category.model.Category;

public class CategoryRepository {

	EntityManager em;

	public Category add(final Category category) {
		em.persist(category);
		return category;
	}

	public Category findById(final Long primaryKey) {
		if (primaryKey == null)
			return null;
		return em.find(Category.class, primaryKey);
	}

	public void update(final Category category) {
		em.merge(category);
	}

	@SuppressWarnings("unchecked")
	public List<Category> findAll(final String orderField) {
		return em.createQuery("From Category Order by :orderFiedl").setParameter("orderField", orderField)
				.getResultList();
	}

	public boolean alreadyExists(final Category category) {
		final StringBuilder sb = new StringBuilder();
		sb.append("Select 1 from Category where name = :name");
		if (category.getId() != null)
			sb.append(" and id != :id");

		final Query query = em.createQuery(sb.toString());
		query.setParameter("name", category.getName());
		if (category.getId() != null)
			query.setParameter("id", category.getId());

		query.setMaxResults(1);
		return !query.getResultList().isEmpty();
	}

	public boolean existsById(final Long id) {
		return !em.createQuery("select 1 from Category where id = :id").setParameter("id", id).setMaxResults(1)
				.getResultList().isEmpty();
	}

}
