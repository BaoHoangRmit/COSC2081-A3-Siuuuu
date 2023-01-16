import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ProductUtils {
    //Category utilities

    static void viewCategoryByID(String id) {
        ArrayList<Category> categories = SystemFile.viewCategoryList();
        ArrayList<Category> sortedCategories = new ArrayList<>();

        if (categories != null) {
            for (Category category: categories) {
                if (id.equalsIgnoreCase(category.getCategoryID())) {
                    sortedCategories.add(category);
                }
            }

            if (!sortedCategories.isEmpty()) {
                ProductUtils.printListOfCategories(sortedCategories);
            } else {
                System.out.println("Cannot find any categories with the ID: "+id+"\n");
            }
        }

    }

    static void viewCategoryByName(String name) {
        ArrayList<Category> categories = SystemFile.viewCategoryList();
        ArrayList<Category> sortedCategories = new ArrayList<>();

        if (categories != null) {
            for (Category category: categories) {
                if (name.equalsIgnoreCase(category.getCategoryName())) {
                    sortedCategories.add(category);
                }
            }

            if (!sortedCategories.isEmpty()) {
                ProductUtils.printListOfCategories(sortedCategories);
            } else {
                System.out.println("Cannot find any categories with the name: " +name+"\n");
            }
        }

    }


    //Product utilities

    static void viewProductByID(String id) {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();
        if (products != null) {
            for (Product product: products) {
                if (id.equals(product.getProductID())) {
                    sortedProducts.add(product);
                }
            }
            if (!sortedProducts.isEmpty()) {
                Collections.sort(sortedProducts);
                System.out.println("Products with the ID: " + id);
                ProductUtils.printListOfProducts(sortedProducts);
            } else {
                System.out.println("Cannot find any products with the ID: "+ id + "\n");
            }
        }
    }

    static void viewProductByName(String name) {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            for (Product product: products) {
                if (name.equalsIgnoreCase(product.getProductName())) {
                    sortedProducts.add(product);
                }
            }
            if (!sortedProducts.isEmpty()) {
                Collections.sort(sortedProducts);
                System.out.println("Products with the name: " + name);
                ProductUtils.printListOfProducts(sortedProducts);
            } else {
                System.out.println("Cannot find any products with the name: "+ name + "\n");
            }
        }
    }

    static void viewProductByCategory(String categoryName) {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            for (Product product: products) {
                if (categoryName.equalsIgnoreCase(product.getCategoryName())) {
                    sortedProducts.add(product);
                }
            }
            if (!sortedProducts.isEmpty()) {
                Collections.sort(sortedProducts);
                System.out.println("Products with the category: " + categoryName);
                ProductUtils.printListOfProducts(sortedProducts);
            } else {
                System.out.println("Cannot find any products with the category: "+categoryName+"\n");
            }
        }
    }

    static void sortProductByPriceRange(double min, double max) {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            if (max >= min) {
                for (Product product: products) {
                    double price = product.getProductPrice();
                    if (price >= min && price <= max) {
                        sortedProducts.add(product);
                    }
                }
                if (!sortedProducts.isEmpty()) {
                    Collections.sort(sortedProducts);
                    System.out.printf("Products with the price range %.2f - %.2f \n", min, max);
                    ProductUtils.printListOfProducts(sortedProducts);
                } else {
                    System.out.println("Cannot find any products in that price range: \n");
                }
            } else {
                System.out.println("Invalid price range! \n");
            }
        }
    }

    static void sortAllProductByPriceAsc() {
        ArrayList<Product> products = SystemFile.viewProductList();

        if (products != null) {
            Collections.sort(products);
            System.out.println("All products sorted by price ascendant order: ");
            ProductUtils.printListOfProducts(products);
        }
    }

    static void sortAllProductByPriceDes() {
        ArrayList<Product> products = SystemFile.viewProductList();

        if (products != null) {
            Collections.sort(products);
            Collections.reverse(products);
            System.out.println("All products sorted by price descendant order: ");
            ProductUtils.printListOfProducts(products);
        }
    }

    static void viewMostPopularProducts() {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            int maxSaleNum = 0;
            for (Product product: products) {
                int saleNum = product.getSaleNumber();
                if (saleNum >= maxSaleNum) {
                    maxSaleNum = saleNum;
                }
            }
            for (Product product: products) {
                if (product.getSaleNumber() == maxSaleNum) {
                    sortedProducts.add(product);
                }
            }

            System.out.println("Most popular products with " + maxSaleNum + " items sold \n");
            ProductUtils.printListOfProducts(sortedProducts);
        }
    }

    static void viewLeastPopularProducts() {
        ArrayList<Product> products = SystemFile.viewProductList();
        ArrayList<Product> sortedProducts = new ArrayList<>();

        if (products != null) {
            Product firstProduct = products.get(0);
            int minSaleNum = firstProduct.getSaleNumber();

            for (Product product: products) {
                int saleNum = product.getSaleNumber();
                if (saleNum <= minSaleNum) {
                    minSaleNum = saleNum;
                }
            }

            for (Product product: products) {
                if (product.getSaleNumber() == minSaleNum) {
                    sortedProducts.add(product);
                }
            }

            System.out.println("Least popular products with " + minSaleNum + " items sold \n");
            ProductUtils.printListOfProducts(sortedProducts);
        }
    }

    //This method will print a given arraylist of product
    static void printListOfProducts(ArrayList<Product> products) {
        System.out.println("ID | Name | Price | Category");
        for (Product product: products) {
            System.out.println(product);
        }
        System.out.println("");
    }

    static void printListOfCategories(ArrayList<Category> categories) {
        System.out.println("ID | Name");
        for (Category category: categories) {
            System.out.println(category);
        }
        System.out.println("");
    }
}