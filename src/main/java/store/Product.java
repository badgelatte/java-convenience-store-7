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

    @Override
    public String toString() {
        if(promotion == null) {
            return name + " " + PRICE_FORMATTER.format(price) + "원 " + quantity + " " + "null";
        }
        return name + " " + PRICE_FORMATTER.format(price) + "원 " + quantity + " " + promotion.getName();
    }
}
