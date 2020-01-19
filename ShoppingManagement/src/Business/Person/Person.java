/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import Business.Product.CartOrder;
import Business.Product.Product;
import Business.WorkQueue.VendorProcessWorkRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author raunak
 */
public class Person {

    private HashMap<String, ArrayList<Product>> productsHashMap;
     private HashMap<VendorProcessWorkRequest,String> VendorProcessWorkRequestMap;
    private CartOrder productCartOrder;

    public HashMap<VendorProcessWorkRequest, String> getVendorProcessWorkRequestMap() {
        return VendorProcessWorkRequestMap;
    }

    public void setVendorProcessWorkRequestMap(HashMap<VendorProcessWorkRequest, String> VendorProcessWorkRequestMap) {
        this.VendorProcessWorkRequestMap = VendorProcessWorkRequestMap;
    }



    public CartOrder getProductCartOrder() {
        return productCartOrder;
    }

    public void setProductCartOrder(CartOrder productCartOrder) {
        this.productCartOrder = productCartOrder;
    }



    public HashMap<String, ArrayList<Product>> getProductsHashMap() {
        return productsHashMap;
    }

    public void setProductsHashMap(HashMap<String, ArrayList<Product>> productsHashMap) {
        this.productsHashMap = productsHashMap;
    }

    private String name;
    private int id;
    private static int count = 1;

    public Person() {
        productCartOrder = new CartOrder();
        productsHashMap = new HashMap<String, ArrayList<Product>>();
        VendorProcessWorkRequestMap=new HashMap<VendorProcessWorkRequest,String>();
        id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void disposeCart() {
        productCartOrder = new CartOrder();
    }

}
