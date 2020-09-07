package com.DeptEmpUI.model;

public class Employee {
	
	
	private int empId;
	private String empName;
	private int age;
	private int deptId;

	public Employee() {
		super();
	}
	

	public Employee(int empId, String empName, int age, int deptId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.age = age;
		this.deptId = deptId;
	}





	public Employee(String empName, int age, int deptId) {
		super();
		this.empName = empName;
		this.age = age;
		this.deptId = deptId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", age=" + age + ", deptId=" + deptId + "]";
	}

}
