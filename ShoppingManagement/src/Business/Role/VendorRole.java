/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.Organization.VendorOrganization;
import Business.Product.Product;
import Business.UserAccount.UserAccount;
import java.util.ArrayList;
import java.util.HashMap;
import userinterface.AdministrativeRole.AdminWorkAreaJPanel;
import javax.swing.JPanel;
import userinterface.CustomerRole.CustomerWorkAreaJPanel;
import userinterface.VendorRole.VendorWorkAreaJPanel;

/**
 *
 * @author raunak
 */
public class VendorRole extends Role {
     

    @Override
    public JPanel createWorkArea(JPanel backGroundJPanel, JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new VendorWorkAreaJPanel(backGroundJPanel, userProcessContainer, account, (VendorOrganization) organization, enterprise,business);
    }

}
