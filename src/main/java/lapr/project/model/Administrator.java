package lapr.project.model;

import java.util.Objects;

public class Administrator extends User{
    private String name;


    /**
     * complete constructor of the Administrator with the parameters of the superclass (User)
     * @param email
     * @param password
     */
    public Administrator(String email, String password, String name){
        super(email, password, "ADMINISTRATOR");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String strPassword) {
        super.setPassword(strPassword);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "name='" + name + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
