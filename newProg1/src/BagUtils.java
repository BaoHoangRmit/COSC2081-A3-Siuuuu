import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BagUtils {
    static void updateBag(ArrayList<Bag> bagsList){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("data/bag.txt", false));
            pw.println("#customerID,ProductID,ProductName,Amount,Price");
            for(Bag bag : bagsList){
                pw.println(String.format("%s,%s,%s,%d,%.4f", bag.getCustomerID(), bag.getProductID(), bag.getProductName(), bag.getProductAmount(), bag.getProductPrice()));
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred (updateBag)!");
        }
    }

    static ArrayList<Bag> viewBag(){
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();
        ArrayList<Bag> currentBagsList = new ArrayList<Bag>();
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
        String currentCustomerId = "";

        if(currentCustomer != null){
            currentCustomerId = currentCustomer.getCustomerID();
        }

        if (bagsList != null) {
            for(Bag item : bagsList){
                if(item.getCustomerID().equalsIgnoreCase(currentCustomerId)){
                    currentBagsList.add(item);
                }
            }
        }
        return currentBagsList;
    }

    static ArrayList<Bag> viewBagDetail(){
        ArrayList<Bag> bagsList = viewBag();
        double totalPrice = 0;
        int totalAmount = 0;
        int cnt = 0;

        if(bagsList.size() > 0){
            for(Bag item : bagsList){
                cnt += 1;
                totalAmount += item.getProductAmount();
                totalPrice += item.getProductPrice();
                System.out.println(String.format("%d. %s: Amount: %d, Price: %.4f", cnt, item.getProductName() ,item.getProductAmount(), item.getProductPrice()));
            }

            if(totalAmount == 1){
                System.out.println("Total: " + totalAmount + " item.");
                System.out.println(String.format("Total Price: %.4f", totalPrice));
            }else {
                System.out.println("Total: " + totalAmount + " items.");
                System.out.println(String.format("Total Price: %.4f", totalPrice));
            }
        }else{
            System.out.println("Your bag currently has 0 item.");
        }

        return bagsList;
    }

    static ArrayList<Bag> mergeBags(ArrayList<Bag> bag, ArrayList<Bag> bagMain){
        ArrayList<Bag> bags1 = new ArrayList<>(bag);
        ArrayList<Bag> bags2 = new ArrayList<>(bagMain);

        String bag1CId, bag1PId, bag2CId, bag2PId;

        for(Bag bag1 : bags1){
            bag1CId = bag1.getCustomerID();
            bag1PId = bag1.getProductID();
            for(Bag bag2 : bags2){
                bag2CId = bag2.getCustomerID();
                bag2PId = bag2.getProductID();
                if(bag2CId.equalsIgnoreCase(bag1CId) && bag2PId.equalsIgnoreCase(bag1PId)){
                    bag2.setProductPrice(bag1.getProductPrice());
                    bag2.setProductAmount(bag1.getProductAmount());
                }
            }
        }
        return bags2;
    }
}
