public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;



    @Override
    public boolean login() {
        return true;
    }
}
