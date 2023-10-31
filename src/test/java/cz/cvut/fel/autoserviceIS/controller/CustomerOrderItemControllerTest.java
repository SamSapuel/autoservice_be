package cz.cvut.fel.autoserviceIS.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.autoserviceIS.dto.CustomerOrderItemDto;
import cz.cvut.fel.autoserviceIS.exception.EntityNotFoundException;
import cz.cvut.fel.autoserviceIS.service.CustomerOrderItemService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerOrderItemController.class})
@ExtendWith(SpringExtension.class)
class CustomerOrderItemControllerTest {
    @Autowired
    private CustomerOrderItemController customerOrderItemController;

    @MockBean
    private CustomerOrderItemService customerOrderItemService;

    @Test
    void testCreateCustomerOrderItem() throws EntityNotFoundException, Exception {
        when(customerOrderItemService.createCustomerOrderItem(Mockito.<Long>any(),
                Mockito.<List<CustomerOrderItemDto>>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .post("/api/v1/customerOrderItem/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(customerOrderItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
    @Test
    void testDeleteCustomerOrderItem() throws EntityNotFoundException, Exception {
        doNothing().when(customerOrderItemService)
                .deleteCustomerOrderItem(Mockito.<Long>any(), Mockito.<CustomerOrderItemDto>any());

        CustomerOrderItemDto customerOrderItemDto = new CustomerOrderItemDto();
        customerOrderItemDto.setDescription("The characteristics");
        customerOrderItemDto.setId(1L);
        customerOrderItemDto.setTotalPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(customerOrderItemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/customerOrderItem/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerOrderItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateCustomerOrderItemDescOrPrice() throws EntityNotFoundException, Exception {
        CustomerOrderItemDto customerOrderItemDto = new CustomerOrderItemDto();
        customerOrderItemDto.setDescription("The characteristics");
        customerOrderItemDto.setId(1L);
        customerOrderItemDto.setTotalPrice(1);
        when(customerOrderItemService.updateCustomerOrderItemDescriptionOrTotalPrice(Mockito.<Long>any(),
                Mockito.<CustomerOrderItemDto>any())).thenReturn(customerOrderItemDto);

        CustomerOrderItemDto customerOrderItemDto2 = new CustomerOrderItemDto();
        customerOrderItemDto2.setDescription("The characteristics");
        customerOrderItemDto2.setId(1L);
        customerOrderItemDto2.setTotalPrice(1);
        String content = (new ObjectMapper()).writeValueAsString(customerOrderItemDto2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/customerOrderItem/{orderId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerOrderItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"description\":\"The characteristics\",\"totalPrice\":1}"));
    }
}

