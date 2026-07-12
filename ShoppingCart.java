/*
 * ShoppingCart.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Manages the list of CartItems the customer has selected.
 * Demonstrates composition: a cart is made up of cart items,
 * and each cart item wraps a product.
 */
import java.util.ArrayList;

public class ShoppingCart {

    private final ArrayList<CartItem> items = new ArrayList<>();

    // Adds a product to the cart. If it is already in the cart,
    // the quantity is increased instead of adding a duplicate entry.
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.addQuantity(quantity);
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Removes a product from the cart entirely by its id
    public void removeProduct(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    // Returns how many of a given product are already in the cart,
    // used to make sure the customer cannot exceed available stock
    public int getQuantity(int productId) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    // Sum of every item's subtotal
    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public ArrayList<CartItem> getItems() { return items; }

    public boolean isEmpty() { return items.isEmpty(); }

    // Empties the cart after a successful checkout
    public void clear() { items.clear() ;}
}