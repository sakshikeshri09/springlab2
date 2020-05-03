package org.cap.service;

import org.cap.entities.Customer;

import java.util.List;

public interface ICustomerService {
    Customer findCustomerById(int id);

    List<Customer> fetchAll();

    void save(Customer customer);

    void update(Customer customer);

    boolean remove(int id);

    boolean credentialsCorrect(int id, String password);

    void addFaveProduct(int customerId,String productId);
}
