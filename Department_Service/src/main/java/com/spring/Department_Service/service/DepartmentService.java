package com.spring.Department_Service.service;

import java.util.List;

import com.spring.Department_Service.model.Department;

public interface DepartmentService {
	
	public Department insertDepartment(Department dept);
	public List<Department> getAllDepartments();
	public Department updateDepartment(int id,Department dept);
	public void deleteDepartment(int deptId);
	public Department getDeptById(int deptId);



}
