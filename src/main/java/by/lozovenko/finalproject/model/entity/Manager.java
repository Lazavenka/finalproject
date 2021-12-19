package by.lozovenko.finalproject.model.entity;

public class Manager extends User{
    private long departmentId;
    private String imageFilePath;
    private String description;
    private long laboratoryId;
    private ManagerDegree managerDegree;

    public Manager(long id, String login, String password, String firstName, String lastName,
                   String email, String phone, UserRole role, UserState state, long departmentId,
                   String imageFilePath, String description, long laboratoryId, ManagerDegree managerDegree) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.departmentId = departmentId;
        this.imageFilePath = imageFilePath;
        this.description = description;
        this.laboratoryId = laboratoryId;
        this.managerDegree = managerDegree;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(long laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public ManagerDegree getDegree() {
        return managerDegree;
    }

    public void setDegree(ManagerDegree managerDegree) {
        this.managerDegree = managerDegree;
    }
}
