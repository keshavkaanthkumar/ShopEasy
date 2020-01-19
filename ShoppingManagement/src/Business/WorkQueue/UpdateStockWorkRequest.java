/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Product.Product;
import Business.UserAccount.UserAccount;
import java.util.Date;

/**
 *
 * @author kesha
 */
public class UpdateStockWorkRequest extends WorkRequest{

   

   

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModalName() {
        return modalName;
    }

    public void setModalName(String modalName) {
        this.modalName = modalName;
    }
    private String category;
    private String modalName;
    private int qty;
    
}
