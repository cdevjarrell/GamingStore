/*
 * GamingStoreGUI.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Main Swing window for the store. Displays the product catalog
 * in a table, shows the current cart contents on the right, and
 * provides buttons to add items, remove items, and check out
 * with a chosen payment method.
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GamingStoreGUI extends JFrame {

    private final ArrayList<Product> catalog;
    private final Customer customer;
    private final ShoppingCart cart = new ShoppingCart();

    // Components that more than one method needs to update
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextArea cartArea;
    private JLabel totalLabel;

    public GamingStoreGUI(ArrayList<Product> catalog, Customer customer) {
        this.catalog = catalog;
        this.customer = customer;

        // Basic window setup
        setTitle("Gaming Store");
        setSize(750, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            // Center the window on screen
        setLayout(new BorderLayout(10, 10));

        add(buildProductPanel(), BorderLayout.CENTER);
        add(buildCartPanel(), BorderLayout.EAST);
        add(buildButtonPanel(), BorderLayout.SOUTH);
    }

    // ---------- Panel builders ----------

    // Product catalog shown as a read-only table
    private JScrollPane buildProductPanel() {
        String[] columns = {"ID", "Title", "Platform", "Price", "Stock"};

        // Override isCellEditable so the user cannot edit the catalog
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        refreshProductTable();                  // Fill the table with the catalog
        productTable = new JTable(tableModel);
        return new JScrollPane(productTable);
    }

    // Cart contents and running total on the right side
    private JPanel buildCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));
        panel.setPreferredSize(new Dimension(270, 0));

        cartArea = new JTextArea();
        cartArea.setEditable(false);
        panel.add(new JScrollPane(cartArea), BorderLayout.CENTER);

        totalLabel = new JLabel("Total: $0.00");
        panel.add(totalLabel, BorderLayout.SOUTH);
        return panel;
    }

    // Action buttons along the bottom of the window
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();

        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(e -> addSelectedToCart());

        JButton removeButton = new JButton("Remove from Cart");
        removeButton.addActionListener(e -> removeSelectedFromCart());

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(checkoutButton);
        return panel;
    }

    // ---------- Button actions ----------

    // Adds the selected catalog row to the cart if stock allows it
    private void addSelectedToCart() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first.");
            return;
        }

        Product product = catalog.get(row);
        int inCart = cart.getQuantity(product.getId());

        // Block the add if the cart already holds all remaining stock
        if (!product.isInStock(inCart + 1)) {
            JOptionPane.showMessageDialog(this,
                    "Not enough stock for " + product.getName() + ".");
            return;
        }

        cart.addProduct(product, 1);
        refreshCart();
    }

    // Removes the selected product from the cart entirely
    private void removeSelectedFromCart() {
        int row = productTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a product first.");
            return;
        }
        cart.removeProduct(catalog.get(row).getId());
        refreshCart();
    }

    // Runs the checkout flow: pick a payment method,
    // process the order, and show the receipt
    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.");
            return;
        }

        // Let the user pick which Payment implementation to use
        String[] options = {"Credit Card", "PayPal", "Gift Card"};
        String choice = (String) JOptionPane.showInputDialog(
                this, "Choose a payment method:", "Checkout",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) {
            return;                             // User cancelled the dialog
        }

        // Polymorphism: one Payment variable can hold any of the three types
        Payment payment;
        switch (choice) {
            case "PayPal":
                payment = new PayPalPayment(customer.getEmail());
                break;
            case "Gift Card":
                payment = new GiftCardPayment("GC-2026", 100.00);
                break;
            default:
                payment = new CreditCardPayment(customer.getName(),
                        "4532111122223333");
                break;
        }

        Order order = new Order(customer, cart, payment);

        // process() runs whichever processPayment() the chosen type defines
        if (order.process()) {
            // Payment succeeded: reduce stock, clear the cart, show receipt
            for (CartItem item : cart.getItems()) {
                item.getProduct().reduceStock(item.getQuantity());
            }
            cart.clear();
            refreshProductTable();
            refreshCart();
            JOptionPane.showMessageDialog(this, order.getReceipt(),
                    "Order Complete", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Payment was declined. Please try another method.",
                    "Payment Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------- Display refresh helpers ----------

    // Rebuilds the product table from the catalog (run after stock changes)
    private void refreshProductTable() {
        tableModel.setRowCount(0);              // Clear the existing rows
        for (Product p : catalog) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getPlatform(),
                    String.format("$%.2f", p.getPrice()), p.getStock()});
        }
    }

    // Rewrites the cart text area and the running total label
    private void refreshCart() {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : cart.getItems()) {
            sb.append(item).append("\n");
        }
        cartArea.setText(sb.toString());
        totalLabel.setText(String.format("Total: $%.2f", cart.getTotal()));
    }
}