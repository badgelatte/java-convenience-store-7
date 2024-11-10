package store;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    Map<String, Integer> items = new HashMap<>();

    public void addItems(String input) {
        String[] itemList = input.split(",");
        for (String item : itemList) {
            check(item);
            String itemName = outputItemName(item);
            int quantity = outputItemQuantity(item);
            items.put(itemName, quantity);
        }
    }

    private void check(String input) {
        if (!input.matches("\\[[가-힣a-zA-Z]+-+[0-9]+]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
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
