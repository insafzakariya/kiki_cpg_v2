/**
 * @author Anjana Thrishakya
 * Nov 2, 2020
 * 9:02:27 AM
 */
package org.kiki_cpg_v2.dto;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class SubscriptionDto {

	private Integer id;
	private String name;
	private Date expireDate;
	private boolean subscribed;
	private List<PlanDto> planDtos;

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

	public Date getExpireDate() {
		return expireDate;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public List<PlanDto> getPlanDtos() {
		return planDtos;
	}

	public void setPlanDtos(List<PlanDto> planDtos) {
		this.planDtos = planDtos;
	}

	@Override
	public String toString() {
		return "SubscriptionDto [id=" + id + ", name=" + name + ", expireDate=" + expireDate + ", subscribed="
				+ subscribed + ", planDtos=" + planDtos + "]";
	}

}
