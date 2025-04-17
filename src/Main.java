import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String[][] stock = new String[0][0];
    private static String[][][] history;
    private static String whenCreate;

    public static int inputOption() {
        while (true) {
            System.out.print("Input option:\t");
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static int promptForInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static void makeTable() {
        int row = promptForInt("[+] Input number of Stock: ");
        stock = new String[row][];
        history = new String[row][][];
        for (int i = 0; i < row; i++) {
            int col = promptForInt("[+] Input number of Catalogue for Stock " + (i + 1) + ": ");
            stock[i] = new String[col];
            history[i] = new String[col][2];
        }
        Arrays.stream(stock).forEach(r -> Arrays.fill(r, "EMPTY"));
        whenCreate = Date.from(Instant.now()).toString();
    }

    public static void inputValue() {
        int row = promptForInt("Input Stock number: ") - 1;
        if (row < 0 || row >= stock.length) {
            System.out.println("Invalid Stock number.");
            return;
        }
        viewWhenInput(row);
        int col = promptForInt("Input Catalogue number: ") - 1;
        if (col >= 0 && col < stock[row].length) {
            System.out.print("Input Product Name: ");
            String product = scanner.nextLine();
            stock[row][col] = product;
            history[row][col][0] = "Inserted";
            history[row][col][1] = Date.from(Instant.now()).toString();
        } else {
            System.out.println("Invalid Catalogue number.");
        }
    }

    public static void update() {
        int row = promptForInt("Input Stock number: ") - 1;
        if (row < 0 || row >= stock.length) {
            System.out.println("Invalid Stock number.");
            return;
        }
        for (int j = 0; j < stock[row].length; j++) {
            System.out.print("\t" + (j + 1) + ".[ " + stock[row][j] + " ]");
        }
        System.out.println();
        int col = promptForInt("Input Catalogue number: ") - 1;
        if (col >= 0 && col < stock[row].length) {
            System.out.print("Input New Product Name: ");
            String product = scanner.nextLine();
            stock[row][col] = product;
            history[row][col][0] = "Updated";
            history[row][col][1] = Date.from(Instant.now()).toString();
        } else {
            System.out.println("Invalid Catalogue number.");
        }
    }

    public static void deleteValue() {
        int row = promptForInt("Input Stock number: ") - 1;
        if (row < 0 || row >= stock.length) {
            System.out.println("Invalid Stock number.");
            return;
        }
        for (int j = 0; j < stock[row].length; j++) {
            System.out.print("\t" + (j + 1) + ".[ " + stock[row][j] + " ]");
        }
        System.out.println();
        int col = promptForInt("Input Catalogue number: ") - 1;
        if (col >= 0 && col < stock[row].length) {
            if (!Objects.equals(stock[row][col], "EMPTY")) {
                stock[row][col] = "EMPTY";
                history[row][col][0] = "Deleted";
                history[row][col][1] = Date.from(Instant.now()).toString();
            } else {
                System.out.println("Already EMPTY.");
            }
        } else {
            System.out.println("Invalid Catalogue number.");
        }
    }

    public static void viewWhenInput(int row) {
        for (int i = 0; i < stock[row].length; i++) {
            if (Objects.equals(stock[row][i], "EMPTY")) {
                System.out.print((i + 1) + "|");
            }
        }
        System.out.println();
    }

    public static void viewTable() {
        for (int i = 0; i < stock.length; i++) {
            int filledCount = 0;
            System.out.print("Stock [" + (i + 1) + "] ->");
            for (int j = 0; j < stock[i].length; j++) {
                System.out.print("\t" + (j + 1) + ".[ " + stock[i][j] + " ]");
                if (!Objects.equals(stock[i][j], "EMPTY")) {
                    filledCount++;
                }
            }
            if (filledCount == stock[i].length && stock[i].length > 0) {
                System.out.print(" -> full");
            }
            System.out.println();
        }
    }

    public static void viewInsertUpdateHistory() {
        for (int i = 0; i < history.length; i++) {
            for (int j = 0; j < history[i].length; j++) {
                String action = history[i][j][0];
                String time = history[i][j][1];
                if (action != null) {
                    String product = stock[i][j];
                    if (!action.equals("Deleted")) {
                        if (!Objects.equals(product, "EMPTY")) {
                            System.out.println("Stock[" + (i + 1) + "], Catalogue[" + (j + 1) + "] -> " + product);
                            System.out.println("    - " + action + " at " + time);
                        }
                    } else {
                        System.out.println("Stock[" + (i + 1) + "], Catalogue[" + (j + 1) + "] -> EMPTY");
                        System.out.println("    - Deleted at " + time);
                    }
                }
            }
        }
    }

    public static void exit() {
        System.out.println("Exiting program...");
        System.exit(0);
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("""
                    Welcome to JavaProduct Stock Program
                    1. Set Up Stock with Catalogue
                    2. View Product in Stock
                    3. Insert Product to Stock Catalogue
                    4. Update Product in Stock Catalogue by Product name
                    5. Delete Product in Stock Catalogue by Name
                    6. View Insertion History in Stock Catalogue
                    7. Exit
                    """);
            int option = inputOption();
            switch (option) {
                case 1 -> {
                    System.out.println("1. Welcome to Set Up Stock with Catalogue");
                    makeTable();
                    System.out.println("\nAt Time : " + whenCreate);
                    viewTable();
                }
                case 2 -> {
                    System.out.println("2. Welcome to View Product in Stock");
                    viewTable();
                }
                case 3 -> {
                    System.out.println("3. Welcome to Insert Product to Stock Catalogue ");
                    inputValue();
                    viewTable();
                }
                case 4 -> {
                    System.out.println("4. Welcome to Update Product in Stock Catalogue by Product name");
                    update();
                    viewTable();
                }
                case 5 -> {
                    System.out.println("5. Welcome to Update Product in Stock Catalogue by Product name");
                    deleteValue();
                    viewTable();
                }
                case 6 -> {
                    System.out.println("6. Welcome to View Insertion History in Stock Catalogue");
                    boolean open = true;
                    while (open) {
                        System.out.println("""
                                History For Stock Table
                                1. Created Time
                                2. View Insert/Update/Delete History
                                3. Exit History View
                                """);
                        int optionForHistory = inputOption();
                        switch (optionForHistory) {
                            case 1 -> System.out.println("The Table was Created At: " + whenCreate);
                            case 2 -> viewInsertUpdateHistory();
                            case 3 -> {
                                System.out.println("Exiting History...");
                                Thread.sleep(300);
                                open = false;
                            }
                        }
                    }
                }
                case 7 -> exit();
                default -> System.out.println("Invalid option. Please select from 1 to 7.");
            }
        }
    }
}
