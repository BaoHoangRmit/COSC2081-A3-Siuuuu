import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SystemFile {
    static ArrayList<Customer> viewCustomerList() {
        try {
            Scanner fileScanner = new Scanner((new File("data/customers.txt")));
            ArrayList<Customer> customersList = new ArrayList<Customer>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 9) {
                    throw new IOException("An error occurred: Invalid File Format (customers)");
                } else {

                    // get each string seperated by ","
                    String fileUserId = inReader.nextToken();
                    String fileUsername = inReader.nextToken();
                    String filePassword = inReader.nextToken();
                    String fileFullName = inReader.nextToken();
                    String filePhone = inReader.nextToken();
                    String fileEmail = inReader.nextToken();
                    String fileAddress = inReader.nextToken();
                    double fileSpending = Double.parseDouble(inReader.nextToken());
                    String fileMembership = inReader.nextToken();

                    // add customers from txt into list
                    customersList.add(new Customer(fileUserId, fileUsername, filePassword, fileFullName,
                            filePhone, fileEmail, fileAddress, fileSpending,
                            fileMembership));
                }
            }

            fileScanner.close();

            return customersList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: File not found (customers)!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static ArrayList<Category> viewCategoryList() {
        try {
            Scanner fileScanner = new Scanner((new File("data/category.txt")));
            ArrayList<Category> categoryList = new ArrayList<>();
            String line;

            fileScanner.nextLine();

            while (fileScanner.hasNext()){
                line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 2) {
                    throw new IOException("Invalid Input Format");
                } else {
                    String fileCategoryID = inReader.nextToken();
                    String fileCategoryName = inReader.nextToken();

                    categoryList.add(new Category(fileCategoryID, fileCategoryName));
                }
            }

            fileScanner.close();

            return categoryList;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static ArrayList<Product> viewProductList() {
        try {
            Scanner fileScanner = new Scanner((new File("data/product.txt")));
            ArrayList<Product> productsList = new ArrayList<Product>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 7) {
                    throw new IOException("Invalid Input Format (product)");
                } else {
                    // get each string seperated by ","
                    String productId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    double productPrice = Double.parseDouble(inReader.nextToken());
                    String productDesc = inReader.nextToken();
                    int productSaleNumber = Integer.parseInt(inReader.nextToken());
                    String productCategoryId= inReader.nextToken();
                    String productCategoryName = inReader.nextToken();

                    // add customers from txt into list
                    productsList.add(new Product(productId, productName, productPrice, productDesc, productSaleNumber, productCategoryId, productCategoryName));
                }
            }

            fileScanner.close();

            return productsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found (product)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    static ArrayList<Bag> viewBagsList() {
        try {
            Scanner fileScanner = new Scanner((new File("data/bag.txt")));
            ArrayList<Bag> bagsList = new ArrayList<Bag>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 5) {
                    throw new IOException("Invalid Input Format (bag)");
                } else {
                    // get each string seperated by ","

                    String customerId = inReader.nextToken();
                    String productId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    int productAmount = Integer.parseInt(inReader.nextToken());
                    double productPrice = Double.parseDouble(inReader.nextToken());

                    bagsList.add(new Bag(customerId, productId, productName, productAmount, productPrice));
                }
            }

            fileScanner.close();

            return bagsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found (bag)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch(NoSuchElementException e){
            return null;
        }
        return null;
    }
}
