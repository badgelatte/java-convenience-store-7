package store;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    Map<String, Integer> items = new HashMap<>();

    public void addItems(String input) {
        String[] itemList = input.split(",");
        for (String item : itemList) {
            String itemName = outputItemName(item);
            int quantity = outputItemQuantity(item);
            items.put(itemName, quantity);
        }
    }

    public String outputItemName(String item) {
        String itemName = item.split("-")[0];

        return itemName.substring(1);
    }

    public int outputItemQuantity(String item) {
        try {
            item = item.split("-")[1];
            item = item.substring(0, item.length() - 1);
            return Integer.parseInt(item);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해주세요");
        }

    }
}
