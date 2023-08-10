package br.com.byteartworks.services;

import br.com.byteartworks.dto.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 *   @author: gabflbm. created on 30/06/2023 !
 */
public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer);

    Boolean deleteById(UUID customerId);

    Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer);
}
