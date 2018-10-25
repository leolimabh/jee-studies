package com.leandro.app.category.resource;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leandro.app.category.model.Category;
import com.leandro.app.category.services.CategoryServices;
import com.leandro.app.common.json.JsonUtils;
import com.leandro.app.common.json.OperationResultJsonWriter;
import com.leandro.app.common.model.HttpCode;
import com.leandro.app.common.model.OperationResult;

public class CategoryResource {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	CategoryServices categoryServices;
	CategoryJsonConverter categoryJsonConverter;

	public Response add(final String body) {
		logger.debug("Adding a new category with body {}", body);

		Category category = categoryJsonConverter.convertFrom(body);
		category = categoryServices.add(category);

		final OperationResult result = OperationResult.success(JsonUtils.getJsonElementWithId(category.getId()));

		logger.debug("Returning the operation result after adding category: {}", result);

		return Response.status(HttpCode.CREATED.getCode()).entity(OperationResultJsonWriter.toJson(result)).build();
	}
}
