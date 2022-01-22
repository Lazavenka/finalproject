package by.lozovenko.finalproject.model.entity;

public class Manager extends User{
    private long managerId;
    private long departmentId;
    private String imageFilePath;
    private String description;
    private long laboratoryId;
    private ManagerDegree managerDegree;

    public Manager(){
    }
    public Manager(User user){
        super(user);
    }
    public Manager(long id, String login, String password, String firstName, String lastName,
                   String email, String phone, UserRole role, UserState state, long departmentId,
                   String imageFilePath, String description, long laboratoryId, ManagerDegree managerDegree, long managerId) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.departmentId = departmentId;
        this.imageFilePath = imageFilePath;
        this.description = description;
        this.laboratoryId = laboratoryId;
        this.managerDegree = managerDegree;
        this.managerId = managerId;
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

    public ManagerDegree getManagerDegree() {
        return managerDegree;
    }

    public void setManagerDegree(ManagerDegree managerDegree) {
        this.managerDegree = managerDegree;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Manager{");
        sb.append(super.toString());
        sb.append("managerId=").append(managerId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", imageFilePath='").append(imageFilePath).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", laboratoryId=").append(laboratoryId);
        sb.append(", managerDegree=").append(managerDegree);
        sb.append('}');
        return sb.toString();
    }
}
