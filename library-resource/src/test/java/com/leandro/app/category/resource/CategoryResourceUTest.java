package com.leandro.app.category.resource;

import static com.leandro.app.commontests.category.CategoryForTestsRepository.categoryWithId;
import static com.leandro.app.commontests.category.CategoryForTestsRepository.java;
import static com.leandro.app.commontests.utils.FileTestNameUtils.getPathFileRequest;
import static com.leandro.app.commontests.utils.JsonTestUtils.assertJsonMatchesExpectedJson;
import static com.leandro.app.commontests.utils.JsonTestUtils.readJsonFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.leandro.app.category.model.Category;
import com.leandro.app.category.services.CategoryServices;

public class CategoryResourceUTest {
	private CategoryResource categoryResource;

	private static final String PATH_RESOURCE = "categories";

	@Mock
	private CategoryServices categoryServices;

	@Before
	public void initTestCase() {
		MockitoAnnotations.initMocks(this);
		categoryResource = new CategoryResource();

		categoryResource.categoryServices = categoryServices;
		categoryResource.categoryJsonConverter = new CategoryJsonConverter();
	}

	@Test
	public void addValidCategory() {
		when(categoryServices.add(Matchers.any(Category.class))).thenReturn(categoryWithId(java(), 1L));

		final Response response = categoryResource
				.add(readJsonFile(getPathFileRequest(PATH_RESOURCE, "newCategory.json")));
		assertThat(response.getStatus(), is(equalTo(201)));
		assertJsonMatchesExpectedJson(response.getEntity().toString(), "{\"id\": 1}");
	}
}
