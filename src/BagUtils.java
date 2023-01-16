import java.io.*;
import java.util.*;

public class BagUtils {
    static ArrayList<Bag> viewBagsList() {
        try {
            Scanner fileScanner = new Scanner((new File("bag.txt")));
            ArrayList<Bag> bagsList = new ArrayList<Bag>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 5) {
                    throw new IOException("Invalid Input Format (bag)");
                } else {
                    // get each string seperated by ","

                    String customerId = inReader.nextToken();
                    String productId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    int productAmount = Integer.parseInt(inReader.nextToken());
                    double productPrice = Double.parseDouble(inReader.nextToken());

                    bagsList.add(new Bag(customerId, productId, productName, productAmount, productPrice));
                }
            }

            fileScanner.close();

            return bagsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found (bag)");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch(NoSuchElementException e){
            return null;
        }
        return null;
    }

    static void updateBag(ArrayList<Bag> bagsList){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("bag.txt", false));
            pw.println("#customerID,ProductID,ProductName,Amount,Price");
            for(Bag bag : bagsList){
                pw.println(String.format("%s,%s,%s,%d,%.4f", bag.getCustomerID(), bag.getProductID(), bag.getProductName(), bag.getProductAmount(), bag.getProductPrice()));
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("An error occurred (updateBag)!");
        }
    }

    static ArrayList<Bag> viewBagDetail(){
        ArrayList<Bag> bagsList = viewBag();
        double totalPrice = getBagTotal();
        int totalAmount = 0;
        int cnt = 0;

        if(bagsList.size() > 0){
            for(Bag item : bagsList){
                cnt += 1;
                totalAmount += item.getProductAmount();
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
    static ArrayList<Bag> viewBag(){
        ArrayList<Bag> bagsList = viewBagsList();
        ArrayList<Bag> currentBagsList = new ArrayList<Bag>();
        Customer currentCustomer = SystemFile.viewCurrentCustomer();
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

    static double getBagTotal(){
        ArrayList<Bag> bagsList = viewBagsList();
        ArrayList<Bag> currentBagsList = new ArrayList<Bag>();
        Customer currentCustomer = SystemFile.viewCurrentCustomer();
        String currentCustomerId = "";
        double totalPrice = 0;

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
        }
        return totalPrice;
    }

    static void checkBag(){
        ArrayList<Bag> bagsList = viewBagsList();
        ArrayList<Bag> bagChangesList = new ArrayList<Bag>();
        ArrayList<Product> productsList = SystemFile.viewProductList();
        Customer currentCustomer = SystemFile.viewCurrentCustomer();

        boolean checkBaginProduct;
        int changeCnt = 0;
        if(currentCustomer != null){
            if (bagsList != null) {
                for(Bag cart : bagsList){
                    checkBaginProduct = true;
                    if (productsList != null) {
                        for(Product item : productsList){
                            if ( ( (cart.getProductID()).equalsIgnoreCase( (item.getProductID()) ) ) ) {
                                checkBaginProduct = false;
                                break;
                            }
                        }
                        if( ( (cart.getCustomerID()).equalsIgnoreCase( (currentCustomer.getCustomerID()) ) ) && checkBaginProduct ){
                            changeCnt += 1;
                            bagChangesList.add(cart);
                        }
                    }else{
                        for(Bag cart2 : bagsList){
                            if( (cart2.getCustomerID()).equalsIgnoreCase( (currentCustomer.getCustomerID()) ) ){
                                changeCnt += 1;
                                bagChangesList.add(cart2);
                            }
                        }
                    }
                }
            }
        }

        if(changeCnt > 0){
            System.out.println("----- Welcome Back -----");
            System.out.print("\n");
            System.out.println("----- Bag Update -----");
            System.out.println("Some items have been removed by either application administrator or product owner.");
            System.out.println(changeCnt + " item(s) will be removed from your Bag:");

            for(Bag cart : bagChangesList){
                System.out.println(cart);
                bagsList.remove(cart);
            }

            updateBag(bagsList);
        }
    }
}
