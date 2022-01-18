package ir.aliakbarmoeini.packing.service;

import ir.aliakbarmoeini.packing.repository.ProductRepository;
import ir.aliakbarmoeini.packing.utils.PackingUtil;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final PackingUtil packingUtil;

    private final ProductRepository productRepo;

    public ProductServiceImpl(PackingUtil packingUtil, ProductRepository productRepo) {
        this.packingUtil = packingUtil;
        this.productRepo = productRepo;
    }

    @Override
    public void saveProducts(String inputString) {
        productRepo.saveAll(packingUtil.extractProductsFromInputString(inputString));
    }

    @Override
    public Float averagePriceBetweenFloorAndCeil(Float floor, Float ceil) {
        return productRepo.averagePriceByWeightBetween(floor, ceil);
    }

}
