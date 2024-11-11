package store;

public class Application {
    static OutputView outputView = new OutputView();
    static InputView inputView = new InputView();
    static Store store = new Store();
    static ShoppingCart cart = new ShoppingCart();

    public static void main(String[] args) {
        outputView.start(store);
        try {
            start();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            start();
        }
    }

    public static void start() {
        store.printProduct();
        inputView.readItem(cart);
        store.buyAllItemsInCart(cart);
    }
}
