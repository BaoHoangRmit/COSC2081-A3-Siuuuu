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

public class Product extends Category implements Comparable<Product>{
    private String productID;
    private String productName;
    private double productPrice;
    private String productDesc;
    private int saleNumber;

    public Product() {
    }

    public Product(String productID, String productName, double productPrice, String productDesc, int saleNumber, String categoryID, String categoryName) {
        super(categoryID, categoryName);
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.saleNumber = saleNumber;
    }

    public Product(String productName, double productPrice, String productDesc, String categoryID, String categoryName) {
        super(categoryID, categoryName);
        this.productID = ProductUtils.getContinuousProductID();
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.saleNumber = 0;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public int getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(int saleNumber) {
        this.saleNumber = saleNumber;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %.2f | %s", getProductID(), getProductName(), getProductPrice(),getCategoryName());
    }

    @Override
    public int compareTo(Product product) {
        if (productPrice == product.productPrice)
            return 0;
        else if (productPrice > product.productPrice)
            return 1;
        else
            return -1;
    }

    // display
    public String displayProduct() {
        return String.format("%s,%s,%.2f,%s,%d,%s,%s", getProductID(),
                getProductName(), getProductPrice(), getProductDesc(), getSaleNumber(), getCategoryID(),
                getCategoryName());
    }
}