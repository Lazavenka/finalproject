package by.lozovenko.finalproject.model.entity;

import java.math.BigDecimal;

public class Client extends User{
    private BigDecimal balance;

    public Client(long id, String login, String password, String firstName, String lastName,
                  String email, String phone, UserRole role, UserState state, BigDecimal balance) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
