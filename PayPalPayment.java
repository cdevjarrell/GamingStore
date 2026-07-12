/*
 * PayPalPayment.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Payment implementation for PayPal accounts, identified by email.
 */

public class PayPalPayment implements Payment {

    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public boolean processPayment(double amount) {
        // Simulated approval - always succeeds in this project
        System.out.printf("Sending $%.2f to PayPal...%n", amount);
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return "PayPal account " + email;
    }
}