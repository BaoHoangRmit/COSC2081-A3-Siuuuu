import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        SystemFile.currentUsername = null;
        ArrayList<Customer> customers = SystemFile.viewCustomerList();
        ArrayList<String> customerIDs = SystemFile.viewCustomerIDList();
        HashMap<String, String> accounts = SystemFile.viewCustomerAccountList();

        System.out.println("#username,password,fullname,phone,email,address,customerID,spending,memebership");

        if (customers != null) {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        } else {
            System.out.println("There are no customers data");
        }

        if (customerIDs != null) {
            for (String s : customerIDs) {
                System.out.println(s);
            }
        } else {
            System.out.println("There are no customers id");
        }

        if (accounts != null) {
            for (String s : accounts.keySet()) {
                System.out.println(s + " : " + accounts.get(s));
            }
        } else {
            System.out.println("There are no customers account");
        }


//        System.out.println(Objects.requireNonNull(SystemFile.viewCustomerByID("C003")).toString());

        System.out.println();
        System.out.println("--------------------------");
        System.out.println();

        boolean hasRun = false;
        do {
            if (!hasRun) {
                System.out.println("Login into the system!");
                hasRun = true;
            }



        } while (!SystemFile.login());

        System.out.println("The current username is: " + SystemFile.currentUsername);

    }
}