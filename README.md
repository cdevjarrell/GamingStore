# Gaming Store Online Shopping System

A Java Swing application that simulates an online game store:
browse a catalog, build a shopping cart, and check out with a
choice of payment methods. CS3700 final project.

## How to Run

Requires JDK 17 or newer.

    cd src
    javac *.java
    java Main

Or open the project in IntelliJ IDEA and run `Main`.

## How to Use the Application

1. **Browse** - the table lists every game with its platform,
   price, and current stock.
2. **Add to Cart** - click a game in the table, then click
   *Add to Cart*. Clicking again increases the quantity. The cart
   panel on the right shows each line item and the running total.
3. **Remove from Cart** - select the game in the table and click
   *Remove from Cart* to take it out of the cart entirely.
4. **Checkout** - click *Checkout* and pick Credit Card, PayPal,
   or Gift Card. On success you get a receipt with an order
   number, and stock counts update in the table.

Notes: you cannot add more copies than are in stock, checkout is
blocked when the cart is empty, and the gift card ($100 balance)
declines any total above its balance.

## Design

The UML class diagram and design document are in `docs/`.
Payments are processed through the `Payment` interface, so the
checkout code works identically for all three payment types
(polymorphism).

## Testing Summary

| # | Test | Expected result | Pass |
|---|------|-----------------|------|
| 1 | Add a product to the cart | Line item appears, total updates | Y |
| 2 | Add the same product twice | Quantity becomes 2, no duplicate line | Y |
| 3 | Add beyond available stock | Blocked with "Not enough stock" message | Y |
| 4 | Add/Remove with no row selected | "Select a product first" message | Y |
| 5 | Remove a product from the cart | Line item disappears, total updates | Y |
| 6 | Checkout with empty cart | Blocked with "cart is empty" message | Y |
| 7 | Checkout with Credit Card | Receipt shows masked card number | Y |
| 8 | Checkout with PayPal | Receipt shows PayPal email | Y |
| 9 | Gift card with total under $100 | Approved, receipt shows remaining balance | Y |
| 10 | Gift card with total over $100 | Declined with payment failed message | Y |
| 11 | Stock after successful checkout | Table stock counts decrease correctly | Y |
| 12 | Order numbers across two checkouts | #1000 then #1001 (static counter) | Y |
