package by.lozovenko.finalproject.model.entity;

public class EquipmentType extends CustomEntity{
    private String name;
    private String description;

    public EquipmentType(long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
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
}
