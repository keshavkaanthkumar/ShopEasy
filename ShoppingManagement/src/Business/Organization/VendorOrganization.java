/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Role.AdminRole;
import Business.Role.Role;
import Business.Role.VendorRole;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class VendorOrganization extends Organization{

    public VendorOrganization() {
        super(Type.Vendor.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList();
        roles.add(new VendorRole());
        return roles;
    }
     
}
