/*
 * Order.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Represents one completed purchase: who bought what, how they
 * paid, and the total. Order numbers come from a static counter
 * shared by every Order object.
 */
import java.util.ArrayList;

public class Order {

    // Static: one counter shared across all orders
    private static int nextOrderNumber = 1000;

    private final int orderNumber;
    private final Customer customer;
    private final ArrayList<CartItem> items;
    private final double total;
    private final Payment payment;

    public Order(Customer customer, ShoppingCart cart, Payment payment) {
        this.orderNumber = nextOrderNumber++;
        this.customer = customer;
        // Copy the cart items so clearing the cart later
        // does not erase this order's record of the purchase
        this.items = new ArrayList<>(cart.getItems());
        this.total = cart.getTotal();
        this.payment = payment;
    }

    // Polymorphic call: Java runs whichever processPayment()
    // belongs to the concrete payment type stored in this order
    public boolean process() {
        return payment.processPayment(total);
    }

    // Builds the text receipt shown after a successful checkout
    public String getReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderNumber).append("\n");
        sb.append("Customer: ").append(customer.getName()).append("\n\n");
        for (CartItem item : items) {
            sb.append(item).append("\n");
        }
        sb.append(String.format("%nTotal: $%.2f%n", total));
        sb.append("Paid with: ").append(payment.getPaymentDetails());
        return sb.toString();
    }
}