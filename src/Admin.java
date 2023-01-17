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
                getUsername(), getPassword(), getFullName(), getPhone(), getEmail(),
                getAddress());
    }

    @Override
    public boolean logout() {
        if (Objects.equals(UserUtils.getCurrentUsername(), null)) {
            return false;
        } else {
            UserUtils.setCurrentUsername(null);
            return true;
        }
    }

    @Override
    public String display() {
        return String.format("ID= %s, Username= %s, Password = %s: \nfullName= %s,\nPhone= %s," +
                        "\nEmail= %s,\nAddress= %s", getAdminID(),
                getUsername(), getPassword(), getFullName(), getPhone(), getEmail(),
                getAddress());
    }
}