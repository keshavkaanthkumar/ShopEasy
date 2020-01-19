/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Role;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author raunak
 */
public abstract class Role {

    public enum RoleType {
        Admin("Admin"),
        StockManager("StockManager"),
        PaymentProcessor("Payment Processor"),
        InventoryManager("Inventory Manager"),
        Vendor("Vendor"),
        DeliveryPerson("Delivery Person");

        private String value;

        private RoleType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public abstract JPanel createWorkArea(JPanel backGroundJPanel,
            JPanel userProcessContainer,
            UserAccount account,
            Organization organization,
            Enterprise enterprise,
            EcoSystem business);

    @Override
    public String toString() {
        return this.getClass().getName();
    }

}
