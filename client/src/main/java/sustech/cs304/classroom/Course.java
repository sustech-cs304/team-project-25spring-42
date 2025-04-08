package sustech.cs304.classroom;

import sustech.cs304.userhome.User;
import java.util.ArrayList;
import java.time.LocalDate;

public class Course {
    private User teacher;
    private ArrayList<User> students = new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();

    public Course() {

    }

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

    public boolean addAssignment(User user, String name, LocalDate dueDate, String status, String discription) {
        if (this.teacher == user) {
            Assignment assignment = new Assignment();
            assignment.setName(name);
            assignment.setCourseBelongTo(this);
            assignment.setDueDate(dueDate);
            assignment.setDiscription(discription);
            this.assignments.add(assignment);
            return true;
        } else {
            System.out.println("非老师无权限布置作业");
            return false;
        }
    }


    public ArrayList<User> getStudents() {
        return students;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
}