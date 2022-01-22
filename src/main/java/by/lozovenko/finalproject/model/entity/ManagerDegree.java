package by.lozovenko.finalproject.model.entity;

import java.util.Arrays;
import java.util.Optional;

public enum ManagerDegree {
    BACHELOR("B.Sc."),
    MASTER("M.Sc."),
    DOCTOR("Ph.D."),
    EMPTY("");
    private final String value;
    ManagerDegree(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
    public static ManagerDegree getDegreeByString(String degree){
        Optional<ManagerDegree> managerDegree = Arrays.stream(ManagerDegree.values()).filter(d -> degree.equalsIgnoreCase(d.getValue())).findAny();
        return managerDegree.orElse(EMPTY);
    }
}
