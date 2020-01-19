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
import Business.UserAccount.UserAccount;
import userinterface.AdministrativeRole.AdminWorkAreaJPanel;
import javax.swing.JPanel;
import userinterface.CustomerRole.CustomerWorkAreaJPanel;

/**
 *
 * @author raunak
 */
public class CustomerRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel backGroundJPanel, JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new CustomerWorkAreaJPanel(backGroundJPanel, userProcessContainer, account, (CustomerOrganization) organization, business, enterprise);
    }

}
