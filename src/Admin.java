import java.util.Objects;

public class Admin extends User{
    private String adminID;

    public Admin(String adminID, String username, String password, String fullname, String phone, String email, String address) {
        super(username, password, fullname, phone, email, address);
        this.adminID = adminID;
    }

    // getter
    public String getAdminID() {
        return adminID;
    }

    // setter
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s", getAdminID(),
                getUsername(), getPassword(), getFullname(), getPhone(), getEmail(),
                getAddress());
    }

    @Override
    public boolean logout() {
        if (Objects.equals(SystemFile.getCurrentUsername(), null)) {
            return false;
        } else {
            SystemFile.setCurrentUsername(null);
            return true;
        }
    }
}
