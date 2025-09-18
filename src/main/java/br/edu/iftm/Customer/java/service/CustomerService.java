package br.edu.iftm.Customer.java.service;

import java.util.List;

import br.edu.iftm.Customer.java.model.Customer;


public interface CustomerService {

    List <Customer> getAllCustomers();
    void saveCustomer(Customer customer);
    Customer getCustomerById(long id);
    void deleteCustomerById(long id);
}
