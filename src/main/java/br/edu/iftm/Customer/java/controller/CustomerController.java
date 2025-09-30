package br.edu.iftm.Customer.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.iftm.Customer.java.model.Customer;
import br.edu.iftm.Customer.java.service.CustomerService;
import jakarta.validation.Valid;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/customer/save")
    public String save(@ModelAttribute @Valid Customer customer,BindingResult result,Model model) {
        System.out.println(customer);
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            if (customer.getId() != null) {
                return "customer/edit";
            }
            return "customer/create";
        }
        
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
