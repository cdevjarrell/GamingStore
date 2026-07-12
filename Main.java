/*
 * Main.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Entry point for the application. Builds the sample product
 * catalog and customer, then launches the store window.
 */
import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Sample catalog the store opens with
        ArrayList<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1, "Elden Ring", "PC", 59.99, 8));
        catalog.add(new Product(2, "Mario Kart 8 Deluxe", "Switch", 49.99, 5));
        catalog.add(new Product(3, "God of War Ragnarok", "PS5", 69.99, 4));
        catalog.add(new Product(4, "Halo Infinite", "Xbox", 39.99, 6));
        catalog.add(new Product(5, "Stardew Valley", "PC", 14.99, 12));
        catalog.add(new Product(6, "Zelda: Tears of the Kingdom", "Switch", 69.99, 3));

        // The customer whose name and email appear on receipts
        Customer customer = new Customer(1, "Christian", "christian@email.com");

        // Swing components should be created on the Event Dispatch Thread
        SwingUtilities.invokeLater(
                () -> new GamingStoreGUI(catalog, customer).setVisible(true));
    }
}