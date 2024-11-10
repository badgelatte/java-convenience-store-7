package store;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("###,###");

        return "- " + name + " " + formatter.format(price) + "원 " + quantity + " " + promotion;
    }
}
