import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Product extends Category{
    private String productID;
    private String productName;
    private double productPrice;
    private String productDesc;
    private int saleNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
//        return "ID: " + productID + ", Name: " + productName + ", Price: " +productPrice ;
        return String.format("%s, %s, %.2f, %s", getProductID(), getProductName(), getProductPrice(), getCategoryName());
    }

    static ArrayList<Product> viewProductList() {
        try {
            Scanner fileScanner = new Scanner((new File("src/product.txt")));
            ArrayList<Product> productList = new ArrayList<Product>();
            String line;

            while (fileScanner.hasNext()){
                line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 7) {
                    throw new IOException("Invalid Input Format");
                } else {
                    String fileProductID = inReader.nextToken();
                    String fileProductName = inReader.nextToken();
                    double fileProductPrice = Double.parseDouble(inReader.nextToken());
                    String fileProductDesc = inReader.nextToken();
                    int fileProductSales = Integer.parseInt(inReader.nextToken());
                    String fileCategoryID = inReader.nextToken();
                    String fileCategoryName = inReader.nextToken();

                    productList.add(new Product(fileProductID, fileProductName, fileProductPrice, fileProductDesc, fileProductSales, fileCategoryID, fileCategoryName));
                }
            }

            fileScanner.close();

            return productList;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
