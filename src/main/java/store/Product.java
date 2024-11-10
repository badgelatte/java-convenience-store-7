package store;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    private final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###");

    public Product(String name, int price, int quantity, String promotion) {
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

    public String getPromotion() {
        return promotion;
    }

    public int buy(int quantity) {
        if (quantity <= this.quantity) {
            int remainingQuantity = quantity - this.quantity;
            this.quantity = 0;
            return remainingQuantity;
        }
        this.quantity -= quantity;
        return 0;
    }

    public String printPrice() {
        return PRICE_FORMATTER.format(price);
    }

    @Override
    public String toString() {

        return "- " + name + " " + PRICE_FORMATTER.format(price) + "원 " + quantity + " " + promotion;
    }
}
