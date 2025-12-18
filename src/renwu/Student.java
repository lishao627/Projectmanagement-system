package renwu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private String name;
    private String studentId;
    private String className;
    private String major;
    private List<ExperimentReport> reports;

    public Student(String name, String studentId, String className, String major) {
        this.name = name;
        this.studentId = studentId;
        this.className = className;
        this.major = major;
        this.reports = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<ExperimentReport> getReports() {
        return reports;
    }

    public void setReports(List<ExperimentReport> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId != null ? studentId.equals(student.studentId) : student.studentId == null;
    }

    @Override
    public int hashCode() {
        return studentId != null ? studentId.hashCode() : 0;
    }
}
