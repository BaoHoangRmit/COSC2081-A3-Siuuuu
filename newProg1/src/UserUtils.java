import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class UserUtils {
    private static String currentUsername;

    public static void setCurrentUsername(String currentUsername) {
        UserUtils.currentUsername = currentUsername;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    static String getContinuousID() {
        ArrayList<String> customerIDs = viewCustomerIDList();
        String inputCustomerID;
        if (customerIDs != null) {
            int largestID = 0;
            for (String s: customerIDs) {
                int id = Integer.parseInt(s.substring(s.indexOf("C") + 1));
                if (largestID < id) {
                    largestID = id;
                }
            }

            int idLength = 4;
            inputCustomerID = "C" + Integer.toString(largestID + 1);
            for (int i = 1; i < idLength - 1; i++) {
                inputCustomerID = inputCustomerID.substring(0,i) + "0" + inputCustomerID.substring(i);
            }
        } else {
            inputCustomerID = "C001";
        }

        return inputCustomerID;
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

    static boolean login() {
        HashMap<String, String> accounts = UserUtils.viewCustomerAccountList();
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
                }
            }

            System.out.println("There are no account with the username you entered!");
            System.out.println("Login again!");
            System.out.print("\n");
        } else {
            System.out.println("Account does not exist!");
        }
        return false;
    }
}
