package com.kiki_cpg.development.dto;

public class CronConfigDto {
    private Integer id;
    private String cronType;
    private String startTime;
    private String lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCronType() {
        return cronType;
    }

    public void setCronType(String cronType) {
        this.cronType = cronType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
