package com.spring.Employee_Service.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.Employee_Service.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void getAllEmployeeTest() {
		List<Employee> employee = new ArrayList<Employee>();
		employee = employeeService.getAllEmployees();
		Assert.assertNotNull(employee);
		Assert.assertEquals(3, employee.size());

	}

	@Test
	public void addEmployeeTest() {

		Employee employee = new Employee(2, "Ratheesh", 22, 2);
		employeeService.insertEmployee(employee);
		Assert.assertNotNull(employee);
		Assert.assertEquals("Ratheesh", employee.getEmpName());

	}

	@Test
	public void updateEmployeeTest() {

		Employee employee = new Employee(2, "Suja", 22, 2);
		employeeService.updateEmployee(2, employee);
		Assert.assertNotNull(employee);
		Assert.assertEquals("Suja", employee.getEmpName());

	}

	@Test
	public void deleteEmployeeTest() {
		Employee employee = new Employee();
		Assert.assertEquals(5, employee.getDeptId());
		employeeService.deleteEmployee(5);

	}

}
