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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (value != null ? !value.equals(token.value) : token.value != null) return false;
        return registerDateTime != null ? registerDateTime.equals(token.registerDateTime) : token.registerDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (registerDateTime != null ? registerDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("value='").append(value).append('\'');
        sb.append(", registerDateTime=").append(registerDateTime);
        sb.append('}');
        return sb.toString();
    }
}
