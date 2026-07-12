/*
 * GamingStoreGUI.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Main Swing window for the store. This first version builds the
 * window layout: the product catalog table in the center and the
 * shopping cart panel on the right. Buttons come next.
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