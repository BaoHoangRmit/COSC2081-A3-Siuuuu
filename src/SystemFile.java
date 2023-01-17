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

import java.io.*;
import java.util.*;

public class SystemFile {
    static HashMap<String, String> getCustomerAccountList() {
        ArrayList<Customer> customers = viewCustomerList();
        HashMap<String, String> accounts = new HashMap<String, String>();

        if (customers != null) {
            for (Customer customer : customers) {
                accounts.put(customer.getUsername(), customer.getPassword());
            }
        } else {
            return null;
        }

        return accounts;
    }

    static ArrayList<Admin> getAdminListFromFile() {
        try {
            Scanner fileScanner = new Scanner((new File("data/admins.txt")));
            ArrayList<Admin> adminsList = new ArrayList<Admin>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 7) {
                    throw new IOException("Invalid Input Format");
                } else {

                    // get each string seperated by ","
                    String fileAdminID = inReader.nextToken();
                    String fileUsername = inReader.nextToken();
                    String filePassword = inReader.nextToken();
                    String fileFullname = inReader.nextToken();
                    String filePhone = inReader.nextToken();
                    String fileEmail = inReader.nextToken();
                    String fileAddress = inReader.nextToken();

                    // add customers from txt into list
                    adminsList.add(new Admin(fileAdminID, fileUsername, filePassword,
                            fileFullname, filePhone, fileEmail, fileAddress));
                }
            }

            fileScanner.close();

            return adminsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static HashMap<String, String> getAdminAccountList() {
        ArrayList<Admin> admins = SystemFile.getAdminListFromFile();
        HashMap<String, String> accounts = new HashMap<String, String>();

        if (admins != null) {
            for (Admin admin : admins) {
                accounts.put(admin.getUsername(), admin.getPassword());
            }
        } else {
            return null;
        }

        return accounts;
    }

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

    static ArrayList<Order> viewOrdersList() {
        try {
            Scanner fileScanner = new Scanner((new File("data/order.txt")));
            ArrayList<Order> ordersList = new ArrayList<Order>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 9) {
                    throw new IOException("Invalid Input Format (order)");
                } else {
                    // get each string seperated by ","

                    String orderDate = inReader.nextToken();
                    String customerId = inReader.nextToken();
                    String orderId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    int productAmount = Integer.parseInt(inReader.nextToken());
                    double productPrice = Double.parseDouble(inReader.nextToken());
                    double orderPrice = Double.parseDouble(inReader.nextToken());
                    String paymentStatus = inReader.nextToken();
                    String orderStatus = inReader.nextToken();

                    ordersList.add(new Order(orderDate, customerId, orderId, productName, productAmount, productPrice, orderPrice, paymentStatus, orderStatus));
                }
            }

            fileScanner.close();

            return ordersList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found (order)!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            return null;
        }
        return null;
    }

    static void updateOrder(ArrayList<Order> ordersList) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("data/order.txt", false));
            pw.println("#orderDate, customerId, orderId, productName, productAmount, productPrice, orderPrice, paymentStatus, orderStatus");
            for (Order order : ordersList) {
                pw.println(String.format("%s,%s,%s,%s,%d,%.4f,%.4f,%s,%s",
                        order.getOrderDate(),
                        order.getUserID(),
                        order.getOrderID(),
                        order.getProductName(),
                        order.getProductAmount(),
                        order.getProductPrice(),
                        order.getOrderPrice(),
                        order.getPaymentStatus(),
                        order.getOrderStatus()));
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred (updateOrder)!");
        }
    }

    static void updateProductByRenew(ArrayList<Product> proList) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("data/product.txt", false));
            pw.println("#productId,productName,Price,ProductDesc,ProductSaleNumber,CatId,CatName");
            for (Product pro : proList) {
                pw.println(String.format("%s,%s,%.4f,%s,%d,%s,%s",
                        pro.getProductID(),
                        pro.getProductName(),
                        pro.getProductPrice(),
                        pro.getProductDesc(),
                        pro.getSaleNumber(),
                        pro.getCategoryID(),
                        pro.getCategoryName()));
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred (updateProductRenew)!");
        }
    }

    static void appendToFile(String filePath, String writeInfo) {
        String fileName = filePath.substring(0, filePath.indexOf("."));
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath, true ));
            pw.println(writeInfo);
            pw.close();
        } catch (IOException e) {
            System.out.println("Cannot write to " + fileName + " data file!");
        }
    }

    static void writeToFile(String filePath, String writeInfo) {
        String fileName = filePath.substring(0, filePath.indexOf("."));
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(filePath, false ));
            pw.println(writeInfo);
            pw.close();
        } catch (IOException e) {
            System.out.println("Cannot write to " + fileName + " data file!");
        }
    }
}
