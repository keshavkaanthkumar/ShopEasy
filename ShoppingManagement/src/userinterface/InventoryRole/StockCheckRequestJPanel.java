/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.InventoryRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.DeliveryOrganization;
import Business.Organization.Organization;
import Business.Organization.VendorOrganization;
import Business.Person.Person;
import Business.Product.CartOrder;
import Business.Product.Item;
import Business.Product.Product;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.CheckStockWorkRequest;
import Business.WorkQueue.OrderProcessWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author kesha
 */
public class StockCheckRequestJPanel extends javax.swing.JPanel {

    /**
     * Creates new form StockCheckRequestJPanel
     */
    JPanel userprocesscontainer;
    WorkRequest request;
    boolean stockStatus;
    EcoSystem business;
    private Enterprise vendorEnterprise;
    private String outofstockprod;

    public StockCheckRequestJPanel(JPanel userprocesscontainer, WorkRequest request, Enterprise enterprise,EcoSystem business) {
        initComponents();
        this.userprocesscontainer = userprocesscontainer;
        this.business=business;
        this.vendorEnterprise = enterprise;
        this.request = request;
        this.business=business;
        this.stockStatus = true;
        initialize();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        productModel = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        availableStock = new javax.swing.JTextField();
        reqQuantity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        complete = new javax.swing.JButton();
        stockstatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setBackground(new java.awt.Color(250, 250, 250));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productModel.setForeground(new java.awt.Color(0, 153, 0));
        add(productModel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 95, 169, -1));

        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("Product model");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 99, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Available stock");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 177, -1, -1));

