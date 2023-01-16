import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class SystemUtils {
    public static void printLoginMenu() {
        System.out.println("\n");
        System.out.println("----- MENU SCREEN -----");
        System.out.println("1: Login as Customer");
        System.out.println("2: Signup as Customer");
        System.out.println("3: Login as Admin");
        System.out.println("4: Exit Application");
    }

    public static void printAdminLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- ADMIN SCREEN -----");
        System.out.println("1: View personal information");
        System.out.println("2: Logout");
        System.out.println("Enter your number option: ");
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
                            UserUtils.printCurrentUserInfo();
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
                printLoggedInMenu();
                break loggedInAdminLoop;
            }
        } while (Objects.equals(UserUtils.getCurrentUsername(), currentAdmin.getUsername()));
    }

    // technical function
    public static void printLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- CUSTOMER SCREEN -----");
        System.out.println("1: View Personal Information");
        System.out.println("2: View Product");
        System.out.println("3: View Current Bag");
        System.out.println("4: View Order(s)");
        System.out.println("5: Logout");
        System.out.println("6: Quit application (your account will be logged out)");
        System.out.print("\n");
    }

    public static void checkLoginMenuInput() {
        boolean hasRun = false;
        logInLoop: do {
            if (hasRun) {
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

    public static void checkLoggedInMenuInput() {
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
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
                            //CustomerUtils.viewInfoMenu();
                            printLoggedInMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 2:
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

                    case 3:
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

                    case 4:
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

                    case 5:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            currentCustomer.logout();
                            printLoginMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 6:
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


}
