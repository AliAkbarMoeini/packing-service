package ir.aliakbarmoeini.packing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aliakbarmoeini.packing.exception.BadRequestException;
import ir.aliakbarmoeini.packing.service.ProductService;
import ir.aliakbarmoeini.packing.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@Tag(name = "Product Web Services")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("saveInDB")
    @Operation(
            summary = "save products",
            description = "save all products that are inside products string",
            parameters = @Parameter(
                    name = "products",
                    in = ParameterIn.QUERY,
                    example = "(6,76.25,$75)(7,60.02,$74)(8,93.18,$35)(9,89.95,$78)",
                    description = "parameter must be in this format: (id,weight,$price)[(id,weight,$price)]*"
            )
    )
    public ResponseEntity<Void> saveInDB(@RequestParam String products) {
        Pattern pattern = Pattern.compile("(?:\\(\\d+\\,\\d+\\.\\d+\\,\\$\\d+\\))+");
        if (!pattern.matcher(products).matches())
            throw new BadRequestException("malformed products string");
        productService.saveProducts(products);
        return ResponseEntity.ok().build();
    }


    @GetMapping("avgPrice")
    @Operation(
            summary = "average price",
            description = "calculate average price of all products that their weights are between floor and ceil"
    )
    public ResponseEntity<Float> avgPrice(@RequestParam Float floor, @RequestParam Float ceil) {
        var avg = productService.averagePriceBetweenFloorAndCeil(floor, ceil);
        return ResponseEntity.ok(avg);
    }
}