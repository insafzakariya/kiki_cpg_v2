package org.kiki_cpg_v2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "viewer_tokens")
public class ViewerTokenEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id")
	private Integer id;

	@Column(name = "viewer_id")
	private int viewerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "token_id")
	private TokenEntity tokenEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getViewerId() {
		return viewerId;
	}

	public void setViewerId(int viewerId) {
		this.viewerId = viewerId;
	}

	public TokenEntity getTokenEntity() {
		return tokenEntity;
	}

	public void setTokenEntity(TokenEntity tokenEntity) {
		this.tokenEntity = tokenEntity;
	}

}
