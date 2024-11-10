package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OutputView {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    public void start(Store store) {
        printWelcomeMsg();
        List<String> productInfos = fileRead(PRODUCTS_FILE_PATH);
        inputProductToStore(productInfos, store);
        List<String> promotionInfos = fileRead(PROMOTIONS_FILE_PATH);
    }

    public void printWelcomeMsg() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재보유하고 있는 상품입니다. \n");
    }

    public List<String> fileRead(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().skip(1).toList();
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 찾을 수 없습니다.");
        }
    }

    public void inputProductToStore(List<String> productList, Store store) {
        for (String productInfo : productList) {
            Product product = makeProduct(productInfo);

            printProduct(product);
            store.addProduct(product);
        }
    }

    public Product makeProduct(String input) {
        String[] inputs = input.split(",");

        int price = Integer.parseInt(inputs[1]);
        int quantity = Integer.parseInt(inputs[2]);

        return new Product(inputs[0], price, quantity, inputs[3]);
    }

    public void printProduct(Product product) {
        System.out.println(product.toString());
    }
}
