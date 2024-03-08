package com.gnt.oms.restimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.dao.EmployeeRepository;
import com.gnt.oms.entities.Employee;
import com.gnt.oms.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/apis/v1/")
public class EmployeeController  {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @SuppressWarnings("null")
    @PostMapping("/createEmployee")
    public Employee creatEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        @SuppressWarnings("null")
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exists with Id :"+ id));
        return ResponseEntity.ok(employee);
    }

    // Update Employee rest api
    @SuppressWarnings("null")
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exists with Id :"+ id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setLastName(employeeDetails.getLastName());
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @SuppressWarnings("null")
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exists with Id :"+ id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
        
    }
}
