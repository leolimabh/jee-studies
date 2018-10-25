package com.leandro.app.category.resource;

import com.google.gson.JsonObject;
import com.leandro.app.category.model.Category;
import com.leandro.app.common.json.JsonReader;

public class CategoryJsonConverter {

	public Category convertFrom(final String json) {
		final JsonObject jsonObject = JsonReader.readAsJsonObject(json);

		final Category category = new Category();
		category.setName(JsonReader.getStringOrNull(jsonObject, "name"));
		return category;
	}
}
