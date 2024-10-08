package io.hhplus.cleancode.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name="SugangSchedule", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sugang_id", "classdate"})
})
public class SugangScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sugangschedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "sugang_id", referencedColumnName = "sugang_id")
//    @Column(nullable = false)
    private SugangEntity sugang;

//    @ManyToOne
//    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
////    @Column(nullable = false)
//    private Student student;

    @Column(nullable = false, name="classdate")
    private String classDate;

    @Column(nullable = true)
    private Long availNum;


    public SugangScheduleEntity() {
    }

    public SugangScheduleEntity(Long scheduleId, SugangEntity sugang, String classDate, Long availNum) {
        this.scheduleId = scheduleId;
        this.sugang = sugang;
        this.classDate = classDate;
        this.availNum = availNum;
    }

    public void setData(Long scheduleId, SugangEntity sugang, String classDate, Long availNum) {
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

    public SugangEntity getSugang() {
        return sugang;
    }

    public void setSugang(SugangEntity sugang) {
        this.sugang = sugang;
    }

//    public Student getStudent() {
//        return student;
//    }
//
//    public void setStudent(Student student) {
//        this.student = student;
//    }

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
