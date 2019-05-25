package com.spring.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rest.model.Employee;
import com.spring.rest.model.Student;
import com.spring.rest.repository.EmployeeRepository;
import com.spring.rest.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	StorageService storageService;

	List<String> files = new ArrayList<String>();

	// GET /Employees Gets all employees
	@RequestMapping(name = "/Employees")
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	// GET /Employees/id Get employee by id
	@RequestMapping(value = "/Employees/{id}", method = RequestMethod.GET)
	public Employee getEmployee(@PathVariable String id) {
		return employeeRepository.findById(Long.valueOf(id)).get();
	}

	// POST /Employees Add employee
	@RequestMapping(value = "/Employees", method = RequestMethod.POST)
	public ResponseEntity<String> addStudent(@RequestParam("employee") String employee, 
			@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			Employee emp = new ObjectMapper().readValue(employee, Employee.class);
			emp.setFileName(file.getOriginalFilename());
			storageService.store(file);
			files.add(file.getOriginalFilename());
			employeeRepository.save(emp);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}

	}

	// PUT /Employees/id Update employee
	@RequestMapping(value = "/Employees/{id}", method = RequestMethod.PUT)
	public void updateStudent(@PathVariable String id, @RequestBody Employee employee) {

		for (Employee element : employeeRepository.findAll()) {
			if (element.getEmployeeID().equals(Long.valueOf(id)) && element.getEmployeeID().equals(Long.valueOf(id))) {
				employeeRepository.save(employee);
				return;
			}
		}
	}

	// DELETE /Employees/id Delete employee
	@RequestMapping(value = "/Employees/{id}", method = RequestMethod.DELETE)
	public void deleteStudent(@PathVariable String id) {
		employeeRepository.deleteById(Long.valueOf(id));
	}
	
	@PostMapping("/post")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.store(file);
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
}
