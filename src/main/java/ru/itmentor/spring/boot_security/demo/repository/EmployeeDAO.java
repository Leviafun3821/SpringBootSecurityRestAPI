package ru.itmentor.spring.boot_security.demo.repository;


import ru.itmentor.spring.boot_security.demo.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees();
    public void saveEmployee(Employee employee);
    public Employee getEmployee(int id);
    public void deleteEmployee(int id);
    Optional<Employee> findByName(String name);
}

