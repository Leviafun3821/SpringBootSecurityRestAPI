package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Employee;
import ru.itmentor.spring.boot_security.demo.repository.EmployeeDAO;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationService authenticationService; // добавьте эту зависимость

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        if (employee.getId() == 0) {
            // Новый сотрудник
            if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            } else {
                throw new IllegalArgumentException("Password is required for new employees.");
            }
            employee.setRole("ROLE_USER");
            employeeDAO.saveEmployee(employee);
        } else {
            // Обновление существующего сотрудника
            Employee existingEmployee = employeeDAO.getEmployee(employee.getId());
            if (existingEmployee != null) {
                existingEmployee.setName(employee.getName());
                existingEmployee.setSurname(employee.getSurname());
                existingEmployee.setDepartment(employee.getDepartment());
                existingEmployee.setSalary(employee.getSalary());

                if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
                    existingEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
                }

                employeeDAO.saveEmployee(existingEmployee);
            }
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public Employee getEmployee(int id) {
        return employeeDAO.getEmployee(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    @Transactional
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }


    @Transactional
    public Employee getCurrentEmployee() {
        String username = authenticationService.getCurrentUsername();
        return employeeDAO.findByName(username).orElse(null);
    }
}

