package org.kiki_cpg_v2.entity;

import javax.persistence.*;

@Entity
@Table(name = "cron_report")
public class CronReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cronId")
    private Integer cronId;

    @Column(name = "cronType")
    private String cronType;

    @Column(name = "startTime")
    private String startTime;
    
    @Column(name = "endTime")
    private String endTime;

    @Column(name = "startedDate")
    private String startedDate;

    @Column(name = "serverIp")
    private String serverIp;
    
    @Column(name = "cronName")
    private String cronName;

    public Integer getCronId() {
        return cronId;
    }

    public void setCronId(Integer cronId) {
        this.cronId = cronId;
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

    public String getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCronName() {
		return cronName;
	}

	public void setCronName(String cronName) {
		this.cronName = cronName;
	}

	@Override
	public String toString() {
		return "CronReportEntity [cronId=" + cronId + ", cronType=" + cronType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", startedDate=" + startedDate + ", serverIp=" + serverIp + ", cronName="
				+ cronName + "]";
	}
    
}
