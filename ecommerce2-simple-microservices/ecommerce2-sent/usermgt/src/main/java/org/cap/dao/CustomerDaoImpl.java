package org.cap.dao;

import org.cap.entities.Customer;
import org.cap.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerDaoImpl implements ICustomerDao {
    private Map<Integer, Customer> store = new HashMap<>();

    @Override
    public Customer findCustomerById(int id) {
        Customer customer = store.get(id);
        if(customer==null){
            throw new CustomerNotFoundException("customer not found for id="+id);
        }
        return customer;
    }

    @Override
    public List<Customer> fetchAll() {
        Collection<Customer>values =store.values();
        List<Customer>list=new ArrayList<>(values);
        return list;
    }

    @Override
    public void save(Customer customer) {
        int id=generateId();
        customer.setId(id);
        store.put(id, customer);
    }

    @Override
    public void update(Customer customer) {
      store.replace(customer.getId(),customer);
    }

    @Override
    public boolean remove(int id) {
        store.remove(id);
        return true;
    }

    @Override
    public boolean credentialsCorrect(int id, String password){
        if(password==null|| password.isEmpty()){
            return false;
        }
        Customer customer=store.get(id);
        if(customer==null){
          return false;
        }
        boolean passwordEquals=customer.getPassword().equals(password);
        return passwordEquals;
    }

    @Override
    public void addFaveProduct(int customerId,String productId){
       Customer customer =store.get(customerId);
      customer.setFavoriteProduct(productId);
    }

    private int generatedId;

    int generateId(){
        generatedId++;
        return generatedId;
    }

}
