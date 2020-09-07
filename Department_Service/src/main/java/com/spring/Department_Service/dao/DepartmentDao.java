package com.spring.Department_Service.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.Department_Service.model.Department;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Integer> {

}
