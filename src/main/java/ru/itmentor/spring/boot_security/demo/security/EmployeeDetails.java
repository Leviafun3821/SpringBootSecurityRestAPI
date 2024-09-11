package ru.itmentor.spring.boot_security.demo.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.model.Employee;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmployeeDetails implements UserDetails {

    private final Employee employee;

    public EmployeeDetails(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(employee.getRole()));
    }

    @Override
    public String getPassword() {
        return this.employee.getPassword();
    }

    @Override
    public String getUsername() {
        return this.employee.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
