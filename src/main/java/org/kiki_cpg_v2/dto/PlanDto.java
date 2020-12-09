/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 9:08:27 AM
 */
package org.kiki_cpg_v2.dto;

import java.util.Date;

/**
 *
 */
public class PlanDto {

	private Integer id;
	private String name;
	private Double amount;
	private Date expDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
}
