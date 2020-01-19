/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Person;

import Business.Product.Product;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author raunak
 */
public class PeopleDirectory {

    private ArrayList<Person> peopleList;

    public PeopleDirectory() {
        peopleList = new ArrayList();
    }

    public ArrayList<Person> getEmployeeList() {
        return peopleList;
    }

    public Person createEmployee(String name) {
        Person person = new Person();
        HashMap<String, ArrayList<Product>> productsHashMap = new HashMap<>();
        productsHashMap.put("Mobiles", new ArrayList<Product>());
        productsHashMap.put("Electronics", new ArrayList<Product>());
        productsHashMap.put("Kids Corner", new ArrayList<Product>());
        person.setProductsHashMap(productsHashMap);
        person.setName(name);
        peopleList.add(person);
        return person;
    }
}
