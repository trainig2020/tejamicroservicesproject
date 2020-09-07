package com.DeptEmpUI;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.DeptEmpUI.controller.DeptController;
import com.DeptEmpUI.model.Department;
import com.DeptEmpUI.model.DepartmentList;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(DeptController.class)
public class DepartmentTest {

	@MockBean
	private DeptController deptController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void insertDepartment() throws Exception {
		Department department = new Department(1, "Admin");
		when(
				restTemplate.postForObject("http://localhost:8084/department/addDept", department, Department.class))
				.thenReturn(department);
		String req = objectMapper.writeValueAsString(department);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/saveDept").content(req))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

	@Test
	public void getDepartments() throws Exception {
		List<Department> dept = new ArrayList<Department>();
		
		dept.add(new Department("Training"));
		dept.add(new Department("HR"));
		dept.add(new Department("Research"));
		DepartmentList lst = new DepartmentList();
		lst.setDepartments(dept);
		
		Mockito.when(restTemplate.getForObject("http://localhost:8084/department/dept", DepartmentList.class))
				.thenReturn(lst);
		String req = objectMapper.writeValueAsString(lst);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/listDept").content(req).contentType(MediaType.APPLICATION_JSON_VALUE));
		Assert.assertEquals(3, lst.getDepartments().size());
	}

	@Test
	public void updateDepartment() throws Exception {
		Department department = new Department(4, "HR");
		verify(restTemplate,times(0)).put("http://localhost:8084/department/updateDept/4", department);
		String req = objectMapper.writeValueAsString(department);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/updateDept").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void deleteDepartment() throws Exception {
		Department department = new Department(5, "Research");
		verify(restTemplate,times(0)).delete("http://localhost:8084/department/deleteDept/5");
		String req = objectMapper.writeValueAsString(department);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/deleteDept").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

}
