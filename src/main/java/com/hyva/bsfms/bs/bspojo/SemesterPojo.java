package com.hyva.bsfms.bs.bspojo;

import lombok.Data;

@Data
public class SemesterPojo {
    private Long semesterId;
    private String semesterName;
    private String level;
    private String branchId;
    private String startDate;
    private String endDate;
    private String description;
    private String semesterStatus;

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSemesterStatus() {
        return semesterStatus;
    }

    public void setSemesterStatus(String semesterStatus) {
        this.semesterStatus = semesterStatus;
    }
}

