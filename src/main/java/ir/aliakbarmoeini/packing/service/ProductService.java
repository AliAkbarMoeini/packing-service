package ir.aliakbarmoeini.packing.service;

public interface ProductService {
    Float averagePriceBetweenFloorAndCeil(Float floor, Float ceil);

    void saveProducts(String inputString);
}
