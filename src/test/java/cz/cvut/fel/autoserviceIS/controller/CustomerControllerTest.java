package cz.cvut.fel.autoserviceIS.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerMapper;
import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.repository.CustomerCarRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.CustomerService;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    void testDeleteCustomer() throws Exception {
        when(customerService.findAll()).thenReturn(new ArrayList<>());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setFirstName("Jane");
        customerDto.setId(1L);
        customerDto.setInfo("Info");
        customerDto.setPassword("iloveyou");
        customerDto.setPhone("728987345");
        customerDto.setSecondName("Second Name");
        customerDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customer");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetCustomerById() throws EntityNotFoundException, Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setAccessType(AccessType.CUSTOMER_ACCESS);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setFirstName("Jane");
        customerDto.setId(1L);
        customerDto.setInfo("Info");
        customerDto.setPassword("iloveyou");
        customerDto.setPhone("728987345");
        customerDto.setSecondName("Second Name");
        customerDto.setUsername("janedoe");
        when(customerService.findById(Mockito.<Long>any())).thenReturn(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customer/{id}", 1L);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"secondName\":\"Second Name\",\"username\":\"janedoe\",\"email\":\"jane.doe@example"
                                        + ".org\",\"phone\":\"728987345\",\"accessType\":\"CUSTOMER_ACCESS\",\"password\":\"iloveyou\",\"info\":\"Info\"}"));
    }
}

