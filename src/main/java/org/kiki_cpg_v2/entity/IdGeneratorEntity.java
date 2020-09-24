/**
 * @DaSep 23, 2020 @IdGeneratorEntity.java
 */
package org.kiki_cpg_v2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Anjana Thrishakya
 */
@Entity
@Table(name = "id_generator")
public class IdGeneratorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer currentSequence;

	private Integer increment;
	
	private String type;
	
	private Integer status;

	@Column(columnDefinition = "DATETIME")
	private Date createDate;

	@Column(columnDefinition = "DATETIME")
	private Date updateDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurrentSequence() {
		return currentSequence;
	}

	public void setCurrentSequence(Integer currentSequence) {
		this.currentSequence = currentSequence;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
