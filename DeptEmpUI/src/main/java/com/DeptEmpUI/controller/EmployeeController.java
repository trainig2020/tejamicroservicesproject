package com.DeptEmpUI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DeptEmpUI.model.Department;
import com.DeptEmpUI.model.Employee;

@RestController
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/newEmp")
	public ModelAndView showFormForAdd(HttpServletRequest request) {
		logger.info("Create a field to add Employee");
		String Register  = "NewForm";
		HttpSession session1 = request.getSession();
		
		List<Employee> lst =(List<Employee>)session1.getAttribute("empLst");
		List<Department> deptlst = (List<Department>) session1.getAttribute("deptList");
		 
		//int did = lst.get(0).getDeptId();
		ModelAndView model = new ModelAndView("form");
		PagedListHolder<Employee> pagedListHolder = new PagedListHolder<Employee>(lst);
		pagedListHolder.setPageSize(3);

		model.addObject("maxPages", pagedListHolder.getPageCount());
		//Integer page =  (Integer) session1.getAttribute("pageEmp");
		
		
	    Integer page = pagedListHolder.getPageCount();

		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		model.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			model.addObject("empLst", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			model.addObject("empLst", pagedListHolder.getPageList());
		}
		model.addObject("deptLst", deptlst);
		//model.addObject("empLst", lst);
		session1.setAttribute("pageEmpAdd", page);
		model.addObject("Register", Register);
		model.addObject("addEmp", "regEmp");
		model.addObject("home", "homemp");
		return model;	
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveEmp")
	public ModelAndView saveEmployee( HttpServletRequest request,@ModelAttribute Employee employee, HttpServletResponse response) {
		HttpSession session1 = request.getSession();
		logger.info("Save the employee in DB");
		HttpSession session2 = request.getSession();
		int deptId =  Integer.parseInt(request.getParameter("deptId"));
		
		
		ModelAndView model = new ModelAndView("form");
		Employee employee1 = new Employee();
		employee1.setEmpId(employee.getEmpId());
		employee1.setEmpName(employee.getEmpName());
		employee1.setAge(employee.getAge());
		employee1.setDeptId(employee.getDeptId());
		
		
		restTemplate.postForObject("http://gateway-service/department/addEmp", employee, Employee.class);
		logger.warn("If added Employee doesnt show ...Please refresh the page");
		List<Employee> lst =(List<Employee>) session2.getAttribute("empLst");
		Integer page =  (Integer) session2.getAttribute("pageEmpAdd");
		PagedListHolder<Employee> pagedListHolder = new PagedListHolder<Employee>(lst);
		int pagerowvalue =  pagedListHolder.getNrOfElements();
		  if((pagerowvalue %3) ==0) {
			  return new ModelAndView("redirect:/listEmp?deptId="+deptId+"&page="+(page+1));
		  }
		  else {
		return new ModelAndView("redirect:/listEmp?deptId="+deptId+"&page="+page);
		  }
	}


	@SuppressWarnings("unchecked")
	@GetMapping(value = "/editEmp")
	public ModelAndView editEmployee(HttpServletRequest request) {
		logger.info("Select the field to edit the employee");
		int employeeId = Integer.parseInt(request.getParameter("empId"));
		int did =  Integer.parseInt(request.getParameter("deptId"));
		HttpSession session2 = request.getSession();
		List<Employee> lst =(List<Employee>) session2.getAttribute("empLst");
		
		session2.setAttribute("empLst", lst);
		ModelAndView model = new ModelAndView("form");
		
		PagedListHolder<Employee> pagedListHolder = new PagedListHolder<Employee>(lst);
		pagedListHolder.setPageSize(3);
		model.addObject("maxPages", pagedListHolder.getPageCount());
		Integer page =  (Integer) session2.getAttribute("pageEmp");
		System.out.println("Page value in list Emp  "+page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		model.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			model.addObject("empLst", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			model.addObject("empLst", pagedListHolder.getPageList());
		}
		
		model.addObject("home", "homemp");
		//model.addObject("empLst", lst);
		model.addObject("employeeid", employeeId);
		model.addObject("deptId", did);
		return model;
	}

	
	@PostMapping(value = "/updateEmp")
	public ModelAndView updateEmployee(HttpServletRequest request, @ModelAttribute Employee employee, HttpServletResponse response) {
		logger.info("Update the Employee in DB");
		HttpSession session2 = request.getSession();
		int employeeId = Integer.parseInt(request.getParameter("empId"));
		int deptId =  Integer.parseInt(request.getParameter("deptId"));
		
		
		
		
		  Employee employee1 = new Employee();
		  employee1.setEmpId(employee.getEmpId());
		  employee1.setEmpName(employee.getEmpName());
		  employee1.setAge(employee.getAge());
		  employee1.setDeptId(employee.getDeptId());
		 
		  restTemplate.put("http://gateway-service/department/updateEmp/"+employeeId, employee);
		  
		  Integer page =  (Integer) session2.getAttribute("pageEmp");
		  if(page!=null) {
			  return new ModelAndView("redirect:/listEmp?deptId="+deptId+"&page="+page);
		  }

		return new ModelAndView("redirect:/listEmp?deptId="+deptId);

	}

	@GetMapping(value = "/deleteEmp")
	public ModelAndView deleteEmployee(HttpServletRequest request) {
		logger.info("Delete the Employee In DB");
		HttpSession session2 = request.getSession();
		int employeeId = Integer.parseInt(request.getParameter("empId"));
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		restTemplate.delete("http://gateway-service/department/deleteEmp/"+employeeId);
		Integer page =  (Integer) session2.getAttribute("pageEmp");
		  if(page!=null) {
			  return new ModelAndView("redirect:/listEmp?deptId="+deptId+"&page="+page);
		  }
		return new ModelAndView("redirect:/listEmp?deptId="+deptId);
	}


}
