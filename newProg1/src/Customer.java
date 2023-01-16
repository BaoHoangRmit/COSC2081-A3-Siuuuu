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
        return String.format("%s, %s, %s, %s, %s, %s, %s, %.2f, %s", getCustomerID(),
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
}
