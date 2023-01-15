import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Customer extends User{
    private String customerID;
    private double spending;
    private String membership;

    public Customer(String username, String password, String fullName, String phone,
                    String email, String address, String customerID, double spending,
                    String membership) {
        super(username, password, fullName, phone, email, address);
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
        String productInfo, productId = "", productName = "";
        String userInputString;
        double productPrice = 0;
        boolean addItemRun = true;
        boolean checkProduct;
        Scanner inputScanner = new Scanner(System.in);

        ArrayList<Product> productsList = SystemFile.viewProductList();
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();

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

    public void viewBagDetail(){
        int cnt = 0;
        double totalPrice = 0;
        HashMap<Integer,Double> result= viewBag();

        for(Map.Entry m:result.entrySet()){
            cnt = (int) m.getKey();
            totalPrice = (double) m.getValue();
        }

        if(cnt == 0){
            System.out.println("Your bag currently has 0 item.");
        } else if(cnt == 1){
            System.out.println("Total: " + cnt + " item.");
            System.out.print(String.format("Total Price: %.4f", totalPrice));
        }else if(cnt > 1){
            System.out.println("Total: " + cnt + " items.");
            System.out.print(String.format("Total Price: %.4f", totalPrice));
        }
    }
    public HashMap<Integer,Double> viewBag(){
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();
        int cnt = 0;
        double totalPrice = 0;

        if (bagsList != null) {
            for(Bag bag : bagsList){
                if(bag.getCustomerID().equalsIgnoreCase(customerID)){
                    cnt += 1;
                    System.out.println(String.format("%d. %s: Amount: %d, Price: %.4f", cnt, bag.getProductName() ,bag.getProductAmount(), bag.getProductPrice()));
                    totalPrice += bag.getProductPrice();
                }
            }
        }

        HashMap<Integer,Double> result=new HashMap<Integer,Double>();
        result.put(cnt, totalPrice);
        return result;
    }

    public void editBag(){
        String userInputString, userInputString2;
        Scanner inputScanner = new Scanner(System.in);
        int bagAmount = 0;
        int userInputInt;
        boolean editBagRun = true;
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();

        System.out.print("\n");
        System.out.println("----- Edit Bag -----");
        do{
            System.out.print("\n");
            HashMap<Integer,Double> result= viewBag();

            for(Map.Entry m:result.entrySet()){
                bagAmount = (int) m.getKey();
            }
            if(bagAmount == 0){
                try{
                    viewBagDetail();
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
                System.out.println((bagAmount + 1) + ". Remove All Items");
                System.out.println((bagAmount + 2) + ". Return to Menu");
                try{
                    System.out.print("Please select a number to change the corresponding item: ");
                    userInputInt = inputScanner.nextInt();
                    inputScanner.nextLine();
                    if(userInputInt == (bagAmount + 1)){
                        if (bagsList != null) {
                            bagsList.removeIf(bag -> bag.getCustomerID().equalsIgnoreCase(customerID));
                            SystemFile.updateBag(bagsList);
                            System.out.println("Your bag currently has 0 item.");
                            editBagRun = false;
                        }else{
                            System.out.println("Your bag currently has 0 item.");
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
                            for(Bag bag : bagsList){
                                if (cnt != userInputInt){
                                    if(bag.getCustomerID().equalsIgnoreCase(customerID)){
                                        cnt += 1;
                                    }
                                }else{
                                    editBag = bag;
                                    System.out.println(editBag);
                                    break;
                                }
                            }
                            System.out.println("1. Edit Amount");
                            System.out.println("2. Remove Item");
                            System.out.println("3. Cancel");
                            System.out.println("Enter your number option: ");
                            int userInputInt2 = inputScanner.nextInt();
                            inputScanner.nextLine();
                            if(userInputInt2 == 3){
                                System.out.println("Processing...");
                            } else if (userInputInt2 == 2) {
                                System.out.print("Do you want to remove this item [y/n]: ");
                                userInputString2 = inputScanner.nextLine();
                                if(userInputString2.equalsIgnoreCase("y") || userInputString2.equalsIgnoreCase("yes")){
                                    if(editBag != null){
                                        bagsList.remove(editBag);
                                        SystemFile.updateBag(bagsList);
                                    }
                                } else if (userInputString2.equalsIgnoreCase("n")) {
                                    System.out.println("Processing...");
                                }else{
                                    System.out.println("Hmm! We will consider this answer as a no.");
                                    System.out.println("Processing...");
                                }
                            } else if (userInputInt2 == 1) {
                                System.out.println("Enter New Amount: ");
                                int userInputAmount = inputScanner.nextInt();
                                inputScanner.nextLine();

                                if (userInputAmount > 0){
                                    if(editBag != null){
                                        editBag.setProductAmount(userInputAmount);
                                        editBag.setProductPrice(editBag.getProductAmount() * editBag.getProductPrice());
                                    }

                                    SystemFile.updateBag(bagsList);
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
                    System.out.println("Do you to continue edit [y/n]:");
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
