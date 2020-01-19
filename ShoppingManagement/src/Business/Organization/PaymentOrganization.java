/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.PaymentProcessorRole;
import Business.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class PaymentOrganization extends Organization{

    public PaymentOrganization() {
        super(Organization.Type.Payment.getValue());
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new PaymentProcessorRole());
        return roles;
    }
     
   
    
    
}
