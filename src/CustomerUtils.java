import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
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

    static void checkoutBag(){
        System.out.println("\n----- Check Out Bag -----");
        ArrayList<Bag> curBagsList = BagUtils.viewBagDetail();
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();

        String userInputString;
        Scanner inputScanner = new Scanner(System.in);

        ArrayList<Order> ordersList = SystemFile.viewOrdersList();
        String orderId = OrderUtils.genOrderId();

        if (curBagsList.size() == 0) {
            System.out.print("");
        } else {
            try {
                Bag editBag = null;
                System.out.print("Do you want to order these items [y/n]: ");
                userInputString = inputScanner.nextLine();

                if (userInputString.equalsIgnoreCase("y")) {
                    for (Bag bag : curBagsList) {
                        if (ordersList == null) {
                            ordersList = new ArrayList<>();
                        }
                        ordersList.add(new Order(bag.getCustomerID(), orderId, bag.getProductName(), bag.getProductAmount(), bag.getProductPrice(), BagUtils.getBagTotal()));

                        if (bagsList != null) {
                            for (Bag bag2 : bagsList) {
                                if ((bag2.getCustomerID()).equalsIgnoreCase(bag.getCustomerID()) && (bag2.getProductID()).equalsIgnoreCase(bag.getProductID())) {
                                    editBag = bag2;
                                }
                            }
                            bagsList.remove(editBag);
                        } else {
                            bagsList = new ArrayList<>();
                        }
                    }
                    if (ordersList != null) {
                        SystemFile.updateOrder(ordersList);
                    }
                    BagUtils.updateBag(Objects.requireNonNullElseGet(bagsList, ArrayList::new));
                    System.out.println("\n----- Orders Updated -----\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    static void viewProductMenu(){
        System.out.println("\n----- Product List -----");

        ArrayList<Product> proList = SystemFile.viewProductList();
        String productID = "", productName = "", productDesc = "", productCat = "";
        double productPrice = 0;
        int saleNumber = 0;

        boolean productMenuRun = true;
        do {
            if (proList != null){
                String userInputString;
                Scanner inputScanner = new Scanner(System.in);

                System.out.print("\n");
                System.out.println("Product List Options:");
                System.out.println("1. View All Product(s)");
                System.out.println("2. Add Item to Bag");
                System.out.println("3. View Product(s) By Category");
                System.out.println("4. View Product(s) By ID");
                System.out.println("5. View Product(s) By Name");
                System.out.println("6. Sort Product(s) By Price Ascending");
                System.out.println("7. Sort Product(s) By Price Descending");
                System.out.println("8. View Most Popular Product");
                System.out.println("9. View Least Popular Product");
                System.out.println("10. Return to Menu");

                try{
                    System.out.print("\n");
                    System.out.print("Enter a number: ");
                    userInputString = inputScanner.nextLine();
                    if(userInputString.equalsIgnoreCase("1")){
                        System.out.println("Product(ID. Name):");
                        for(Product pro : proList){
                            productID = pro.getProductID();
                            productName = pro.getProductName();
                            productDesc = pro.getProductDesc();
                            productCat = pro.getCategoryName();
                            productPrice = pro.getProductPrice();
                            saleNumber = pro.getSaleNumber();

                            System.out.println(String.format("%s. %s: " +
                                    "\n    Description: %s " +
                                    "\n    Category: %s" +
                                    "\n    Price: %.4f" +
                                    "\n    Total Sale: %d", productID, productName, productDesc, productCat, productPrice, saleNumber));
                        }
                    } else if (userInputString.equalsIgnoreCase("2")) {
                        addItem();
                    }else if (userInputString.equalsIgnoreCase("3")) {
                        System.out.print("Enter Category Name: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByCategory(userInputString2);
                    } else if (userInputString.equalsIgnoreCase("4")) {
                        System.out.print("Enter ProductID: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByID(userInputString2);
                    }else if (userInputString.equalsIgnoreCase("5")) {
                        System.out.print("Enter Product Name: ");
                        String userInputString2 = inputScanner.nextLine();
                        ProductUtils.viewProductByName(userInputString2);
                    }else if (userInputString.equalsIgnoreCase("6")) {
                        ProductUtils.sortAllProductByPriceAsc();
                    }else if (userInputString.equalsIgnoreCase("7")) {
                        ProductUtils.sortAllProductByPriceDes();
                    }else if (userInputString.equalsIgnoreCase("8")) {
                        ProductUtils.viewMostPopularProducts();
                    }else if (userInputString.equalsIgnoreCase("9")) {
                        ProductUtils.viewLeastPopularProducts();
                    }else if (userInputString.equalsIgnoreCase("10")) {
                        productMenuRun = false;
                    } else{
                        System.out.println("Please Enter A Valid Number!");
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter a valid input!");
                }
            }
            else{
                System.out.println("0 Product Found!");
            }
        }while(productMenuRun);
    }

    static void viewBagMenu(){
        System.out.println("----- Bag Content -----");

        ArrayList<Bag> currentBagsList = BagUtils.viewBagDetail();

        boolean viewBagRun = true;
        int bagAmount = currentBagsList.size();
        String userInputString;
        Scanner inputScanner = new Scanner(System.in);

        try{
            do{
                if(bagAmount == 0){
                    viewBagRun = false;
                }else{
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
                        checkoutBag();
                    } else if (userInputString.equalsIgnoreCase("3")) {
                        viewBagRun = false;
                    }else{
                        System.out.println("Please Enter A Valid Number!");
                    }
                }
            }while(viewBagRun);
        }catch(InputMismatchException e){
            viewBagRun = false;
            System.out.println("Please enter a valid input!");
        }
    }

    static void viewOrderMenu(){
        System.out.println("----- Orders -----");

        ArrayList<String> currentOrdersList = OrderUtils.viewOrderDetail();

        boolean viewOrderRun = true;
        int bagAmount = currentOrdersList.size();
        String userInputString;
        Scanner inputScanner = new Scanner(System.in);

        try{
            do{
                if(bagAmount == 0){
                    viewOrderRun = false;
                }else{
                    System.out.print("\n");
                    System.out.println("Order Options");
                    System.out.println("1. Pay Order(s)");
                    System.out.println("2. Return To Menu");
                    System.out.print("Please Select A Number: ");
                    userInputString = inputScanner.nextLine();

                    if(userInputString.equalsIgnoreCase("1")){
                        viewOrderRun = false;
                        currentOrdersList = OrderUtils.viewOrder();
                        ArrayList<Order> ordersList = SystemFile.viewOrdersList();

                        if(ordersList == null){
                            ordersList = new ArrayList<>();
                        }

                        try {
                            System.out.print("Do you want to order these items [y/n]: ");
                            String userInputString2 = inputScanner.nextLine();

                            if (userInputString2.equalsIgnoreCase("y")) {
                                for(String ordersId : currentOrdersList){
                                    for (Order orders : ordersList){
                                        if(orders.getOrderID().equalsIgnoreCase(ordersId)){
                                            orders.setPaymentStatus("Paid");
                                        }
                                    }
                                }
                                SystemFile.updateOrder(ordersList);

                                System.out.println("\n----- Orders Updated -----");
                                System.out.println("All bills have been paid!");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid input!");
                        }
                    }else if (userInputString.equalsIgnoreCase("2")) {
                        viewOrderRun = false;
                    }else{
                        System.out.println("Please Enter A Valid Number!");
                    }
                }
            }while(viewOrderRun);
        }catch(InputMismatchException e){
            viewOrderRun = false;
            System.out.println("Please enter a valid input!");
        }
    }

    static void updateSpending(){
        Customer curCus = UserUtils.viewCurrentCustomer();
        ArrayList<Order> ordersList = SystemFile.viewOrdersList();
        ArrayList<Customer> cusLists = SystemFile.viewCustomerList();

        String currentCustomerId = "";
        double spending = 0;

        if (curCus != null) {
            currentCustomerId = curCus.getCustomerID();
            if (ordersList != null) {
                for (Order orders : ordersList) {
                    if (orders.getUserID().equalsIgnoreCase(currentCustomerId)) {
                        if (orders.getOrderStatus().equalsIgnoreCase("Paid")) {
                            spending += orders.getOrderPrice();
                        }
                    }
                }

                curCus.setSpending(spending);
                if(spending > 5000000 && spending <= 10000000){
                    curCus.setMembership("Silver");
                } else if (spending > 10000000 && spending <= 25000000) {
                    curCus.setMembership("Gold");
                } else if (spending > 25000000) {
                    curCus.setMembership("Diamond");
                }else{
                    curCus.setMembership("None");
                }

                try {
                    PrintWriter pw = new PrintWriter(new FileWriter("data/customers.txt", false));
                    pw.println("#ID,Username,Password,Name,Phone,Email,Address,Spending,Membership");
                    if (cusLists != null) {
                        for(Customer cus : cusLists){
                            if(cus.getCustomerID().equalsIgnoreCase(currentCustomerId)){
                                pw.println(String.format("%s,%s,%s,%s,%s,%s,%s,%.4f,%s",
                                        currentCustomerId,
                                        curCus.getUsername(),
                                        curCus.getPassword(),
                                        curCus.getFullName(),
                                        curCus.getPhone(),
                                        curCus.getEmail(),
                                        curCus.getAddress(),
                                        curCus.getSpending(),
                                        curCus.getMembership()));
                            }else{
                                pw.println(String.format("%s,%s,%s,%s,%s,%s,%s,%.4f,%s",
                                        cus.getCustomerID(),
                                        cus.getUsername(),
                                        cus.getPassword(),
                                        cus.getFullName(),
                                        cus.getPhone(),
                                        cus.getEmail(),
                                        cus.getAddress(),
                                        cus.getSpending(),
                                        cus.getMembership()));
                            }
                        }
                        pw.close();
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("File not found (customer)!");
                } catch (IOException e) {
                    System.out.println("An unexpected error occurred!");
                }
            }
        }
    }
}