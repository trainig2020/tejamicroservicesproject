package com.DeptEmpUI.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.DeptEmpUI.model.Department;
import com.DeptEmpUI.model.DepartmentList;
import com.DeptEmpUI.model.Employee;
import com.DeptEmpUI.model.EmployeeList;

@RestController
public class DeptController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/")
	public ModelAndView loginPage() {
		return new ModelAndView("redirect:/listDept");

	}
	
	
	
	@GetMapping("/home")
	public ModelAndView listDeptId(HttpServletRequest request, ModelAndView modelAndView,@RequestParam(required = false) Integer page) {
		DepartmentList lst =  restTemplate.getForObject("http://gateway-service/department/dept", DepartmentList.class);
		int deptId = lst.getDepartments().get(0).getDeptId();
		modelAndView.addObject("emplang", "lang");
		System.out.println(page);
		if(page!=null) {
			return new ModelAndView("redirect:/listEmp?deptId=" + deptId+"&page="+page);
		}
		return new ModelAndView("redirect:/listEmp?deptId=" + deptId);

	}

	
	@GetMapping("/listEmp")
	public ModelAndView listDeptName(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) Integer page) {
		HttpSession httpSession = request.getSession();
		int id = Integer.parseInt(request.getParameter("deptId"));
		List<Department> lstDept = new ArrayList<Department>();
		
		
		DepartmentList lst =  restTemplate.getForObject("http://gateway-service/department/dept", DepartmentList.class);
		for(int i=0; i< lst.getDepartments().size();i++) {
			lstDept.add(lst.getDepartments().get(i));
		}
		EmployeeList empLst =  restTemplate.getForObject("http://gateway-service/department/emp/"+id, EmployeeList.class);
		List<Employee> listEmp = new ArrayList<Employee>();
		for(int i=0; i< empLst.getEmployees().size();i++) {
			listEmp.add(empLst.getEmployees().get(i));
		}

		logger.info("This is Employee list");
	
		
		PagedListHolder<Employee> pagedListHolder = new PagedListHolder<Employee>(listEmp);
		pagedListHolder.setPageSize(3);
		ModelAndView modelAndView = new ModelAndView("form");
		modelAndView.addObject("maxPages", pagedListHolder.getPageCount());
		System.out.println("Page value in list Emp  "+page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		modelAndView.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			modelAndView.addObject("empLst", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			modelAndView.addObject("empLst", pagedListHolder.getPageList());
		}
		httpSession.setAttribute("pageEmp", page);
		httpSession.setAttribute("empLst", listEmp);
		modelAndView.addObject("deptId", id);
		modelAndView.addObject("deptLst", lstDept);
		//modelAndView.addObject("empLst", listEmp);
		modelAndView.addObject("home", "homemp");
		modelAndView.addObject("check", "checklist");
		modelAndView.addObject("emplang", "lang");
		logger.warn("If Service shows some error...Please refresh the page");

		return modelAndView;

	}


	@GetMapping("/listDept")
	public ModelAndView listDepartment(HttpServletRequest request, @RequestParam(required = false)Integer page) {
		logger.info("This is Department list");
		logger.warn("If Service shows some error...Please refresh the page");
		List<Department> lstDept = new ArrayList<Department>();
		
		DepartmentList lst =  restTemplate.getForObject("http://gateway-service/department/dept", DepartmentList.class);
		System.out.println("In list dept");
		for(int i=0; i< lst.getDepartments().size();i++) {
			lstDept.add(lst.getDepartments().get(i));
		}
		ModelAndView modelAndView = new ModelAndView("form");
		
		
		PagedListHolder<Department> pagedListHolder = new PagedListHolder<Department>(lstDept);
		pagedListHolder.setPageSize(4);

		modelAndView.addObject("maxPages", pagedListHolder.getPageCount());

		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		modelAndView.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			modelAndView.addObject("deptList", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			modelAndView.addObject("deptList", pagedListHolder.getPageList());
		}
		//modelAndView.addObject("deptList", lstDept);
		modelAndView.addObject("deptlang", "language");
		HttpSession session = request.getSession();
		session.setAttribute("pageDept", page);
		session.setAttribute("deptList", lstDept);
		return modelAndView;

	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/newDept")
	public ModelAndView newDepartment(ModelAndView model, HttpServletRequest request) {
		logger.info(" create a field to add department ");
		String Register = "NewFormDept";
		HttpSession session1 = request.getSession();
		List<Department> lst = (List<Department>) session1.getAttribute("deptList");
		session1.setAttribute("deptList", lst);
		
		PagedListHolder<Department> pagedListHolder = new PagedListHolder<Department>(lst);
		pagedListHolder.setPageSize(4);

		model.addObject("maxPages", pagedListHolder.getPageCount());
		//Integer page =  (Integer) session1.getAttribute("pageDept");
		Integer page = pagedListHolder.getPageCount();
		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		model.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			model.addObject("deptList", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			model.addObject("deptList", pagedListHolder.getPageList());
		}
		session1.setAttribute("pageAdd", page);
		model.addObject("Register", Register);
		model.addObject("insertDept", "newDept");
		model.setViewName("form");
		Department department = new Department();
		model.addObject("department", department);
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/saveDept")
	public ModelAndView saveDepartment1(@ModelAttribute Department department,HttpServletRequest request, HttpServletResponse response) {
		logger.info("Save the department in DB");
		System.out.println("In before"+department.getDeptName());
		HttpSession session2 = request.getSession();
		ModelAndView model = new ModelAndView("form");
		Department department1 = new Department();
		department1.setDeptId(department.getDeptId());
		department1.setDeptName(department.getDeptName());
		restTemplate.postForObject("http://gateway-service/department/addDept", department, Department.class);
		logger.info("In save department");
		List<Department> lst = (List<Department>) session2.getAttribute("deptList");
		Integer page =  (Integer) session2.getAttribute("pageAdd");
		PagedListHolder<Department> pagedListHolder = new PagedListHolder<Department>(lst);
		int pagerowvalue =  pagedListHolder.getNrOfElements();
		System.out.println("Page in Dept "+page);
		  if((pagerowvalue %4) ==0) {
			  return new ModelAndView("redirect:/listDept?page="+(page+1));
		  }
		  else {
			 return new ModelAndView("redirect:/listDept?page="+page);
		  }
		
		
		
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/editDept")
	public ModelAndView editDepartment(HttpServletRequest request) {
		logger.info("Get the particular field to edit");
		int deptId = Integer.parseInt(request.getParameter("id"));
		HttpSession session2 = request.getSession();
		Department department = restTemplate.getForObject("http://gateway-service/department/listDept/"+deptId, Department.class);
		session2.setAttribute("department", department);
		List<Department> lst = (List<Department>) session2.getAttribute("deptList");
		session2.setAttribute("deptList", lst);
		ModelAndView model = new ModelAndView("form");
		
		PagedListHolder<Department> pagedListHolder = new PagedListHolder<Department>(lst);
		pagedListHolder.setPageSize(4);

		model.addObject("maxPages", pagedListHolder.getPageCount());
		Integer page =  (Integer) session2.getAttribute("pageDept");
		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		model.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			model.addObject("deptList", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			model.addObject("deptList", pagedListHolder.getPageList());
		}
		//model.addObject("deptList", lst);
		model.addObject("departId", deptId);
		return model;
	}
	
	@PostMapping(value = "/updateDept")
	public ModelAndView updateEmployee(HttpServletRequest request, @ModelAttribute Department department, HttpServletResponse response) {
		logger.info("Update the department in DB");
		HttpSession session2 = request.getSession();
		int deptId =  Integer.parseInt(request.getParameter("deptId"));

		  Department department1 = new Department();
		  department1.setDeptId(department.getDeptId());
		  department1.setDeptName(department.getDeptName());
		  restTemplate.put("http://gateway-service/department/updateDept/"+deptId, department);
		  Integer page =  (Integer) session2.getAttribute("pageDept");
		  if(page!=null) {
			  return new ModelAndView("redirect:/listDept?page="+page);
		  }
		return new ModelAndView("redirect:/listDept");

	}
	
	@GetMapping(value = "/deleteDept")
	public ModelAndView deleteDepartment(HttpServletRequest request) {
		logger.info("Deleted the department in DB");
		HttpSession session2 = request.getSession();
		int departId = Integer.parseInt(request.getParameter("id"));
		restTemplate.delete("http://gateway-service/department/deleteDept/"+departId);
		Integer page =  (Integer) session2.getAttribute("pageDept");
		  if(page!=null) {
			  return new ModelAndView("redirect:/listDept?page="+page);
		  }
		return new ModelAndView("redirect:/listDept");
	}




	 

}
