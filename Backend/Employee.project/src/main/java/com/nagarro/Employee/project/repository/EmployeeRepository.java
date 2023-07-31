package com.nagarro.Employee.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.Employee.project.model.Employee;

//maily JPA repository extends CRUD Opetations.

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
     
}
