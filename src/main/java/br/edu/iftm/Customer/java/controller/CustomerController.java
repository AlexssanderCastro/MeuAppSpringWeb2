package br.edu.iftm.Customer.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.iftm.Customer.java.impl.CustomerServiceImpl;

@Controller
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/customer")
    public String listCustomers(Model model) {
        model.addAttribute("customerList", customerService.getAllCustomers());
        return "customer/index";
    }
}
