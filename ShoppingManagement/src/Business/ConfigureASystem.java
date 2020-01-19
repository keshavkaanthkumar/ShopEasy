package Business;

import Business.Person.Person;
import Business.Enterprise.Enterprise;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Product.Product;
import Business.Role.AdminRole;
import Business.Role.CustomerRole;
import Business.Role.DeliveryRole;
import Business.Role.InventoryRole;
import Business.Role.PaymentProcessorRole;
import Business.Role.SystemAdminRole;
import Business.Role.VendorRole;
import Business.UserAccount.UserAccount;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rrheg
 */
public class ConfigureASystem {

    //static method which reads file of different vendors for products
    private static HashMap<String, ArrayList<Product>> ReadFromProductsExcel(String pathToCSv, String vendorName) throws FileNotFoundException, IOException {

        HashMap<String, ArrayList<Product>> productsListMap = new HashMap<>();
        String row = "";

        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCSv));
        int rowCount = 0;

        Product lastAddedProduct = null;
       
        while ((row = csvReader.readLine()) != null) {
            if (rowCount == 0) {
                rowCount++;
                continue;
            }
            String[] data = row.split(",");
            if (data.length == 1) {
                lastAddedProduct.getDescription().add(data[0]);
                continue;
            }
            if (data.length == 2) {
                lastAddedProduct.getDescription().add(data[0] + " " + data[1]);
                continue;
            }
            String category = data[6];
            Product product = new Product();
            product.setBrand_name(data[1]);
            product.setCategoryName(category);
            product.getDescription().add(data[7]);
            product.setImageFileName(data[5]);
            product.setModel(data[2]);
            product.setPrice(Integer.parseInt(data[3]));
            product.setStock(Integer.parseInt(data[4]));
            product.setVendorName(vendorName);
            
            if (productsListMap.containsKey(category)) {
                ArrayList<Product> arrayList = productsListMap.get(category);
                arrayList.add(product);
                lastAddedProduct = product;
            } else {
                ArrayList<Product> arrayList = new ArrayList<Product>();
                arrayList.add(product);
                lastAddedProduct = product;
                productsListMap.put(category, arrayList);
            }
            rowCount++;
            // do something with the data
        }

        csvReader.close();
        return productsListMap;
    }

    public static EcoSystem configure() {
        try {
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            Path vendor1Csv = Paths.get(currentPath.toString(), "Vendor-1_products.csv");
            Path vendor2Csv = Paths.get(currentPath.toString(), "Vendor-2_products.csv");
            Path vendor3Csv = Paths.get(currentPath.toString(), "Vendor-3_products.csv");

            HashMap<String, ArrayList<Product>> vendor1Products = ReadFromProductsExcel(vendor1Csv.toString(), "vendor1");
            HashMap<String, ArrayList<Product>> vendor2Products = ReadFromProductsExcel(vendor2Csv.toString(), "vendor2");
            HashMap<String, ArrayList<Product>> vendor3Products = ReadFromProductsExcel(vendor3Csv.toString(), "vendor3");
            
            EcoSystem system = EcoSystem.getInstance();
            Network network = system.createAndAddNetwork();
            network.setName("Boston");
            
            Enterprise marketEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("Market", Enterprise.EnterpriseType.Market);
            Organization adminOrganization = marketEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Admin);
            Person marketadminemployee = adminOrganization.getEmployeeDirectory().createEmployee("marketadmin");
            UserAccount marketadminua = adminOrganization.getUserAccountDirectory().createUserAccount("marketadmin", "marketadmin", marketadminemployee, new AdminRole());
            Organization customerOrganization = marketEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Customer);
            Person customerEmployee1 = customerOrganization.getEmployeeDirectory().createEmployee("customer1");
            UserAccount customerua1 = customerOrganization.getUserAccountDirectory().createCustomerUserAccount("Demo Customer1","gundal.b@husky.neu.edu","customer1", "customer1", customerEmployee1, new CustomerRole());

            Person customerEmployee2 = customerOrganization.getEmployeeDirectory().createEmployee("customer2");
            UserAccount customerua2 = customerOrganization.getUserAccountDirectory().createCustomerUserAccount("Demo Customer2","sivprakash.h@husky.neu.edu","customer2", "customer2", customerEmployee2, new CustomerRole());
            Organization paymentOrganization = marketEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Payment);
            Person paymentProcessorEmployee1 = paymentOrganization.getEmployeeDirectory().createEmployee("paymentProcessor1");
            UserAccount paymentProcessorua1 = paymentOrganization.getUserAccountDirectory().createUserAccount("paymentProcessor1", "paymentProcessor1", paymentProcessorEmployee1, new PaymentProcessorRole());
            Person paymentProcessorEmployee2 = paymentOrganization.getEmployeeDirectory().createEmployee("paymentProcessor2");
            UserAccount paymentProcessorua2 = paymentOrganization.getUserAccountDirectory().createUserAccount("paymentProcessor2", "paymentProcessor2", paymentProcessorEmployee2, new PaymentProcessorRole());

            Enterprise wholesaleEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("Wholesale", Enterprise.EnterpriseType.Wholesale);
            Organization wholesaleadminOrganization = wholesaleEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Admin);
            Person wholesaleadminemployee = wholesaleadminOrganization.getEmployeeDirectory().createEmployee("wholesaleadmin");
            UserAccount wholesaleadminua = wholesaleadminOrganization.getUserAccountDirectory().createUserAccount("wholesaleadmin", "wholesaleadmin", wholesaleadminemployee, new AdminRole());
            Organization vendorOrganization = wholesaleEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Vendor);
            Organization inventoryOrganization = wholesaleEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Inventory);
            Person vendorEmployee1 = vendorOrganization.getEmployeeDirectory().createEmployee("vendor1");
            vendorEmployee1.setProductsHashMap(vendor1Products);
            UserAccount vendorua1 = vendorOrganization.getUserAccountDirectory().createUserAccount("vendor1", "vendor1", vendorEmployee1, new VendorRole());
            Person vendorEmployee2 = vendorOrganization.getEmployeeDirectory().createEmployee("vendor2");
            vendorEmployee2.setProductsHashMap(vendor2Products);
            UserAccount vendorEmployeeua2 = vendorOrganization.getUserAccountDirectory().createUserAccount("vendor2", "vendor2", vendorEmployee2, new VendorRole());
            Person vendorEmployee3 = vendorOrganization.getEmployeeDirectory().createEmployee("vendor3");
            vendorEmployee3.setProductsHashMap(vendor3Products);
            UserAccount vendorEmployeeua3 = vendorOrganization.getUserAccountDirectory().createUserAccount("vendor3", "vendor3", vendorEmployee3, new VendorRole());
            Person inventoryEmployee1 = inventoryOrganization.getEmployeeDirectory().createEmployee("inventory1");
            UserAccount inventoryEmployeeua1 = inventoryOrganization.getUserAccountDirectory().createUserAccount("inventory1", "inventory1", inventoryEmployee1, new InventoryRole());
            //Create a network
            //create an enterprise  
            //initialize some organizations
            //have some employees
            //create user account
            
             Enterprise deliveryEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("Delivery", Enterprise.EnterpriseType.Delivery);
            Organization deliveryadminOrganization = deliveryEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Admin);
            Person deliveryadminemployee = deliveryadminOrganization.getEmployeeDirectory().createEmployee("deliveryadmin");
            UserAccount deliveryadminua = deliveryadminOrganization.getUserAccountDirectory().createUserAccount("deliveryadmin", "deliveryadmin", deliveryadminemployee, new AdminRole());
                       Organization deliveryOrganization = deliveryEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Delivery);
            Person deliveryEmployee1 = deliveryOrganization.getEmployeeDirectory().createEmployee("delivery1");
            UserAccount deliveryua1 = deliveryOrganization.getUserAccountDirectory().createUserAccount("delivery1", "delivery1", deliveryEmployee1, new DeliveryRole());
Person employee = system.getEmployeeDirectory().createEmployee("sysadmin");
            UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());

            return system;
        } catch (IOException ex) {
            Logger.getLogger(ConfigureASystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
