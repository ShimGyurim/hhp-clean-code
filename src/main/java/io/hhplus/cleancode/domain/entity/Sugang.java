package io.hhplus.cleancode.domain.entity;

import jakarta.persistence.*;

public class Sugang {


    private Long sugangId;

    private String className;

    private String teacher;

    public Sugang() {
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Sugang(Long sugangId) {
        this.sugangId = sugangId;
    }

    public Long getSugangId() {
        return sugangId;
    }

    public void setSugangId(Long sugangId) {
        this.sugangId = sugangId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public Sugang(Long sugangId, String className, String teacher) {
        this.sugangId = sugangId;
        this.className = className;
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Sugang{" +
                "sugangId=" + sugangId +
                ", className='" + className + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
