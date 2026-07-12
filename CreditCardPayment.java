/*
 * CreditCardPayment.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Payment implementation for credit cards. The charge is simulated;
 * a real system would contact a payment gateway here.
 */

public class CreditCardPayment implements Payment {

    private final String cardHolder;
    private final String cardNumber;

    public CreditCardPayment(String cardHolder, String cardNumber) {
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        //Simulated approval - always succeeds in this project
        System.out.printf("Charge $%.2f to credit card...%n", amount);
        return true;
    }

    @Override
    public String getPaymentDetails() {
        // Only the last four digits are shown for security
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "Credit Card ending in " + lastFour + " (" + cardNumber + ")";
    }
}