package by.lozovenko.finalproject.model.entity;

public class User extends CustomEntity{
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private UserState state;

    public User(){
    }
    public User(User user){
        this(user.getId(), user.login, user.password, user.firstName, user.lastName,
                user.email, user.phone, user.role, user.state);
    }
    public User(long id, String login, String password, String firstName, String lastName,
                String email, String phone, UserRole role, UserState state) {
        super(id);
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.state = state;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }
}
