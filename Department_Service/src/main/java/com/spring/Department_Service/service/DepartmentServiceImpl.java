package com.spring.Department_Service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.Department_Service.dao.DepartmentDao;
import com.spring.Department_Service.model.Department;



@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao ;

	@Override
	public Department insertDepartment(Department dept) {
		return departmentDao.save(dept);
		
	}

	@Override
	public List<Department> getAllDepartments() {
		
		return  (List<Department>) departmentDao.findAll();
	}

	@Override
	public Department updateDepartment(int id,Department dept) {
		
		return departmentDao.save(dept);
	}

	@Override
	public void deleteDepartment(int deptId) {
		departmentDao.deleteById(deptId);
		
	}

	@Override
	public Department getDeptById(int deptId) {
		
		return departmentDao.findById(deptId).get();
	}

	

}
