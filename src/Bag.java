public class Bag{
    private String customerID;
    private String productID;
    private int productAmount;
    private double productPrice;

    public Bag(String customerID, String productID, int productAmount, double productPrice) {
        this.customerID = customerID;
        this.productID = productID;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getProductID() {
        return productID;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
