package store;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    private final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###");

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void buy(int quantity) {
        this.quantity -= quantity;
    }




    public String printPrice(int quantity) {
        return PRICE_FORMATTER.format(price * quantity);
    }

    @Override
    public String toString() {

        return "- " + name + " " + PRICE_FORMATTER.format(price) + "원 " + quantity + " " + promotion;
    }
}
