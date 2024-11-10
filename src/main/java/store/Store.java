package store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Store {
    List<Product> productList = new ArrayList<>();
    List<Promotion> promotionList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void addPromotion(Promotion promotion) {
        promotionList.add(promotion);
    }

    public void buyAllItemsInCart(ShoppingCart cart) {
        Map<String, Integer> itemList = cart.getItems();
        for (Entry<String, Integer> item : itemList.entrySet()) {
            List<Product> product = findProduct(item.getKey());
            if (product == null) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해주세요");
            }
            purchaseItem(product, item.getValue());
        }
    }

    public void purchaseItem(List<Product> product, int itemQuantity) {
        int quantity = product.getFirst().buy(itemQuantity);
        if (quantity > 0) {
            product.get(1).buy(quantity);
        }
    }

    public List<Product> findProduct(String itemName) {
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            String productName = product.getName();
            if (productName.equals(itemName)) {
                products.add(product);
            }
        }
        return products;
    }

    public Promotion findPromotion(String productName) {
        for (Promotion promotion : promotionList) {
            String productPromotionName = promotion.getName();
            if (productName.equals(productPromotionName)) {
                return promotion;
            }
        }
        return null;
    }
}
