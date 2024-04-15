package com.demigod.labs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demigod.labs.entity.Employee;
import com.demigod.labs.service.EmployeeService;

@RestController
@RequestMapping("emp")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	@PostMapping("/add")
	public ResponseEntity<?> addEmployee(@RequestBody Employee emp) throws Exception {
		return new ResponseEntity<Employee>(empService.addEmployee(emp), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<List<Employee>>(empService.getAllEmployees(), HttpStatus.OK);
	}

}
