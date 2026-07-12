/*
 * Payment.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Interface implemented by every payment type in the store.
 * The Order class processes payments through this interface,
 * so it never needs to know which concrete payment type it holds.
 * This is the core polymorphism demonstration of the project.
 */

public interface Payment {

    // Attempts to charge the given amount; returns true on success
    boolean processPayment(double amount);

    // Short, masked summary of the payment method for the receipt
    String getPaymentDetails();
}