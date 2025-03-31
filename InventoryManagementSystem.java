import java.io.*;
import java.util.*;

public class InventoryManagementSystem {

    private static final String FILE_NAME = "Inventory.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menuDisplay();
    }

    public static void menuDisplay() {
        while (true) {
            System.out.println("===============================");
            System.out.println("= Inventory Management System =");
            System.out.println("===============================");
            System.out.println("(1) Add New Item");
            System.out.println("(2) Remove Item");
            System.out.println("(3) Update Item");
            System.out.println("(4) Search Item");
            System.out.println("(5) Print Inventory Report");
            System.out.println("(6) Quit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            menuSelection(choice);
        }
    }

    public static void menuSelection(int choice) {
        switch (choice) {
            case 1 -> addInventory();
            case 2 -> removeInventory();
            case 3 -> updateInventory();
            case 4 -> searchInventory();
            case 5 -> printInventory();
            case 6 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void addInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            System.out.println("Adding Inventory");
            System.out.println("================");
            System.out.print("Enter the name of the item: ");
            String itemDescription = scanner.nextLine();
            System.out.print("Enter the quantity of the item: ");
            int itemQuantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            writer.write(itemDescription);
            writer.newLine();
            writer.write(String.valueOf(itemQuantity));
            writer.newLine();

            System.out.print("Enter y to continue or n to exit: ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                System.exit(0);
            }

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void removeInventory() {
        System.out.println("Removing Inventory");
        System.out.println("==================");
        System.out.print("Enter the item name to remove from inventory: ");
        String itemDescription = scanner.nextLine();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(itemDescription)) {
                    reader.readLine(); // Skip the quantity
                    found = true;
                } else {
                    lines.add(line);
                }
            }

            if (!found) {
                System.out.println("Item not found.");
                return;
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }

        System.out.print("Enter y to continue or n to exit: ");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    public static void updateInventory() {
        System.out.println("Updating Inventory");
        System.out.println("==================");
        System.out.print("Enter the item to update: ");
        String itemDescription = scanner.nextLine();
        System.out.print("Enter the updated quantity (use - for decrease): ");
        int itemQuantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(itemDescription)) {
                    lines.add(line);
                    int existingQuantity = Integer.parseInt(reader.readLine());
                    int updatedQuantity = existingQuantity + itemQuantity;
                    lines.add(String.valueOf(updatedQuantity));
                    found = true;
                } else {
                    lines.add(line);
                }
            }

            if (!found) {
                System.out.println("Item not found.");
                return;
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating file: " + e.getMessage());
        }

        System.out.print("Enter y to continue or n to exit: ");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    public static void searchInventory() {
        System.out.println("Searching Inventory");
        System.out.println("===================");
        System.out.print("Enter the name of the item: ");
        String itemDescription = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(itemDescription)) {
                    System.out.println("Item:     " + line);
                    System.out.println("Quantity: " + reader.readLine());
                    System.out.println("----------");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Item not found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.print("Enter y to continue or n to exit: ");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }

    public static void printInventory() {
        System.out.println("Current Inventory");
        System.out.println("-----------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String itemDescription;

            while ((itemDescription = reader.readLine()) != null) {
                String itemQuantity = reader.readLine();
                System.out.println("Item:     " + itemDescription);
                System.out.println("Quantity: " + itemQuantity);
                System.out.println("----------");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        System.out.print("Enter y to continue or n to exit: ");
        if (scanner.nextLine().equalsIgnoreCase("n")) {
            System.exit(0);
        }
    }
}
