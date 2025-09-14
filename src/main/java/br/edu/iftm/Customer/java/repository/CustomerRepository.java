package br.edu.iftm.Customer.java.repository;

import br.edu.iftm.Customer.java.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
