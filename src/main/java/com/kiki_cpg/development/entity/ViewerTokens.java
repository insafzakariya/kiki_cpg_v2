package com.kiki_cpg.development.entity;

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
public class ViewerTokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id")
	private Integer rec_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "token_id")
	private Tokens token_id;

	@Column(name = "rec_id")
	private int viewer_id;

	public Integer getRec_id() {
		return rec_id;
	}

	public void setRec_id(Integer rec_id) {
		this.rec_id = rec_id;
	}

	public Tokens getToken_id() {
		return token_id;
	}

	public void setToken_id(Tokens token_id) {
		this.token_id = token_id;
	}

	public int getViewer_id() {
		return viewer_id;
	}

	public void setViewer_id(int viewer_id) {
		this.viewer_id = viewer_id;
	}
	
	

}
