import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminUtils {
    static void viewAdminProductMenu(){
        System.out.println("\n----- Product List -----");

        ArrayList<Product> proList = SystemFile.viewProductList();
        String productID = "", productName = "", productDesc = "", productCat = "";
        double productPrice = 0;
        int saleNumber = 0;

        boolean productMenuRun = true;
        do {
            if (proList != null){
                String userInputString;
                Scanner inputScanner = new Scanner(System.in);

                System.out.print("\n");
                System.out.println("Product List Options:");
                System.out.println("1. View All Product(s)");
                System.out.println("2. View Product(s) By Category");
                System.out.println("3. View Product(s) By ID");
                System.out.println("4. View Product(s) By Name");
                System.out.println("5. Sort Product(s) By Price Ascending");
                System.out.println("6. Sort Product(s) By Price Descending");
                System.out.println("7. View Most Popular Product");
                System.out.println("8. View Least Popular Product");
                System.out.println("9. Return to Menu");

                try{
                    System.out.print("\n");
                    System.out.print("Enter a number: ");
                    userInputString = inputScanner.nextLine();
                    if(userInputString.equalsIgnoreCase("1")){
                        System.out.println("Product(ID. Name):");
                        for(Product pro : proList){
                            productID = pro.getProductID();
                            productName = pro.getProductName();
                            productDesc = pro.getProductDesc();
                            productCat = pro.getCategoryName();
                            productPrice = pro.getProductPrice();
                            saleNumber = pro.getSaleNumber();

                            System.out.println(String.format("%s. %s: " +
                                    "\n    Description: %s " +
                                    "\n    Category: %s" +
                                    "\n    Price: %.4f" +
                                    "\n    Total Sale: %d", productID, productName, productDesc, productCat, productPrice, saleNumber));
                        }
                    } else if (userInputString.equalsIgnoreCase("2")) {
                        System.out.print("Enter Category Name: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByCategory(userInputString2);
                    } else if (userInputString.equalsIgnoreCase("3")) {
                        System.out.print("Enter ProductID: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByID(userInputString2);
                    }else if (userInputString.equalsIgnoreCase("4")) {
                        System.out.print("Enter Product Name: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByName(userInputString2);
                    }else if (userInputString.equalsIgnoreCase("5")) {
                        ProductUtils.sortAllProductByPriceAsc();
                    }else if (userInputString.equalsIgnoreCase("6")) {
                        ProductUtils.sortAllProductByPriceDes();
                    }else if (userInputString.equalsIgnoreCase("7")) {
                        ProductUtils.viewMostPopularProducts();
                    }else if (userInputString.equalsIgnoreCase("8")) {
                        ProductUtils.viewLeastPopularProducts();
                    }else if (userInputString.equalsIgnoreCase("9")) {
                        productMenuRun = false;
                    } else{
                        System.out.println("Please Enter A Valid Number!");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter a valid input!");
                }
            }
            else{
                productMenuRun = false;
                System.out.println("0 Product Found!");
            }
        }while(productMenuRun);
    }

    public static void printAdminLoggedInManageProductsMenu() {
        System.out.print("\n");
        System.out.println("----- Edit Product -----");
        System.out.println("1: View Products");
        System.out.println("2: Add new product");
        System.out.println("3: Update product");
        System.out.println("4: Delete product");
        System.out.println("5: Back to Menu");

        System.out.print("\nEnter your number option: ");
    }

    public static void checkAdminLoggedInManageProductsMenuInput() {
        Admin currentAdmin = (Admin) UserUtils.getCurrentUser();
        Scanner scanner = new Scanner(System.in);
        int inputOption;

        if(currentAdmin != null){
            inputOption = scanner.nextInt();

            if(inputOption == 1){
                viewAdminProductMenu();
                System.out.println("Returning to Menu...");
            } else if (inputOption == 2) {
                ProductUtils.addNewFood();
                System.out.println("Returning to Menu...");
            } else if (inputOption == 3) {
                Scanner smallScanner = new Scanner(System.in);
                System.out.print("Please enter the product ID you want to update: ");
                String inputDesireProductID = smallScanner.nextLine();

                if (ProductUtils.getProductByID(inputDesireProductID) == null) {
                    System.out.println("There are no product with the ID you entered!");
                } else {
                    Product product = ProductUtils.updateProduct(inputDesireProductID);

                    System.out.println("Updated Info: ");
                    System.out.println(product.displayProduct());
                }
                System.out.println("Returning to Menu...");
            } else if (inputOption == 4) {
                System.out.print("Please enter the product ID/Name to remove: ");
                String userInput = scanner.nextLine();
                ArrayList<Product> proList = SystemFile.viewProductList();
                Product tmp = null;
                if(proList != null){
                    for(Product pro : proList){
                        if(( (pro.getProductID()).equalsIgnoreCase(userInput)) || ((pro.getProductName()).equalsIgnoreCase(userInput)) ){
                            tmp = pro;
                        }
                    }
                    proList.remove(tmp);
                    SystemFile.updateProductByRenew(proList);
                    System.out.println("\n----- Item Removed -----\n");
                }
            } else if (inputOption == 5) {
                System.out.println("Returning to Menu...");
            }else{
                System.out.println("Invalid Input!");
                System.out.println("Returning to Menu...");
            }
        }
    }
}
