package Test;

import applicacion.ProductsDB;

/**
 *
 * @author reus
 */
public class Test {
    public static void main(String[] args) {
        ProductsDB prods = new ProductsDB("Ropa");
        for(String prod : prods.getProducts()){
            System.out.println(prod);
        }
    }
}
