package org.cap.dao;

import org.cap.entities.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IProductDao {

	List<Product> fetchAll();
	
	Product findById(int id);
	
	Product save(Product product);
}
