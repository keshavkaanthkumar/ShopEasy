/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import userinterface.CustomerRole.CustomerWorkAreaJPanel;
import javax.swing.JPanel;
import userinterface.PaymentProcessRole.PaymentProcessWorkAreaJPanel;

/**
 *
 * @author raunak
 */
public class PaymentProcessorRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel backGroundJPanel, JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        return new PaymentProcessWorkAreaJPanel(backGroundJPanel, userProcessContainer, account, organization, business);
    }

}
