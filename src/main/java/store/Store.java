package store;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Store {
    private List<Product> productList = new ArrayList<>();
    private List<Promotion> promotionList = new ArrayList<>();
    private Map<Product, Integer> promotionItem = new HashMap<>();
    private Map<Product, Integer> noPromotionItem = new HashMap<>();

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

    private int isReceivedPromotion(Product product, int quantity) {
        String answer = InputView.giveAwayPromotionMsg(product.getName(), product.getPromotion());
        if (answer.equals("Y")) {
            return ++quantity;
        }
        return quantity;
    }

    private int calculatePromotionQuantity(Product product) {
        int promotionBuy = product.getPromotion().getBuy();
        int promotionGet = product.getPromotion().getGet();
        return product.getQuantity() % (promotionBuy + promotionGet);
    }
    private boolean isPromotionOutOfSock(Product product, int quantity) {
        String answer = InputView.promotionOutOfStockMsg(product.getName(), quantity);
        return answer.equals("Y");
    }

    public void promotionOutOfStockProcedure(List<Product> products, int itemQuantity) {
        Product productWithPromotion = products.getFirst();
        Product productWithNoPromotion = products.getLast();
        int remainQuantity = itemQuantity - productWithPromotion.getQuantity();
        int noPromotionProductQuantity = calculatePromotionQuantity(productWithPromotion) + remainQuantity;
        if (isPromotionOutOfSock(productWithNoPromotion, noPromotionProductQuantity)) {
            productWithPromotion.buy(productWithPromotion.getQuantity());
            promotionItem.put(productWithPromotion, productWithPromotion.getQuantity());
            productWithNoPromotion.buy(remainQuantity);
            noPromotionItem.put(productWithNoPromotion, noPromotionProductQuantity);
        }
    }

    public void checkPromotion (List<Product> products, int itemQuantity) {
        Product productWithPromotion = products.getFirst();
        if (productWithPromotion.getQuantity() <= itemQuantity) {
            promotionOutOfStockProcedure(products, itemQuantity);
            return;
        }
        itemQuantity = isReceivedPromotion(productWithPromotion, itemQuantity);
        productWithPromotion.buy(itemQuantity);
    }

    public void purchaseItem(List<Product> products, int itemQuantity) {
        int allItemCount = products.get(0).getQuantity() + products.get(1).getQuantity();
        if (allItemCount <= itemQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        if(products.getFirst().getPromotion().isPromotionPeriod()) {
            checkPromotion(products, itemQuantity);
        }
    }

    public int applyDiscount(int amount) {
        int allPrice = 0;
        String answer = InputView.membershipDiscountMsg();
        if (answer.equals("Y")) {
            allPrice = calculateDiscount(amount);
            if (allPrice > 8000) {
                return 8000;
            }
        }
        return allPrice;
    }

    private int calculateDiscount(int amount) {
        return (int)(amount * 0.3);
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
