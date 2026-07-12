/*
 * Product.java
 * Gaming Store Online Shopping System - CS3700 Object Orientation
 *
 * Represents a single game product available in the store.
 * Each product tracks its id, title, platform, price, and stock level.
 * Stock is reduced as customers complete purchases.
 */
public class Product {

    // Fields are private to enforce encapsulation
    private final int id;
    private final String name;
    private final String platform;
    private final double price;
    private int stock;

    // Constructor initializes all product details
    public Product(int id, String name, String platform, double price, int stock) {
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.price = price;
        this.stock = stock;
    }

    // Getters provide read-only access to the private fields
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPlatform() { return platform; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // Returns true if at least the requested quantity is available
    public boolean isInStock(int quantity) {
        return stock >= quantity;
    }

    // Reduces stock after a successful purchase
    public void reduceStock(int quantity) {
        if (isInStock(quantity)) {
            stock -= quantity;
        }
    }

    // Readable description used by the cart display and receipt
    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f", name, platform, price);
    }
}