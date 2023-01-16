import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class SystemUtils {
    public static void printLoginMenu() {
        System.out.println("----- MENU SCREEN -----");
        System.out.println("1: Login");
        System.out.println("2: Exit Application");
    }

    public static void printLoggedInMenu() {
        System.out.print("\n");
        System.out.println("----- CUSTOMER SCREEN -----");
        System.out.println("1: View Personal Information");
        System.out.println("2: View Product");
        System.out.println("3: View Current Bag");
        System.out.println("6: View Order(s)");
        System.out.println("7: Logout");
        System.out.println("8: Quit application (your account will be logged out)");
        System.out.print("\n");
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
                        if (UserUtils.login()) {
                            printLoggedInMenu();
                            checkLoggedInMenuInput();
                            hasRun = false;
                        } else {
                            printLoginMenu();
                            hasRun = true;
                        }
                        break;
                    case 2:
                        hasRun = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please enter one of the given number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }

        } while (Objects.equals(UserUtils.getCurrentUsername(), null));
    }

    public static void checkLoggedInMenuInput() {
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
        boolean hasRun1 = false;
        loggedInLoop: do {
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
                            System.out.println(currentCustomer);
                            printLoggedInMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 2:
                        if (currentCustomer != null) {
                            hasRun1 = false;

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
                            printLoggedInMenu();

                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 7:
                        if (currentCustomer != null) {
                            hasRun1 = false;
                            currentCustomer.logout();
                            printLoginMenu();
                        } else {
                            hasRun1 = true;
                            System.out.println("You are not logged in yet!");
                        }
                        break;

                    case 8:
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
