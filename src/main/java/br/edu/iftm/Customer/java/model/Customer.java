package br.edu.iftm.Customer.java.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "O nome é um campo obrigatório")
    @Size(min = 3, max = 150, message = "O nome deve conter entre 3 a 150 caracteres.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message= "O email é um campo obrigatório")
    @Email(message = "Insira um email válido.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message= "O telefone é um campo obrigatório")
    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Past(message = "A data de nascimento deve ser uma data no passado.")
    @NotNull(message = "A data de nascimento é um campo obrigatório.")
    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "O CPF é um campo obrigatório.")
    //@CPF(message = "Insira um CPF válido.")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "O endereço é um campo obrigatório.")
    @Column(nullable = false)
    private String address;


    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
             

