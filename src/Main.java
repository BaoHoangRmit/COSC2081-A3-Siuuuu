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

public class Main {

    public static void main(String[] args) {
        System.out.println("\n----- WELCOME SCREEN -----");
        System.out.println("COSC2081 GROUP ASSIGNMENT\n" +
                "STORE ORDER MANAGEMENT SYSTEM\n" +
                "Instructor: Mr. Tom Huynh & Dr. Phong Ngo\n" +
                "Group: Siuuuu\n" +
                "s3926050, Hoang Quoc Bao\n" +
                "s3938338, Nguyen Thien Co\n" +
                "s3927474, Hoang Vinh Khue\n" +
                "s3924594, Nguyen Dang Ha");
        System.out.println("----- WELCOME SCREEN -----");

        System.out.println();

        SystemUtils.printLoginMenu();
        SystemUtils.checkLoginMenuInput();
    }
}