package com.leandro.app.category.services.impl;

import static com.leandro.app.commontests.category.CategoryForTestsRepository.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

import com.leandro.app.category.exception.CategoryExistentException;
import com.leandro.app.category.exception.CategoryNotFoundException;
import com.leandro.app.category.model.Category;
import com.leandro.app.category.repository.CategoryRepository;
import com.leandro.app.category.services.CategoryServices;
import com.leandro.app.common.exception.FieldNotValidException;

public class CategoryServicesUTest {

	private CategoryServices categoryServices;
	private CategoryRepository categoryRepository;
	private Validator validator;

	@Before
	public void initTestCase() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();

		categoryRepository = mock(CategoryRepository.class);

		categoryServices = new CategoryServicesImpl();
		((CategoryServicesImpl) categoryServices).validator = validator;
		((CategoryServicesImpl) categoryServices).categoryRepository = categoryRepository;
	}

	@Test
	public void addCategoryWithNullName() {
		addCategoryWithInvalidName(null);
	}

	@Test
	public void addCategoryWithShortName() {
		addCategoryWithInvalidName("A");
	}

	@Test
	public void addCategoryWithLongName() {
		addCategoryWithInvalidName("This is a long name that will cause an exception to be trhown!");
	}

	@Test(expected = CategoryExistentException.class)
	public void addCategoryWithExistentName() {
		when(categoryRepository.alreadyExists(java())).thenReturn(true);
		categoryServices.add(java());
	}

	@Test
	public void addValidCategory() {
		when(categoryRepository.alreadyExists(java())).thenReturn(false);
		when(categoryRepository.add(java())).thenReturn(categoryWithId(java(), 1L));

		final Category categoryAdded = categoryServices.add(java());
		assertThat(categoryAdded.getId(), is(equalTo(1L)));

	}

	@Test
	public void updateCategoryWithNullName() {
		updateCategoryWithInvalidName(null);
	}

	@Test
	public void updateCategoryWithShortName() {
		updateCategoryWithInvalidName("A");
	}

	@Test
	public void updateCategoryWithLongName() {
		updateCategoryWithInvalidName("This is a long name that will cause an exception to be trhown!");
	}

	@Test(expected = CategoryExistentException.class)
	public void updateCategoryWithExistentName() {
		final Category categoryWithId = categoryWithId(java(), 1L);
		when(categoryRepository.alreadyExists(categoryWithId)).thenReturn(true);
		categoryServices.update(categoryWithId);
	}

	@Test(expected = CategoryNotFoundException.class)
	public void updateCategoryNotFound() {
		final Category categoryWithId = categoryWithId(java(), 1L);
		when(categoryRepository.alreadyExists(categoryWithId)).thenReturn(false);
		when(categoryRepository.existsById(1L)).thenReturn(false);
		categoryServices.update(categoryWithId);
	}

	@Test
	public void updateValidaCategory() {
		final Category categoryWithId = categoryWithId(java(), 1L);
		when(categoryRepository.alreadyExists(categoryWithId)).thenReturn(false);
		when(categoryRepository.existsById(1L)).thenReturn(true);
		categoryServices.update(categoryWithId);

		verify(categoryRepository).update(categoryWithId);
	}

	@Test(expected = CategoryNotFoundException.class)
	public void findCategoryByIdNotFound() {
		when(categoryRepository.existsById(1L)).thenReturn(false);
		categoryServices.findById(1L);
	}

	@Test
	public void findCategoryById() {
		final Category categoryWithId = categoryWithId(java(), 1L);
		when(categoryRepository.findById(1L)).thenReturn(categoryWithId);
		when(categoryRepository.existsById(1L)).thenReturn(true);
		final Category categoryFound = categoryServices.findById(1L);

		assertThat(categoryFound, is(equalTo(categoryWithId)));
	}

	@Test
	public void findAllNoCategories() {
		when(categoryRepository.findAll("name")).thenReturn(Collections.emptyList());
		final List<Category> categories = categoryServices.findAll();

		assertThat(categories.isEmpty(), is(equalTo(true)));
	}

	@Test
	public void findAllCategories() {
		final List<Category> categories = Arrays.asList(categoryWithId(java(), 1L), categoryWithId(networks(), 2L));
		when(categoryRepository.findAll("name")).thenReturn(categories);
		final List<Category> categoriesFound = categoryServices.findAll();

		assertThat(categoriesFound, is(equalTo(categories)));
	}

	private void addCategoryWithInvalidName(final String name) {
		try {
			categoryServices.add(new Category(name));
			fail("An error should have been thrown");
		} catch (final FieldNotValidException e) {
			assertThat(e.getFieldName(), is(equalTo("name")));
		}
	}

	private void updateCategoryWithInvalidName(final String name) {
		try {
			categoryServices.update(new Category(name));
			fail("An error should have been thrown");
		} catch (final FieldNotValidException e) {
			assertThat(e.getFieldName(), is(equalTo("name")));
		}
	}

}
