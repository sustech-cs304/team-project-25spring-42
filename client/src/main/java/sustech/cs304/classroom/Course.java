package sustech.cs304.classroom;

import sustech.cs304.userhome.User;
import java.util.ArrayList;

public class Course {
    private User teacher;
    private ArrayList<User> students = new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void addStudent(User student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
}