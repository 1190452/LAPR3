package lapr.project.model;

import java.util.Objects;

public class User {

    /**
     * email of the user
     */
    private String email;

    /**
     * password of the user
     */
    private String password;

    /**
     * role of the user
     */
    private String role;


    /**
     * complete constructor that creates a instance of User with the following parameters
     * @param email
     * @param password
     * @param role
     */
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * copy constructor
     * @param user
     */
    public User(User user) {
        this.email = user.email;
        this.password = user.password;
        this.role = user.role;
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    /**
     * returns the email of the user
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * returns the role of the user
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * returns the password of the user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * returns the email of the user
     * @param strEmail
     */
    public void setEmail(String strEmail) {
        this.email = strEmail;
    }

    /**
     * modifies the password of the user
     * @param strPassword
     */
    public void setPassword(String strPassword) {
        this.password = strPassword;
    }

    /**
     * modifies the role of the user
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        User u = (User) obj;
        return this.email.equalsIgnoreCase(u.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * writing method of the class User
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s - %s", this.role, this.email);
    }
}