        availableStock.setEditable(false);
        availableStock.setForeground(new java.awt.Color(0, 153, 0));
        add(availableStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 172, 170, -1));

        reqQuantity.setEditable(false);
        reqQuantity.setForeground(new java.awt.Color(0, 153, 0));
        add(reqQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 243, 170, -1));

        jLabel4.setForeground(new java.awt.Color(0, 153, 0));
        jLabel4.setText("Required Quantity");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 248, -1, -1));

        complete.setBackground(new java.awt.Color(0, 153, 0));
        complete.setForeground(new java.awt.Color(255, 255, 255));
        complete.setText("Complete request");
        complete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeActionPerformed(evt);
            }
        });
        add(complete, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 371, -1, -1));
        add(stockstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 299, 440, 45));

        jButton1.setText("<<back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void completeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeActionPerformed
        // TODO add your handling code here:
        CheckStockWorkRequest checkStockWorkRequest = (CheckStockWorkRequest) request;
        checkStockWorkRequest.setAvailability(stockStatus);

        checkStockWorkRequest.setStatus("Completed");
        checkStockWorkRequest.getSender().getEmployee().getVendorProcessWorkRequestMap().put(checkStockWorkRequest.getVendorProcessWorkRequest(), "Stock check completed");
        //UpdateStockQuanityOfCompletedOrders(checkStockWorkRequest.getVendorProcessWorkRequest().getOrder());
        if(!stockStatus)
{
    checkStockWorkRequest.getVendorProcessWorkRequest().getPaymentProcessWorkRequest().setStatus(outofstockprod+" is out of stock");
}
        else
        {
        UpdateStockQuanityOfCompletedOrders(checkStockWorkRequest);
        }
        JOptionPane.showMessageDialog(null, "Request marked as complete");
        // checkStockWorkRequest.set

    }//GEN-LAST:event_completeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         userprocesscontainer.remove(this);
        Component[] componentArray = userprocesscontainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        InventoryWorkAreaJPanel dwjp = (InventoryWorkAreaJPanel) component;
        dwjp.populateRequestTable();
        CardLayout layout = (CardLayout)userprocesscontainer.getLayout();
        layout.previous(userprocesscontainer);
    }//GEN-LAST:event_jButton1ActionPerformed
    private boolean CheckIfAllItemsAreAvailable(List<Item> itemlist) {
        for (Item item : itemlist) {
            if (!item.isAvailability()) {
                return false;
            }
        }
        return true;
    }

     private void UpdateStockQuanityOfCompletedOrders(CheckStockWorkRequest checkStockWorkRequest) {
        Organization organization = vendorEnterprise.getOrganizationDirectory().getOrganizationList().stream().filter(org -> org.getName().equals("Vendor Organization")).findAny().orElse(null);
        ArrayList<Person> employees = organization.getEmployeeDirectory().getEmployeeList();
//
//        ArrayList<WorkRequest> customerWorkRequests = userAccount.getWorkQueue().getWorkRequestList();
//        for (int i = 0; i < customerWorkRequests.size(); i++) {
//            OrderProcessWorkRequest workRequest = (OrderProcessWorkRequest) customerWorkRequests.get(i);
//            CartOrder order = workRequest.getOrder();
CartOrder order = checkStockWorkRequest.getVendorProcessWorkRequest().getOrder();
        boolean inventoryCheck = CheckIfAllItemsAreAvailable(order.getItemlist());
        if (inventoryCheck) {
            checkStockWorkRequest.getVendorProcessWorkRequest().getPaymentProcessWorkRequest().setStatus("Out for delivery");
                    checkStockWorkRequest.getSender().getEmployee().getVendorProcessWorkRequestMap().put(checkStockWorkRequest.getVendorProcessWorkRequest(), "Out for delivery");

            forwardtoDelivery(checkStockWorkRequest.getVendorProcessWorkRequest().getPaymentProcessWorkRequest());
            for (Item item : order.getItemlist()) {
                Product product = item.getProduct();
                String cartOrderProductName = product.getBrand_name();
                int quantityOrdered = item.getQuantity();
                String vendorName = product.getVendorName();
                Person person = employees.stream().filter(v -> v.getName().equals(vendorName)).findAny().orElse(null);
                for (Map.Entry<String, ArrayList<Product>> entry : person.getProductsHashMap().entrySet()) {
                    String category = entry.getKey();
                    ArrayList<Product> productList = entry.getValue();
                    Product matchedProduct = productList.stream().filter(prdct -> prdct.getBrand_name().equals(cartOrderProductName)).findAny().orElse(null);
                    if (matchedProduct != null) {
                        matchedProduct.setStock(matchedProduct.getStock() - quantityOrdered);
                    }
                }
            }
        }
        // }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField availableStock;
    private javax.swing.JButton complete;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> productModel;
    private javax.swing.JTextField reqQuantity;
    private javax.swing.JLabel stockstatus;
    // End of variables declaration//GEN-END:variables

    private void initialize() {
        CheckStockWorkRequest checkStockWorkRequest = (CheckStockWorkRequest) request;

        for (Item item : checkStockWorkRequest.getItemList()) {
            productModel.addItem(item.getProduct().getModel());
            if (item.getProduct().getStock() < item.getQuantity()) {
                this.outofstockprod=item.getProduct().getModel();
                this.stockStatus = false;
            }

        }
        for (Item item : checkStockWorkRequest.getItemList()) {
            item.setAvailability(stockStatus);
        }
        if (stockStatus) {
            stockstatus.setText("All Items in the order are available in inventory");

        } else {
            stockstatus.setText("One or more items are not available in inventory");
        }
        populatetext(checkStockWorkRequest);
        productModel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {

                populatetext(checkStockWorkRequest);

            }
        });

    }

    private void populatetext(CheckStockWorkRequest checkStockWorkRequest) {
        for (Item item : checkStockWorkRequest.getItemList()) {
            if (item.getProduct().getModel().equals(productModel.getSelectedItem().toString())) {
                availableStock.setText(String.valueOf(item.getProduct().getStock()));
                reqQuantity.setText(String.valueOf(item.getQuantity()));
            }
        }
    }

    private void forwardtoDelivery(OrderProcessWorkRequest paymentProcessWorkRequest) {
         Network currentNetwork=null;
         for(Network network:business.getNetworkList())
        {
           for(Enterprise enterprise: network.getEnterpriseDirectory().getEnterpriseList())
           {
               for(Organization organization: enterprise.getOrganizationDirectory().getOrganizationList()){
                   for(UserAccount account: organization.getUserAccountDirectory().getUserAccountList())
                   {
                      if(account.getUsername().equals(request.getReceiver().getUsername())) 
                      {
                          currentNetwork=network;
                          break;
                      }
                   }
               }
           }
        }
         //business.getNetworkList().stream().filter(network -> network.getName().equals("Boston")).findAny().orElse(null);
        Enterprise DeliveryEnterprise = currentNetwork.getEnterpriseDirectory().getEnterpriseList().stream().filter(enterprise -> enterprise.getEnterpriseType().equals(Enterprise.EnterpriseType.Delivery)).findAny().orElse(null);
        
        paymentProcessWorkRequest.setReceiver(null);
        paymentProcessWorkRequest.setStatus("Out for delivery");
        Organization org = null;
        for (Organization organization : DeliveryEnterprise.getOrganizationDirectory().getOrganizationList()) {
            if (organization instanceof DeliveryOrganization) {
                org = organization;
               
                break;
            }

        }
        if (org != null) {
            org.getWorkQueue().getWorkRequestList().add(paymentProcessWorkRequest);
           

        }
    }
}
