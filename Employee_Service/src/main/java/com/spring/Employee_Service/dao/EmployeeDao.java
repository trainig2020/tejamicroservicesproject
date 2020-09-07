package com.spring.Employee_Service.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.Employee_Service.model.Employee;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
	
	public List<Employee> findByDeptId( int deptId);
	
	

}
