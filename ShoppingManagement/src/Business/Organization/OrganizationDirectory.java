/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Organization;

import Business.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author raunak
 */
public class OrganizationDirectory {

    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    public Organization createOrganization(Type type) {
        Organization organization = null;
        if (type.getValue().equals(Type.Inventory.getValue())) {
            organization = new InventoryOrganization();
            organizationList.add(organization);
        } else if (type.getValue().equals(Type.Payment.getValue())) {
            organization = new PaymentOrganization();
            organizationList.add(organization);
        } else if (type.getValue().equals(Type.Customer.getValue())) {
            organization = new CustomerOrganization();
            organizationList.add(organization);
        } else if (type.getValue().equals(Type.Vendor.getValue())) {
            organization = new VendorOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.Delivery.getValue())) {
            organization = new DeliveryOrganization();
            organizationList.add(organization);
        }
         else if (type.getValue().equals(Type.Admin.getValue())) {
            organization = new AdminOrganization();
            organizationList.add(organization);
        }
        return organization;
    }
}
