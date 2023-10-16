package br.com.byteartworks.controllers;

import br.com.byteartworks.config.SpringSecConfig;
import br.com.byteartworks.dto.CustomerDTO;
import br.com.byteartworks.services.CustomerService;
import br.com.byteartworks.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 *   @author: gabflbm. created on 04/07/2023 !
 */
@WebMvcTest(CustomerController.class)
@Import(SpringSecConfig.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertEquals(testCustomer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(customerMap.get("customerName"), customerArgumentCaptor.getValue().getCustomerName());

    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteById(uuidArgumentCaptor.capture());

        assertEquals(testCustomer.getId(), uuidArgumentCaptor.getValue());
    }


    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.updateCustomerById(any(), any())).willReturn(Optional.of(testCustomer));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(UUID.class),any(CustomerDTO.class));
    }

    @Test
    void testCreateNewCustomer() throws Exception {
        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);
        testCustomer.setId(null);
        testCustomer.setVersion(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class))).willReturn(testCustomer);

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void ListAllCustomers() throws Exception {

        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCustomerById() throws Exception {

        CustomerDTO testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(Optional.of(testCustomer));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                        .with(httpBasic(BeerControllerTest.USERNAME, BeerControllerTest.PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));

    }
}
