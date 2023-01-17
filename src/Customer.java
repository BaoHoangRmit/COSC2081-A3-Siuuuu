/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3 (Group Project)

  Author & ID:
  Hoang Quoc Bao s3926050
  Nguyen Thien Co s3938338
  Hoang Vinh Khue s3927474
  Nguyen Dang Ha s3924594

  Acknowledgement: None.
*/

import java.util.Objects;

public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;

    public Customer(String customerID, String username, String password, String fullName, String phone,
                    String email, String address, double spending,
                    String membership) {
        super(username, password, fullName, phone, email, address);
        this.customerID = customerID;
        this.spending = spending;
        this.membership = membership;
    }

    // customer register constructor
    public Customer(String username, String password, String fullName, String phone,
                    String email, String address) {
        super(username, password, fullName, phone, email, address);
        this.customerID = UserUtils.getContinuousID();
        this.spending = 0;
        this.membership = "None";
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%s", getCustomerID(),
                getUsername(), getPassword(), getFullName(), getPhone(), getEmail(),
                getAddress(), getSpending(), getMembership());
    }

    @Override
    public boolean logout() {
        if (Objects.equals(UserUtils.getCurrentUsername(), null)) {
            System.out.println("Logout unsuccessfully!\n");
            return false;
        } else {
            UserUtils.setCurrentUsername(null);
            System.out.println("Logout successfully!\n");
            return true;
        }
    }

    // technical methods
    @Override
    public String display() {
        return String.format("ID= %s, Username= %s, Password = %s: \nfullName= %s,\nPhone= %s," +
                        "\nEmail= %s,\nAddress= %s,\nSpending= %.4f,\nMembership= %s", getCustomerID(),
                getUsername(), getPassword(), getFullName(), getPhone(), getEmail(),
                getAddress(), getSpending(), getMembership());
    }

}
