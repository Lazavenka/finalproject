package by.lozovenko.finalproject.entity;

import java.math.BigDecimal;

public class Client extends User{
    private BigDecimal balance;
    private long clientId;
    public Client(){
    }
    public Client(User user){
        super(user);
    }
    public Client(long id, String login, String password, String firstName, String lastName,
                  String email, String phone, UserRole role, UserState state, BigDecimal balance, long clientId) {
        super(id, login, password, firstName, lastName, email, phone, role, state);
        this.balance = balance;
        this.clientId = clientId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }
}
