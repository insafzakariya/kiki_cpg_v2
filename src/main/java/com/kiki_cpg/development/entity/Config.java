package com.kiki_cpg.development.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day_charge")
    private Double day_charge;

    @Column(name = "week_charge")
    private Double week_charge;
    /**
     *
     */
    public Config() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getDay_charge() {
        return day_charge;
    }
    public void setDay_charge(Double day_charge) {
        this.day_charge = day_charge;
    }
    public Double getWeek_charge() {
        return week_charge;
    }
    public void setWeek_charge(Double week_charge) {
        this.week_charge = week_charge;
    }

}
