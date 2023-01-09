import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SystemFile {
    static String currentUsername;

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

    static boolean login() {
        HashMap<String, String> accounts = SystemFile.viewCustomerAccountList();
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
                        SystemFile.currentUsername = s;
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
}
