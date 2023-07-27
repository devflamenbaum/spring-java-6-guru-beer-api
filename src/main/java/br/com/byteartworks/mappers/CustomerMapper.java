package br.com.byteartworks.mappers;

import br.com.byteartworks.dto.CustomerDTO;
import br.com.byteartworks.entities.Customer;
import org.mapstruct.Mapper;

/*
 *   @author: gabflbm. created on 19/07/2023 !
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
