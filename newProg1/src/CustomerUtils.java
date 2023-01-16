import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerUtils {
    static void addItem(){
        String productInfo, productId = "", productName = "";
        String userInputString;
        double productPrice = 0;
        boolean addItemRun = true;
        boolean checkProduct;
        Scanner inputScanner = new Scanner(System.in);

        ArrayList<Product> productsList = SystemFile.viewProductList();
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();
        Customer currentCus = UserUtils.viewCurrentCustomer();

        System.out.print("\n");
        System.out.println("----- Add Items(s) -----");

        if(currentCus != null){
            String customerID;
            customerID = currentCus.getCustomerID();
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

                                BagUtils.updateBag(bagsList);
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
        }else{
            System.out.println("An error occurred! (addItemLoginSession)!");
        }
    }

    static void editBag(){
        String userInputString, userInputString2;
        Scanner inputScanner = new Scanner(System.in);
        int bagAmount;
        int userInputInt;
        boolean editBagRun = true;

        ArrayList<Bag> bagsList = SystemFile.viewBagsList();
        Customer currentCus = UserUtils.viewCurrentCustomer();
        String customerID = "";

        if (currentCus != null) {
            customerID = currentCus.getCustomerID();
        }

        System.out.print("\n");
        do{
            System.out.println("----- Edit Bag -----");
            ArrayList<Bag> currentBags = BagUtils.viewBagDetail();
            bagAmount = currentBags.size();

            if(bagAmount == 0){
                editBagRun = false;
            }
            else{
                System.out.print("\n");
                System.out.println("Enter a number to edit the corresponding section or:");
                System.out.println((bagAmount + 1) + ". Remove All Items");
                System.out.println((bagAmount + 2) + ". Cancel");
                try{
                    System.out.print("\n");
                    System.out.print("Enter a number: ");
                    userInputInt = inputScanner.nextInt();
                    inputScanner.nextLine();
                    System.out.print("\n");

                    if(userInputInt == (bagAmount + 1)){
                        if (bagsList != null) {
                            String finalCustomerID = customerID;
                            bagsList.removeIf(bag -> bag.getCustomerID().equalsIgnoreCase(finalCustomerID));
                            BagUtils.updateBag(bagsList);

                            System.out.print("\n");
                            System.out.println("----- Bag Updated -----");
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
                        System.out.println("----- Edit Item -----");
                        if (bagsList != null) {
                            int cnt = 1;
                            Bag editBag = null;
                            for(Bag bag : currentBags){
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
                    System.out.print("Do you to continue edit [y/n]: ");
                    userInputString = inputScanner.nextLine();

                    if ((!userInputString.equalsIgnoreCase("y"))) {
                        if ((!userInputString.equalsIgnoreCase("n"))) {
                            System.out.println("Hmm! We will consider this answer as a no.");
                        }
                        editBagRun = false;
                    }
                    System.out.print("\n");
                }catch(InputMismatchException e){
                    editBagRun = false;
                }
            }
        }while(editBagRun);
    }

    static void viewBagMenu(){
        System.out.println("----- Bag Content -----");

        ArrayList<Bag> currentBagsList = BagUtils.viewBagDetail();

        boolean viewBagRun;
        int bagAmount = 0;
        String userInputString;
        Scanner inputScanner = new Scanner(System.in);

        try{
            do{
                viewBagRun = true;

                System.out.print("\n");
                System.out.println("Bag Options");
                System.out.println("1. Edit Bag");
                System.out.println("2. Check Out Bag");
                System.out.println("3. Return To Menu");
                System.out.print("Please Select A Number: ");
                userInputString = inputScanner.nextLine();

                if(userInputString.equalsIgnoreCase("1")){
                    viewBagRun = false;
                    editBag();
                } else if (userInputString.equalsIgnoreCase("2")) {
                    viewBagRun = false;
                } else if (userInputString.equalsIgnoreCase("3")) {
                    viewBagRun = false;
                }else{
                    System.out.println("Please Enter A Valid Number!");
                }

            }while(viewBagRun);
        }catch(InputMismatchException e){
            viewBagRun = false;
            System.out.println("Please enter a valid input!");
        }
    }
}
