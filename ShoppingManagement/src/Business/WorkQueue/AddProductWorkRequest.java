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
public class AddProductWorkRequest extends WorkRequest {
    private Product product;
   
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
