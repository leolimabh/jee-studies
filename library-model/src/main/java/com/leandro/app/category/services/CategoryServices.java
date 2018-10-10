package com.leandro.app.category.services;

import java.util.List;

import com.leandro.app.category.model.Category;

public interface CategoryServices {

	Category add(final Category category);

	void update(Category category);

	Category findById(long id);

	List<Category> findAll();
}
