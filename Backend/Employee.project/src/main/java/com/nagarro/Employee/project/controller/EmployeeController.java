package com.nagarro.Employee.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.Employee.project.exception.ResourceNotFoundException;
import com.nagarro.Employee.project.model.Employee;
import com.nagarro.Employee.project.repository.EmployeeRepository;

//Here we create REST APIs
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController{
  
	//by using autowired it will automatically inject in spring container
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	//get all employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	// create employee REST api or It save the emoloyee into our SQL database
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//This is created for getting employee details with its employee ID.
	//Here we use @path variable annotation because we hava to map the mapping id with function parameter ID
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		return ResponseEntity.ok(employee);
	}
	
	//Update employee REST API
	//@PutMapping is mainly used for update data in database
	//Here @RequestBody annotation is used for mapping JSON object with java object.
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		
		//retreve the old employee from the database
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		//setting the new employee details to the older employee details
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		
		//setting the new updated employee object to the database.
		Employee updatedEmployee = employeeRepository.save(employee);
		
		//ResponseEntity.ok() is mainly 200 status.
		return ResponseEntity.ok(updatedEmployee);
	}
	
	//Delete employee REST API
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
