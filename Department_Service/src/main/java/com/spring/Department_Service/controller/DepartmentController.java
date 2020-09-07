package com.spring.Department_Service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.Department_Service.model.Department;
import com.spring.Department_Service.model.DepartmentList;
import com.spring.Department_Service.model.Employee;
import com.spring.Department_Service.model.EmployeeList;
import com.spring.Department_Service.service.DepartmentService;
import com.spring.Department_Service.service.EmployeeService;


@RestController
@RequestMapping("/department")
public class DepartmentController {
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/listDept")
	public List<Department> getAllDepartment(){
		return departmentService.getAllDepartments();
	}
	
	
	@GetMapping("/listDept/{id}")
	public Department getDeptById(@PathVariable int id) {
		return departmentService.getDeptById(id);
	}
	
	@GetMapping("/dept")
	public DepartmentList getDepartments(){
		
		List<Department> dept = departmentService.getAllDepartments();
		DepartmentList list = new DepartmentList();
		list.setDepartments(dept);
		logger.info("Department List");
		return list;
		
	}
	
	
	@PostMapping("/addDept")
	public Department addDepartment(@RequestBody Department department) {
		departmentService.insertDepartment(department);
		logger.info("Add Department in DB");
		return department;
	}
	
	
	
	@PutMapping("/updateDept/{id}")
	public Department updateDepartment(@RequestBody Department department, @PathVariable int id) {
		departmentService.updateDepartment(id,department);
		logger.info("Update a particular department in DB");
		return department;
	}
	
	
	
	@DeleteMapping("/deleteDept/{id}")
	public String deleteDepartment(@PathVariable int id) {
		departmentService.deleteDepartment(id);
		logger.info("Delete the department in DB");
		return "Record Deleted";
		
	}
	
	@GetMapping("listEmp")
	public EmployeeList getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("listEmp/{empid}")
	public Employee getEmployeeById(@PathVariable int empid) {
		return employeeService.getEmployeeById(empid);
	}
	
	
	@PostMapping("/addEmp")
	public Employee addEmployee(@RequestBody Employee employee) {
		logger.info("Add Employee in DB");
		return employeeService.insertEmployee(employee);
	}
	
	
	@PutMapping("/updateEmp/{empid}")
	public String updateEmployee(@RequestBody Employee employee, @PathVariable int empid) {
		 employeeService.updateEmployee(empid, employee);
		 logger.info("Update the particular Employee in DB");
		 return "Record Updated";
	}
	
	@DeleteMapping("/deleteEmp/{id}")
	public String deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployee(id);
		logger.info("Delete the Employee in DB");
		return "Record deleted";
	}
	
	@GetMapping("/emp/{deptId}")
	public EmployeeList getEmployeeByDeptId(@PathVariable int deptId) {
		logger.info("List all Employees based on Dept Id");
		return employeeService.getAllEmployeesByDeptId(deptId);
	}



}
