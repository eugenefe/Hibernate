package org.domain.revtest.entity;

// Generated Mar 8, 2013 4:58:15 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "PRODUCT", catalog = "takion77_db")
public class Product implements java.io.Serializable {

	private String id;
	private String name;
	private String type;

	public Product() {
	}

	public Product(String id) {
		this.id = id;
	}

	public Product(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50)
	@Size(max = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 10)
	@Size(max = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
