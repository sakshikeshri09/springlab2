package org.cap.controller;

import org.cap.dto.AddCustomerDto;
import org.cap.dto.CustomerAndProductDto;
import org.cap.dto.ProductDto;
import org.cap.entities.Customer;
import org.cap.exceptions.CustomerNotFoundException;
import org.cap.service.ICustomerService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/customers") //base url
public class CartRestController {
    private static final Logger Log = LoggerFactory.getLogger(CartRestController.class);

    @Value("${productservice.baseurl}")
    private String baseProductServiceUrl;
    public String getBaseProductServiceUrl(){
        return baseProductServiceUrl;
    }

    public void setBaseProductServiceUrl(String url){
        this.baseProductServiceUrl=url;
    }

    @Autowired
    private ICustomerService service;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * final url will be /customers/add
     */
    @PostMapping("/add")
    public ResponseEntity<Customer> addCustomer(@RequestBody AddCustomerDto dto) {
        Customer customer = convert(dto);
        service.save(customer);
        ResponseEntity<Customer> response = new ResponseEntity<>(customer, HttpStatus.OK);
        return response;
    }

    /**
     * fetches product from product service app
     */
    public ProductDto fetchProductById(String productId) {
        if (productId == null || productId.isEmpty()) {
            return null;
        }
        String url = baseProductServiceUrl + "/get/" + productId;
        System.out.println("constructed complete url="+url);
        ProductDto productDto = restTemplate.getForObject(url, ProductDto.class);
        System.out.println("product id=" + productId + " product name=" + productDto.getProductName());
        return productDto;

    }

    @GetMapping
    public ResponseEntity<List<CustomerAndProductDto>> fetchAllCustomers() {
        List<Customer> customers = service.fetchAll();
        List<CustomerAndProductDto> list = new ArrayList<>();
        for (Customer customer : customers) {
            ProductDto productDto = fetchProductById(customer.getFavoriteProduct());
            CustomerAndProductDto customerAndProductDto = convertToCustomerDto(customer, productDto);
            list.add(customerAndProductDto);
        }
        ResponseEntity<List<CustomerAndProductDto>> response = new ResponseEntity<>(list, HttpStatus.OK);
        return response;
    }

    /**
     * final url will be /customers/find/{id}
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<CustomerAndProductDto> findById(@PathVariable("id") int id) {
        Customer customer = service.findCustomerById(id);
        String productId = customer.getFavoriteProduct();
        ProductDto productDto = fetchProductById(productId);
        CustomerAndProductDto customerAndProductDto = convertToCustomerDto(customer, productDto);
        ResponseEntity<CustomerAndProductDto> response = new ResponseEntity<>(customerAndProductDto, HttpStatus.OK);
        return response;
    }

    /**
     * converts to CustomerAndProductDto
     */
    public CustomerAndProductDto convertToCustomerDto(Customer customer, ProductDto productDto) {
        CustomerAndProductDto customerAndProductDto = new CustomerAndProductDto();
        customerAndProductDto.setCustomerId(customer.getId());
        customerAndProductDto.setCustomerName(customer.getName());
        customerAndProductDto.setPassword(customer.getPassword());
        if (productDto != null) {
            customerAndProductDto.setProductId(productDto.getProductId());
            customerAndProductDto.setProductName(productDto.getProductName());
        }
        return customerAndProductDto;
    }


    public Customer convert(AddCustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPassword(dto.getPassword());
        return customer;
    }


    @PutMapping("/favorite/{cid}/{pid}")
    public ResponseEntity<CustomerAndProductDto> addFavoriteProduct(@PathVariable("cid") int customerId,
                                                                    @PathVariable("pid") String productId) {
        service.addFaveProduct(customerId, productId);
        Customer customer = service.findCustomerById(customerId);
        ProductDto productDto = fetchProductById(productId);
        CustomerAndProductDto customerAndProductDto = convertToCustomerDto(customer, productDto);
        ResponseEntity<CustomerAndProductDto> response = new ResponseEntity<>(customerAndProductDto, HttpStatus.OK);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable("id") int customerId) {
        boolean result = service.remove(customerId);
        ResponseEntity respose = new ResponseEntity(result, HttpStatus.OK);
        return respose;
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        Log.error("handleCustomerNotFound()", ex);
        String msg = ex.getMessage();
        ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleAll(Throwable ex) {
        Log.error("handleAll()", ex);// this will get logged
        String msg = ex.getMessage();
        ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }


}
