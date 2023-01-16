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
        return String.format("%s, %s, %s, %s, %s, %s, %s, %.2f, %s", getCustomerID(),
                getUsername(), getPassword(), getFullname(), getPhone(), getEmail(),
                getAddress(), getSpending(), getMembership());
    }

    // functional methods
    private String getContinuousID() {
        ArrayList<String> customerIDs = SystemFile.viewCustomerIDList();
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
        Scanner inputScanner = new Scanner(System.in);

        ArrayList<Product> productsList = ProductUtils.viewProductList();
        ArrayList<Bag> bagsList = BagUtils.viewBagsList();

        System.out.print("\n");
        System.out.println("----- Add Items(s) -----");
        do{
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
                        if (userInputInt > 0){
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
                                    bagsList.add(new Bag(customerID, productId, productName, userInputInt, userInputInt * productPrice));
                                }
                            }else{
                                bagsList = new ArrayList<>();
                                bagsList.add(new Bag(customerID, productId, productName, userInputInt, userInputInt * productPrice));
                            }

                            PrintWriter pw = new PrintWriter(new FileWriter("bag.txt", false));
                            pw.println("#customerID,ProductID,ProductName,Amount,Price");
                            for(Bag bag : bagsList){
                                pw.println(String.format("%s,%s,%s,%d,%.2f", bag.getCustomerID(), bag.getProductID(), bag.getProductName(), bag.getProductAmount(), bag.getProductPrice()));
                            }
                            pw.close();
                        }else{
                            System.out.println("Please enter a valid input!");
                        }
                    }else if(userInputString.equalsIgnoreCase("n")){
                        System.out.println("Processing...");
                    }else{
                        System.out.println("Hmm! We will consider this answer as a no.");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input!");
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                    System.out.println("An error occurred (updateBag)!");
                }
            }
            else {
                System.out.println(productInfo + " not found!");
            }

            try{
                System.out.print("Do you want to add another item [y/n]: ");
                userInputString = inputScanner.nextLine();
                if(userInputString.equalsIgnoreCase("n")){
                    addItemRun = false;
                } else if (userInputString.equalsIgnoreCase("y")) {
                    System.out.println("Processing...");
                }else{
                    System.out.println("Hmm! We will consider this answer as a no.");
                    addItemRun = false;
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a valid input!");
            }

        }while(addItemRun);
    }

    public void editBag(){
        String userInputString, userInputString2;
        Scanner inputScanner = new Scanner(System.in);
        int bagAmount = 0;
        int userInputInt;
        boolean editBagRun = true;
        ArrayList<Bag> bagsList = BagUtils.viewBagsList();

        System.out.print("\n");
        System.out.println("----- Edit Bag -----");
        do{
            System.out.print("\n");
            ArrayList<Bag> result= BagUtils.viewBagDetail();
            bagAmount = result.size();

            if(bagAmount == 0){
                try{
                    System.out.print("\n");
                    System.out.print("Do you want to add item [y/n]: ");
                    userInputString = inputScanner.nextLine();
                    if (userInputString.equalsIgnoreCase("y")) {
                        addItem();
                    }else if((!userInputString.equalsIgnoreCase("y")) && (!userInputString.equalsIgnoreCase("n"))){
                        System.out.println("Hmm! We will consider this answer as a no.");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter a valid input!");
                }
                editBagRun = false;
            }else{
                System.out.print("\n");
                System.out.println((bagAmount + 1) + ". Remove All Items");
                System.out.println((bagAmount + 2) + ". Return to Menu");
                try{
                    System.out.print("\n");
                    System.out.print("Please select a number to change the corresponding item: ");
                    userInputInt = inputScanner.nextInt();
                    inputScanner.nextLine();
                    System.out.print("\n");
                    if(userInputInt == (bagAmount + 1)){
                        if (bagsList != null) {
                            bagsList.removeIf(bag -> bag.getCustomerID().equalsIgnoreCase(customerID));
                            BagUtils.updateBag(bagsList);
                            System.out.println("Your bag currently has 0 item.");
                            editBagRun = false;
                        }else{
                            editBagRun = false;
                        }
                    }
                    else if(userInputInt == (bagAmount + 2)){
                        editBagRun = false;
                    } else if (userInputInt > (bagAmount + 1)) {
                        System.out.println("Please enter a valid number!");
                    } else if(userInputInt < 0){
                        System.out.println("Please enter a valid number!");
                    }else{
                        if (bagsList != null) {
                            int cnt = 1;
                            Bag editBag = null;
                            for(Bag bag : result){
                                if (cnt == userInputInt){
                                    editBag = bag;
                                    System.out.println(editBag);
                                    break;
                                }
                                cnt += 1;
                            }
                            System.out.println("1. Edit Amount");
                            System.out.println("2. Remove Item");
                            System.out.println("3. Cancel");
                            System.out.print("\n");
                            System.out.print("Enter your number option: ");
                            int userInputInt2 = inputScanner.nextInt();
                            inputScanner.nextLine();
                            if(userInputInt2 == 3){
                                System.out.println("Processing...");
                            } else if (userInputInt2 == 2) {
                                System.out.print("Do you want to remove this item [y/n]: ");
                                userInputString2 = inputScanner.nextLine();
                                if(userInputString2.equalsIgnoreCase("y") || userInputString2.equalsIgnoreCase("yes")){
                                    if(editBag != null){
                                        Bag finalEditBag = editBag;
                                        bagsList.removeIf(p -> ( (p.getProductID()).equalsIgnoreCase(finalEditBag.getProductID()) && (p.getCustomerID()).equalsIgnoreCase(finalEditBag.getCustomerID())));
                                        BagUtils.updateBag(bagsList);
                                    }
                                } else if (userInputString2.equalsIgnoreCase("n")) {
                                    System.out.println("Processing...");
                                }else{
                                    System.out.println("Hmm! We will consider this answer as a no.");
                                    System.out.println("Processing...");
                                }
                            } else if (userInputInt2 == 1) {
                                System.out.print("Enter New Amount: ");
                                int userInputAmount = inputScanner.nextInt();
                                inputScanner.nextLine();

                                if (userInputAmount > 0){
                                    if(editBag != null){
                                        for(Bag bag2 : bagsList){
                                            if((bag2.getProductID()).equalsIgnoreCase(editBag.getProductID()) && (bag2.getCustomerID()).equalsIgnoreCase(editBag.getCustomerID())){
                                                bag2.setProductAmount(userInputAmount);
                                                bag2.setProductPrice(bag2.getProductAmount() * bag2.getProductPrice());
                                                break;
                                            }
                                        }
                                    }

                                    BagUtils.updateBag(bagsList);
                                }else{
                                    System.out.println("Please enter a valid input!");
                                }
                            }
                        }else{
                            System.out.println("Your bag currently has 0 item.");
                            editBagRun = false;
                        }
                    }
                }catch (InputMismatchException e){
                    System.out.println("Please enter a valid input!");
                }
            }
            if(editBagRun){
                try{
                    System.out.print("Do you to continue edit [y/n]:");
                    userInputString = inputScanner.nextLine();

                    if((!userInputString.equalsIgnoreCase("y")) && (!userInputString.equalsIgnoreCase("n"))){
                        System.out.println("Hmm! We will consider this answer as a no.");
                        editBagRun = false;
                    } else if ((userInputString.equalsIgnoreCase("n"))) {
                        editBagRun = false;
                    }
                }catch(InputMismatchException e){
                    editBagRun = false;
                }
            }
        }while(editBagRun);
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
