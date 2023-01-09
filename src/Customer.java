import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;

    public Customer(String username, String password, String fullname, String phone,
                    String email, String address, String customerID, double spending,
                    String membership) {
        super(username, password, fullname, phone, email, address);
        this.customerID = customerID;
        this.spending = spending;
        this.membership = membership;
    }

    // getter
    public String getCustomerID() {
        return customerID;
    }

    public double getSpending() {
        return spending;
    }

    public String getMembership() {
        return membership;
    }

    // setter
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %.2f, %s", getUsername(),
                getPassword(), getFullname(), getPhone(), getEmail(), getAddress(),
                getCustomerID(), getSpending(), getMembership());
    }




    @Override
    public boolean signout() {
        return false;
    }
}
