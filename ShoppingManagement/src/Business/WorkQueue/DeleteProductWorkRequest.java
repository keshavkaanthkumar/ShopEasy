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
public class DeleteProductWorkRequest extends WorkRequest{
    private String modelName;
    private String category;

       public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
