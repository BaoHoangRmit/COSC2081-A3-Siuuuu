import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;

    public Customer(String username, String password, String fullname, String phone,
                    String email, String address, String customerID, double spending,
                    String membership) {
        super(username, password, fullname, phone, email, address);
        this.customerID = customerID;
        this.spending = spending;
        this.membership = membership;
    }

    // getter
    public String getCustomerID() {
        return customerID;
    }

    public double getSpending() {
        return spending;
    }

    public String getMembership() {
        return membership;
    }

    // setter
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s, %.2f, %s", getUsername(),
                getPassword(), getFullname(), getPhone(), getEmail(), getAddress(),
                getCustomerID(), getSpending(), getMembership());
    }

    public void addItem(){
        String productInfo, productId = "", productName;
        String userInputString;
        double productPrice = 0;
        boolean addItemRun = true;
        boolean checkProduct;

        ArrayList<Product> productsList = SystemFile.viewProductList();
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();

        Scanner fileScanner = null;

        System.out.println("Add Item(s)");
        do{
            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Enter ProductID/ ProductName:");
            try{
                productInfo = inputScanner.nextLine();
                productInfo = productInfo.trim();
            }catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
                break;
            }
            checkProduct = false;

            if(productsList != null){
                for (Product product : productsList) {
                    productId = product.getProductID();
                    productName = product.getProductName();
                    productPrice = product.getProductPrice();
                    if ((productInfo).equalsIgnoreCase(productId) || (productInfo).equalsIgnoreCase(productName)) {
                        checkProduct = true;
                        break;
                    }
                }
            }

            if(checkProduct){
                try{
                    System.out.println(productId);
                    System.out.println("Do you want to add this item ?[y/n]");
                    userInputString = inputScanner.nextLine();

                    if(userInputString.equalsIgnoreCase("y")){
                        System.out.println("Enter amount: ");
                        int userInputInt = inputScanner.nextInt();
                        inputScanner.nextLine();

                        if(bagsList != null){
                            boolean checkBag = false;
                            for (Bag bag : bagsList) {
                                if(customerID.equalsIgnoreCase(bag.getCustomerID()) && productId.equalsIgnoreCase(bag.getProductID())) {
                                    checkBag = true;
                                    bag.setProductAmount(bag.getProductAmount() + userInputInt);
                                    bag.setProductPrice(bag.getProductAmount() * productPrice);
                                    break;
                                }
                            }

                            if(!checkBag){
                                bagsList.add(new Bag(customerID, productId, userInputInt, userInputInt * productPrice));
                                checkBag = false;
                            }
                        }else{
                            bagsList = new ArrayList<>();
                            bagsList.add(new Bag(customerID, productId, userInputInt, userInputInt * productPrice));
                        }

                        PrintWriter pw = new PrintWriter(new FileWriter("bag.txt", false));
                        pw.println("#customerID,ProductID,Amount,Price");
                        for(Bag bag : bagsList){
                            pw.println(String.format("%s,%s,%d,%.2f", getCustomerID(), bag.getProductID(), bag.getProductAmount(), bag.getProductPrice()));
                        }
                        pw.close();
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println(productInfo + " not found!");
            }

            try{
                System.out.println("Do you want to add more item ?[y/n]");
                userInputString = inputScanner.nextLine();
                if(userInputString.equalsIgnoreCase("n")){
                    addItemRun = false;
                } else if (userInputString.equalsIgnoreCase("y")) {
                    System.out.println("Processing...");
                }else{
                    System.out.println("Hmm! We will consider this answer as a no.");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid input!");
            }

        }while(addItemRun);


    }

    @Override
    public boolean logout() {
        if (Objects.equals(SystemFile.getCurrentUsername(), null)) {
            System.out.println("Logout unsuccessfully!");
            return false;
        } else {
            SystemFile.setCurrentUsername(null);
            System.out.println("Logout successfully!");
            return true;
        }
    }
}
