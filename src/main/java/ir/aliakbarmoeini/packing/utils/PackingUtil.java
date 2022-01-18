package ir.aliakbarmoeini.packing.utils;

import ir.aliakbarmoeini.packing.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PackingUtil {

    /**
     * Returns numbers of optimal products that their selections yields most price
     *
     * @param maximumCapacity maximum capacity of pack
     * @param productsString  products string with format of {@code (id,weight,$price)[(id,weight,$price)]*}
     * @return numbers of optimal products
     */
    public List<Integer> pack(int maximumCapacity, String productsString) {
        ArrayList<Product> products = extractProductsFromInputString(productsString);
        Collections.sort(products);

        int[] weights = products.stream().mapToInt(product -> (int) (product.getWeight() * 100)).toArray();
        int[] prices = products.stream().mapToInt(Product::getPrice).toArray();

        ArrayList<Integer> selectedProducts = optimalProductsSelection(weights, prices, maximumCapacity * 100);
        return selectedProducts.stream().map(i -> products.get(i - 1).getId()).toList();
    }


    /**
     * Parses and extract products from input string
     *
     * @param productsString products string with format of {@code (id,weight,$price)[(id,weight,$price)]*}
     * @return container object that contains id, weight and
     * price of products in three different arrays
     * @apiNote <p>an example of input:
     * <p>
     * {@code (3,3.98,$16)(4,26.24,$55)(5,63.69,$52)}
     */

    public ArrayList<Product> extractProductsFromInputString(String productsString) {
        Pattern pattern = Pattern.compile("[^0-9.]");
        String[] numbers = pattern.matcher(productsString).replaceAll(" ").strip().split("\s+");

        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < numbers.length; i += 3) {
            products.add(new Product(
                    Integer.parseInt(numbers[i]),
                    Float.parseFloat(numbers[i + 1]),
                    Integer.parseInt(numbers[i + 2])
            ));
        }
        return products;
    }

    /**
     * finds most suited products that with respect to maximum capacity yields most price
     *
     * @param weights         the products weight
     * @param prices          the products price
     * @param maximumCapacity the maximum capacity of pack
     * @return list of indices of optimal products
     */
    public ArrayList<Integer> optimalProductsSelection(int[] weights, int[] prices, int maximumCapacity) {

        int[][] matrix = new int[weights.length + 1][maximumCapacity + 1];

        // populate matrix
        for (int i = 0; i <= weights.length; i++) {
            for (int j = 0; j <= maximumCapacity; j++) {
                if (i == 0 | j == 0) {
                    matrix[i][j] = 0;
                    continue;
                }
                if (weights[i - 1] > j)
                    matrix[i][j] = matrix[i - 1][j];
                else
                    matrix[i][j] = Math.max(matrix[i - 1][j], prices[i - 1] + matrix[i - 1][j - weights[i - 1]]);
            }
        }

        // find the best products that yields maximum price
        int result = matrix[prices.length][maximumCapacity];
        int j = maximumCapacity;
        var selectedProducts = new ArrayList<Integer>();

        for (int i = prices.length; i > 0 && result > 0; i--) {
            if (matrix[i - 1][j] != result) {
                selectedProducts.add(i);
                result -= prices[i - 1];
                j -= weights[i - 1];
            }
        }
        return selectedProducts;
    }
}
