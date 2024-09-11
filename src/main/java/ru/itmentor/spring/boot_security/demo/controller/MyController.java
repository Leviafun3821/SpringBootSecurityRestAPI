package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.model.Employee;
import ru.itmentor.spring.boot_security.demo.service.AuthenticationService;
import ru.itmentor.spring.boot_security.demo.service.EmployeeService;

import java.util.List;

@Controller
public class MyController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/")
    public String showAllEmployees(Model model){
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("allEmps", allEmployees);

        return "all-employees";
    }

    @GetMapping("/addNewEmployee")
    public String addNewEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        model.addAttribute("isNewEmployee", true); // Добавляем флаг
        return "employee-info";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    @GetMapping("/updateInfo")
    public String updateEmployee(@RequestParam("empId") int id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);

        return "employee-info";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("empId") int id) {
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showCurrentEmployee(Model model) {
        Employee employee = employeeService.getCurrentEmployee();
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "profile";
        } else {
            return "redirect:/auth/login";
        }
    }
}
