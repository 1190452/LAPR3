package lapr.project.model;

public class CreditCard {
    int number;
    int monthExpiration;
    int yearExpiration;
    int ccv;

    public CreditCard(int number, int monthExpiration, int yearExpiration, int ccv) {
        this.number = number;
        this.monthExpiration = monthExpiration;
        this.yearExpiration = yearExpiration;
        this.ccv = ccv;
    }


}
