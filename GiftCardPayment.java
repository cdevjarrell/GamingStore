/*
 * GiftCardPayment.java
 * Gaming Store Online Shopping System - CS3700 Final Project
 *
 * Payment implementation for store gift cards. Unlike the other
 * two types, this one can decline the charge if the card balance
 * is too low, which lets the project demonstrate a failed payment.
 */

public class GiftCardPayment implements Payment {

    private final String cardCode;
    private double balance;

    public GiftCardPayment(String cardCode, double  balance) {
        this.cardCode = cardCode;
        this.balance = balance;
    }

    @Override
    public boolean processPayment(double amount) {
        //Decline the charge if the balance cannot cover it
        if (balance < amount) {
            System.out.printf("Gift card declined: balance $%.2f is below $%.2f%n", balance, amount);
            return false;
        }
        balance -= amount;
        System.out.printf("Charging $%.2f to gift card %s...%n", amount, cardCode);
        return true;
    }

    @Override
    public String getPaymentDetails() {
        return String.format("Gift Card %s (remaining balance: $%2.f)", cardCode, balance);
    }
}