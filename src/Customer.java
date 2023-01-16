import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;

    public Customer(String customerID, String username, String password, String fullName, String phone,
                    String email, String address, double spending,
                    String membership) {
        super(username, password, fullName, phone, email, address);
        this.customerID = customerID;
        this.spending = spending;
        this.membership = membership;
    }

    // customer register constructor
    public Customer(String username, String password, String fullName, String phone,
                    String email, String address) {
        super(username, password, fullName, phone, email, address);
        this.customerID = getContinuousID();
        this.spending = 0;
        this.membership = "Normal";
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%s", getCustomerID(),
                getUsername(), getPassword(), getFullname(), getPhone(), getEmail(),
                getAddress(), getSpending(), getMembership());
    }

    @Override
    public boolean logout() {
        if (Objects.equals(SystemFile.getCurrentUsername(), null)) {
            return false;
        } else {
            SystemFile.setCurrentUsername(null);
            return true;
        }
    }

    // functional methods
    private String getContinuousID() {
        ArrayList<String> customerIDs = SystemFile.getCustomerIDList();
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

    public void addItem(){
        String productInfo, productId = "", productName = "";
        String userInputString;
        double productPrice = 0;
        boolean addItemRun = true;
        boolean checkProduct;

        ArrayList<Product> productsList = SystemFile.viewProductList();
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();

        System.out.print("\n");
        System.out.println("----- Add Items(s) -----");
        do{
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter ProductID/ ProductName: ");
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
                    System.out.println("Item Found: " + productName + ", Price: " + productPrice);
                    System.out.print("Do you want to add this item [y/n]: ");
                    userInputString = inputScanner.nextLine();

                    if(userInputString.equalsIgnoreCase("y")){
                        System.out.print("Enter amount: ");
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
                            }
                        }else{
                            bagsList = new ArrayList<>();
                            bagsList.add(new Bag(customerID, productId, userInputInt, userInputInt * productPrice));
                        }

                        PrintWriter pw = new PrintWriter(new FileWriter("bag.txt", false));
                        pw.println("#customerID,ProductID,Amount,Price");
                        for(Bag bag : bagsList){
                            pw.println(String.format("%s,%s,%d,%.2f", getCustomerID(), bag.getProductID(),
                                    bag.getProductAmount(), bag.getProductPrice()));
                        }
                        pw.close();
                    }else if(userInputString.equalsIgnoreCase("n")){
                        System.out.println("Processing...");
                    }else{
                        System.out.println("Hmm! We will consider this answer as a no.");
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
                System.out.print("Do you want to add another item [y/n]: ");
                userInputString = inputScanner.nextLine();
                if(userInputString.equalsIgnoreCase("n")){
                    System.out.println("Returning to Menu...");
                    addItemRun = false;
                } else if (userInputString.equalsIgnoreCase("y")) {
                    System.out.println("Processing...");
                }else{
                    System.out.println("Hmm! We will consider this answer as a no.");
                    System.out.println("Returning to Menu...");
                    addItemRun = false;
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid input!");
            }

        }while(addItemRun);


    }
}
