package br.edu.iftm.Customer.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iftm.Customer.java.model.Customer;

@Service
public interface CustomerService {

    List <Customer> getAllCustomers();
    void saveCustomer(Customer customer);
    Customer getCustomerById(long id);
    void deleteCustomerById(long id);
}
