package by.lozovenko.finalproject.entity;

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
}
