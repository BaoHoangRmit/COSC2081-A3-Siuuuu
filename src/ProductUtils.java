import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProductUtils {

    static ArrayList<Category> viewCategoryList() {
        try {
            Scanner fileScanner = new Scanner((new File("category.txt")));
            ArrayList<Category> categoryList = new ArrayList<Category>();
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

    static Category viewCategoryByID(String id) {
        ArrayList<Category> categories = ProductUtils.viewCategoryList();

        if (categories != null) {
            for (Category category: categories) {
                if (id.equals(category.getCategoryID())) {
                    return category;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    static Category viewCategoryByName(String name) {
        ArrayList<Category> categories = ProductUtils.viewCategoryList();

        if (categories != null) {
            for (Category category: categories) {
                if (name.equals(category.getCategoryName())) {
                    return category;
                }
            }
        } else {
            return null;
        }
        return null;
    }
}
