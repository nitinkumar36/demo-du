package com.deloitte.accelerator.jpa;

import com.deloitte.accelerator.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee create(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	public Employee update(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		List<Employee> result = new ArrayList<>();
		Iterable<Employee> iterable = this.employeeRepository.findAll();
		if(iterable!=null){
			iterable.forEach(result::add);
		}
		return result;
	}

	public List<Employee> getByFirstName(String firstName) {
		return this.employeeRepository.findByFirstName(firstName);
	}

	public Optional<Employee> getEmployee(Long id) {
		return this.employeeRepository.findById(id);
	}

    public void deleteEmployee(Long id) {
		boolean employee  = this.employeeRepository.existsById(id);
		if(!employee){
			throw new ResourceNotFoundException("Employee not present");
		}
		this.employeeRepository.deleteById(id);
    }
}
