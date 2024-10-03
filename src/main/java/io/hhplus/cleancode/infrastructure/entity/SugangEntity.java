package io.hhplus.cleancode.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name="SUGANG")
public class SugangEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sugang_id")
    private Long sugangId;

    private String className;

    private String teacher;

    public SugangEntity() {
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public SugangEntity(Long sugangId) {
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


    @Override
    public String toString() {
        return "SugangEntity{" +
                "sugangId=" + sugangId +
                ", className='" + className + '\'' +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
