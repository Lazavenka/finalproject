package by.lozovenko.finalproject.model.entity;

import java.math.BigDecimal;

public class Client extends User {
    private BigDecimal balance;
    private long clientId;

    public Client() {
    }

    public Client(User user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (clientId != client.clientId) return false;
        return balance != null ? balance.equals(client.balance) : client.balance == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (int) (clientId ^ (clientId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("balance=").append(balance);
        sb.append(", clientId=").append(clientId);
        sb.append('}');
        return sb.toString();
    }
}
