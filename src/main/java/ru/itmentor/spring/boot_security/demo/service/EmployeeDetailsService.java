package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.Employee;
import ru.itmentor.spring.boot_security.demo.repository.EmployeeRepository;
import ru.itmentor.spring.boot_security.demo.security.EmployeeDetails;

import java.util.Optional;


@Service
public class EmployeeDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
       Optional<Employee> employee = employeeRepository.findByName(name);

       if (employee.isEmpty()){
           throw new UsernameNotFoundException("User not found!");
       }
       return new EmployeeDetails(employee.get());
    }
}
