package lapr.project.controller;

import lapr.project.model.Courier;

public class AddCourierController {


    public AddCourierController(){

    }

    public void setNewCourierData(String name, String email, int NIF, String socialSecurityNumber, double maxWeightCapacity, String pwd){
        Courier cour = new Courier(name, email, NIF, socialSecurityNumber, maxWeightCapacity, pwd);
        cour.saveCourier();

    }

}
