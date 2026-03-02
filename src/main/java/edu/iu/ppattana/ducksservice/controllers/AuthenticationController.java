package edu.iu.ppattana.ducksservice.controllers;

import edu.iu.ppattana.ducksservice.model.Customer;
import edu.iu.ppattana.ducksservice.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final CustomerRepository customerRepository;

    public AuthenticationController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody Customer customer) {
        try {
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String passwordEncoded = bc.encode(customer.getPassword());
            customer.setPassword(passwordEncoded);
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}