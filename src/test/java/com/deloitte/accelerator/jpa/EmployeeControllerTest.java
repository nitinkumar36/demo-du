package com.deloitte.accelerator.jpa;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.deloitte.accelerator.jpa.api.EmployeeResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	EmployeeController employeeController;
	
	@MockBean
	EmployeeService employeeservice;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	EmployeeService empserv;
	EmployeeMapper empmap;

	@Before
	public void setup() {
		
		employeeController = new EmployeeController(empserv,empmap);
	}

	@Test
	public void findbyID() throws Exception {
		
		when(employeeservice.getEmployee(Mockito.anyLong())).thenReturn(Optional.empty());
		 mockMvc.perform(get("/api/v1/employees/{id}",1L)
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isNotFound());
	      verify(employeeservice,times(1)).getEmployee(Mockito.anyLong());
		
		
	}
	
}

