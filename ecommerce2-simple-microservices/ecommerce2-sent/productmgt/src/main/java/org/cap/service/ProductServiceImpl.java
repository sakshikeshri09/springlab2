package org.cap.service;

import org.cap.dao.IProductDao;
import org.cap.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

	
	private IProductDao dao;
	
	public IProductDao getDao() {
		return dao;
	}

	@Autowired
	public void setDao(IProductDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Product> fetchAll() {
		List<Product > list=dao.fetchAll();
		return list;
	}

	@Override
	public Product findById(int id) {
		 Product product=dao.findById(id);
		return product;
	}

	@Override
	public Product save(Product product) {
		 Product p= dao.save(product);
		return p;
	}
   

	
}
