package store;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public void readItem(ShoppingCart cart) {
        try {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요.(예: [사이다-2], [감자칩-1])");
            String item = Console.readLine();
            cart.addItems(item);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readItem(cart);
        }
    }
}
