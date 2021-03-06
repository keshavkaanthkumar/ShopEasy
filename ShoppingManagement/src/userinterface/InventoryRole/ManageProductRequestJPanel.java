/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.InventoryRole;

import Business.Product.Product;
import Business.UserAccount.UserAccount;
import Business.WorkQueue.AddProductWorkRequest;
import Business.WorkQueue.DeleteProductWorkRequest;
import Business.WorkQueue.UpdateStockWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author kesha
 */
public class ManageProductRequestJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AddProductRequestJPanel
     */
    WorkRequest request;
    JPanel userProcessContainer;
    public ManageProductRequestJPanel(JPanel userProcessContainer,WorkRequest request) {
        initComponents();
             this.request=request;
        this.userProcessContainer=userProcessContainer;
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        reqType = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        brandName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        model = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        category = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        completeReq = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(250, 250, 250));
        setForeground(new java.awt.Color(204, 153, 255));
        setMinimumSize(new java.awt.Dimension(1680, 1050));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("Work Request");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 190, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Request Type");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, 120, -1));

        reqType.setEditable(false);
        reqType.setForeground(new java.awt.Color(0, 153, 0));
        add(reqType, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 260, 150, -1));

        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setText("Product Details");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 120, -1));

        jLabel4.setForeground(new java.awt.Color(0, 153, 0));
        jLabel4.setText("Brand Name");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 120, -1));

        brandName.setEditable(false);
        brandName.setForeground(new java.awt.Color(0, 153, 0));
        add(brandName, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, 150, -1));

        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("Model");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 380, 120, -1));

        model.setEditable(false);
        model.setForeground(new java.awt.Color(0, 153, 0));
        add(model, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, 150, -1));

        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Quantity");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 420, 110, -1));

        qty.setEditable(false);
        qty.setForeground(new java.awt.Color(0, 153, 0));
        add(qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, 150, -1));
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 180, 70, 30));

        category.setEditable(false);
        category.setForeground(new java.awt.Color(0, 153, 0));
        add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, 150, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setForeground(new java.awt.Color(0, 153, 0));
        jLabel8.setMinimumSize(new java.awt.Dimension(10, 10));
        jLabel8.setPreferredSize(new java.awt.Dimension(10, 10));
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 300, 150, 20));

        jLabel9.setForeground(new java.awt.Color(0, 153, 0));
        jLabel9.setText("Category");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 460, -1, -1));

        completeReq.setBackground(new java.awt.Color(0, 153, 0));
        completeReq.setForeground(new java.awt.Color(255, 255, 255));
        completeReq.setText("Complete Request");
        completeReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeReqActionPerformed(evt);
            }
        });
        add(completeReq, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 510, -1, -1));

        jButton1.setText("<<back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void completeReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeReqActionPerformed
        // TODO add your handling code here:
        if(request.getRequestType().equals("Add Product"))
        {
            AddProductWorkRequest addProductWorkRequest=(AddProductWorkRequest)request;
             
            ArrayList<Product> arrList=request.getSender().getEmployee().getProductsHashMap().get(addProductWorkRequest.getProduct().getCategoryName());
            if(arrList==null)
            {
               arrList=new ArrayList<>(); 
               request.getSender().getEmployee().getProductsHashMap().put(addProductWorkRequest.getProduct().getCategoryName(), arrList);
            }
           arrList.add(addProductWorkRequest.getProduct());
           request.setResolveDate(new Date());
           request.setStatus("Completed");
           JOptionPane.showMessageDialog(null, "Product added to the inventory");
           
        }
        if(request.getRequestType().equals("Delete Product")){
            DeleteProductWorkRequest deleteProductWorkRequest=(DeleteProductWorkRequest)request;
             ArrayList<Product> arrList=request.getSender().getEmployee().getProductsHashMap().get(deleteProductWorkRequest.getCategory());
          // arrList.add(addProductWorkRequest.getProduct());
          for(Product product: arrList)
          {
              if(product.getModel().equals(deleteProductWorkRequest.getModelName()))
              {
                  arrList.remove(product);
                  break;
              }
          }
          
           request.setResolveDate(new Date());
           request.setStatus("Completed");
           JOptionPane.showMessageDialog(null, "Product deleted from the inventory");
        }
         if(request.getRequestType().equals("Update Stock")){
             
             UpdateStockWorkRequest updateStockWorkRequest=(UpdateStockWorkRequest)request;
              ArrayList<Product> arrList=request.getSender().getEmployee().getProductsHashMap().get(updateStockWorkRequest.getCategory());
             for(Product product: arrList)
          {
              if(product.getModel().equals(updateStockWorkRequest.getModalName()))
              {
                  product.setStock(Integer.valueOf(qty.getText()));
                  break;
              }
          }
           request.setResolveDate(new Date());
           request.setStatus("Completed");
           JOptionPane.showMessageDialog(null, "Product stock updated in the inventory");
         }
         
        
    }//GEN-LAST:event_completeReqActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        userProcessContainer.remove(this);
        Component[] componentArray = userProcessContainer.getComponents();
        Component component = componentArray[componentArray.length - 1];
        InventoryWorkAreaJPanel dwjp = (InventoryWorkAreaJPanel) component;
        dwjp.populateRequestTable();
        CardLayout layout = (CardLayout)userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField brandName;
    private javax.swing.JTextField category;
    private javax.swing.JButton completeReq;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField model;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField reqType;
    // End of variables declaration//GEN-END:variables

    private void initialize() {
       reqType.setText(request.getRequestType());
       if(request.getRequestType().equals("Add Product")){
           AddProductWorkRequest addProductWorkRequest=(AddProductWorkRequest)request;
           brandName.setText(addProductWorkRequest.getProduct().getBrand_name());
           qty.setText(String.valueOf(addProductWorkRequest.getProduct().getStock()));
           category.setText(addProductWorkRequest.getProduct().getCategoryName());
           model.setText(addProductWorkRequest.getProduct().getModel());
          
           
       }
       if(request.getRequestType().equals("Delete Product")){
           DeleteProductWorkRequest deleteProductWorkRequest=(DeleteProductWorkRequest)request;
          
           model.setText(String.valueOf(deleteProductWorkRequest.getModelName()));
           category.setText(deleteProductWorkRequest.getCategory());
          
          
       }
       if(request.getRequestType().equals("Update Stock")){
           UpdateStockWorkRequest updateStockWorkRequest=(UpdateStockWorkRequest)request;
           qty.setText(String.valueOf(updateStockWorkRequest.getQty()));
           category.setText(updateStockWorkRequest.getCategory());
           model.setText(updateStockWorkRequest.getModalName());
          
       }
    }

   
}
