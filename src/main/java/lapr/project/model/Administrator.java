package lapr.project.model;

import java.util.Objects;

public class Administrator extends User{

    private String name;

    /**
     * complete constructor of the Administrator with the parameters of the superclass (User)
     * @param email administrator email
     * @param password administrator password
     */
    public Administrator(String email, String password, String name){
        super(email, password, "ADMINISTRATOR");
        this.name = name;
    }

    /***
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
    @Override
    public String toString() {
        return "Administrator{" +
                "name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

}
