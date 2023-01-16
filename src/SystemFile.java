import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SystemFile {
    private static String currentUsername;

    // getter setter
    public static void setCurrentUsername(String currentUsername) {
        SystemFile.currentUsername = currentUsername;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }


    // menu function
    public static void printLoginMenu() {
        System.out.println("----- MENU SCREEN -----");
        System.out.println("1: Login");
        System.out.println("2: Exit Application");
        System.out.print("Enter your number option: ");
    }

    public static void checkLoginMenuInput() {
        boolean hasRun = false;
        logInLoop: do {
            if (hasRun) {
                System.out.print("Enter your number option again: ");
            }

            try {
                Scanner scanner = new Scanner(System.in);
                int inputOption = scanner.nextInt();
                switch (inputOption) {
                    case 1:
                        if (login()) {
                            printLoggedInMenu();
                            checkLoggedInMenuInput();
                            continue logInLoop;
                        } else {
                            printLoginMenu();
                            hasRun = false;
                            continue logInLoop;
                        }
                    case 2:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please enter one of the given number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }

            hasRun = true;
        } while (Objects.equals(SystemFile.getCurrentUsername(), null));
    }

    public static void printLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- CUSTOMER SCREEN -----");
        System.out.println("1: View personal information");
        System.out.println("2: Add Item");
        System.out.println("3: View Bag");
        System.out.println("4: Edit Bag");
        System.out.println("5: Confirm Bag");
        System.out.println("6: Edit Bill(s)");
        System.out.println("7: Logout");
        System.out.println("8: Quit application (your account will be logged out)");
        System.out.print("\n");
        System.out.print("Enter your number option: ");
    }

    public static void checkLoggedInMenuInput() {
        Customer currentCustomer = SystemFile.viewCurrentCustomer();
        boolean hasRun1 = false;
        loggedInLoop: do {
            if (hasRun1) {
                System.out.print("Enter your number option again: ");
            }

            try {
                Scanner scanner = new Scanner(System.in);
                int inputOption = scanner.nextInt();
                switch (inputOption) {
                    case 1:
                        printCurrentCustomer(currentCustomer);
                        printLoggedInMenu();
                        hasRun1 = false;
                        continue loggedInLoop;

                    case 2:
                        if(currentCustomer != null){
                            currentCustomer.addItem();
                            System.out.print("\n");
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                            hasRun1 = false;
                            continue loggedInLoop;
                        }else{
                            System.out.println("You are not logged in yet!");
                            printLoggedInMenu();
                            break loggedInLoop;
                        }

                    case 3:
                        if(currentCustomer != null){
                            System.out.print("\n");
                            System.out.println("----- Bag Content -----");
                            BagUtils.viewBag();
                            BagUtils.viewBagDetail();
                            System.out.print("\n");
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                            hasRun1 = false;
                            continue loggedInLoop;
                        }else{
                            System.out.println("You are not logged in yet!");
                            printLoggedInMenu();
                            break loggedInLoop;
                        }

                    case 4:
                        if(currentCustomer != null){
                            currentCustomer.editBag();
                            System.out.print("\n");
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                            hasRun1 = false;
                            continue loggedInLoop;
                        }else{
                            System.out.println("You are not logged in yet!");
                            printLoggedInMenu();
                            break loggedInLoop;
                        }

                    case 5:
                        if(currentCustomer != null){
                            System.out.print("\n");
                            System.out.println("----- Generating Bill(s)-----");
                            OrderUtils.generateOrder();
                            System.out.print("\n");
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                            hasRun1 = false;
                            continue loggedInLoop;
                        }else{
                            System.out.println("You are not logged in yet!");
                            printLoggedInMenu();
                            break loggedInLoop;
                        }

                    case 6:
                        if(currentCustomer != null){
                            System.out.print("\n");
                            System.out.println("----- Edit Bill(s)-----");
                            OrderUtils.payBill();
                            System.out.print("\n");
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                            hasRun1 = false;
                            continue loggedInLoop;
                        }else{
                            System.out.println("You are not logged in yet!");
                            printLoggedInMenu();
                            break loggedInLoop;
                        }

                    case 7:
                        if (currentCustomer != null) {
                            currentCustomer.logout();
                        } else {
                            System.out.println("You are not logged in yet!");
                        }
                        printLoginMenu();
                        break loggedInLoop;

                    case 8:
                        System.exit(0);
                    default:
                        System.out.println("Please enter one of the given number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }

            hasRun1 = true;
        } while (Objects.equals(SystemFile.getCurrentUsername(), currentCustomer.getUsername()));
    }

    public static void printCurrentCustomer(Customer currentCustomer) {
        if (currentCustomer != null) {
            System.out.println(currentCustomer.toString());
        } else {
            System.out.println("You are not logged in yet!");
        }
    }

    // technical function
    static boolean login() {
        HashMap<String, String> accounts = SystemFile.viewCustomerAccountList();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (accounts != null) {
            for (String s : accounts.keySet()) {
                if (Objects.equals(inputUsername, s)) {
                    if (Objects.equals(inputPassword, accounts.get(s))) {
                        System.out.println("Login successfully!");
                        setCurrentUsername(s);
                        return true;
                    } else {
                        System.out.println("Wrong password!");
                        System.out.println("Login again!");
                        return false;
                    }
                } else {
                    continue;
                }
            }

            System.out.println("There are no account with the username you entered!");
            System.out.println("Login again!");
            System.out.print("\n");
            return false;
        } else {
            System.out.println("There are no accounts in the DB");
            return false;
        }
    }

    // cannot validate in while loop yet
    // care for pointer in file
    static void createCustomer() {
        Scanner scanner = new Scanner(System.in);

        String inputUsername = null;
        boolean hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your new username: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your new username: ");
            }

            inputUsername = scanner.nextLine();
        } while (Objects.equals(inputUsername, null));

        String inputPassword = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your new password: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your new password: ");
            }

            inputPassword = scanner.nextLine();
        } while (Objects.equals(inputPassword, null));

        String inputFullName = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your full name: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your full name: ");
            }

            inputFullName = scanner.nextLine();
        } while (Objects.equals(inputFullName, null));

        String inputPhone = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your phone number: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your phone number: ");
            }

            inputPhone = scanner.nextLine();
        } while (Objects.equals(inputPhone, null));

        String inputEmail = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your email address: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your email address: ");
            }

            inputEmail = scanner.nextLine();
        } while (Objects.equals(inputEmail, null));

        String inputAddress = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Enter your address: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your address: ");
            }

            inputAddress = scanner.nextLine();
        } while (Objects.equals(inputAddress, null));

        Customer newCustomer = new Customer(inputUsername.trim(), inputPassword.trim(),
                inputFullName.trim(), inputPhone.trim(), inputEmail.trim(), inputAddress.trim());

        // append new Customer to file
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("customersSiuuuu.txt", true ));
            pw.println(String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%s", newCustomer.getCustomerID(), newCustomer.getUsername(), newCustomer.getPassword(),
                    newCustomer.getFullname(), newCustomer.getPhone(), newCustomer.getEmail(), newCustomer.getAddress(),
                    newCustomer.getSpending(), newCustomer.getMembership()));
            pw.close();
        } catch (IOException e) {
            System.out.println("Cannot create a new account!");
        }
    }

    static ArrayList<Customer> viewCustomerList() {
        try {
            Scanner fileScanner = new Scanner((new File("customersSiuuuu.txt")));
            ArrayList<Customer> customersList = new ArrayList<Customer>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 9) {
                    throw new IOException("Invalid Input Format");
                } else {

                    // get each string seperated by ","
                    String fileUsername = inReader.nextToken();
                    String filePassword = inReader.nextToken();
                    String fileFullname = inReader.nextToken();
                    String filePhone = inReader.nextToken();
                    String fileEmail = inReader.nextToken();
                    String fileAddress = inReader.nextToken();
                    String fileCustomerID = inReader.nextToken();
                    double fileSpending = Double.parseDouble(inReader.nextToken());
                    String fileMembership = inReader.nextToken();

                    // add customers from txt into list
                    customersList.add(new Customer(fileUsername, filePassword, fileFullname,
                            filePhone, fileEmail, fileAddress, fileCustomerID, fileSpending,
                            fileMembership));
                }
            }

            fileScanner.close();

            return customersList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static ArrayList<String> viewCustomerIDList(){
        ArrayList<Customer> customers = SystemFile.viewCustomerList();
        ArrayList<String> customerIDs = new ArrayList<String>();

        if (customers != null) {
            for (Customer customer : customers) {
                customerIDs.add(customer.getCustomerID());
            }
        } else {
            return null;
        }

        return customerIDs;
    }

    static HashMap<String, String> viewCustomerAccountList() {
        ArrayList<Customer> customers = SystemFile.viewCustomerList();
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

    static Customer viewCustomerByID(String id) {
        ArrayList<Customer> customers = SystemFile.viewCustomerList();

        if (customers != null) {
            for (Customer customer : customers) {
                if (Objects.equals(id, customer.getCustomerID())) {
                    return customer;
                } else {
                    continue;
                }
            }
        } else {
            return null;
        }

        return null;
    }

    static Customer viewCustomerByUsername(String username) {
        ArrayList<Customer> customers = SystemFile.viewCustomerList();

        if (customers != null) {
            for (Customer customer : customers) {
                if (Objects.equals(username, customer.getUsername())) {
                    return customer;
                } else {
                    continue;
                }
            }
        } else {
            return null;
        }

        return null;
    }

    static Customer viewCurrentCustomer() {
        if (getCurrentUsername() != null) {
            return viewCustomerByUsername(getCurrentUsername());
        } else {
            return null;
        }
    }

    static ArrayList<Product> viewProductList() {
        try {
            Scanner fileScanner = new Scanner((new File("product.txt")));
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
                    String productCategoryName = inReader.nextToken();
                    String productCategoryId = inReader.nextToken();

                    // add customers from txt into list
                    productsList.add(new Product(productId, productName, productPrice, productDesc, productSaleNumber, productCategoryId, productCategoryName));
                }
            }

            fileScanner.close();

            return productsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
