package com.spring.Department_Service.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.spring.Department_Service.dao.DepartmentDao;
import com.spring.Department_Service.model.Department;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentTest {

	@Mock
	private DepartmentDao departmentDao;

	@InjectMocks
	private DepartmentService departmentService = new DepartmentServiceImpl();

	@Test
	public void getAllDepartmentTest() {

		List<Department> dept = new ArrayList<Department>();
		dept.add(new Department("Admin"));
		dept.add(new Department("Training"));
		dept.add(new Department("HR"));
		dept.add(new Department("Research"));

		Mockito.when(departmentDao.findAll()).thenReturn(dept);
		List<Department> deptList = departmentService.getAllDepartments();
		Assert.assertEquals(4, deptList.size());
		Assert.assertEquals(deptList, dept);

	}

	@Test
	public void addDepartmentTest() {

		Department dept = new Department("Developer");
		Mockito.when(departmentDao.save(dept)).thenReturn(dept);
		Department dept1 = departmentService.insertDepartment(dept);
		Assert.assertNotNull(dept1);
		Assert.assertEquals("Developer", dept1.getDeptName());

	}

	@Test
	public void updateDepartmentTest() {

		Department dept = new Department(4, "Human Resources");
		Mockito.when(departmentDao.save(dept)).thenReturn(dept);
		Department dept1 = (Department) departmentService.updateDepartment(4, dept);
		Assert.assertEquals("Human Resources", dept.getDeptName());
		Assert.assertEquals(4, dept1.getDeptId());
		

	}

	@Test
	public void deleteDepartmentTest() {
		Department dept = new Department(4, "Human Resources");
		Assert.assertEquals(4, dept.getDeptId());
		departmentService.deleteDepartment(4);
		Assert.assertNotNull(dept);
		

	}

}
