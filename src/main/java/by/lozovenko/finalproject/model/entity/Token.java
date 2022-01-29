package by.lozovenko.finalproject.model.entity;

import java.time.LocalDateTime;

public class Token extends CustomEntity{
    private final String value;
    private final LocalDateTime registerDateTime;

    public Token(String value, LocalDateTime registerDateTime) {
        this.value = value;
        this.registerDateTime = registerDateTime;
    }

    public Token(long id, String value, LocalDateTime registerDateTime) {
        super(id);
        this.value = value;
        this.registerDateTime = registerDateTime;
    }

    public String getValue() {
        return value;
    }

    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }
}
