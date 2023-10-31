package cz.cvut.fel.autoserviceIS.controller;

import cz.cvut.fel.autoserviceIS.security.jwt.JwtAuthenticationResponse;
import cz.cvut.fel.autoserviceIS.security.model.LoginRequest;
import cz.cvut.fel.autoserviceIS.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final LoginService loginService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("User logged in");
        return ResponseEntity.ok(new JwtAuthenticationResponse(loginService.login(loginRequest.getUsername(), loginRequest.getPassword())));
    }
}