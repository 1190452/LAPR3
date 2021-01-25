package lapr.project.model;

public class UserSession {
    private static UserSession instance;

    private User user;

    /**
     * private constructor for UserSession
     */
    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return this.user;
    }


}
