package ir.aliakbarmoeini.packing.utils;

import ir.aliakbarmoeini.packing.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PackingUtilTest {

    @Autowired
    private PackingUtil packingUtil;

    @Test
    void checkExtractProductsFromInputStringWithOneProduct() {
        String input = "(1,15.3,$34)";
        var product = packingUtil.extractProductsFromInputString(input);
        var manualProduct = List.of(new Product(1, 15.3f, 34));
        assertEquals(manualProduct, product);
    }

    @Test
    void checkExtractProductsFromInputStringWithMultipleProduct() {
        String input = """
                (1,85.31,$29)(2,14.55,$74)(3,3.98,$16)(4,26.24,$55)(5,63.69,$52)
                (6,76.25,$75)(7,60.02,$74)(8,93.18,$35)(9,89.95,$78)
                """;

        var products = packingUtil.extractProductsFromInputString(input);
        var manualProducts = List.of(
                new Product(1, 85.31f, 29),
                new Product(2, 14.55f, 74),
                new Product(3, 3.98f, 16),
                new Product(4, 26.24f, 55),
                new Product(5, 63.69f, 52),
                new Product(6, 76.25f, 75),
                new Product(7, 60.02f, 74),
                new Product(8, 93.18f, 35),
                new Product(9, 89.95f, 78)
        );
        assertEquals(manualProducts, products);
    }

    @Test
    void checkPackFirstCase() {
        String input = """
                (1,53.38,$45)(2,88.62,$98)(3,78.48,$3)(4,72.30,$76)(5,30.18,$9)
                (6,46.34,$48)
                """;
        var expectedProductsNumbers = List.of(4);
        var productsNumbers = packingUtil.pack(81, input);
        assertThat(expectedProductsNumbers).hasSameElementsAs(productsNumbers);
    }

    @Test
    void checkPackSecondCase() {
        String input = "(1,15.3,$34)";
        var expectedProductsNumbers = List.of();
        var productsNumbers = packingUtil.pack(8, input);
        assertThat(expectedProductsNumbers).hasSameElementsAs(productsNumbers);
    }

    @Test
    void checkPackThirdCase() {
        String input = """
                (1,85.31,$29)(2,14.55,$74)(3,3.98,$16)(4,26.24,$55)(5,63.69,$52)
                (6,76.25,$75)(7,60.02,$74)(8,93.18,$35)(9,89.95,$78)
                """;
        var expectedProductsNumbers = List.of(7, 2);
        var productsNumbers = packingUtil.pack(75, input);
        assertThat(expectedProductsNumbers).hasSameElementsAs(productsNumbers);
    }

    @Test
    void checkPackForthCase() {
        String input = """
                (1,90.72,$13)(2,33.80,$40)(3,43.15,$10)(4,37.97,$16)(5,46.81,$36)
                (6,48.77,$79)(7,81.80,$45)(8,19.36,$79)(9,6.76,$64)
                """;
        var expectedProductsNumbers = List.of(9, 8);
        var productsNumbers = packingUtil.pack(56, input);
        assertThat(productsNumbers).hasSameElementsAs(expectedProductsNumbers);
    }
}