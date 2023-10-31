package cz.cvut.fel.autoserviceIS.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.autoserviceIS.dto.CustomerDto;
import cz.cvut.fel.autoserviceIS.dto.EmployeeDto;
import cz.cvut.fel.autoserviceIS.model.enums.AccessType;
import cz.cvut.fel.autoserviceIS.security.jwt.JwtAuthenticationResponse;
import cz.cvut.fel.autoserviceIS.security.model.LoginStatus;
import cz.cvut.fel.autoserviceIS.security.service.LoginService;
import cz.cvut.fel.autoserviceIS.service.CustomerService;
import cz.cvut.fel.autoserviceIS.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("oauth2authSuccessHandler")
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final LoginService loginService;
    private final ObjectMapper mapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String username = oauthUser.getAttribute("username");
        String firstName = oauthUser.getAttribute("firstName");
        String secondName = oauthUser.getAttribute("email");
        String phone = oauthUser.getAttribute("phone");
        String accessType = oauthUser.getAttribute("accessType");
        try {
            if (accessType!= null && accessType.equals(AccessType.CUSTOMER_ACCESS)) {
                CustomerDto customer = customerService.findByEmail(email);
            } else if (accessType!= null && (accessType.equals(AccessType.EMPLOYEE_ACCESS) || accessType.equals("admin_access"))) {
                EmployeeDto employee = employeeService.findByEmail(email);
            }
        } catch (EntityNotFoundException e) {
            if (accessType.equals(AccessType.CUSTOMER_ACCESS)) {
                CustomerDto customer = new CustomerDto();
                customer.setFirstName(firstName);
                customer.setSecondName(secondName);
                customer.setUsername(username);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setAccessType(AccessType.CUSTOMER_ACCESS);
                customerService.addCustomer(customer);
            } else  {
                EmployeeDto employee = new EmployeeDto();
                employee.setFirstName(firstName);
                employee.setSecondName(secondName);
                employee.setUsername(username);
                employee.setEmail(email);
                employee.setPhone(phone);
                if (accessType.equals(AccessType.EMPLOYEE_ACCESS)) {
                    employee.setAccessType(AccessType.EMPLOYEE_ACCESS);
                } else if (accessType.equals(AccessType.ADMIN_ACCESS)) {
                    employee.setAccessType(AccessType.ADMIN_ACCESS);
                }
                employeeService.addEmployee(employee);
            }
        }
        final LoginStatus loginStatus = new LoginStatus(true, username, null, authentication.isAuthenticated());
        response.setStatus(HttpStatus.OK.value());
        ResponseEntity<?> entity = ResponseEntity.ok(new JwtAuthenticationResponse(loginService.login(username, "")));
        String json = new ObjectMapper().writeValueAsString(entity.getBody());
        ServletOutputStream out = response.getOutputStream();
        out.print(json);
        mapper.writeValue(out, loginStatus);
    }
}
