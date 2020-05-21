package org.kiki_cpg_v2.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tokens")
public class TokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Integer id;

	@Column(name = "type")
	private Integer type;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "expire_date")
	private Date expireDate;

	@Column(name = "status")
	private Integer status;

	@Column(name = "token_hash")
	private String tokenHash;

	@OneToMany(mappedBy = "tokenEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ViewerTokenEntity> viewerTokenEntities = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTokenHash() {
		return tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public List<ViewerTokenEntity> getViewerTokenEntities() {
		return viewerTokenEntities;
	}

	public void setViewerTokenEntities(List<ViewerTokenEntity> viewerTokenEntities) {
		this.viewerTokenEntities = viewerTokenEntities;
	}

}
