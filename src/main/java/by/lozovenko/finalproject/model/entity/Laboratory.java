package by.lozovenko.finalproject.model.entity;

public class Laboratory extends CustomEntity{
    private String name;
    private String location;
    private String imageFilePath;
    private String description;
    private long departmentId;

    public Laboratory(){
    }
    public Laboratory(long id, String name, String location, String imageFilePath, String description, long departmentId) {
        super(id);
        this.name = name;
        this.location = location;
        this.imageFilePath = imageFilePath;
        this.description = description;
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Laboratory that = (Laboratory) o;

        if (departmentId != that.departmentId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (imageFilePath != null ? !imageFilePath.equals(that.imageFilePath) : that.imageFilePath != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (imageFilePath != null ? imageFilePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) (departmentId ^ (departmentId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Laboratory{");
        sb.append("name='").append(name).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", imageFilePath='").append(imageFilePath).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", departmentId=").append(departmentId);
        sb.append('}');
        return sb.toString();
    }
}
