package sustech.cs304.entity;

import java.time.LocalDate;

public class Assignment {
    private String name;
    private Course courseBelongTo;
    private LocalDate dueDate;
    private String status;
    private String discription;

    public Assignment() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourseBelongTo() {
        return courseBelongTo;
    }

    public void setCourseBelongTo(Course courseBelongTo) {
        this.courseBelongTo = courseBelongTo;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
