package store;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StoreTest {
    private Store store;
    @BeforeEach
    void setUp() {
        store = new Store();
        LocalDate past = LocalDate.of(2024,1,1);
        LocalDate future = LocalDate.of(2024,1,1);

        Promotion promotion = new Promotion("1+1", 1, 1, past, future);

        store.addPromotion(promotion);
        store.addProduct(new Product("초코", 1000, 10, promotion));
        store.addProduct(new Product("초코", 1000, 10, null));
        store.addProduct(new Product("사이다",1000, 10, null));

    }

    @Test
    @DisplayName("저장된 물픔이 찾아지는 가에 대한 테스트")
    void addProductTest() {
        assertNotNull(store.findProduct("초코"));
        assertNotNull(store.findProduct("사이다"));
    }

    @Test
    @DisplayName("가지고 있는 프로모션 찾기에 대한 테스트")
    void promotionOutOfStockProcedureTest() {
        assertNotNull(store.findPromotion("1+1"));
    }
}
