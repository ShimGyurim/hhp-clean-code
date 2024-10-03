package io.hhplus.cleancode.domain.entity;


import jakarta.persistence.*;

public class SugangHistory {

    private Long historyId;

    private SugangSchedule sugangSchedule;

    private Sugang sugang;

    private Student student;


    public SugangHistory() {
    }

    public SugangHistory(Long historyId, SugangSchedule sugangSchedule, Sugang sugang, Student student) {
        this.historyId = historyId;
        this.sugangSchedule = sugangSchedule;
        this.sugang = sugang;
        this.student = student;
    }

    //    private String classDate;

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public void setSugangSchedule(SugangSchedule sugangSchedule) {
        this.sugangSchedule = sugangSchedule;
    }

    public void setSugang(Sugang sugang) {
        this.sugang = sugang;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

//    public void setClassDate(String classDate) {
//        this.classDate = classDate;
//    }

    public Long getHistoryId() {
        return historyId;
    }

    public SugangSchedule getSugangSchedule() {
        return sugangSchedule;
    }

    public Sugang getSugang() {
        return sugang;
    }

    public Student getStudent() {
        return student;
    }

//    public String getClassDate() {
//        return classDate;
//    }

    @Override
    public String toString() {
        return "SugangHistory{" +
                "historyId=" + historyId +
                ", sugangSchedule=" + sugangSchedule +
                ", sugang=" + sugang +
                ", student=" + student +
//                ", classDate='" + classDate + '\'' +
                '}';
    }
}
