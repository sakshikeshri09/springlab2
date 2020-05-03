package org.cap.service;

import org.cap.entities.Product;

import java.util.List;

public interface IProductService {
	
	List<Product> fetchAll();
	
	Product findById(int id);
	
	Product save(Product product);
}
