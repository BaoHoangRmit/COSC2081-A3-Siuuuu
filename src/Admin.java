public class Admin extends User{
    private String AdminID;

    @Override
    public boolean login() {
        return true;
    }
}
