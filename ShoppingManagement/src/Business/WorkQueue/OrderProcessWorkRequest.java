/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Product.CartOrder;
import Business.Product.Product;
import Business.UserAccount.UserAccount;
import Utilities.Email;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author kesha
 */
public class OrderProcessWorkRequest extends WorkRequest {

    
    private CartOrder order;
    private String paymentProcessResult;
        private String status;
 @Override
    public void setStatus(String status) {
        this.status = status;
        if(status.equals("Order placed"))
        {
           try {
            Email.sendMail(this.order.getEmail(), "Hello "+order.getFullname()+",\n\t Your Order has been placed.",  "ShopEasy Order status");
        } catch (MessagingException ex) {
            Logger.getLogger(OrderProcessWorkRequest.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        else
        {
        try {
            Email.sendMail(this.order.getEmail(), "Hello "+order.getFullname()+",\n\t Your Order status has been updated to '"+status+"'.",  "ShopEasy Order status");
        } catch (MessagingException ex) {
            Logger.getLogger(OrderProcessWorkRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    @Override
    public String getStatus() {
        return status;
    }

    public String getPaymentProcessResult() {
        return paymentProcessResult;
    }

    public void setPaymentProcessResult(String paymentProcessResult) {
        this.paymentProcessResult = paymentProcessResult;
    }

    

    public CartOrder getOrder() {
        return order;
    }

    public void setOrder(CartOrder order) {
        this.order = order;
    }

}
