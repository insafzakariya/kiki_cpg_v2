package org.kiki_cpg_v2.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class ConfigEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "day_charge")
	private Double dayCharge;

	@Column(name = "week_charge")
	private Double weekCharge;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDayCharge() {
		return dayCharge;
	}

	public void setDayCharge(Double dayCharge) {
		this.dayCharge = dayCharge;
	}

	public Double getWeekCharge() {
		return weekCharge;
	}

	public void setWeekCharge(Double weekCharge) {
		this.weekCharge = weekCharge;
	}

}
