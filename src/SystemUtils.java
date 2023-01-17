import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class SystemUtils {
    public static void printLoginMenu() {
        System.out.println();
        System.out.println("----- MENU SCREEN -----");
        System.out.println("1: Login as Customer");
        System.out.println("2: Signup as Customer");
        System.out.println("3: Login as Admin");
        System.out.println("4: Exit Application");
    }

    public static void checkLoginMenuInput() {
        boolean hasRun = false;
        logInLoop: do {
            if (hasRun) {
                System.out.print("Enter your number option again: ");
            }else{
                System.out.print("Enter your number option: ");
            }

            try {
                Scanner scanner = new Scanner(System.in);
                int inputOption = scanner.nextInt();
                switch (inputOption) {
                    case 1:
                        if (UserUtils.login("Customer")) {
                            printLoggedInMenu();
                            checkLoggedInMenuInput();
                            hasRun = false;
                        } else {
                            printLoginMenu();
                            hasRun = true;
                        }
                        break;

                    case 2:
                        UserUtils.registerCustomer();
                        hasRun = false;
                        break;

                    case 3:
                        if (UserUtils.login("Admin")) {
                            printAdminLoggedInMenu();
                            checkAdminLoggedInMenuInput();
                            hasRun = false;
                        } else {
                            printLoginMenu();
                            hasRun = true;
                        }
                        break;

                    case 4:
                        hasRun = false;
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Please enter one of the given number!");
                        System.out.print("\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }

        } while (Objects.equals(UserUtils.getCurrentUsername(), null));
    }

    public static void printAdminLoggedInMenu() {
        double storeRevenue = updateSaleNumber();
        System.out.println();
        System.out.println("----- ADMIN SCREEN -----");
        System.out.println("Store Revenue: " + storeRevenue + " VND");
        System.out.println("1: View personal information");
        System.out.println("2: Manage Products");
        System.out.println("3: Categories");
        System.out.println("4: Manage Orders");
        System.out.println("5: Logout");
        System.out.print("Enter your number option: ");
    }

    public static void checkAdminLoggedInMenuInput() {
        Admin currentAdmin = (Admin) UserUtils.getCurrentUser();
        boolean hasRunAdmin = false;
        loggedInAdminLoop: do {
            if (currentAdmin != null){
                if (hasRunAdmin) {
                    System.out.print("Enter your number option again: ");
                }

                try {
                    Scanner scanner = new Scanner(System.in);
                    int inputOption = scanner.nextInt();
                    switch (inputOption) {
                        case 1:
                            System.out.println("\n----- Personal Information -----");
                            UserUtils.printCurrentUserInfo();
                            System.out.print("\n");
                            printAdminLoggedInMenu();
                            hasRunAdmin = false;
                            continue loggedInAdminLoop;

                        case 2:
                            AdminUtils.printAdminLoggedInManageProductsMenu();
                            AdminUtils.checkAdminLoggedInManageProductsMenuInput();
                            printAdminLoggedInMenu();
                            hasRunAdmin = false;
                            continue loggedInAdminLoop;

                        case 3:
                            System.out.println("\n----- Category List -----");
                            ProductUtils.printListOfCategories(Objects.requireNonNull(SystemFile.viewCategoryList()));
                            System.out.println("Returning to Menu...");
                            printAdminLoggedInMenu();
                            hasRunAdmin = false;
                            continue loggedInAdminLoop;

                        case 4:
                            ArrayList<Order> orderList = SystemFile.viewOrdersList();
                            ArrayList<String> orderIds = new ArrayList<>();
                            System.out.println("\n----- Order List -----");
                            if(orderList == null){
                                System.out.println("0 Order Found!");
                            }else{
                                for(Order order : orderList){
                                    if(!orderIds.contains(order.getOrderID())){
                                        orderIds.add(order.getOrderID());
                                    }
                                }
                                boolean genOrder;
                                Order tmp = null;
                                int cnt = 1;
                                for(String id : orderIds){
                                    genOrder = true;
                                    for(Order order : orderList){
                                        if(genOrder){
                                            System.out.println(cnt + ". " + id + "(OrderID):");
                                            System.out.println("Customer: " + order.getUserID());
                                            tmp = order;
                                            genOrder = false;
                                        }
                                        if(id.equalsIgnoreCase(order.getOrderID())){
                                            System.out.println(String.format("%s, Amount: %d, Price: %.4f", order.getProductName(), order.getProductAmount(), order.getProductPrice()));
                                        }
                                    }
                                    double oPrice = tmp.getOrderPrice();
                                    String pStatus = tmp.getPaymentStatus();
                                    String oStatus = tmp.getOrderStatus();
                                    System.out.println(String.format("Order Price: %.4f", oPrice));
                                    System.out.println("Payment Status: " + pStatus);
                                    System.out.println("Order Status: " + oStatus);
                                    System.out.print("\n");
                                }

                                System.out.println("Order Options:");
                                System.out.println("1. Deliver All Order(s)");
                                System.out.println("2. Return to Menu");
                                String inputValue = "";
                                System.out.print("Enter a number: ");
                                Scanner stringScanner = new Scanner(System.in);
                                inputValue = stringScanner.nextLine();

                                if(inputValue.equalsIgnoreCase("1")){
                                    for(Order order : orderList){
                                        order.setOrderStatus("Delivered");
                                    }
                                    System.out.println("\n----- Orders Updated -----\n");
                                    SystemFile.updateOrder(orderList);
                                } else if (inputValue.equalsIgnoreCase("2")) {
                                    System.out.println("Processing...");
                                }else{
                                    System.out.println("Invalid Input!");
                                }
                            }
                            System.out.println("Returning to Menu...");
                            printAdminLoggedInMenu();
                            hasRunAdmin = false;
                            continue loggedInAdminLoop;

                        case 5:
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
                printLoggedInMenu();
                break loggedInAdminLoop;
            }
        } while (Objects.equals(UserUtils.getCurrentUsername(), currentAdmin.getUsername()));
    }

    // technical function
    public static void printLoggedInMenu() {
        BagUtils.announceBagChange();
        updateSaleNumber();
        CustomerUtils.updateSpending();
        Customer curCus = UserUtils.viewCurrentCustomer();

        System.out.print("\n");
        System.out.println("----- CUSTOMER SCREEN -----");
        if(curCus != null){
            String mbs = curCus.getMembership();
            if(mbs.equalsIgnoreCase("Silver")){
                System.out.println("Membership: Silver");
            } else if (mbs.equalsIgnoreCase("Gold")) {
                System.out.println("Membership: Gold");
            } else if (mbs.equalsIgnoreCase("Diamond")) {
                System.out.println("Membership: Diamond");
            }
        }
        System.out.println("1: View Personal Information");
        System.out.println("2: Update Personal Information");
        System.out.println("3: View Product");
        System.out.println("4: View Current Bag");
        System.out.println("5: View Order(s)");
        System.out.println("6: Logout");
        System.out.println("7: Quit application (your account will be logged out)");
        System.out.print("\n");
    }

    public static void checkLoggedInMenuInput() {
//        Customer currentCustomer = UserUtils.viewCurrentCustomer();
        Customer currentCustomer = (Customer) UserUtils.getCurrentUser();
//        System.out.println(currentCustomer);
        boolean hasRun1 = false;
        do {
            if (hasRun1) {
                System.out.print("\n");
                System.out.print("Enter your number option again: ");
            }else{
                System.out.print("Enter your number option: ");
            }

            try {
                Scanner scanner = new Scanner(System.in);
                int inputOption = scanner.nextInt();
                switch (inputOption) {
                    case 1:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            UserUtils.printCurrentUserInfo();
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 2:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            Customer updateCustomer = UserUtils.updateCustomer(UserUtils.getCustomerByUsername(
                                    UserUtils.getCurrentUsername()).getCustomerID());

                            UserUtils.setCurrentUsername(updateCustomer.getUsername());

                            currentCustomer = (Customer) UserUtils.getCurrentUser();

                            System.out.println("Updated Info: ");
                            UserUtils.printCurrentUserInfo();
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 3:
                    if (currentCustomer != null) {
                        hasRun1 = false;
                        CustomerUtils.viewProductMenu();
                        System.out.println("Returning to Menu...");
                        printLoggedInMenu();
                    } else {
                        hasRun1 = true;
                        System.out.println("You are not logged in yet!");
                    }
                    break;

                    case 4:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            System.out.print("\n");
                            CustomerUtils.viewBagMenu();
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();

                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 5:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            System.out.print("\n");
                            CustomerUtils.viewOrderMenu();
                            System.out.println("Returning to Menu...");
                            printLoggedInMenu();

                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 6:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            currentCustomer.logout();
                            printLoginMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 7:
                        hasRun1 = false;
                        System.exit(0);

                    default:
                        hasRun1 = true;
                        System.out.println("Please enter one of the given number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }
        } while (Objects.equals(UserUtils.getCurrentUsername(), (currentCustomer != null ? currentCustomer.getUsername() : null)));
    }

    public static double updateSaleNumber(){
        ArrayList<Order> orderList = SystemFile.viewOrdersList();
        ArrayList<Product> proList = SystemFile.viewProductList();

        if(orderList == null){
            orderList = new ArrayList<>();
        }
        if (proList == null){
            proList = new ArrayList<>();
        }

        int sale;
        double revenue = 0;

        for(Product pro : proList){
            sale = 0;
            for(Order order : orderList){
                if( ((order.getProductName()).equalsIgnoreCase(pro.getProductName())) && ((order.getPaymentStatus()).equalsIgnoreCase("Paid")) ){
                    sale += order.getProductAmount();
                }
            }

            pro.setSaleNumber(sale);
        }

        for(Order order : orderList){
            if( (order.getPaymentStatus()).equalsIgnoreCase("Paid") ){
                revenue += order.getOrderPrice();
            }
        }

        SystemFile.updateProductByRenew(proList);
        return revenue;
    }
}
