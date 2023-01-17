/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3 (Group Project)

  Author & ID:
  Hoang Quoc Bao s3926050
  Nguyen Thien Co s3938338
  Hoang Vinh Khue s3927474
  Nguyen Dang Ha s3924594

  Acknowledgement: None.
*/

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
        int dst = 100;
        int cnt = 0;
        String mbs = "None";
        Customer curCus = UserUtils.viewCurrentCustomer();
        if(curCus != null){
            mbs = curCus.getMembership();
            if(mbs.equalsIgnoreCase("silver")){
                dst = 90;
            } else if (mbs.equalsIgnoreCase("gold")) {
                dst = 85;
            } else if (mbs.equalsIgnoreCase("diamond")) {
                dst = 80;
            }
        }

        if(bagsList.size() > 0){
            for(Bag item : bagsList){
                cnt += 1;
                totalAmount += item.getProductAmount();
                totalPrice += item.getProductPrice();
                System.out.println(String.format("%d. %s: Amount: %d, Price: %.4f", cnt, item.getProductName() ,item.getProductAmount(), item.getProductPrice()));
            }

            System.out.print("\n");

            if(totalAmount == 1){
                System.out.println("Total: " + totalAmount + " item.");
                System.out.println(String.format("Total Price: %.4f", totalPrice));
                if(dst != 100){
                    System.out.println(String.format("Membership Discount: %d%%", (100-dst)));
                    System.out.println(String.format("Final Price: %.4f", (totalPrice * dst / 100)));
                }
            }else {
                System.out.println("Total: " + totalAmount + " items.");
                System.out.println(String.format("Total Price: %.4f", totalPrice));
                System.out.println(String.format("Membership Discount: %d%%", (100-dst)));
                System.out.println(String.format("Final Price: %.4f", (totalPrice * dst / 100)));
            }
        }else{
            System.out.println("Your bag currently has 0 item.");
        }

        return bagsList;
    }

    static double getBagTotal(){
        ArrayList<Bag> bagsList = SystemFile.viewBagsList();
        ArrayList<Bag> currentBagsList = new ArrayList<Bag>();
        Customer currentCustomer = UserUtils.viewCurrentCustomer();
        String currentCustomerId = "";
        double totalPrice = 0;

        int dst = 100;
        String mbs = "None";
        Customer curCus = UserUtils.viewCurrentCustomer();
        if(curCus != null){
            mbs = curCus.getMembership();
            if(mbs.equalsIgnoreCase("silver")){
                dst = 90;
            } else if (mbs.equalsIgnoreCase("gold")) {
                dst = 85;
            } else if (mbs.equalsIgnoreCase("diamond")) {
                dst = 80;
            }
        }

        if(currentCustomer != null){
            currentCustomerId = currentCustomer.getCustomerID();
        }

        if (bagsList != null) {
            for(Bag item : bagsList){
                if(item.getCustomerID().equalsIgnoreCase(currentCustomerId)){
                    currentBagsList.add(item);
                }
            }
            for(Bag item : currentBagsList){
                if(item.getCustomerID().equalsIgnoreCase(currentCustomerId)){
                    totalPrice += item.getProductPrice();
                }
            }
            totalPrice = totalPrice * dst / 100;
        }
        return totalPrice;
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

    static void announceBagChange(){
        Customer curCus = UserUtils.viewCurrentCustomer();
        ArrayList<Bag> curBags = viewBag();

        ArrayList<Bag> removeBags = new ArrayList<>();
        ArrayList<Bag> editBags = new ArrayList<>();

        ArrayList<Bag> bagList = SystemFile.viewBagsList();
        ArrayList<Product> proList = SystemFile.viewProductList();

        boolean bagCheck;
        int cnt = 0;

        for(Bag bag : curBags){
            bagCheck = true;
            if (proList != null) {
                for(Product pro : proList){
                    if((bag.getProductID()).equalsIgnoreCase(pro.getProductID())){
                        bagCheck = false;
                        if( (pro.getProductPrice()) * (bag.getProductAmount()) != (bag.getProductPrice()) ){
                            bag.setProductPrice( ((pro.getProductPrice()) * (bag.getProductAmount())) );
                            cnt += 1;
                            editBags.add(bag);
                        }
                    }
                }
                if(bagCheck){
                    cnt += 1;
                    removeBags.add(bag);
                    editBags.add(bag);
                }
            }
        }

        if(cnt > 0){
            System.out.print("\n");
            System.out.println("----- Bag Update -----");
            System.out.println("Bag is updated due to the change of product price or existence!");
            System.out.println(cnt + " item(s) (changes) found in your Bag");
            int i = 0;
            for (Bag bag : editBags) {
                i += 1;
                System.out.println((i) + ". " + (bag.getProductID()) + " - " + (bag.getProductName()));
                i++;

            }

            if(removeBags.size() > 0){
                Bag tmp = null;
                for (Bag bag : removeBags){
                    if (bagList != null) {
                        for(Bag bag2 : bagList){
                            if( (bag2.getProductID()).equalsIgnoreCase(bag.getProductID()) && (bag2.getCustomerID()).equalsIgnoreCase(bag.getCustomerID()) ){
                                tmp = bag2;
                                break;
                            }
                        }
                        bagList.remove(tmp);
                    }

                    for(Bag bag2 : curBags){
                        if( (bag2.getProductID()).equalsIgnoreCase(bag.getProductID()) && (bag2.getCustomerID()).equalsIgnoreCase(bag.getCustomerID()) ){
                            tmp = bag2;
                            break;
                        }
                    }
                    curBags.remove(tmp);
                }
            }

            System.out.println("Please re-check your Bag!");
            System.out.println("----------------------");

            bagList = mergeBags(curBags, bagList);
            updateBag(bagList);
        }
    }
}