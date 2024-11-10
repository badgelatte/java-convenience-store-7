package store;

import java.util.ArrayList;
import java.util.List;

public class Store {
    List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }
}
