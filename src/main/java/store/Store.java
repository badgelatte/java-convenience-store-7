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
            printRceipt();
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

    private int calculateTotalPurchase() {
        int totalPurchase = 0;
        for (Entry<Product, Integer> item : promotionItem.entrySet()) {
            totalPurchase += item.getValue() * item.getKey().getPrice();
        }
        for (Entry<Product, Integer> item : noPromotionItem.entrySet()) {
            totalPurchase += item.getValue() * item.getKey().getPrice();
        }
        return totalPurchase;
    }

    private int calculateTotalQuantity() {
        int totalQuantity = 0;
        for (Entry<Product, Integer> item : promotionItem.entrySet()) {
            totalQuantity += item.getValue();
        }
        for (Entry<Product, Integer> item : noPromotionItem.entrySet()) {
            totalQuantity += item.getValue();
        }
        return totalQuantity;
    }

    private int calculateTotalPromotionPrice() {
        int totalPrice = 0;
        for (Entry<Product, Integer> item : promotionItem.entrySet()) {
            totalPrice += item.getValue() * item.getKey().getPrice();
        }
        return totalPrice;
    }

    private void procedurePurchaseItem() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (Entry<Product, Integer> item : promotionItem.entrySet()) {
            Promotion promotion = item.getKey().getPromotion();
            int promotionQuantity = item.getValue() % (promotion.getBuy() + promotion.getGet()) * promotion.getBuy();
            System.out.println(item.getKey() + "\t\t" + promotionQuantity + "\t" + item.getKey().printPrice(item.getValue()));
        }
    }

    private void procedurePromotion() {
        System.out.println("=============증\t정===============");
        for (Entry<Product, Integer> item : promotionItem.entrySet()) {
            Promotion promotion = item.getKey().getPromotion();
            int promotionQuantity = item.getValue() % (promotion.getBuy() + promotion.getGet()) * promotion.getGet();
            System.out.println(item.getKey() + "\t\t" + promotionQuantity);
        }
    }

    private void procedureTotal(DecimalFormat priceFormatter) {
        int totalPurchase = calculateTotalPurchase();
        int eventDiscountAmount = calculateTotalPromotionPrice();
        int membershipDiscountAmount = applyDiscount(totalPurchase - eventDiscountAmount);
        int totalPay = totalPurchase - eventDiscountAmount - membershipDiscountAmount;

        System.out.println("====================================");
        System.out.println("총구매액\t\t" + calculateTotalQuantity() +"\t" + priceFormatter.format(totalPurchase));
        System.out.println("행사할인\t\t\t-" + eventDiscountAmount);
        System.out.println("멤버십할인\\t\\t\\t-" + priceFormatter.format(membershipDiscountAmount));
        System.out.println("내실돈\t\t\t " + priceFormatter.format(totalPay));
    }

    public void printRceipt() {
        DecimalFormat priceFormatter = new DecimalFormat("###,###");

        procedurePurchaseItem();
        procedurePromotion();
        procedureTotal(priceFormatter);
    }
}

