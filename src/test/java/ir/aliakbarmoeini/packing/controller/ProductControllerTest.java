package ir.aliakbarmoeini.packing.controller;

import ir.aliakbarmoeini.packing.entity.Product;
import ir.aliakbarmoeini.packing.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepo;


    @Test
    void checkSaveInDB() throws Exception {
        String input = "(1,53.38,$45)(2,88.62,$98)(3,78.48,$3)(4,72.30,$76)(5,30.18,$9)(6,46.34,$48)";

        var uri = UriComponentsBuilder.fromUriString("/saveInDB")
                .queryParam("products", input).build().toUri();

        mockMvc.perform(post(uri)).andExpect(status().isOk());

        var ids = List.of(1, 2, 3, 4, 5, 6);
        assertEquals(ids, productRepo.findAll().stream().map(Product::getId).toList());
    }


    @Test
    void checkSaveInDBWithMalformedRequestParam() throws Exception {
        String input = "(1,53.38,$45)(2,88.62,$98)5,30.18,$9)";

        var uri = UriComponentsBuilder.fromUriString("/saveInDB")
                .queryParam("products", input).build().toUri();

        mockMvc.perform(post(uri)).andExpect(status().isBadRequest());
    }

    @Test
    void checkAvgPrice() throws Exception {
        var products = List.of(
                new Product(1, 8.97f, 12),
                new Product(2, 25.6f, 34),
                new Product(3, 35.75f, 70),
                new Product(4, 80.31f, 89)
        );
        var avgFrom10to50 = (34 + 70) / 2.0;
        productRepo.saveAll(products);

        URI uri = UriComponentsBuilder.fromUriString("/avgPrice")
                .queryParam("floor", 10)
                .queryParam("ceil", 50).build().toUri();

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().string(avgFrom10to50 + ""));
    }

    @AfterEach
    void clear() {
        productRepo.deleteAll();
    }
}