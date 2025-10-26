package br.edu.iftm.Customer.java.customer;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.edu.iftm.Customer.java.config.TestConfig;
import br.edu.iftm.Customer.java.controller.CustomerController;
import br.edu.iftm.Customer.java.model.Customer;
import br.edu.iftm.Customer.java.service.CustomerService;



@WebMvcTest(CustomerController.class)
@Import(TestConfig.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @AfterEach
    void resetMocks() {
        reset(customerService);
    }

    private List<Customer> createTestCustomerList(){
        Customer customerB = new Customer();
        customerB.setId(1L);
        customerB.setName("Customer B");
        customerB.setEmail("customer.b@example.com");
        customerB.setPhoneNumber("99999-8888");
        customerB.setDateOfBirth(LocalDate.of(1995, 5, 20));
        customerB.setCpf("123.456.789-00");
        customerB.setAddress("Rua Teste, 123");
        
        return List.of(customerB);
    }

    @Test
    @DisplayName("GET /customer - Listar Customers na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
         mockMvc.perform(get("/customer"))
            .andExpect(status().isUnauthorized()); // Correção aqui
    }

    @Test
    @WithMockUser
    @DisplayName("GET /customer - Listar Customers na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(createTestCustomerList());

        mockMvc.perform(get("/customer"))
               .andExpect(status().isOk())
               .andExpect(view().name("customer/index")) // O nome da view no controller é customer/index
               .andExpect(model().attributeExists("customerList")) // O nome do atributo no controller é customerList
               .andExpect(content().string(containsString("Listagem de Clientes")))
               .andExpect(content().string(containsString("Customer B")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /customer/create - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        mockMvc.perform(get("/customer/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/create")) // A view de criação/edição é a form.html
                .andExpect(model().attributeExists("customer"))
                .andExpect(content().string(containsString("Cadastrar Cliente")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /customer - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(createTestCustomerList());
       // Obter o HTML da página renderizada pelo controlador
       mockMvc.perform(get("/customer"))
            .andExpect(status().isOk())
            .andExpect(view().name("customer/index"))
            .andExpect(model().attributeExists("customerList"))
            .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/customer/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /customer/save - Falha na validação e retorna para o formulário")
    void testSavecustomerValidationError() throws Exception {
        // Cliente com nome em branco para causar erro de validação
        Customer customer = new Customer();
        customer.setName(""); // Campo inválido
        customer.setEmail("valid@email.com");
        customer.setPhoneNumber("12345-6789");
        customer.setDateOfBirth(LocalDate.of(2000, 1, 1));
        customer.setCpf("111.222.333-44");
        customer.setAddress("Rua Válida, 456");

        mockMvc.perform(post("/customer/save")
                        .with(csrf())
                        .flashAttr("customer", customer))
                .andExpect(view().name("customer/create")) // Em caso de erro, retorna para o formulário
                .andExpect(model().attributeHasErrors("customer"));

        verify(customerService, never()).saveCustomer(any(Customer.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /customer/save - Customer válido é salvo com sucesso")
    void testSaveValidcustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Novo Cliente Válido");
        customer.setEmail("novo.cliente@example.com");
        customer.setPhoneNumber("11111-2222");
        customer.setDateOfBirth(LocalDate.of(1988, 11, 15));
        customer.setCpf("987.654.321-10");
        customer.setAddress("Avenida Principal, 789");


        mockMvc.perform(post("/customer/save")
                        .with(csrf())
                        .flashAttr("customer", customer))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer"));

        verify(customerService).saveCustomer(any(Customer.class));
    }

}
