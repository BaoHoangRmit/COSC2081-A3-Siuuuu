import java.time.LocalDateTime;

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
        return String.format("%s, %s, %.2f, %s", getProductID(), getProductName(), getProductPrice(), getCategoryName());
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
}
