package com.leandro.app.commontests.category;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;

import com.leandro.app.category.model.Category;

@Ignore
public class CategoryForTestsRepository {

	public static Category java() {
		return new Category("java");
	}

	public static Category cleanCode() {
		return new Category("cleanCode");
	}

	public static Category archtecture() {
		return new Category("archtecture");
	}

	public static Category networks() {
		return new Category("networks");
	}

	public static List<Category> allCategories() {
		return Arrays.asList(java(), cleanCode(), archtecture(), networks());
	}

	public static Category categoryWithId(Category category, long id) {
		category.setId(id);
		return category;
	}
}
