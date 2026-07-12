/*
 * CartItem.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Pairs one Product with a quantity inside the shopping cart,
 * so the same game can be purchased more than once without
 * storing duplicate entries.
 */

public class CartItem {

    private final Product product;
    private int quantity;

    // Constructor links a product to its starting quantity
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // Increases the quantity when the same product is added again
    public void addQuantity(int amount) {
        quantity += amount;
    }

    // Line total for this item (price x quantity)
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    // Example output: "2 x Elden Ring (PC) - $59.99 = $119.98"
    @Override
    public String toString() {
        return String.format("%d x %s = $%.2f", quantity, product, getSubtotal());
    }
}
