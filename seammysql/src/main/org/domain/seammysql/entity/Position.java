package org.domain.seammysql.entity;

// Generated Mar 8, 2013 4:46:14 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Position generated by hbm2java
 */
@Entity
@Table(name = "POSITION", catalog = "takion77_db")
public class Position implements java.io.Serializable {

	private String id;
	private String name;
	private String productId;
	private String dealerId;

	public Position() {
	}

	public Position(String id, String productId, String dealerId) {
		this.id = id;
		this.productId = productId;
		this.dealerId = dealerId;
	}

	public Position(String id, String name, String productId, String dealerId) {
		this.id = id;
		this.name = name;
		this.productId = productId;
		this.dealerId = dealerId;
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

	@Column(name = "PRODUCT_ID", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "DEALER_ID", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	public String getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

}
