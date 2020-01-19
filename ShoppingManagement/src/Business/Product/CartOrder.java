/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Product;

import Business.UserAccount.UserAccount;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kesha
 */
public class CartOrder {
    //private UserAccount userAcc;

    private String fullname;
    private String creditCardNo;
    private int cvv;
    private String email;
    private String shippingAddress;
    private int estDelivery;

    public int getEstDelivery() {
        return estDelivery;
    }

    public void setEstDelivery(int estDelivery) {
        this.estDelivery = estDelivery;
    }
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    List<Item> itemlist = new ArrayList<Item>();
    private int totalprice;

    public void addItem(Item item) {
        this.itemlist.add(item);
        this.totalprice += (item.getCost() * item.getQuantity());
    }

    public List<Item> getItemlist() {
        return itemlist;
    }

    public void setItemlist(List<Item> itemlist) {
        this.itemlist = itemlist;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
    @Override
 public String toString(){
     return "Cart Order";
 }

}
