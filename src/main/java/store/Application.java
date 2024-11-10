package store;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        Store store = new Store();
        outputView.start(store);

        ShoppingCart cart = new ShoppingCart();
        inputView.readItem(cart);

    }
}
