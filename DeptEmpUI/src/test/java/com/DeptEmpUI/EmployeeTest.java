package com.DeptEmpUI;

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

import com.DeptEmpUI.controller.EmployeeController;
import com.DeptEmpUI.model.Employee;
import com.DeptEmpUI.model.EmployeeList;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeTest {

	@MockBean
	private EmployeeController employeeController;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void insertEmployee() throws Exception {
		Employee employee = new Employee(1, "Sandhiya", 22, 1);
		Mockito.when(restTemplate.postForObject("http://gateway-service/department/addEmp", employee, Employee.class))
				.thenReturn(employee);
		String req = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/saveEmp").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void getEmployees() throws Exception {
		List<Employee> emp = new ArrayList<Employee>();
		emp.add(new Employee(1, "sandhiya", 22, 1));
		emp.add(new Employee(2, "Suja", 26, 1));
		
		EmployeeList lst = new EmployeeList();
		lst.setEmployees(emp);
		
		Mockito.when(restTemplate.getForObject("http://gateway-service/department/emp/1", EmployeeList.class))
		.thenReturn(lst);
		String req = objectMapper.writeValueAsString(lst);
		mockMvc.perform(
		MockMvcRequestBuilders.get("/listDeptName").content(req).contentType(MediaType.APPLICATION_JSON_VALUE));
		Assert.assertEquals(2, lst.getEmployees().size());
	}

	@Test
	public void updateEmployee() throws Exception {
		Employee employee = new Employee(1, "Divya", 22, 1);
		verify(restTemplate, times(0)).put("http://gateway-service/department/updateEmp/1", employee);
		String req = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/updateEmp").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	public void deleteEmployee() throws Exception {
		Employee employee = new Employee(1, "Divya", 22, 1);
		verify(restTemplate,times(0)).delete("http://gateway-service/department/deleteEmp/1");
		String req = objectMapper.writeValueAsString(employee);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/deleteEmp").content(req).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}
}
