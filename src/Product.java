import java.time.LocalDateTime;

public class Product extends Category{
    private String productID;
    private String productName;
    private double productPrice;
    private String productDesc;
    private int saleNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
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

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public int getSaleNumber() {
        return saleNumber;
    }
}
