package com.deloitte.accelerator.jpa;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.deloitte.accelerator.web.exception.ResourceNotFoundException;
import com.deloitte.accelerator.jpa.api.EmployeeRequest;
import com.deloitte.accelerator.jpa.api.EmployeeResponse;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Employee"})
@SwaggerDefinition(tags = {
		@Tag(name = "Employee")
})
@RestController
@RequestMapping(path = "/api/v1/employees")
@Slf4j
public class EmployeeController {
	private EmployeeService employeeService;
	private EmployeeMapper mapper;

	@Autowired
	public EmployeeController(EmployeeService employeeService, EmployeeMapper mapper) {
		this.employeeService = employeeService;
		this.mapper = mapper;
	}


	@ApiOperation(value = "Get Employee by ID", notes = "Returns an Employee for the given ID", response = EmployeeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200,
					message = "Employee present by id"),
			@ApiResponse(code = 404, message = "Employee by Id not present"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) {
		Optional<Employee> employee = this.employeeService.getEmployee(id);
		if (!employee.isPresent()) {
			throw new ResourceNotFoundException("Employee Not Found");
		}

		EmployeeResponse result = this.mapper.fromEmployee(employee.get());

		return ResponseEntity.ok(result);
	}


	@ApiOperation(value = "Get All Employees", notes = "Returns all employees")
	@ApiResponses(value = {
			@ApiResponse(code = 200,
					message = "Employee are returned"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@GetMapping
	public ResponseEntity<List<EmployeeResponse>> list() {
		log.info("get all employees called");
		List<Employee> allEmployees = this.employeeService.getAllEmployees();

		List<EmployeeResponse> allEmployeeResponses = this.mapper.fromEmployees(allEmployees);

		return ResponseEntity.ok(allEmployeeResponses);
	}


	@ApiOperation(value = "Create Employee", notes = "Create a Employee based on the given input")
	@ApiResponses(value = {
			@ApiResponse(code = 201,
					message = "Employee created"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@PostMapping
	public ResponseEntity<EmployeeResponse> create(@ApiParam(value = "Employee Request", required = true)  @Valid @RequestBody EmployeeRequest employeeRequest) {
		Employee employee = this.mapper.toEmployee(employeeRequest);
		Employee employee1 = this.employeeService.create(employee);

		EmployeeResponse response = this.mapper.fromEmployee(employee1);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update Employee", notes = "Update an employee for the given ID based on the given input", response = EmployeeResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200,
					message = "Employee updated"),
			@ApiResponse(code = 404, message = "Employee by Id not present"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponse> update(@PathVariable Long id, @ApiParam(value = "Employee Request", required = true) @RequestBody EmployeeRequest employeeRequest) {
		Optional<Employee> currentEmployee = employeeService.getEmployee(id);
		if (!currentEmployee.isPresent()) {
			throw new ResourceNotFoundException("Employee Not Found");
		}

	    Employee employeeUpdated = employeeService.update(mapper.toUpdateEmployee(employeeRequest, currentEmployee.get()));

		EmployeeResponse response = this.mapper.fromEmployee(employeeUpdated);

		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Delete Employee by ID", notes = "returns empty body if success")
	@ApiResponses(value = {
			@ApiResponse(code = 204,
					message = "Employee deleted by id"),
			@ApiResponse(code = 404, message = "Employee by Id not present"),
			@ApiResponse(code = 401,
					message = "Security Error, unauthenticated"),
			@ApiResponse(code = 403,
					message = "Security Error, Request lacks valid authentication credentials."),
			@ApiResponse(code = 500, message = "Server Error, Unknown Server Error."),
			@ApiResponse(code = 503,
					message = "Availability Error")})
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		this.employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
}
