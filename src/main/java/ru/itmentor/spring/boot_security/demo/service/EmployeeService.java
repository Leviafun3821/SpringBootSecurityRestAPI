package ru.itmentor.spring.boot_security.demo.service;


import ru.itmentor.spring.boot_security.demo.model.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public void saveEmployee(Employee employee);
    public Employee getEmployee (int id);
    public void deleteEmployee (int id);
    Employee getCurrentEmployee();
}

