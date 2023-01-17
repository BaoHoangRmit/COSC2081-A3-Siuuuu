public class Order {
    final String orderDate;
    private final String userID;
    private final String orderID;
    private final String productName;
    private final int productAmount;
    private final double productPrice;
    private final double orderPrice;
    private String paymentStatus;
    private String orderStatus;


    public Order(String orderDate, String userID, String orderID, String productName, int productAmount, double productPrice, double orderPrice) {
        this.orderDate = orderDate;
        this.userID = userID;
        this.orderID = orderID;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.orderPrice = orderPrice;
        this.paymentStatus = "Unpaid";
        this.orderStatus = "Delivering";
    }

    public Order(String orderDate, String userID, String orderID, String productName, int productAmount, double productPrice, double orderPrice, String paymentStatus, String orderStatus) {
        this.orderDate = orderDate;
        this.userID = userID;
        this.orderID = orderID;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
        this.orderPrice = orderPrice;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getUserID() {
        return userID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}