import java.util.*;

public class UserUtils {
    private static String currentUsername;
    private static String currentRole;

    public static void setCurrentUsername(String currentUsername) {
        UserUtils.currentUsername = currentUsername;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentRole(String currentRole) {
        UserUtils.currentRole = currentRole;
    }

    public static String getCurrentRole() {
        return currentRole;
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

    static Customer getCustomerByUsername(String username) {
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

    static Admin getAdminByUsername(String username) {
        ArrayList<Admin> admins = SystemFile.getAdminListFromFile();

        if (admins != null) {
            for (Admin admin : admins) {
                if (Objects.equals(username, admin.getUsername())) {
                    return admin;
                }
            }
        } else {
            return null;
        }

        return null;
    }

    static Customer viewCurrentCustomer() {
        if (getCurrentUsername() != null) {
            return getCustomerByUsername(getCurrentUsername());
        } else {
            return null;
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

    public static void printCurrentUserInfo() {
        if (getCurrentUsername() != null) {
            User currentUser = getCurrentUser();
            if (currentUser != null) {
                System.out.print(currentUser.display());
            } else {
                System.out.println("You are not logged in yet1!");
            }
        } else {
            System.out.println("You are not logged in yet!");
        }
    }

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

    // customer self register
    static void registerCustomer() {
        ArrayList<Customer> customers = SystemFile.viewCustomerList();
        Scanner scanner = new Scanner(System.in);

        String inputUsername = null;
        boolean hasRun = false;
        String message = null;
        loop: do {
            if (!hasRun) {
                System.out.print("\n");
                System.out.print("Enter your new username: ");
                hasRun = true;
            } else {
                System.out.println(message);
                System.out.print("\n");
                System.out.print("Please re-enter your new username: ");
            }
            inputUsername = scanner.nextLine();

            if (customers != null) {
                for (Customer customer: customers) {
                    if (Objects.equals(customer.getUsername(), inputUsername)) {
                        message = "This account name is taken!";
                        inputUsername = null;
                        continue loop;
                    }
                }
            }

            message = "Cannot leave the field empty!";
        } while (inputUsername == null || inputUsername.isEmpty());

        String inputPassword = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.print("Enter your new password: ");
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
                System.out.print("Enter your full name: ");
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
                System.out.print("Enter your phone number: ");
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
                System.out.print("Enter your email address: ");
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
                System.out.print("Enter your address: ");
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
        SystemFile.appendToFile("data/customers.txt", newCustomer.toString());
    }

    // customer self update
    static Customer updateCustomer(String updateCustomerID) {
        ArrayList<Customer> customers = SystemFile.viewCustomerList();
        Customer updateCustomer = viewCustomerByID(updateCustomerID);

        int position = 0;
        for (Customer customer : customers) {
            if (Objects.equals(updateCustomer.getCustomerID(), customer.getCustomerID())) {
                break;
            }
            position++;
        }

        Scanner scanner = new Scanner(System.in);

        String inputUsername = null;
        boolean hasRun = false;
        String message = null;
        loop: do {
            if (!hasRun) {
                System.out.print("\n");
                System.out.print("Enter your desired username: ");
                hasRun = true;
            } else {
                System.out.println(message);
                System.out.print("\n");
                System.out.print("Please re-enter the username: ");
            }
            inputUsername = scanner.nextLine();

            if (customers != null) {
                for (Customer customer: customers) {
                    if (Objects.equals(customer.getUsername(), inputUsername) &&
                            (!Objects.equals(customer.getCustomerID(), updateCustomer.getCustomerID()))) {
                        message = "This account name is taken!";
                        inputUsername = null;
                        continue loop;
                    }
                }
            }

            message = "Cannot leave the field empty!";
        } while (inputUsername == null || inputUsername.isEmpty());

        String inputPassword = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.print("Enter your desired password: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter the password: ");
            }

            inputPassword = scanner.nextLine();
        } while (inputPassword == null || inputPassword.isEmpty());

        String inputFullName = null;
        hasRun = false;
        do {
            if (!hasRun) {
                System.out.print("Enter your full name: ");
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
                System.out.print("Enter your phone number: ");
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
                System.out.print("Enter your email address: ");
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
                System.out.print("Enter your address: ");
                hasRun = true;
            } else {
                System.out.println("Cannot leave this field empty!");
                System.out.println("Please re-enter your address: ");
            }

            inputAddress = scanner.nextLine();
        } while (inputAddress == null || inputAddress.isEmpty());

        updateCustomer.setUsername(inputUsername);
        updateCustomer.setPassword(inputPassword);
        updateCustomer.setFullName(inputFullName);
        updateCustomer.setPhone(inputPhone);
        updateCustomer.setEmail(inputEmail);
        updateCustomer.setAddress(inputAddress);

        customers.set(position, updateCustomer);

        // write all Customers to file with updated one
        updateCustomerToFile(customers);

        return  updateCustomer;
    }

    static void updateCustomerToFile(ArrayList<Customer> customers) {
        SystemFile.writeToFile("data/customers.txt", "#ID,Username,Password,Name,Phone,Email,Address,Spending,Membership");
        for (Customer customer : customers) {
            SystemFile.appendToFile("data/customers.txt", customer.toString());
        }
    }
}
