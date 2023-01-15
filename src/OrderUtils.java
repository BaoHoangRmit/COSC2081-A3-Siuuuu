import java.io.*;
import java.util.*;

public class OrderUtils {
    static ArrayList<Order> viewOrdersList() {
        try {
            Scanner fileScanner = new Scanner((new File("order.txt")));
            ArrayList<Order> ordersList = new ArrayList<Order>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 8) {
                    throw new IOException("Invalid Input Format (order)");
                } else {
                    // get each string seperated by ","

                    String customerId = inReader.nextToken();
                    String orderId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    int productAmount = Integer.parseInt(inReader.nextToken());
                    double productPrice = Double.parseDouble(inReader.nextToken());
                    double orderPrice = Double.parseDouble(inReader.nextToken());
                    String paymentStatus = inReader.nextToken();
                    String orderStatus = inReader.nextToken();

                    ordersList.add(new Order(customerId, orderId, productName, productAmount, productPrice, orderPrice, paymentStatus, orderStatus));
                }
            }

            fileScanner.close();

            return ordersList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found (order)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            return null;
        }
        return null;
    }

    static void updateOrder(ArrayList<Order> ordersList) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("order.txt", false));
            pw.println("#customerId, orderId, productName, productAmount, productPrice, orderPrice, paymentStatus, orderStatus");
            for (Order order : ordersList) {
                pw.println(String.format("%s,%s,%s,%d,%.4f,%.4f,%s,%s",
                        order.getUserID(),
                        order.getOrderID(),
                        order.getProductName(),
                        order.getProductAmount(),
                        order.getProductPrice(),
                        order.getOrderPrice(),
                        order.getPaymentStatus(),
                        order.getOrderStatus()));
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred (updateOrder)!");
        }
    }

    static void generateOrder() {
        ArrayList<Bag> curBagsList = BagUtils.viewBagDetail();
        ArrayList<Bag> bagsList = BagUtils.viewBagsList();
        String userInputString;
        Scanner inputScanner = new Scanner(System.in);
        ArrayList<Order> ordersList = viewOrdersList();
        String orderId = "B0";
        int numOrderId = 0;
        if (ordersList == null) {
            ordersList = new ArrayList<>();
            orderId = "B0";
        } else {
            for (Order orders : ordersList) {
                numOrderId = Integer.parseInt((orders.getOrderID()).substring(1));
                orderId = String.format("B%d", numOrderId);
            }
            numOrderId += 1;
            orderId = String.format("B%d", numOrderId);
        }


        if (curBagsList.size() == 0) {
            System.out.println("Your bag currently has 0 item.");
        } else {
            try {
                Bag editBag = null;
                System.out.print("Do you want to order these items [y/n]: ");
                userInputString = inputScanner.nextLine();
                if (userInputString.equalsIgnoreCase("y")) {
                    for (Bag bag : curBagsList) {
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
                    updateOrder(ordersList);
                    BagUtils.updateBag(Objects.requireNonNullElseGet(bagsList, ArrayList::new));
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid input!");
            }
        }
    }

    static ArrayList<String> viewOrder() {
        ArrayList<Order> ordersList = viewOrdersList();
        ArrayList<String> currentOrdersList = new ArrayList<>();
        Customer currentCustomer = SystemFile.viewCurrentCustomer();
        String currentCustomerId = "";
        ArrayList<String> ordersId = new ArrayList<>();

        if (currentCustomer != null) {
            currentCustomerId = currentCustomer.getCustomerID();
        }

        if (ordersList != null) {
            for (Order orders : ordersList) {
                if (orders.getUserID().equalsIgnoreCase(currentCustomerId)) {
                    if (!ordersId.contains(orders.getOrderID())) {
                        ordersId.add(orders.getOrderID());
                    }
                }
            }
            if (ordersId.size() > 0) {
                int orderCnt = 1;
                String pStatus = "";
                String oStatus = "";
                System.out.println(ordersId.size() + " Bill(s) Found!");
                for (String orderId : ordersId) {
                    System.out.print("\n");
                    System.out.println(orderCnt + "." + orderId + ":");
                    orderCnt += 1;
                    for (Order orders : ordersList) {
                        if ((orders.getOrderID()).equalsIgnoreCase(orderId)) {
                            pStatus = orders.getPaymentStatus();
                            oStatus = orders.getOrderStatus();
                            System.out.println(String.format("%s, Amount: %d, Price: %.4f", orders.getProductName(), orders.getProductAmount(), orders.getProductPrice()));
                        }
                    }
                    System.out.println("Payment Status: " + pStatus);
                    System.out.println("Order Status: " + oStatus);
                    currentOrdersList.add(orderId);
                }
            } else {
                System.out.println("No Bill Found!");
            }
        } else {
            System.out.println("No Bill Found!");
        }
        return currentOrdersList;
    }

    static void payBill() {
        ArrayList<String> currentOrdersList = viewOrder();
        ArrayList<Order> ordersList = viewOrdersList();
        Scanner inputScanner = new Scanner(System.in);
        Customer currentCustomer = SystemFile.viewCurrentCustomer();

        if (currentOrdersList.size() > 0 && ordersList != null) {
            int billAmount = currentOrdersList.size();
            System.out.println((billAmount + 1) + ". Pay All");
            System.out.println((billAmount + 2) + ". Cancel");
            System.out.println("Enter a number to pay the corresponding bill: ");
            int userInputInt = inputScanner.nextInt();
            inputScanner.nextLine();

            if (userInputInt == (billAmount + 1)) {
                for(String ordersId : currentOrdersList){
                    for (Order orders : ordersList){
                        if(orders.getOrderID().equalsIgnoreCase(ordersId)){
                            orders.setPaymentStatus("Paid");
                        }
                    }
                }
                System.out.println("All bills have been paid!");
            }else if( (userInputInt > (billAmount + 2)) || (userInputInt <= 0) ){
                System.out.println("Invalid Input");
            } else if ( (userInputInt < (billAmount + 1))) {
                int cnt = 1;
                for(String ordersId : currentOrdersList){
                    if(cnt == userInputInt){
                        for (Order orders : ordersList){
                            if(orders.getPaymentStatus().equalsIgnoreCase("paid")){
                                System.out.println("This bill has already been paid!");
                                break;
                            }
                            if(orders.getOrderID().equalsIgnoreCase(ordersId)){
                                orders.setPaymentStatus("Paid");
                            }
                        }
                        break;
                    }
                    cnt += 1;
                }
            }
            updateOrder(ordersList);
        }
    }
}