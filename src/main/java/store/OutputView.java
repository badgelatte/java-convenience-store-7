package store;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OutputView {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";

    public void start(Store store) {
        printWelcomeMsg();
        List<String> promotionInfos = fileRead(PROMOTIONS_FILE_PATH);
        inputPromotion(promotionInfos, store);
        List<String> productInfos = fileRead(PRODUCTS_FILE_PATH);
        inputProductToStore(productInfos, store);
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
            Product product = makeProduct(productInfo, store);

            printProduct(product);
            store.addProduct(product);
        }
    }

    public void inputPromotion(List<String> promotionList, Store store) {
        for (String promotionInfo : promotionList) {
            Promotion promotion = makePromotion(promotionInfo);

            store.addPromotion(promotion);
        }
    }

    public Product makeProduct(String input, Store store) {
        String[] inputs = input.split(",");

        int price = validatePrice(inputs[1]);
        int quantity = Integer.parseInt(inputs[2]);

        Promotion promotion = store.findPromotion(inputs[3]);
        return new Product(inputs[0], price, quantity, promotion);
    }

    public Promotion makePromotion(String input) {
        String[] inputs = input.split(",");
        int buy = validatePrice(inputs[1]);
        int get = validatePrice(inputs[2]);

        LocalDate startDate = LocalDate.parse(inputs[3]);
        LocalDate endDate = LocalDate.parse(inputs[4]);

        return new Promotion(inputs[0], buy, get, startDate, endDate);
    }

    public int validatePrice(String input) {
        int price = 0;
        if (!Objects.equals(input, "재고 없음")) {
            price = Integer.parseInt(input);
        }
        return price;
    }

    public void printProduct(Product product) {
        System.out.println(product.toString());
    }
}
