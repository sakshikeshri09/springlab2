package org.cap.controller;

import org.cap.dto.ProductDto;
import org.cap.entities.Product;
import org.cap.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  java.util.*;

import javax.annotation.PostConstruct;

@RestController 
@RequestMapping("/products")
public class ProductRestController {

	@Autowired
	IProductService service;
   
	@GetMapping
	 public ResponseEntity<List<Product>> toFetchAll()
	{
		 List<Product> list=service.fetchAll();
		 ResponseEntity<List<Product>> response=new ResponseEntity<List<Product>>(list, HttpStatus.OK);
		 return response;
	}
	
	@GetMapping("/find/{id}")
	ResponseEntity<Product> findId(@PathVariable("id") int id)
	{
		Product product=service.findById(id);
		ResponseEntity<Product> response=new ResponseEntity<>(product,HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> toSave(@RequestBody ProductDto dto){
		
		Product product=convertDto(dto);
		Product product1=service.save(product);
		
		ResponseEntity<Product> response=new ResponseEntity<>(product1, HttpStatus.OK);
		return response;
		
	}

	public Product convertDto(ProductDto dto)
	{
		Product p=new Product();
		p.setPrice(dto.getPrice());
		p.setProductName(dto.getProductName());
		//System.out.println(p);
		return p;
	}
	
	
}
