/*
 * Customer.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Represents the customer who is shopping. Stores the basic
 * identifying information printed on the order receipt.
 */
public class Customer {

    private final int id;
    private final String name;
    private final String email;

    // Constructor sets the customer's identifying details
    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters for read-only access
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}