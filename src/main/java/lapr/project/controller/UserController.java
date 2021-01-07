package lapr.project.controller;

import lapr.project.data.UserDataHandler;
import lapr.project.model.Client;

public class UserController {
    private final UserDataHandler userDataHandler;

    public UserController(UserDataHandler userDataHandler){
        this.userDataHandler = userDataHandler;
    }

    public Client login(String email, String password) {
        return null;
    }
}
