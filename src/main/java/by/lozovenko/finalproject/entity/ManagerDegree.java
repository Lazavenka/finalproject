package by.lozovenko.finalproject.entity;

public enum ManagerDegree {
    BACHELOR("B.Sc."),
    MASTER("M.Sc."),
    DOCTOR("Ph.D.");
    private final String value;
    ManagerDegree(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
