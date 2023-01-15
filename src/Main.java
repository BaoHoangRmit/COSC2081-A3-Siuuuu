import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        SystemFile.setCurrentUsername(null);
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



//        System.out.println("The current username is: " + SystemFile.getCurrentUsername());
//        Customer currentCustomer = SystemFile.viewCustomerByUsername(SystemFile.getCurrentUsername());
//        if (currentCustomer != null) {
//            System.out.println(currentCustomer.toString());
//        } else {
//            System.out.println("You are not logged in yet!");
//        }



        System.out.println("----- WELCOME SCREEN -----");
        System.out.println("COSC2081 GROUP ASSIGNMENT\n" +
                "STORE ORDER MANAGEMENT SYSTEM\n" +
                "Instructor: Mr. Tom Huynh & Dr. Phong Ngo\n" +
                "Group: Siuuuu\n" +
                "s3926050, Hoang Quoc Bao\n" +
                "s3938338, Nguyen Thien Co\n" +
                "s3927474, Hoang Vinh Khue\n" +
                "sXXXXXXX, Student Name");
        System.out.println("----- WELCOME SCREEN -----");

        System.out.println();

        SystemFile.printLoginMenu();
        SystemFile.checkLoginMenuInput();




    }
}