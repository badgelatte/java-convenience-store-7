package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class OutputView {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    public void start(Store store) {
        printWelcomeMsg();
        fileRead(store);
    }

    public void printWelcomeMsg() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재보유하고 있는 상품입니다. \n");
    }

    public void fileRead(Store store) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine())!= null) {
                inputProduct(line, store);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 찾을 수 없습니다.");
        }
    }

    public void inputProduct(String input, Store store) {
        if (input.contains("name")) {
            return;
        }
        Product product = makeProduct(input);

        printProuct(product);
        store.addProduct(product);
    }

    public Product makeProduct(String input) {
        String[] inputs = input.split(",");

        int price = Integer.parseInt(inputs[1]);
        int quantity = Integer.parseInt(inputs[2]);

        return new Product(inputs[0], price, quantity, inputs[3]);
    }

    public void printProuct(Product product) {
        System.out.println(product.toString());
    }
}
