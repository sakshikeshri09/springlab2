package org.cap.dao;

import org.cap.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductDaoImpl implements IProductDao{
	private  Map<Integer,Product> store=new HashMap<>();
	
	public int id=0;
	
	
		
		
		
		
		
	
	public Map<Integer, Product> getStore() {
		return store;
	}

	public void setStore(Map<Integer, Product> store) {
		this.store = store;
	}

	@Override
	public List<Product> fetchAll() {
	Collection<Product> value=store.values();	
	List<Product> list=new ArrayList<>(value);
	return list;
		}

	@Override
	public Product findById(int id) {
		Product product=store.get(id);
		return product;
	}
	

	@Override
	public Product save(Product product) {
	int productId=generateId();
	//System.out.println(productId);
	product.setId(productId);
		store.put(productId, product);
		return product;
		
	}
   

	int generateId()
	{    
		id++;
		return  id;
	}
    
    
  
}
