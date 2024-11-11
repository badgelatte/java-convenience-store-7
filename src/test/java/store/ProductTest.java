package store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        LocalDate past = LocalDate.of(2024,1,1);
        LocalDate future = LocalDate.of(2024,12,31);
        Promotion promotion = new Promotion("a", 1, 1, past, future);
        product = new Product("choco", 1000, 5, promotion);
    }

    @Test
    @DisplayName("기본적인 getter 메서드들 테스트")
    void basicTest() {
        assertEquals("choco", product.getName());
        assertEquals(1000, product.getPrice());
        assertEquals(5, product.getQuantity());
        assertNotNull(product.getPromotion());
    }

    @Test
    @DisplayName("물품 구매한 만큼 갯수가 사라지는 가에 대한 테스트")
    void buyTest() {
        product.buy(2);
        assertEquals(3, product.getQuantity());
    }
}
