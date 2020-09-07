package com.spring.Employee_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.Employee_Service.dao.EmployeeDao;
import com.spring.Employee_Service.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public void insertEmployee(Employee emp) {
		employeeDao.save(emp);
		
	}

	@Override
	public Employee updateEmployee(int empid,Employee emp) {
		
		return employeeDao.save(emp);
	}

	@Override
	public void deleteEmployee(int id) {
		employeeDao.deleteById(id);
		
	}

	@Override
	public Employee getEmployees(int empId) {
		
		return employeeDao.findById(empId).get();
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return (List<Employee>) employeeDao.findAll();
	}

	
	@Override
	public List<Employee> getEmployeesByDept(int deptId) {
		
		return employeeDao.findByDeptId(deptId);
		
		 
	}

	

}
