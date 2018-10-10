package com.leandro.app.category.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "category")
@ToString(includeFieldNames = true)
@NoArgsConstructor
public @Data class Category implements Serializable {

	private static final long serialVersionUID = 762520880394916563L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 2, max = 25, message = "Name should be greater than 2 and shorter than 25.")
	@Column(unique = true)
	private String name;

	public Category(final String name) {
		this.name = name;
	}

}
