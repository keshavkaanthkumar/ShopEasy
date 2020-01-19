/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.CustomerOrganization;
import Business.Organization.InventoryOrganization;
import Business.Organization.Organization;
import Business.Organization.VendorOrganization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;
import userinterface.CustomerRole.CustomerWorkAreaJPanel;
import userinterface.InventoryRole.InventoryWorkAreaJPanel;
import userinterface.VendorRole.VendorWorkAreaJPanel;

/**
 *
 * @author kesha
 */
public class InventoryRole extends Role{
    public JPanel createWorkArea(JPanel backGroundJPanel, JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
return new InventoryWorkAreaJPanel(userProcessContainer, account, (InventoryOrganization) organization, enterprise,business);    }
}
