package com.leandro.app.category.services.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.leandro.app.category.exception.CategoryExistentException;
import com.leandro.app.category.exception.CategoryNotFoundException;
import com.leandro.app.category.model.Category;
import com.leandro.app.category.repository.CategoryRepository;
import com.leandro.app.category.services.CategoryServices;
import com.leandro.app.common.exception.FieldNotValidException;

public class CategoryServicesImpl implements CategoryServices {

	Validator validator;
	CategoryRepository categoryRepository;

	@Override
	public Category add(final Category category) {
		validateCategory(category);
		return categoryRepository.add(category);
	}

	@Override
	public void update(final Category category) {
		validateCategory(category);

		if (!categoryRepository.existsById(category.getId()))
			throw new CategoryNotFoundException();

		categoryRepository.update(category);
	}

	@Override
	public Category findById(final long id) {
		if (!categoryRepository.existsById(id))
			throw new CategoryNotFoundException();

		return categoryRepository.findById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll("name");
	}

	private void validateCategory(final Category category) {
		validateCategoryFields(category);
		if (categoryRepository.alreadyExists(category))
			throw new CategoryExistentException();
	}

	private void validateCategoryFields(final Category category) {
		final Set<ConstraintViolation<Category>> errors = validator.validate(category);
		final Iterator<ConstraintViolation<Category>> itErrors = errors.iterator();
		if (itErrors.hasNext()) {
			final ConstraintViolation<Category> violation = itErrors.next();
			throw new FieldNotValidException(violation.getPropertyPath().toString(), violation.getMessage());
		}
	}

}
