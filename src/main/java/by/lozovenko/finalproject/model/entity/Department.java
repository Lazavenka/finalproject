package by.lozovenko.finalproject.model.entity;

public class Department extends CustomEntity{
    private String name;
    private String description;
    private String address;

    public Department(){
    }
    public Department(long id, String name, String description, String address) {
        super(id);
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
