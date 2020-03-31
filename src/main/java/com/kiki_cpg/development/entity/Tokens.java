package com.kiki_cpg.development.entity;

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
public class Tokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Integer token_id;
	
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "created_date")
	private Date created_date;
	
	@Column(name = "expire_date")
	private Date expire_date;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "token_hash")
	private String token_hash;
	
	@OneToMany(mappedBy = "token_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ViewerTokens> viewer_tokens = new ArrayList<>();

	public Integer getToken_id() {
		return token_id;
	}

	public void setToken_id(Integer token_id) {
		this.token_id = token_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getToken_hash() {
		return token_hash;
	}

	public void setToken_hash(String token_hash) {
		this.token_hash = token_hash;
	}

	public List<ViewerTokens> getViewer_tokens() {
		return viewer_tokens;
	}

	public void setViewer_tokens(List<ViewerTokens> viewer_tokens) {
		this.viewer_tokens = viewer_tokens;
	}
	
	
	

}
