package com.leandro.app.category.repository;

import static com.leandro.app.commontests.category.CategoryForTestsRepository.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.leandro.app.category.model.Category;
import com.leandro.app.commontests.utils.DBCommandTransactionExecutor;

public class CategoryRepositoryUTest {
	private EntityManagerFactory emf;
	private EntityManager em;
	private CategoryRepository categoryRepository;

	private DBCommandTransactionExecutor dbCommandTransationExecutor;

	@Before
	public void initTestCase() {
		emf = Persistence.createEntityManagerFactory("libraryPU");
		em = emf.createEntityManager();
		categoryRepository = new CategoryRepository();
		categoryRepository.em = em;
		dbCommandTransationExecutor = new DBCommandTransactionExecutor(em);
	}

	@After
	public void closeEntityManager() {
		em.close();
		emf.close();
	}

	@Test
	public void addCategoryAndFindId() {
		final Long categoryAddedId = dbCommandTransationExecutor
				.executeCommand(() -> categoryRepository.add(java()).getId());
		assertThat(categoryAddedId, is(notNullValue()));
		final Category category = categoryRepository.findById(categoryAddedId);
		assertThat(category, is(notNullValue()));
		assertThat(category.getName(), is(equalTo(java().getName())));
	}

	@Test
	public void findCategoryByIdNotFound() {
		final Category category = categoryRepository.findById(999L);
		assertThat(category, is(nullValue()));
	}

	@Test
	public void findCategoryByIdWithNullId() {
		final Category category = categoryRepository.findById(null);
		assertThat(category, is(nullValue()));
	}

	@Test
	public void updateCategory() {
		final Long categoryAddedId = dbCommandTransationExecutor
				.executeCommand(() -> categoryRepository.add(java()).getId());
		final Category categoryAfterAdd = categoryRepository.findById(categoryAddedId);
		assertThat(categoryAfterAdd.getName(), is(equalTo(java().getName())));

		categoryAfterAdd.setName(cleanCode().getName());
		dbCommandTransationExecutor.executeCommand(() -> {
			categoryRepository.update(categoryAfterAdd);
			return null;
		});

		final Category categoryAfterUpdate = categoryRepository.findById(categoryAddedId);
		assertThat(categoryAfterUpdate.getName(), is(equalTo(cleanCode().getName())));
	}

	@Test
	public void findAllCategories() {
		dbCommandTransationExecutor
				.executeCommand(() -> {
					allCategories().forEach(categoryRepository::add);
					return null;
				});
		final List<Category> categories = categoryRepository.findAll("name");
		assertThat(categories.size(), is(equalTo(4)));
		assertThat(categories.stream().map(Category::getName).collect(Collectors.toList()),
				hasItems(allCategories().stream().map(Category::getName).collect(Collectors.toList()).toArray()));
	}

	@Test
	public void alreadyExistsForAdd() {
		dbCommandTransationExecutor.executeCommand(() -> categoryRepository.add(java()));
		assertThat(categoryRepository.alreadyExists(java()), is(equalTo(true)));
		assertThat(categoryRepository.alreadyExists(cleanCode()), is(equalTo(false)));
	}

	@Test
	public void alreadyExistsCategoryWithId() {
		final Category java = dbCommandTransationExecutor.executeCommand(() -> {
			categoryRepository.add(cleanCode());
			return categoryRepository.add(java());
		});

		assertThat(categoryRepository.alreadyExists(java), is(equalTo(false)));

		java.setName(cleanCode().getName());
		assertThat(categoryRepository.alreadyExists(java), is(equalTo(true)));

		java.setName(networks().getName());
		assertThat(categoryRepository.alreadyExists(java), is(equalTo(false)));
	}

	@Test
	public void existsById() {
		final Long categoryAddedId = dbCommandTransationExecutor
				.executeCommand(() -> categoryRepository.add(java()).getId());

		assertThat(categoryRepository.existsById(categoryAddedId), is(equalTo(true)));
		assertThat(categoryRepository.existsById(999L), is(equalTo(false)));
	}

}
