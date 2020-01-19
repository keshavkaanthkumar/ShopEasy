/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userinterface.CustomerRole;

import Business.EcoSystem;
import Business.Person.Person;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.CustomerOrganization;
import Business.Organization.PaymentOrganization;
import Business.Organization.Organization;
import Business.Product.CartOrder;
import Business.Product.Item;
import Business.Product.Product;
import Business.UserAccount.UserAccount;

import Business.WorkQueue.OrderProcessWorkRequest;
import Business.WorkQueue.WorkRequest;
import Utilities.Maps;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author raunak
 */
public class CustomerWorkAreaJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private EcoSystem business;
    private UserAccount userAccount;
    private CustomerOrganization customerOrganization;
    private JPanel backGroundJpanel;
    final static Logger logger = Logger.getLogger(CustomerWorkAreaJPanel.class);

    /**
     * Creates new form LabAssistantWorkAreaJPanel
     */
    private Product selectedProduct;
    private Enterprise wholeSaleEnterprise;

    public CustomerWorkAreaJPanel(JPanel backGroundJpanel, JPanel userProcessContainer, UserAccount account, Organization organization, EcoSystem business, Enterprise enterprise) {
        initComponents();
        this.wholeSaleEnterprise = enterprise;
        this.backGroundJpanel = backGroundJpanel;
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.business = business;
        this.customerOrganization = (CustomerOrganization) organization;
        populateTable();
    }

    void populateTable() {
        Network currentNetwork = null;
        //business.getNetworkList().stream().filter(network -> network.getName().equals("Boston")).findAny().orElse(null);
        for (Network network : business.getNetworkList()) {
            for (Enterprise enterprise : network.getEnterpriseDirectory().getEnterpriseList()) {
                for (Organization organization : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    for (UserAccount account : organization.getUserAccountDirectory().getUserAccountList()) {
                        if (account.getUsername().equals(userAccount.getUsername())) {
                            currentNetwork = network;
                            break;
                        }
                    }
                }
            }
        }
        ArrayList<Enterprise> enterpriseList = currentNetwork.getEnterpriseDirectory().getEnterpriseList();
        Enterprise vendorEnterprise = enterpriseList.stream().filter(enterprise -> enterprise.getEnterpriseType() == Enterprise.EnterpriseType.Wholesale).findAny()
                .orElse(null);
        Organization organization = vendorEnterprise.getOrganizationDirectory().getOrganizationList().stream().filter(org -> org.getName().equals("Vendor Organization")).findAny().orElse(null);
        ArrayList<Person> employees = organization.getEmployeeDirectory().getEmployeeList();
        SetProductCatgeoryAndVendorName(employees);
        ArrayList<Product> NewlyAddedProducts = new ArrayList<>();
        for (Person employee : employees) {
            ArrayList<Product> vendorproducts = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Product>> en : employee.getProductsHashMap().entrySet()) {
                vendorproducts.addAll(en.getValue());
            }
            NewlyAddedProducts.addAll(vendorproducts);
        }
        Display(allProductsTable, NewlyAddedProducts);
        ArrayList<Product> employeeCategoryProducts = new ArrayList<>();
        for (Person employee : employees) {
            ArrayList<Product> vendorproducts = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Product>> en : employee.getProductsHashMap().entrySet()) {
                if (en.getKey().equals("Electronics")) {
                    vendorproducts.addAll(en.getValue());
                }
            }
            employeeCategoryProducts.addAll(vendorproducts);
        }
        Display(electronicsTable, employeeCategoryProducts);

        ArrayList<Product> mobileCategoryProducts = new ArrayList<>();
        for (Person employee : employees) {
            ArrayList<Product> vendorproducts = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Product>> en : employee.getProductsHashMap().entrySet()) {
                if (en.getKey().equals("Mobiles")) {
                    vendorproducts.addAll(en.getValue());
                }
            }
            mobileCategoryProducts.addAll(vendorproducts);
        }
        Display(mobileTable, mobileCategoryProducts);
        ArrayList<Product> kidsCategoryProducts = new ArrayList<>();
        for (Person employee : employees) {
            ArrayList<Product> vendorproducts = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Product>> en : employee.getProductsHashMap().entrySet()) {
                if (en.getKey().equals("Kids Corner")) {
                    vendorproducts.addAll(en.getValue());
                }
            }
            kidsCategoryProducts.addAll(vendorproducts);
        }
        Display(kidsTable, kidsCategoryProducts);

    }

    private void SetProductCatgeoryAndVendorName(ArrayList<Person> employees) {
        for (Person employee : employees) {
            String vendorName = employee.getName();
            for (Map.Entry<String, ArrayList<Product>> en : employee.getProductsHashMap().entrySet()) {
                String category = en.getKey();
                for (Product product : en.getValue()) {
                    product.setCategoryName(category);
                    product.setVendorName(vendorName);
                }
            }
        }
    }

    private void Display(JTable homeTable, ArrayList<Product> list) {
        homeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = homeTable.rowAtPoint(evt.getPoint());
                int col = homeTable.columnAtPoint(evt.getPoint());
                if (selectedRow >= 0 && col >= 0) {

                    String brand = homeTable.getValueAt(selectedRow, 0).toString();
                    String model = homeTable.getValueAt(selectedRow, 1).toString();
                    String price = homeTable.getValueAt(selectedRow, 2).toString();
                    //  String stock = homeTable.getValueAt(selectedRow, 3).toString();
                    String feature = homeTable.getValueAt(selectedRow, 3).toString();
                    String category = homeTable.getValueAt(selectedRow, 5).toString();
                    String vendor = homeTable.getValueAt(selectedRow, 6).toString();

                    ImageIcon img = (ImageIcon) homeTable.getValueAt(selectedRow, 4);
                    Product selectedproductObj = list.stream().filter(prdct -> prdct.getModel().equals(model)).findAny().orElse(null);
                    selectedProduct = new Product();
                    selectedProduct.setBrand_name(brand);
                    selectedProduct.setModel(model);
                    selectedProduct.setPrice(Integer.parseInt(price));
                    selectedProduct.setStock(selectedproductObj.getStock());
                    selectedProduct.setVendorName(vendor);
                    selectedProduct.setCategoryName(category);
                    productInfoBrandName.setText(brand);
                    productInfoModel.setText(model);
                    productInfoPrice.setText(price);
                    //productInfoStock.setText(stock);
                    productInfoFeature.setText(feature);
                    productPhoto.setIcon(img);
                    cardParentPanel.removeAll();
                    cardParentPanel.add(productInfoPanel);
                    cardParentPanel.repaint();
                    cardParentPanel.revalidate();
                    System.out.println(".mouseClicked()" + selectedRow + " " + col);
                }
            }
        });

        Object rowData[] = new Object[8];

        DefaultTableModel model = (DefaultTableModel) homeTable.getModel();
        model.setRowCount(0); //To clear mobileTable

        for (int i = 0; i < list.size(); i++) {
            rowData[0] = list.get(i).getBrand_name();
            rowData[1] = list.get(i).getModel();
            rowData[2] = list.get(i).getPrice();
            //   rowData[3] = list.get(i).getStock();
            rowData[3] = list.get(i).getDescription();

            String temp = list.get(i).getImageFileName();
            String path = Paths.get("products", temp).toString();
            try {
                ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
                Image resizedImage = ii.getImage();
                ii = new ImageIcon(resizedImage.getScaledInstance(160, 160, Image.SCALE_SMOOTH));
                rowData[4] = ii;
            } catch (Exception e) {
                logger.warn(e);
            }
            rowData[5] = list.get(i).getCategoryName();
            rowData[6] = list.get(i).getVendorName();
            model.addRow(rowData);
            homeTable.setRowHeight(150);
            homeTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchBarJPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        CartBarJPanel = new javax.swing.JPanel();
        allProductJBtn = new javax.swing.JButton();
        electronicsButton = new javax.swing.JButton();
        cartButton = new javax.swing.JButton();
        mobilesButton = new javax.swing.JButton();
        kidsButton = new javax.swing.JButton();
        orderStatusButton = new javax.swing.JButton();
        cardParentPanel = new javax.swing.JPanel();
        allProductsJPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        allProductsTable = new javax.swing.JTable();
        searchPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        electronicsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        electronicsTable = new javax.swing.JTable();
        cartPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cartItemTable = new javax.swing.JTable();
        billLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        buyButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        mobilesPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        mobileTable = new javax.swing.JTable();
        kidsPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        kidsTable = new javax.swing.JTable();
        productInfoPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        productPhoto = new javax.swing.JLabel();
        productInfoBrand = new javax.swing.JLabel();
        productInfoPriceL = new javax.swing.JLabel();
        productInfoModelL = new javax.swing.JLabel();
        productInfoFeatur = new javax.swing.JLabel();
        addToCart = new javax.swing.JButton();
        productInfoBrandName = new javax.swing.JLabel();
        productInfoModel = new javax.swing.JLabel();
        productInfoPrice = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productInfoFeature = new javax.swing.JTextArea();
        productInfoQty = new javax.swing.JLabel();
        productQtyField = new javax.swing.JTextField();
        backjButton = new javax.swing.JButton();
        orderStatusPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        viewDetailsjButton = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        orderHistoryTable = new javax.swing.JTable();
        shippingDetailsjPanel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        placeOrderBtn = new javax.swing.JButton();
        deliveryAddressField = new javax.swing.JTextField();
        fullnamejLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fullnameField = new javax.swing.JTextField();
        creditcardNoField = new javax.swing.JTextField();
        cvvField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        totalPriceField = new javax.swing.JTextField();
        emailid = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        orderDetailsPanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        orderDetailsTable = new javax.swing.JTable();
        backFromViewDetailsjButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1680, 980));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchBarJPanel.setBackground(new java.awt.Color(0, 153, 0));
        searchBarJPanel.setPreferredSize(new java.awt.Dimension(1680, 60));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        searchBarJPanel.add(jLabel4);

        add(searchBarJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 60));

        CartBarJPanel.setBackground(new java.awt.Color(0, 153, 0));
        CartBarJPanel.setPreferredSize(new java.awt.Dimension(1680, 50));

        allProductJBtn.setBackground(new java.awt.Color(0, 153, 0));
        allProductJBtn.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        allProductJBtn.setForeground(new java.awt.Color(255, 255, 255));
        allProductJBtn.setText("All ");
        allProductJBtn.setToolTipText("Check what's new!");
        allProductJBtn.setBorderPainted(false);
        allProductJBtn.setContentAreaFilled(false);
        allProductJBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allProductJBtn.setOpaque(true);
        allProductJBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                allProductJBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                allProductJBtnMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allProductJBtnMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                allProductJBtnMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                allProductJBtnMouseEntered(evt);
            }
        });
        allProductJBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allProductJBtnActionPerformed(evt);
            }
        });

        electronicsButton.setBackground(new java.awt.Color(0, 153, 0));
        electronicsButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        electronicsButton.setForeground(new java.awt.Color(255, 255, 255));
        electronicsButton.setText("Electronics");
        electronicsButton.setToolTipText("TV, Fridge, Electronic Devices");
        electronicsButton.setBorderPainted(false);
        electronicsButton.setContentAreaFilled(false);
        electronicsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        electronicsButton.setOpaque(true);
        electronicsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                electronicsButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                electronicsButtonMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                electronicsButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                electronicsButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                electronicsButtonMouseEntered(evt);
            }
        });
        electronicsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                electronicsButtonActionPerformed(evt);
            }
        });

        cartButton.setBackground(new java.awt.Color(0, 153, 0));
        cartButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        cartButton.setForeground(new java.awt.Color(255, 255, 255));
        cartButton.setText("Cart");
        cartButton.setToolTipText("View your cart.");
        cartButton.setBorder(null);
        cartButton.setBorderPainted(false);
        cartButton.setContentAreaFilled(false);
        cartButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cartButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cartButton.setMaximumSize(new java.awt.Dimension(67, 49));
        cartButton.setOpaque(true);
        cartButton.setPreferredSize(new java.awt.Dimension(67, 49));
        cartButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cartButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cartButtonMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cartButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cartButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cartButtonMouseEntered(evt);
            }
        });
        cartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartButtonActionPerformed(evt);
            }
        });
        cartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_OpenCart_24px.png")));

        mobilesButton.setBackground(new java.awt.Color(0, 153, 0));
        mobilesButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        mobilesButton.setForeground(new java.awt.Color(255, 255, 255));
        mobilesButton.setText("Mobiles");
        mobilesButton.setToolTipText("Latest mobiles available on store!");
        mobilesButton.setBorderPainted(false);
        mobilesButton.setContentAreaFilled(false);
        mobilesButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mobilesButton.setOpaque(true);
        mobilesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mobilesButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mobilesButtonMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mobilesButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mobilesButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mobilesButtonMouseEntered(evt);
            }
        });
        mobilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilesButtonActionPerformed(evt);
            }
        });

        kidsButton.setBackground(new java.awt.Color(0, 153, 0));
        kidsButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        kidsButton.setForeground(new java.awt.Color(255, 255, 255));
        kidsButton.setText("Kids Corner");
        kidsButton.setToolTipText("Toys, Teddys and much more!");
        kidsButton.setBorderPainted(false);
        kidsButton.setContentAreaFilled(false);
        kidsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kidsButton.setOpaque(true);
        kidsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                kidsButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                kidsButtonMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kidsButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kidsButtonMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kidsButtonMouseEntered(evt);
            }
        });
        kidsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kidsButtonActionPerformed(evt);
            }
        });

        orderStatusButton.setBackground(new java.awt.Color(0, 153, 0));
        orderStatusButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        orderStatusButton.setForeground(new java.awt.Color(255, 255, 255));
        orderStatusButton.setText("Order  Status");
        orderStatusButton.setToolTipText("View your cart.");
        orderStatusButton.setBorderPainted(false);
        orderStatusButton.setContentAreaFilled(false);
        orderStatusButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        orderStatusButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        orderStatusButton.setMaximumSize(new java.awt.Dimension(67, 49));
        orderStatusButton.setOpaque(true);
        orderStatusButton.setPreferredSize(new java.awt.Dimension(67, 49));
        orderStatusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderStatusButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CartBarJPanelLayout = new javax.swing.GroupLayout(CartBarJPanel);
        CartBarJPanel.setLayout(CartBarJPanelLayout);
        CartBarJPanelLayout.setHorizontalGroup(
            CartBarJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CartBarJPanelLayout.createSequentialGroup()
                .addComponent(allProductJBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(electronicsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mobilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kidsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 829, Short.MAX_VALUE)
                .addComponent(cartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        CartBarJPanelLayout.setVerticalGroup(
            CartBarJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CartBarJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(electronicsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mobilesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(kidsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(allProductJBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(orderStatusButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(CartBarJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1680, -1));

        cardParentPanel.setPreferredSize(new java.awt.Dimension(1680, 980));
        cardParentPanel.setLayout(new java.awt.CardLayout());

        allProductsTable.setBackground(new java.awt.Color(239, 240, 239));
        allProductsTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        allProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Price",  "Features", "Photo","Category","Vendor"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                java.lang.Integer.class,  javax.swing.Icon.class,java.lang.String.class,java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }

        });
        allProductsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allProductsTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        allProductsTable.getTableHeader().setReorderingAllowed(false);
        allProductsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                allProductsTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(allProductsTable);

        javax.swing.GroupLayout allProductsJPanelLayout = new javax.swing.GroupLayout(allProductsJPanel);
        allProductsJPanel.setLayout(allProductsJPanelLayout);
        allProductsJPanelLayout.setHorizontalGroup(
            allProductsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, allProductsJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1674, Short.MAX_VALUE)
                .addContainerGap())
        );
        allProductsJPanelLayout.setVerticalGroup(
            allProductsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allProductsJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 108, Short.MAX_VALUE))
        );

        cardParentPanel.add(allProductsJPanel, "card5");

        searchTable.setBackground(new java.awt.Color(239, 240, 239));
        searchTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Price", "Stock", "Features", "Photo"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                java.lang.Integer.class, java.lang.String.class, javax.swing.Icon.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }

        });
        searchTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        searchTable.getTableHeader().setReorderingAllowed(false);
        searchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(searchTable);

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 110, Short.MAX_VALUE))
        );

        cardParentPanel.add(searchPanel, "card9");

        electronicsTable.setBackground(new java.awt.Color(239, 240, 239));
        electronicsTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        electronicsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Price",  "Features", "Photo","Category","Vendor"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                java.lang.Integer.class,  javax.swing.Icon.class,java.lang.String.class,java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }

        });
        electronicsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        electronicsTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        electronicsTable.getTableHeader().setReorderingAllowed(false);
        electronicsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                electronicsTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(electronicsTable);

        javax.swing.GroupLayout electronicsPanelLayout = new javax.swing.GroupLayout(electronicsPanel);
        electronicsPanel.setLayout(electronicsPanelLayout);
        electronicsPanelLayout.setHorizontalGroup(
            electronicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
        );
        electronicsPanelLayout.setVerticalGroup(
            electronicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );

        cardParentPanel.add(electronicsPanel, "card4");

        jPanel8.setBackground(new java.awt.Color(250, 250, 250));

        cartItemTable.setBackground(new java.awt.Color(239, 240, 239));
        cartItemTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cartItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Model", "Price", "Quantity", "Item Cost"
            }
        ));
        cartItemTable.setEnabled(false);
        jScrollPane2.setViewportView(cartItemTable);
        if (cartItemTable.getColumnModel().getColumnCount() > 0) {
            cartItemTable.getColumnModel().getColumn(1).setHeaderValue("Model");
            cartItemTable.getColumnModel().getColumn(2).setHeaderValue("Price");
            cartItemTable.getColumnModel().getColumn(3).setHeaderValue("Quantity");
            cartItemTable.getColumnModel().getColumn(4).setHeaderValue("Item Cost");
        }

        billLabel.setBackground(new java.awt.Color(250, 250, 250));
        billLabel.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        billLabel.setForeground(new java.awt.Color(0, 153, 0));
        billLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel6.setBackground(new java.awt.Color(250, 250, 250));
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Dollars only");

        buyButton.setBackground(new java.awt.Color(0, 153, 0));
        buyButton.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        buyButton.setForeground(new java.awt.Color(255, 255, 255));
        buyButton.setText("Buy");
        buyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyButtonActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(250, 250, 250));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 0));
        jLabel7.setText("Total Bill:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(billLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Buy_24px.png")));

        javax.swing.GroupLayout cartPanelLayout = new javax.swing.GroupLayout(cartPanel);
        cartPanel.setLayout(cartPanelLayout);
        cartPanelLayout.setHorizontalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1680, Short.MAX_VALUE)
            .addGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cartPanelLayout.setVerticalGroup(
            cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cartPanelLayout.createSequentialGroup()
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardParentPanel.add(cartPanel, "card8");

        mobileTable.setBackground(new java.awt.Color(239, 240, 239));
        mobileTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        mobileTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Price",  "Features", "Photo","Category","Vendor Name"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                java.lang.Integer.class,  javax.swing.Icon.class,java.lang.String.class,java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }

        });
        mobileTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mobileTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        mobileTable.getTableHeader().setReorderingAllowed(false);
        mobileTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mobileTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(mobileTable);

        javax.swing.GroupLayout mobilesPanelLayout = new javax.swing.GroupLayout(mobilesPanel);
        mobilesPanel.setLayout(mobilesPanelLayout);
        mobilesPanelLayout.setHorizontalGroup(
            mobilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
        );
        mobilesPanelLayout.setVerticalGroup(
            mobilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );

        cardParentPanel.add(mobilesPanel, "card3");

        kidsTable.setBackground(new java.awt.Color(239, 240, 239));
        kidsTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        kidsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Price",  "Features", "Photo","Category","Vendor Name"
            }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class,
                java.lang.Integer.class,  javax.swing.Icon.class,java.lang.String.class,java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }

        });
        kidsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kidsTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        kidsTable.getTableHeader().setReorderingAllowed(false);
        kidsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kidsTableMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(kidsTable);

        javax.swing.GroupLayout kidsPanelLayout = new javax.swing.GroupLayout(kidsPanel);
        kidsPanel.setLayout(kidsPanelLayout);
        kidsPanelLayout.setHorizontalGroup(
            kidsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
        );
        kidsPanelLayout.setVerticalGroup(
            kidsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );

        cardParentPanel.add(kidsPanel, "card2");

        productInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        productInfoPanel.setPreferredSize(new java.awt.Dimension(1680, 900));
        productInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(102, 153, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(1680, 72));

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Product Information");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Info_48px.png")));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1150, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        productInfoPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1680, 60));
        jPanel4.getAccessibleContext().setAccessibleParent(searchBarJPanel);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(0, 153, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(1680, 860));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productPhoto.setBackground(new java.awt.Color(255, 255, 255));
        productPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        productPhoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        productPhoto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        productPhoto.setPreferredSize(new java.awt.Dimension(250, 270));
        jPanel5.add(productPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1311, 58, 319, 296));

        productInfoBrand.setBackground(new java.awt.Color(255, 255, 255));
        productInfoBrand.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        productInfoBrand.setForeground(new java.awt.Color(38, 114, 0));
        productInfoBrand.setText("Brand:");
        jPanel5.add(productInfoBrand, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 88, -1, 19));

        productInfoPriceL.setBackground(new java.awt.Color(255, 255, 255));
        productInfoPriceL.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        productInfoPriceL.setForeground(new java.awt.Color(38, 114, 0));
        productInfoPriceL.setText("Price:");
        jPanel5.add(productInfoPriceL, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 267, 47, -1));

        productInfoModelL.setBackground(new java.awt.Color(255, 255, 255));
        productInfoModelL.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        productInfoModelL.setForeground(new java.awt.Color(38, 114, 0));
        productInfoModelL.setText("Model:");
        jPanel5.add(productInfoModelL, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 181, -1, -1));

        productInfoFeatur.setBackground(new java.awt.Color(255, 255, 255));
        productInfoFeatur.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        productInfoFeatur.setForeground(new java.awt.Color(38, 114, 0));
        productInfoFeatur.setText("Features:");
        jPanel5.add(productInfoFeatur, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 27, 104, -1));

        addToCart.setBackground(new java.awt.Color(0, 153, 0));
        addToCart.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        addToCart.setForeground(new java.awt.Color(255, 255, 255));
        addToCart.setText("Add to cart");
        addToCart.setBorder(null);
        addToCart.setBorderPainted(false);
        addToCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToCartActionPerformed(evt);
            }
        });
        addToCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Add_Shopping_Cart_24px_1.png")));
        jPanel5.add(addToCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(1451, 618, 169, 50));

        productInfoBrandName.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        productInfoBrandName.setForeground(new java.awt.Color(38, 114, 0));
        jPanel5.add(productInfoBrandName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 88, 175, 19));

        productInfoModel.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        productInfoModel.setForeground(new java.awt.Color(38, 114, 0));
        jPanel5.add(productInfoModel, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 181, 166, 19));

        productInfoPrice.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        productInfoPrice.setForeground(new java.awt.Color(38, 114, 0));
        jPanel5.add(productInfoPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 267, 166, 19));

        productInfoFeature.setEditable(false);
        productInfoFeature.setColumns(20);
        productInfoFeature.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        productInfoFeature.setForeground(new java.awt.Color(38, 114, 0));
        productInfoFeature.setLineWrap(true);
        productInfoFeature.setRows(5);
        productInfoFeature.setBorder(null);
        productInfoFeature.setCaretColor(new java.awt.Color(0, 153, 0));
        productInfoFeature.setFocusable(false);
        productInfoFeature.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(productInfoFeature);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(366, 56, 333, 364));

        productInfoQty.setBackground(new java.awt.Color(255, 255, 255));
        productInfoQty.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        productInfoQty.setForeground(new java.awt.Color(0, 153, 0));
        productInfoQty.setText("Qty:");
        jPanel5.add(productInfoQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(1299, 633, -1, -1));

        productQtyField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        productQtyField.setForeground(new java.awt.Color(0, 153, 51));
        productQtyField.setText("1");
        productQtyField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 1, true));
        productQtyField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productQtyFieldActionPerformed(evt);
            }
        });
        jPanel5.add(productQtyField, new org.netbeans.lib.awtextra.AbsoluteConstraints(1353, 632, 57, -1));

        backjButton.setBackground(new java.awt.Color(0, 153, 0));
        backjButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        backjButton.setForeground(new java.awt.Color(255, 255, 255));
        backjButton.setText("Back ");
        backjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backjButtonActionPerformed(evt);
            }
        });
        jPanel5.add(backjButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 633, -1, -1));

        productInfoPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, -1, 830));

        cardParentPanel.add(productInfoPanel, "card8");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(1680, 980));

        viewDetailsjButton.setBackground(new java.awt.Color(0, 153, 0));
        viewDetailsjButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        viewDetailsjButton.setForeground(new java.awt.Color(255, 255, 255));
        viewDetailsjButton.setText("View Order");
        viewDetailsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDetailsjButtonActionPerformed(evt);
            }
        });

        orderHistoryTable.setBackground(new java.awt.Color(239, 240, 239));
        orderHistoryTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        orderHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cart Order", "Order total price", "Full Name", "Credit Card No", "CVV", "Shipping address", "Receiver", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderHistoryTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        jScrollPane8.setViewportView(orderHistoryTable);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(viewDetailsjButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(viewDetailsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout orderStatusPanelLayout = new javax.swing.GroupLayout(orderStatusPanel);
        orderStatusPanel.setLayout(orderStatusPanelLayout);
        orderStatusPanelLayout.setHorizontalGroup(
            orderStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1680, Short.MAX_VALUE)
            .addGroup(orderStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        orderStatusPanelLayout.setVerticalGroup(
            orderStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(orderStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(orderStatusPanelLayout.createSequentialGroup()
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardParentPanel.add(orderStatusPanel, "card8");

        shippingDetailsjPanel.setBackground(new java.awt.Color(255, 255, 255));
        shippingDetailsjPanel.setMinimumSize(new java.awt.Dimension(1680, 980));
        shippingDetailsjPanel.setPreferredSize(new java.awt.Dimension(1680, 980));
        shippingDetailsjPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(38, 114, 0));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel13.setBackground(new java.awt.Color(38, 114, 0));
        jLabel13.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Enter Delivery Address");

        placeOrderBtn.setBackground(new java.awt.Color(255, 255, 255));
        placeOrderBtn.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        placeOrderBtn.setForeground(new java.awt.Color(51, 153, 0));
        placeOrderBtn.setText("Place Order");
        placeOrderBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        placeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeOrderBtnActionPerformed(evt);
            }
        });

        deliveryAddressField.setBackground(new java.awt.Color(38, 114, 0));
        deliveryAddressField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        deliveryAddressField.setForeground(new java.awt.Color(255, 255, 255));
        deliveryAddressField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        fullnamejLabel.setBackground(new java.awt.Color(38, 114, 0));
        fullnamejLabel.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        fullnamejLabel.setForeground(new java.awt.Color(255, 255, 255));
        fullnamejLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fullnamejLabel.setText("Full Name");

        jLabel16.setBackground(new java.awt.Color(38, 114, 0));
        jLabel16.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Credit Card No");

        jLabel17.setBackground(new java.awt.Color(38, 114, 0));
        jLabel17.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("CVV");

        fullnameField.setBackground(new java.awt.Color(38, 114, 0));
        fullnameField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        fullnameField.setForeground(new java.awt.Color(255, 255, 255));
        fullnameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        fullnameField.setEnabled(false);
        fullnameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnameFieldActionPerformed(evt);
            }
        });

        creditcardNoField.setBackground(new java.awt.Color(38, 114, 0));
        creditcardNoField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        creditcardNoField.setForeground(new java.awt.Color(255, 255, 255));
        creditcardNoField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        creditcardNoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditcardNoFieldActionPerformed(evt);
            }
        });

        cvvField.setBackground(new java.awt.Color(38, 114, 0));
        cvvField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        cvvField.setForeground(new java.awt.Color(255, 255, 255));
        cvvField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        cvvField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cvvFieldActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(38, 114, 0));
        jLabel15.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Total Price");

        totalPriceField.setEditable(false);
        totalPriceField.setBackground(new java.awt.Color(38, 114, 0));
        totalPriceField.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        totalPriceField.setForeground(new java.awt.Color(255, 255, 255));
        totalPriceField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        totalPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalPriceFieldActionPerformed(evt);
            }
        });

        emailid.setBackground(new java.awt.Color(38, 114, 0));
        emailid.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        emailid.setForeground(new java.awt.Color(255, 255, 255));
        emailid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        emailid.setEnabled(false);
        emailid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailidActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(38, 114, 0));
        jLabel18.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Email ID");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(placeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fullnamejLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(77, 77, 77)))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailid)
                    .addComponent(creditcardNoField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                    .addComponent(fullnameField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cvvField)
                    .addComponent(totalPriceField)
                    .addComponent(deliveryAddressField))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fullnamejLabel)
                    .addComponent(fullnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailid, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(creditcardNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cvvField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel15))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deliveryAddressField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(placeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        shippingDetailsjPanel.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 53, 500, 410));

        jPanel13.setBackground(new java.awt.Color(38, 114, 0));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Customer Details");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        shippingDetailsjPanel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 50));

        cardParentPanel.add(shippingDetailsjPanel, "card10");

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(1680, 980));

        orderDetailsTable.setBackground(new java.awt.Color(250, 250, 250));
        orderDetailsTable.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        orderDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand Name", "Model", "Quantity", "Cost", "TotalCost"
            }
        ));
        orderDetailsTable.setEnabled(false);
        orderDetailsTable.setSelectionBackground(new java.awt.Color(0, 153, 0));
        jScrollPane9.setViewportView(orderDetailsTable);

        backFromViewDetailsjButton.setBackground(new java.awt.Color(0, 153, 0));
        backFromViewDetailsjButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        backFromViewDetailsjButton.setForeground(new java.awt.Color(255, 255, 255));
        backFromViewDetailsjButton.setText("Back >>");
        backFromViewDetailsjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backFromViewDetailsjButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1680, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(backFromViewDetailsjButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(backFromViewDetailsjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout orderDetailsPanelLayout = new javax.swing.GroupLayout(orderDetailsPanel);
        orderDetailsPanel.setLayout(orderDetailsPanelLayout);
        orderDetailsPanelLayout.setHorizontalGroup(
            orderDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1680, Short.MAX_VALUE)
            .addGroup(orderDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        orderDetailsPanelLayout.setVerticalGroup(
            orderDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
            .addGroup(orderDetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(orderDetailsPanelLayout.createSequentialGroup()
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        cardParentPanel.add(orderDetailsPanel, "card8");

        add(cardParentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void allProductJBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductJBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allProductJBtnMouseClicked

    private void allProductJBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductJBtnMouseEntered
        // TODO add your handling code here:;
        allProductJBtn.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_allProductJBtnMouseEntered

    private void allProductJBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductJBtnMouseExited
        // TODO add your handling code here:
        allProductJBtn.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_allProductJBtnMouseExited

    private void allProductJBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductJBtnMousePressed
        // TODO add your handling code here:
        allProductJBtn.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_allProductJBtnMousePressed

    private void allProductJBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductJBtnMouseReleased
        // TODO add your handling code here:
        allProductJBtn.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_allProductJBtnMouseReleased

    private void allProductJBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allProductJBtnActionPerformed
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(allProductsJPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_allProductJBtnActionPerformed

    private void electronicsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsButtonMouseClicked
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(electronicsPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_electronicsButtonMouseClicked

    private void electronicsButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsButtonMouseEntered
        // TODO add your handling code here:
        electronicsButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_electronicsButtonMouseEntered

    private void electronicsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsButtonMouseExited
        // TODO add your handling code here:
        electronicsButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_electronicsButtonMouseExited

    private void electronicsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsButtonMousePressed
        // TODO add your handling code here:
        electronicsButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_electronicsButtonMousePressed

    private void electronicsButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsButtonMouseReleased
        // TODO add your handling code here:
        electronicsButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_electronicsButtonMouseReleased

    private void electronicsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electronicsButtonActionPerformed
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(electronicsPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_electronicsButtonActionPerformed

    private void cartButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cartButtonMouseClicked

    private void cartButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartButtonMouseEntered
        // TODO add your handling code here:
        cartButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_cartButtonMouseEntered

    private void cartButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartButtonMouseExited
        // TODO add your handling code here:
        cartButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_cartButtonMouseExited

    private void cartButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartButtonMousePressed
        // TODO add your handling code here:
        cartButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_cartButtonMousePressed

    private void cartButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartButtonMouseReleased
        // TODO add your handling code here:
        cartButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_cartButtonMouseReleased
    private void DisplayCartItems(JTable cartItemTable, List<Item> cartItems) {
        Object rowData[] = new Object[5];

        DefaultTableModel model = (DefaultTableModel) cartItemTable.getModel();

        model.setRowCount(0); //To clear mobileTable

        for (int i = 0; i < cartItems.size(); i++) {
            rowData[0] = cartItems.get(i).getProduct().getBrand_name();
            rowData[1] = cartItems.get(i).getProduct().getModel();
            rowData[2] = cartItems.get(i).getProduct().getPrice();
            rowData[3] = cartItems.get(i).getQuantity();
            rowData[4] = cartItems.get(i).getTotalItemCost();
            model.addRow(rowData);
            cartItemTable.setRowHeight(150);
        }

    }
    private void cartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartButtonActionPerformed
        DisplayCartItems(cartItemTable, userAccount.getEmployee().getProductCartOrder().getItemlist());
        billLabel.setText(String.valueOf(userAccount.getEmployee().getProductCartOrder().getTotalprice()));
        cardParentPanel.removeAll();
        cardParentPanel.add(cartPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_cartButtonActionPerformed

    private void mobilesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobilesButtonMouseClicked
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(mobilesPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_mobilesButtonMouseClicked

    private void mobilesButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobilesButtonMouseEntered
        // TODO add your handling code here:
        mobilesButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_mobilesButtonMouseEntered

    private void mobilesButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobilesButtonMouseExited
        // TODO add your handling code here:
        mobilesButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_mobilesButtonMouseExited

    private void mobilesButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobilesButtonMousePressed
        // TODO add your handling code here:
        mobilesButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_mobilesButtonMousePressed

    private void mobilesButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobilesButtonMouseReleased
        // TODO add your handling code here:
        mobilesButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_mobilesButtonMouseReleased

    private void mobilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilesButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mobilesButtonActionPerformed

    private void kidsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsButtonMouseClicked
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(kidsPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_kidsButtonMouseClicked

    private void kidsButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsButtonMouseEntered
        // TODO add your handling code here:
        kidsButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_kidsButtonMouseEntered

    private void kidsButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsButtonMouseExited
        // TODO add your handling code here:
        kidsButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_kidsButtonMouseExited

    private void kidsButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsButtonMousePressed
        // TODO add your handling code here:
        kidsButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_kidsButtonMousePressed

    private void kidsButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsButtonMouseReleased
        // TODO add your handling code here:
        kidsButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_kidsButtonMouseReleased

    private void kidsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kidsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kidsButtonActionPerformed

    private void allProductsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_allProductsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_allProductsTableMouseClicked

    private void searchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTableMouseClicked

    private void electronicsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_electronicsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_electronicsTableMouseClicked

    private void buyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyButtonActionPerformed
        // TODO add your handling code here:
        try {
            CartOrder cuurentCart = userAccount.getEmployee().getProductCartOrder();
            if (cuurentCart.getItemlist().size() == 0) {
                JOptionPane.showMessageDialog(null, "Please add items to cart to buy");
                return;
            }
            fullnameField.setText(userAccount.getName());
            emailid.setText(userAccount.getEmail());
            creditcardNoField.setText("");
            cvvField.setText("");
            deliveryAddressField.setText("");
            totalPriceField.setText(String.valueOf(cuurentCart.getTotalprice()));
            cardParentPanel.removeAll();
            cardParentPanel.add(shippingDetailsjPanel);
            cardParentPanel.repaint();
            cardParentPanel.revalidate();
        } catch (Exception e) {
            logger.error("Error while buying the order");
            logger.error(e);
        }
    }//GEN-LAST:event_buyButtonActionPerformed

    private void mobileTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mobileTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mobileTableMouseClicked

    private void kidsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kidsTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kidsTableMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void addToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToCartActionPerformed
        try {
            Item item = new Item();
            item.setProduct(selectedProduct);
            item.setCost(Integer.parseInt(this.productInfoPrice.getText()));
            item.setQuantity(Integer.parseInt(this.productQtyField.getText()));
            userAccount.getEmployee().getProductCartOrder().addItem(item);
            JOptionPane.showMessageDialog(null, "Product added to cart!");
        } catch (Exception e) {
            logger.error("Error while adding item to cart");
            logger.error(e);
        }

    }//GEN-LAST:event_addToCartActionPerformed

    private void productQtyFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productQtyFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productQtyFieldActionPerformed

    private void backjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backjButtonActionPerformed
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(allProductsJPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_backjButtonActionPerformed

    private void orderStatusButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderStatusButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_orderStatusButtonMouseClicked

    private void orderStatusButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderStatusButtonMouseEntered
        // TODO add your handling code here:
        orderStatusButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_orderStatusButtonMouseEntered

    private void orderStatusButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderStatusButtonMouseExited
        // TODO add your handling code here:
        orderStatusButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_orderStatusButtonMouseExited

    private void orderStatusButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderStatusButtonMousePressed
        // TODO add your handling code here:
        orderStatusButton.setBackground(new Color(42, 162, 42));
    }//GEN-LAST:event_orderStatusButtonMousePressed

    private void orderStatusButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderStatusButtonMouseReleased
        // TODO add your handling code here:
        orderStatusButton.setBackground(new Color(0, 153, 0));
    }//GEN-LAST:event_orderStatusButtonMouseReleased

    private void orderStatusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderStatusButtonActionPerformed
        // TODO add your handling code here:
        DisplayOrderhistory(orderHistoryTable);
        cardParentPanel.removeAll();
        cardParentPanel.add(orderStatusPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_orderStatusButtonActionPerformed

    private void placeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_placeOrderBtnActionPerformed
        try {
            CartOrder order = userAccount.getEmployee().getProductCartOrder();
            order.setFullname(fullnameField.getText());
            order.setCreditCardNo(creditcardNoField.getText());
            order.setCvv(Integer.parseInt(cvvField.getText()));
            order.setShippingAddress(deliveryAddressField.getText());
            order.setEmail(emailid.getText());
            try {
                Maps.getAddressDistance(order.getShippingAddress());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid shipping address");
                return;
            }
            userAccount.getEmployee().disposeCart();
            OrderProcessWorkRequest request = new OrderProcessWorkRequest();
            request.setOrder(order);
            request.setSender(userAccount);
            request.setStatus("Sent for Payment Processing");

            Organization org = null;
            for (Organization organization : this.wholeSaleEnterprise.getOrganizationDirectory().getOrganizationList()) {
                if (organization instanceof PaymentOrganization) {
                    org = organization;
                    break;
                }
            }
            if (org != null) {
                org.getWorkQueue().getWorkRequestList().add(request);
                userAccount.getWorkQueue().getWorkRequestList().add(request);
            }
            JOptionPane.showMessageDialog(null, "Order has been placed successfully!");
            DisplayOrderhistory(orderHistoryTable);
            cardParentPanel.removeAll();
            cardParentPanel.add(orderStatusPanel);
            cardParentPanel.repaint();
            cardParentPanel.revalidate();
        } catch (Exception e) {
            logger.error("Error while placing the order");
            JOptionPane.showMessageDialog(null, "Error while placing order");
            logger.error(e);
        }

    }//GEN-LAST:event_placeOrderBtnActionPerformed

    private void cvvFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cvvFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cvvFieldActionPerformed

    private void fullnameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnameFieldActionPerformed

    private void totalPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalPriceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPriceFieldActionPerformed

    private void creditcardNoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditcardNoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditcardNoFieldActionPerformed

    private void emailidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailidActionPerformed

    private void viewDetailsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDetailsjButtonActionPerformed
        // TODO add your handling code here:
        int selectedRow = orderHistoryTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row!");
            return;
        }
        CartOrder cartOrder = (CartOrder) orderHistoryTable.getValueAt(selectedRow, 0);
        DisplayOrderDetails(orderDetailsTable, cartOrder);
        cardParentPanel.removeAll();
        cardParentPanel.add(orderDetailsPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_viewDetailsjButtonActionPerformed

    private void backFromViewDetailsjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backFromViewDetailsjButtonActionPerformed
        // TODO add your handling code here:
        cardParentPanel.removeAll();
        cardParentPanel.add(orderStatusPanel);
        cardParentPanel.repaint();
        cardParentPanel.revalidate();
    }//GEN-LAST:event_backFromViewDetailsjButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CartBarJPanel;
    private javax.swing.JButton addToCart;
    private javax.swing.JButton allProductJBtn;
    private javax.swing.JPanel allProductsJPanel;
    private javax.swing.JTable allProductsTable;
    private javax.swing.JButton backFromViewDetailsjButton;
    private javax.swing.JButton backjButton;
    public static javax.swing.JLabel billLabel;
    private javax.swing.JButton buyButton;
    private javax.swing.JPanel cardParentPanel;
    private javax.swing.JButton cartButton;
    public static javax.swing.JTable cartItemTable;
    private javax.swing.JPanel cartPanel;
    private javax.swing.JTextField creditcardNoField;
    private javax.swing.JTextField cvvField;
    private javax.swing.JTextField deliveryAddressField;
    private javax.swing.JButton electronicsButton;
    private javax.swing.JPanel electronicsPanel;
    private javax.swing.JTable electronicsTable;
    private javax.swing.JTextField emailid;
    private javax.swing.JTextField fullnameField;
    private javax.swing.JLabel fullnamejLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton kidsButton;
    private javax.swing.JPanel kidsPanel;
    private javax.swing.JTable kidsTable;
    private javax.swing.JTable mobileTable;
    private javax.swing.JButton mobilesButton;
    private javax.swing.JPanel mobilesPanel;
    private javax.swing.JPanel orderDetailsPanel;
    public static javax.swing.JTable orderDetailsTable;
    public static javax.swing.JTable orderHistoryTable;
    private javax.swing.JButton orderStatusButton;
    private javax.swing.JPanel orderStatusPanel;
    private javax.swing.JButton placeOrderBtn;
    private javax.swing.JLabel productInfoBrand;
    public javax.swing.JLabel productInfoBrandName;
    private javax.swing.JLabel productInfoFeatur;
    public javax.swing.JTextArea productInfoFeature;
    public javax.swing.JLabel productInfoModel;
    private javax.swing.JLabel productInfoModelL;
    private javax.swing.JPanel productInfoPanel;
    public javax.swing.JLabel productInfoPrice;
    private javax.swing.JLabel productInfoPriceL;
    private javax.swing.JLabel productInfoQty;
    public javax.swing.JLabel productPhoto;
    private javax.swing.JTextField productQtyField;
    private javax.swing.JPanel searchBarJPanel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTable searchTable;
    private javax.swing.JPanel shippingDetailsjPanel;
    private javax.swing.JTextField totalPriceField;
    private javax.swing.JButton viewDetailsjButton;
    // End of variables declaration//GEN-END:variables

    private void DisplayOrderhistory(JTable orderHistoryTable) {
        ArrayList<WorkRequest> customerWorkRequests = userAccount.getWorkQueue().getWorkRequestList();
        Object rowData[] = new Object[9];

        DefaultTableModel model = (DefaultTableModel) orderHistoryTable.getModel();

        model.setRowCount(0); //To clear mobileTable
        for (int i = 0; i < customerWorkRequests.size(); i++) {

            OrderProcessWorkRequest workRequest = (OrderProcessWorkRequest) customerWorkRequests.get(i);
            CartOrder order = workRequest.getOrder();
            boolean inventoryCheck = CheckIfAllItemsAreAvailable(order.getItemlist());
            rowData[0] = order;
            rowData[1] = order.getTotalprice();
            rowData[2] = order.getFullname();
            rowData[3] = order.getCreditCardNo();
            rowData[4] = order.getCvv();
            rowData[5] = order.getShippingAddress();
            rowData[6] = workRequest.getReceiver() != null ? workRequest.getReceiver().getUsername() : null;
            //rowData[7] = inventoryCheck == true ? "In Stock" : workRequest.getStatus();
            // String result = inventoryCheck == true ? "Out for delivery" : workRequest.getPaymentProcessResult();
            // rowData[8] = result == null ? "Waiting" : result;

            rowData[7] = workRequest.getStatus();
            //   String result = inventoryCheck == true ? "Out for delivery" : workRequest.getPaymentProcessResult();
            //   rowData[8] = result == null ? "Waiting" : result;

            model.addRow(rowData);
            orderHistoryTable.setRowHeight(150);
        }
    }

    private boolean CheckIfAllItemsAreAvailable(List<Item> itemlist) {
        for (Item item : itemlist) {
            if (!item.isAvailability()) {
                return false;
            }
        }
        return true;

    }

    private void DisplayOrderDetails(JTable orderDetailsTable, CartOrder order) {
        Object rowData[] = new Object[5];

        DefaultTableModel model = (DefaultTableModel) orderDetailsTable.getModel();
        model.setRowCount(0); //To clear mobileTable
        List<Item> listItems = order.getItemlist();
        for (int i = 0; i < listItems.size(); i++) {
            Item item = listItems.get(i);
            rowData[0] = item.getProduct().getBrand_name();
            rowData[1] = item.getProduct().getModel();
            rowData[2] = item.getQuantity();
            rowData[3] = item.getCost();
            rowData[4] = item.getTotalItemCost();
            model.addRow(rowData);
            orderDetailsTable.setRowHeight(150);
        }
    }

}
