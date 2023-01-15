import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ProductUtils {

    static ArrayList<Category> viewCategoryList() {
        try {
            Scanner fileScanner = new Scanner((new File("category.txt")));
            ArrayList<Category> categoryList = new ArrayList<>();
            String line;

            while (fileScanner.hasNext()){
                line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 2) {
                    throw new IOException("Invalid Input Format");
                } else {
                    String fileCategoryID = inReader.nextToken();
                    String fileCategoryName = inReader.nextToken();

                    categoryList.add(new Category(fileCategoryID, fileCategoryName));
                }
            }

            fileScanner.close();

            return categoryList;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static void viewCategoryByID(String id) {
        ArrayList<Category> categories = ProductUtils.viewCategoryList();
        ArrayList<Category> sortedCategories = new ArrayList<>();

        if (categories != null) {
            for (Category category: categories) {
                if (id.equals(category.getCategoryID())) {
                    sortedCategories.add(category);
                }
            }

            if (!sortedCategories.isEmpty()) {
                for (Category category: sortedCategories) {
                    System.out.println(category);
                }
            } else {
                System.out.println("Cannot find any categories with the ID: "+id);
            }
        }

    }

    static void viewCategoryByName(String name) {
        ArrayList<Category> categories = ProductUtils.viewCategoryList();
        ArrayList<Category> sortedCategories = new ArrayList<>();

        if (categories != null) {
            for (Category category: categories) {
                if (name.equals(category.getCategoryName())) {
                    sortedCategories.add(category);
                }
            }

            if (!sortedCategories.isEmpty()) {
                for (Category category: sortedCategories) {
                    System.out.println(category);
                }
            } else {
                System.out.println("Cannot find any categories with the name: " +name);
            }
        }

    }

    static ArrayList<Product> viewProductList() {
        try {
            Scanner fileScanner = new Scanner((new File("product.txt")));
            ArrayList<Product> productsList = new ArrayList<Product>();

            fileScanner.nextLine();

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                StringTokenizer inReader = new StringTokenizer(line, ",");

                if (inReader.countTokens() != 7) {
                    throw new IOException("Invalid Input Format (product)");
                } else {
                    // get each string seperated by ","
                    String productId = inReader.nextToken();
                    String productName = inReader.nextToken();
                    double productPrice = Double.parseDouble(inReader.nextToken());
                    String productDesc = inReader.nextToken();
                    int productSaleNumber = Integer.parseInt(inReader.nextToken());
                    String productCategoryId= inReader.nextToken();
                    String productCategoryName = inReader.nextToken();

                    // add customers from txt into list
                    productsList.add(new Product(productId, productName, productPrice, productDesc, productSaleNumber, productCategoryId, productCategoryName));
                }
            }

            fileScanner.close();

            return productsList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    static void viewProductByCategory(String categoryName) {
        ArrayList<Product> products = ProductUtils.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            for (Product product: products) {
                if (categoryName.equals(product.getCategoryName())) {
                    sortedProducts.add(product);
                }
            }
            if (!sortedProducts.isEmpty()) {
                Collections.sort(sortedProducts);
                System.out.println("Products with the category: " + categoryName);
                for (Product product: sortedProducts) {
                    System.out.println(product);
                }
            } else {
                System.out.println("Cannot find any products with the category: "+categoryName);
            }
        }
    }

    static void sortProductByPriceRange(double min, double max) {
        ArrayList<Product> products = ProductUtils.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            for (Product product: products) {
                double price = product.getProductPrice();
                if (price >= min && price <= max) {
                    sortedProducts.add(product);
                }
            }
            if (!sortedProducts.isEmpty()) {
                Collections.sort(sortedProducts);
                System.out.printf("Products with the price range %.2f - %.2f \n", min, max);
                for (Product product: sortedProducts) {
                    System.out.println(product);
                }
            } else {
                System.out.println("Cannot find any products in that price range");
            }
        }
    }

    static void sortAllProductByPriceAsc() {
        ArrayList<Product> products = ProductUtils.viewProductList();

        if (products != null) {
            Collections.sort(products);
            System.out.println("All products sorted by price ascendant order: ");
            for (Product product: products) {
                System.out.println(product);
            }
        }
    }

}
