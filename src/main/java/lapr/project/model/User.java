package lapr.project.model;

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
     * role of the user by omission
     */
    private static final String ROLE_BY_OMISSION = "USER";

    /**
     * password of the user by omission
     */
    private static final String PASSWORD_BY_OMISSION = "NO PASSWORD";

    /**
     * email of the user by omission
     */
    private static final String EMAIL_BY_OMISSION = "NO EMAIL";

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



    /**
     * empty constructor that creates a instance of User with the following parameters
     */
    public User() {
        this.email = EMAIL_BY_OMISSION;
        this.password = PASSWORD_BY_OMISSION;
        this.role = ROLE_BY_OMISSION;
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

    /**
     * method that checks if a user has email
     * @param strEmail
     * @return
     */
    public boolean hasEmail(String strEmail) {
        return this.email.equalsIgnoreCase(strEmail);
    }

    /**
     * method that checks if a user has password
     * @param strPassword
     * @return
     */
    public boolean hasPassword(String strPassword) {
        return this.password.equals(strPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != User.class) {
            return false;
        }

        User u = (User) obj;
        return this.email.equalsIgnoreCase(u.email) && u.role.equalsIgnoreCase(role)
                && u.password.equals(password);
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