/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.WorkQueue;

import Business.Product.CartOrder;
import Business.Product.Product;
import Business.UserAccount.UserAccount;
import java.util.Date;
import userinterface.InventoryRole.StockCheckRequestJPanel;

/**
 *
 * @author kesha
 */
public class VendorProcessWorkRequest extends WorkRequest {

    private UserAccount sender;
    private UserAccount receiver;
    private Date requestDate;
    private Date resolveDate;
    private CartOrder order;
    private String paymentProcessResult;
  //  private CheckStockWorkRequest checkStockWorkRequest;
    private OrderProcessWorkRequest paymentProcessWorkRequest;

    public OrderProcessWorkRequest getPaymentProcessWorkRequest() {
        return paymentProcessWorkRequest;
    }

    public void setPaymentProcessWorkRequest(OrderProcessWorkRequest paymentProcessWorkRequest) {
        this.paymentProcessWorkRequest = paymentProcessWorkRequest;
    }

//    public CheckStockWorkRequest getCheckStockWorkRequest() {
//        return checkStockWorkRequest;
//    }
//
//    public void setCheckStockWorkRequest(CheckStockWorkRequest checkStockWorkRequest) {
//        this.checkStockWorkRequest = checkStockWorkRequest;
//    }
    public String getPaymentProcessResult() {
        return paymentProcessResult;
    }

    public void setPaymentProcessResult(String paymentProcessResult) {
        this.paymentProcessResult = paymentProcessResult;
    }

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResolveDate() {
        return resolveDate;
    }

    public void setResolveDate(Date resolveDate) {
        this.resolveDate = resolveDate;
    }

    public CartOrder getOrder() {
        return order;
    }

    public void setOrder(CartOrder order) {
        this.order = order;
    }

}
