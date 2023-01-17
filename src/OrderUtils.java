import java.util.ArrayList;

public class OrderUtils {
    static String genOrderId(){
        ArrayList<Order> ordersList = SystemFile.viewOrdersList();
        int numOrderId = 0;
        String orderId;
        if (ordersList == null) {
            ordersList = new ArrayList<>();
            orderId = "B0";
        } else {
            for (Order orders : ordersList) {
                numOrderId = Integer.parseInt((orders.getOrderID()).substring(1));
            }
            numOrderId += 1;
            orderId = String.format("B%d", numOrderId);
        }
        return orderId;
    }

    static ArrayList<String> viewOrder() {
        ArrayList<Order> ordersList = SystemFile.viewOrdersList();
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
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
        }
        return ordersId;
    }

    static ArrayList<String> viewOrderDetail() {
        ArrayList<Order> ordersList = SystemFile.viewOrdersList();
        ArrayList<String> currentOrdersList = new ArrayList<>();
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
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
                double oPrice = 0;
                int orderCnt = 1;
                String pStatus = "";
                String oStatus = "";
                boolean printDate = true;
                System.out.println(ordersId.size() + " Bill(s) Found!");
                for (String orderId : ordersId) {
                    System.out.print("\n");
                    System.out.println(orderCnt + "." + orderId + ":");
                    orderCnt += 1;
                    printDate = true;
                    for (Order orders : ordersList) {
                        if ((orders.getOrderID()).equalsIgnoreCase(orderId)) {
                            if(printDate){
                                System.out.println("Date: " + orders.getOrderDate());
                                printDate = false;
                            }
                            oPrice = orders.getOrderPrice();
                            pStatus = orders.getPaymentStatus();
                            oStatus = orders.getOrderStatus();
                            System.out.println(String.format("%s, Amount: %d, Price: %.4f", orders.getProductName(), orders.getProductAmount(), orders.getProductPrice()));
                        }
                    }
                    System.out.print("\n");
                    System.out.println(String.format("Order Price: %.4f", oPrice));
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
}
