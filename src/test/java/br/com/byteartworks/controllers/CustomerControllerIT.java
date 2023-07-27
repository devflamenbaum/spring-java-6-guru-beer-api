package br.com.byteartworks.controllers;

import br.com.byteartworks.dto.CustomerDTO;
import br.com.byteartworks.entities.Customer;
import br.com.byteartworks.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 *   @author: gabflbm. created on 26/07/2023 !
 */
@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());

        assertThat(dto.getCustomerName()).isEqualTo(customer.getCustomerName());

    }

    @Test
    void testListCustomers() {

        List<CustomerDTO> list = customerController.listAllCustomers();

        assertThat(list.size()).isEqualTo(3);
    }
}
