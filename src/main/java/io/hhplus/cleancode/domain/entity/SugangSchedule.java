package io.hhplus.cleancode.domain.entity;

import jakarta.persistence.*;

public class SugangSchedule {

    private Long scheduleId;

    private Sugang sugang;

    private String classDate;

    private Long availNum;

    public SugangSchedule() {
    }

    public SugangSchedule(Long scheduleId, Sugang sugang, String classDate, Long availNum) {
        this.scheduleId = scheduleId;
        this.sugang = sugang;
        this.classDate = classDate;
        this.availNum = availNum;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public Sugang getSugang() {
        return sugang;
    }

    public void setSugang(Sugang sugang) {
        this.sugang = sugang;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public Long getAvailNum() {
        return availNum;
    }

    public void setAvailNum(Long availNum) {
        this.availNum = availNum;
    }

    @Override
    public String toString() {
        return "SugangSchedule{" +
                "scheduleId=" + scheduleId +
                ", sugang=" + sugang.toString() +
//                ", student=" + student +
                ", classDate='" + classDate + '\'' +
                ", availNum=" + availNum +
                '}';
    }
}
