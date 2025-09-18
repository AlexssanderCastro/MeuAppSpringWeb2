package br.edu.iftm.Customer.java.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.Customer.java.model.Customer;
import br.edu.iftm.Customer.java.repository.CustomerRepository;
import br.edu.iftm.Customer.java.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;


     @Override
    public List <Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer){
        this.customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(long id) {
        Optional < Customer > optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public void deleteCustomerById(long id) {
        this.customerRepository.deleteById(id);
    }
}
