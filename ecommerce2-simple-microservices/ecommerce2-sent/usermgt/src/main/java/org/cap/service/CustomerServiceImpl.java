package org.cap.service;

import org.cap.dao.ICustomerDao;
import org.cap.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    private ICustomerDao customerDao;

    public ICustomerDao getCustomerDao() {
        return customerDao;
    }

    @Autowired
    public void setCustomerDao(ICustomerDao dao) {
        this.customerDao = dao;
    }

    @Override
    public Customer findCustomerById(int id) {
        Customer customer = customerDao.findCustomerById(id);
        return customer;
    }

    @Override
    public List<Customer> fetchAll() {
        List<Customer>customers=customerDao.fetchAll();
        return customers;
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public boolean remove(int id) {
      boolean result=  customerDao.remove(id);
      return result;
    }

    @Override
    public boolean credentialsCorrect(int id, String password) {
        boolean correct = customerDao.credentialsCorrect(id, password);
        return correct;
    }

    @Override
    public void addFaveProduct(int customerId, String productId) {
        customerDao.addFaveProduct(customerId,productId);
    }
}
