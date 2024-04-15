package com.demigod.labs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demigod.labs.entity.Employee;
import com.demigod.labs.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepo;

	public Employee addEmployee(Employee emp) throws Exception {
		if (emp.getName().length() < 3)
			throw new Exception("Please provide at least 3 letters.");
		return employeeRepo.save(emp);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}
}
