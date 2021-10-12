package com.deloitte.accelerator.jpa;

import com.deloitte.accelerator.jpa.api.EmployeeRequest;
import com.deloitte.accelerator.jpa.api.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

	public EmployeeResponse fromEmployee(Employee employee) {
		return EmployeeResponse.builder()
				.id(employee.getId())
				.age(employee.getAge())
				.firstName(employee.getFirstName())
				.lastName(employee.getLastName())
				.city(employee.getCity())
				.build();
	}

	public Employee toEmployee(EmployeeRequest employeeRequest) {
		return Employee.builder()
				.age(employeeRequest.getAge())
				.city(employeeRequest.getCity())
				.firstName(employeeRequest.getFirstName())
				.lastName(employeeRequest.getLastName())
				.build();
	}

	public Employee toUpdateEmployee(EmployeeRequest employeeRequest, Employee currentEmployee) {
		currentEmployee.setAge(employeeRequest.getAge());
		currentEmployee.setCity(employeeRequest.getCity());
		currentEmployee.setFirstName(employeeRequest.getFirstName());
		currentEmployee.setLastName(employeeRequest.getLastName());
		return currentEmployee;
	}

	public List<EmployeeResponse> fromEmployees(List<Employee> employees) {
		return employees.stream().map(this::fromEmployee).collect(Collectors.toList());
	}

}
