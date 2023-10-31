package cz.cvut.fel.autoserviceIS.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.cvut.fel.autoserviceIS.dto.CustomerCarDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.mapper.CustomerCarMapper;
import cz.cvut.fel.autoserviceIS.model.Customer;
import cz.cvut.fel.autoserviceIS.model.CustomerCar;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.repository.CustomerCarRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderItemRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerOrderRepository;
import cz.cvut.fel.autoserviceIS.repository.CustomerRepository;
import cz.cvut.fel.autoserviceIS.repository.EmployeesCustomersOrderRepository;
import cz.cvut.fel.autoserviceIS.security.service.model.UserDetailsImpl;
import cz.cvut.fel.autoserviceIS.service.CustomerCarService;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerCarController.class})
@ExtendWith(SpringExtension.class)
class CustomerCarControllerTest {
    @Autowired
    private CustomerCarController customerCarController;

    @MockBean
    private CustomerCarService customerCarService;

    /**
     * Method under test: {@link CustomerCarController#getAllCars()}
     */
    @Test
    void testGetAllCars() throws Exception {
        when(customerCarService.getAllCars()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customerCar/all");
        MockMvcBuilders.standaloneSetup(customerCarController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerCarController#getAllCars()}
     */
    @Test
    void testGetAllCars2() throws Exception {
        when(customerCarService.getAllCars()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customerCar/all");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(customerCarController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testDeleteCar() throws EntityNotFoundException, Exception {
        Customer customer = new Customer();
        customer.setAccessType(AccessType.CUSTOMER_ACCESS);
        customer.setEmail("jane.doe@example.org");
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setInfo("Info");
        customer.setPassword("iloveyou");
        customer.setPhone("6625550144");
        customer.setSecondName("Second Name");
        customer.setUsername("janedoe");

        CustomerCarDto customerCarDto = new CustomerCarDto();
        customerCarDto.setAge(1);
        customerCarDto.setCondition("Condition");
        customerCarDto.setCustomer(customer);
        customerCarDto.setId(1L);
        customerCarDto.setLicensePlate("License Plate");
        customerCarDto.setModel("Model");
        when(customerCarService.findById(Mockito.<Long>any())).thenReturn(customerCarDto);
        doNothing().when(customerCarService).deleteCar(Mockito.<CustomerCarDto>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/customerCar/{carId}", 1L);
        MockMvcBuilders.standaloneSetup(customerCarController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link CustomerCarController#getCustomerCar(Long)}
     */
    @Test
    void testGetCustomerCar() throws Exception {
        when(customerCarService.findCustomerCars(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/customerCar/{customerId}", 1L);
        MockMvcBuilders.standaloneSetup(customerCarController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

