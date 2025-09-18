package br.edu.iftm.Customer.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.Customer.java.impl.CustomerServiceImpl;
import br.edu.iftm.Customer.java.model.Customer;

@Controller
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/customer/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/customer/save")
    public String postMethodName(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer";
    }

    @GetMapping("/customer/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.customerService.deleteCustomerById(id);
        return "redirect:/customer";
    }

    @GetMapping("/customer/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return "customer/edit";
    }

    @GetMapping("/customer")
    public String listCustomers(Model model) {
        model.addAttribute("customerList", customerService.getAllCustomers());
        return "customer/index";
    }
}
