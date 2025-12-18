package renwu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Student> studentList = new ArrayList<>();
    private final String filePath = "students.dat";

    public DataManager() {
        loadData();
    }

    public void addStudent(Student student) {
        studentList.add(student);
        saveData();
    }

    public void deleteStudent(Student student) {
        studentList.remove(student);
        saveData();
    }

    public void updateStudent(Student oldStudent, Student newStudent) {
        int index = studentList.indexOf(oldStudent);
        if (index >= 0) {
            // 保留原有的报告列表
            List<ExperimentReport> reports = studentList.get(index).getReports();
            newStudent.getReports().clear();
            newStudent.getReports().addAll(reports);
            studentList.set(index, newStudent);
            saveData();
        }
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Student s : studentList) {
            if (s.getName().toLowerCase().contains(lowerKeyword) ||
                    s.getStudentId().toLowerCase().contains(lowerKeyword) ||
                    s.getClassName().toLowerCase().contains(lowerKeyword) ||
                    s.getMajor().toLowerCase().contains(lowerKeyword)) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Student> getAllStudents() {
        return studentList;
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            studentList = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
