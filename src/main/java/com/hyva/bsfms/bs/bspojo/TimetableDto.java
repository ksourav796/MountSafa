/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hyva.bsfms.bs.bspojo;

import com.hyva.bsfms.bs.bsentities.PeriodsMaster;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Nataraj t
 */
public class TimetableDto {

   private List<PeriodsMaster> periodsList;
    private List<TimeTablePojo> list;
    private String level;
    private String className;
    private String weekday;

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PeriodsMaster> getPeriodsList() {
        return periodsList;
    }

    public void setPeriodsList(List<PeriodsMaster> periodsList) {
        this.periodsList = periodsList;
    }

    public List<TimeTablePojo> getList() {
        return list;
    }

    public void setList(List<TimeTablePojo> list) {
        this.list = list;
    }
}
