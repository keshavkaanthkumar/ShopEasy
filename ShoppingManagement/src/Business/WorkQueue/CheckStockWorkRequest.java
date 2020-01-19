/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Product.CartOrder;
import Business.Product.Item;
import java.util.ArrayList;

/**
 *
 * @author kesha
 */
public class CheckStockWorkRequest extends WorkRequest {

    public ArrayList<Item> ItemList;
    public int reqQuantity;
    public boolean availability;
    public CartOrder cartOrder;

    public CartOrder getCartOrder() {
        return cartOrder;
    }

    public void setCartOrder(CartOrder cartOrder) {
        this.cartOrder = cartOrder;
    }
    public VendorProcessWorkRequest vendorProcessWorkRequest;

    public VendorProcessWorkRequest getVendorProcessWorkRequest() {
        return vendorProcessWorkRequest;
    }

    public void setVendorProcessWorkRequest(VendorProcessWorkRequest vendorProcessWorkRequest) {
        this.vendorProcessWorkRequest = vendorProcessWorkRequest;
    }

    public CheckStockWorkRequest() {
        this.ItemList = new ArrayList<>();
    }

    public int getReqQuantity() {
        return reqQuantity;
    }

    public ArrayList<Item> getItemList() {
        return ItemList;
    }

    public void setItemList(ArrayList<Item> ItemList) {
        this.ItemList = ItemList;
    }

    public void setReqQuantity(int reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

}
