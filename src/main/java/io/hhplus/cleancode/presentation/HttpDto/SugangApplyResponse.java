package io.hhplus.cleancode.presentation.HttpDto;

public class SugangApplyResponse {

    private long sugangId;
    private long studentId;
    private long availNum;
    private String classDate;
    private String className;
    private String message;

    public SugangApplyResponse() {
    }

    public SugangApplyResponse(String message) {
        this.message = message;
        this.sugangId=0L;
        this.studentId=0L;
        this.availNum=0L;
        this.classDate="";
        this.className="";
    }

    public SugangApplyResponse(long sugangId, long studentId, long availNum, String classDate, String className, String message) {
        this.sugangId = sugangId;
        this.studentId = studentId;
        this.availNum = availNum;
        this.classDate = classDate;
        this.className = className;
        this.message = message;
    }

    public long getSugangId() {
        return sugangId;
    }

    public long getStudentId() {
        return studentId;
    }

    public long getAvailNum() {
        return availNum;
    }

    public String getClassDate() {
        return classDate;
    }

    public String getClassName() {
        return className;
    }

    public String getMessage() {
        return message;
    }
}
