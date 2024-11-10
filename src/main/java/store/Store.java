package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Store {
    List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void buyItem(ShoppingCart cart) {
        Map<String, Integer> itemList = cart.getItems();
        for (Entry<String, Integer> item : itemList.entrySet()) {
            findProduct(item);
        }
    }

    public void findProduct(Entry<String, Integer> purchaseItem) {
        for (Product product : productList) {
            String productName = product.getName();
            if (productName.equals(purchaseItem.getKey())) {
                product.buy(purchaseItem.getValue());
            }
        }
    }
}
