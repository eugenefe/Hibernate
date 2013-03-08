package org.domain.revtest.entity;

// Generated Mar 8, 2013 4:58:15 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Portfolio generated by hbm2java
 */
@Entity
@Table(name = "PORTFOLIO", catalog = "takion77_db")
public class Portfolio implements java.io.Serializable {

	private String id;
	private String name;
	private String parentPortId;

	public Portfolio() {
	}

	public Portfolio(String id, String parentPortId) {
		this.id = id;
		this.parentPortId = parentPortId;
	}

	public Portfolio(String id, String name, String parentPortId) {
		this.id = id;
		this.name = name;
		this.parentPortId = parentPortId;
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

	@Column(name = "PARENT_PORT_ID", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getParentPortId() {
		return this.parentPortId;
	}

	public void setParentPortId(String parentPortId) {
		this.parentPortId = parentPortId;
	}

}
