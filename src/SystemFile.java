import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SystemFile {
    private static String currentUsername;
    private static String currentRole;

    // getter setter
    public static void setCurrentUsername(String currentUsername) {
        SystemFile.currentUsername = currentUsername;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentRole(String currentRole) {
        SystemFile.currentRole = currentRole;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    // MAIN MENU function
    public static void printLoginMenu() {
        System.out.println("----- MENU SCREEN -----");
        System.out.println("1: Login as Customer");
        System.out.println("2: Login as Admin");
        System.out.println("3: Exit Application");
        System.out.println("Enter your number option: ");
    }

    public static void checkLoginMenuInput() {
        boolean hasRun = false;
        logInLoop: do {
            if (hasRun) {
                System.out.println("Enter your number option again: ");
            }

            try {
                Scanner scanner = new Scanner(System.in);
                int inputOption = scanner.nextInt();
                switch (inputOption) {
                    case 1:
                        if (login("Customer")) {
                            printCustomerLoggedInMenu();
                            checkCustomerLoggedInMenuInput();
                            continue logInLoop;
                        } else {
                            printLoginMenu();
                            hasRun = false;
                            continue logInLoop;
                        }
                    case 2:
                        if (login("Admin")) {
                            printAdminLoggedInMenu();
                            checkAdminLoggedInMenuInput();
                            continue logInLoop;
                        } else {
                            printLoginMenu();
                            hasRun = false;
                            continue logInLoop;
                        }
                    case 3:
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

    public static void printCurrentUserInfo() {
        if (currentUsername != null) {
            User currentUser = getCurrentUser();
            if (currentUser != null) {
                System.out.println(currentUser.toString());
            } else {
                System.out.println("You are not logged in yet!");
            }
        } else {
            System.out.println("You are not logged in yet!");
        }
    }

    // CUSTOMER MENU function
    public static void printCustomerLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- CUSTOMER SCREEN -----");
        System.out.println("1: View personal information");
        System.out.println("2: Add Item");
        System.out.println("3: Logout");
        System.out.println("Enter your number option: ");
    }

    public static void checkCustomerLoggedInMenuInput() {
        Customer currentCustomer = (Customer) SystemFile.getCurrentUser();
        boolean hasRunCustomer = false;
        loggedInCustomerLoop: do {
            if (currentCustomer != null){
                if (hasRunCustomer) {
                    System.out.println("Enter your number option again: ");
                }

                try {
                    Scanner scanner = new Scanner(System.in);
                    int inputOption = scanner.nextInt();
                    switch (inputOption) {
                        case 1:
                            printCurrentUserInfo();
                            printCustomerLoggedInMenu();
                            hasRunCustomer = false;
                            continue loggedInCustomerLoop;

                        case 2:
                            currentCustomer.addItem();
                            printCustomerLoggedInMenu();
                            hasRunCustomer = false;
                            continue loggedInCustomerLoop;
                        case 3:
                            if (currentCustomer.logout()) {
                                System.out.println("Logout successfully!");
                                printLoginMenu();
                                break loggedInCustomerLoop;
                            } else {
                                System.out.println("Logout unsuccessfully!");
                            }
                        default:
                            System.out.println("Please enter one of the given number!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input!");
                }

                hasRunCustomer = true;
            } else {
                System.out.println("You are not logged in yet!");
                printCustomerLoggedInMenu();
                break loggedInCustomerLoop;
            }
        } while (Objects.equals(SystemFile.getCurrentUsername(), currentCustomer.getUsername()));
    }

    // ADMIN MENU function
    public static void printAdminLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- ADMIN SCREEN -----");
        System.out.println("1: View personal information");
        System.out.println("2: Logout");
        System.out.println("Enter your number option: ");
    }

    public static void checkAdminLoggedInMenuInput() {
        Admin currentAdmin = (Admin) SystemFile.getCurrentUser();
        boolean hasRunAdmin = false;
        loggedInAdminLoop: do {
            if (currentAdmin != null){
                if (hasRunAdmin) {
                    System.out.println("Enter your number option again: ");
                }

                try {
                    Scanner scanner = new Scanner(System.in);
                    int inputOption = scanner.nextInt();
                    switch (inputOption) {
                        case 1:
                            printCurrentUserInfo();
                            printAdminLoggedInMenu();
                            hasRunAdmin = false;
                            continue loggedInAdminLoop;
                        case 2:
                            if (currentAdmin.logout()) {
                                System.out.println("Logout successfully!");
                                printLoginMenu();
                                break loggedInAdminLoop;
                            } else {
                                System.out.println("Logout unsuccessfully!");
                            }
                        default:
                            System.out.println("Please enter one of the given number!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input!");
                }

                hasRunAdmin = true;
            } else {
                System.out.println("You are not logged in yet!");
                printCustomerLoggedInMenu();
                break loggedInAdminLoop;
            }
        } while (Objects.equals(SystemFile.getCurrentUsername(), currentAdmin.getUsername()));
    }

    // technical function
    static User getCurrentUser() {
        if (getCurrentUsername() != null) {
            if (Objects.equals(getCurrentRole(), "Customer")) {
                return getCustomerByUsername(getCurrentUsername());
            } else if (Objects.equals(getCurrentRole(), "Admin")) {
                return getAdminByUsername(getCurrentUsername());
            }
        }
        return null;
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

    static boolean login(String role) {
        HashMap<String, String> accounts = null;
        if (Objects.equals(role, "Customer")) {
            accounts = SystemFile.getCustomerAccountList();
        } else if (Objects.equals(role, "Admin")) {
            accounts = SystemFile.getAdminAccountList();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.println("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (accounts != null) {
            for (String s : accounts.keySet()) {
                if (Objects.equals(inputUsername, s)) {
                    if (Objects.equals(inputPassword, accounts.get(s))) {
                        System.out.println("Login successfully!");
                        setCurrentUsername(s);
                        setCurrentRole(role);
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
            return false;
        } else {
            System.out.println("There are no accounts in the DB");
            return false;
        }
    }

    // CUSTOMER ---------------------------------------------------------------------------------------------------

    // care for pointer in file
    static void registerCustomer() {
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
        } while (inputUsername == null || inputUsername.isEmpty());

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
        } while (inputPassword == null || inputPassword.isEmpty());

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
        } while (inputFullName == null || inputFullName.isEmpty());

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
        } while (inputPhone == null || inputPhone.isEmpty());

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
        } while (inputEmail == null || inputEmail.isEmpty());

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
        } while (inputAddress == null || inputAddress.isEmpty());

        Customer newCustomer = new Customer(inputUsername.trim(), inputPassword.trim(),
                inputFullName.trim(), inputPhone.trim(), inputEmail.trim(), inputAddress.trim());

        // append new Customer to file
        appendToFile("customersSiuuuu.txt", newCustomer.toString());
    }

    static ArrayList<Customer> getCustomerListFromFile() {
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

    static ArrayList<String> getCustomerIDList(){
        ArrayList<Customer> customers = SystemFile.getCustomerListFromFile();
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

    static HashMap<String, String> getCustomerAccountList() {
        ArrayList<Customer> customers = SystemFile.getCustomerListFromFile();
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

    static Customer getCustomerByID(String id) {
        ArrayList<Customer> customers = SystemFile.getCustomerListFromFile();

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

    static Customer getCustomerByUsername(String username) {
        ArrayList<Customer> customers = SystemFile.getCustomerListFromFile();

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

    static ArrayList<Bag> viewBagsList() {
        try {
            Scanner fileScanner = new Scanner((new File("bag.txt")));
            ArrayList<Bag> bagsList = new ArrayList<Bag>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 4) {
                    throw new IOException("Invalid Input Format (bag)");
                } else {
                    // get each string seperated by ","

                    String customerId = inReader.nextToken();
                    String productId = inReader.nextToken();
                    int productAmount = Integer.parseInt(inReader.nextToken());
                    double productPrice = Double.parseDouble(inReader.nextToken());

                    bagsList.add(new Bag(customerId, productId, productAmount, productPrice));
                }
            }

            fileScanner.close();

            return bagsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch(NoSuchElementException e){
            return null;
        }
        return null;
    }



    // ADMIN ---------------------------------------------------------------------------------------------------

    static ArrayList<Admin> getAdminListFromFile() {
        try {
            Scanner fileScanner = new Scanner((new File("adminsSiuuuu.txt")));
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

    static Admin getAdminByUsername(String username) {
        ArrayList<Admin> admins = SystemFile.getAdminListFromFile();

        if (admins != null) {
            for (Admin admin : admins) {
                if (Objects.equals(username, admin.getUsername())) {
                    return admin;
                } else {
                    continue;
                }
            }
        } else {
            return null;
        }

        return null;
    }
}
