package org.cap.dao;

import org.cap.entities.Customer;

import java.util.List;

public interface ICustomerDao {

    Customer findCustomerById(int id);

    List<Customer>fetchAll();

    void save(Customer customer);

    void update(Customer customer);

    boolean credentialsCorrect(int id, String password);

    boolean remove(int id);
    void addFaveProduct(int customerId,String productId);
}
