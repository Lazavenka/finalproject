package by.lozovenko.finalproject.model.entity;

public class Manager extends User {
    private long managerId;
    private long departmentId;
    private String imageFilePath;
    private String description;
    private long laboratoryId;
    private ManagerDegree managerDegree;

    public Manager() {
    }

    public Manager(User user) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Manager manager = (Manager) o;

        if (managerId != manager.managerId) return false;
        if (departmentId != manager.departmentId) return false;
        if (laboratoryId != manager.laboratoryId) return false;
        if (imageFilePath != null ? !imageFilePath.equals(manager.imageFilePath) : manager.imageFilePath != null)
            return false;
        if (description != null ? !description.equals(manager.description) : manager.description != null) return false;
        return managerDegree == manager.managerDegree;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (managerId ^ (managerId >>> 32));
        result = 31 * result + (int) (departmentId ^ (departmentId >>> 32));
        result = 31 * result + (imageFilePath != null ? imageFilePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (laboratoryId ^ (laboratoryId >>> 32));
        result = 31 * result + (managerDegree != null ? managerDegree.hashCode() : 0);
        return result;
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
