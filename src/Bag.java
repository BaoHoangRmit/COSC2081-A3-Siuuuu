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

public class Bag{
    private String customerID;
    private String productID;
    private String productName;
    private int productAmount;
    private double productPrice;

    public Bag(String customerID, String productID, String productName, int productAmount, double productPrice) {
        this.customerID = customerID;
        this.productID = productID;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName(){
        return productName;
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
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return String.format("%s: Amount: %d, Price: %.4f", productName ,productAmount, productPrice);
    }
}
